#include <iostream>
#include <utility>
#include <vector>
#include <random>
#include <algorithm>
#include <chrono>

// PROBLEM 3
using namespace std;

#define n 50
#define k (int)(n * 0.2)
#define population 100
#define operationTimeFrom 5
#define operationTimeTo 20
#define maintenanceTimeFrom (int)(operationTimeFrom / 2)
#define maintenanceTimeTo (int)(operationTimeTo / 2)


struct Operation {
    int timeToComplete;
    int startTime;
    int finishTime;
    Operation *otherOperation;

    explicit Operation(int ttc) : timeToComplete(ttc),
                         startTime(-1),
                         finishTime(-1),
                         otherOperation(nullptr) {}
};

struct Job {
    int id;
    Operation *first;
    Operation *second;

    Job(int jobID, Operation *one, Operation *two){
        id = jobID;
        one->otherOperation = two;
        two->otherOperation = one;
        first = one;
        second = two;
    };
};

struct Maintenance {
    int startTime;
    int finishTime;
    int duration;

    Maintenance(int sT, int dur) : startTime(sT), finishTime(sT+dur), duration(dur){};
};

struct Machine {
    vector<Maintenance> maintenances;
    vector<Job> jobs;
    bool finished;

    Machine(vector<Maintenance> maintenances, vector<Job> jobs, bool finished) : maintenances(std::move(maintenances)),
                                                                                 jobs(std::move(jobs)),
                                                                                 finished(finished) {}
};

struct Instance {
    vector<Job> jobs;
    vector<Maintenance> maintenances;

    Instance(vector<Job> jobs, vector<Maintenance> maintenances) : jobs(std::move(jobs)),
                                                                   maintenances(std::move(maintenances)) {}
};

struct Solution {
    int finishTime;
    vector<Job> jobs;
    vector<Maintenance> maintenances;
    vector<int> order;

    Solution(int finishTime, vector<Job> jobs, vector<Maintenance> maintenances, vector<int> order)
            : finishTime(finishTime),
            jobs(std::move(jobs)),
            maintenances(std::move(maintenances)),
            order(std::move(order)) {}
};

struct Matrix {
    vector<vector<float>> antsPaths;

    explicit Matrix(int dimensions) {
        for(int i = 0; i < dimensions; i++) antsPaths.emplace_back(dimensions, 0);
    }
};

int getRandomInt(int from, int to) {
    static random_device rd;
    static mt19937 mt(rd());

    uniform_int_distribution<int> random(from, to);
    return random(mt);
};

float getRandomFloat(float from, float to) {
    static random_device rd;
    static mt19937 mt(rd());

    uniform_real_distribution<float> random(from, to);
    return random(mt);
}

vector<Job> generateJobs(int numberOfJobs) {
    auto jobs = vector<Job>();
    int r1, r2;
    for (int i = 1; i < numberOfJobs + 1; ++i) {
        r1 = getRandomInt(operationTimeFrom, operationTimeTo);
        r2 = getRandomInt(operationTimeFrom, operationTimeTo);
        jobs.push_back(Job(i, new Operation(r1), new Operation(r2)));
    }
    return jobs;
};

template <class T>
bool validateSpot(T vector, int startTime, int duration) {
    int finishTime = startTime + duration;
    for(auto o : vector) {
        if((o.startTime < startTime && o.finishTime > startTime) ||
           (o.startTime < finishTime && o.finishTime > finishTime) ||
           (o.startTime >= startTime && o.finishTime <= finishTime))
        {return false;};
    }
    return true;
}

vector<Maintenance> generateMaintenance(int numberOfMaintenances, const vector<Job> &jobs) {
    auto maintenances = vector<Maintenance>();
    int operationsTotalTime = 0;
    int startTime, duration;
    for(auto job: jobs) operationsTotalTime += job.first -> timeToComplete;

    for (int i = 0; i < numberOfMaintenances; ++i) {
        do{
            duration = getRandomInt(maintenanceTimeFrom, maintenanceTimeTo);
            startTime = getRandomInt(0, operationsTotalTime);
        }while(!validateSpot<vector<Maintenance>>(maintenances, startTime, duration));

        maintenances.emplace_back(startTime, duration);
    }
    return maintenances;
};

Instance generateInstance(int numberOfJobs) {
    auto jobs = generateJobs(numberOfJobs);
    auto mains = generateMaintenance(k, jobs);

    return Instance(jobs, mains);
};

int pickOneJob(int size) {
    if(size > 0) return getRandomInt(0, size - 1);
    return -1;
}

Instance copyInstance(const Instance &instance) {
    auto jobs = vector<Job>();
    for(Job j : instance.jobs)
        jobs.emplace_back(j.id, new Operation(j.first->timeToComplete), new Operation(j.second->timeToComplete));
    return Instance(jobs, instance.maintenances);
};

void showInstance(const Instance &i) {
//    printf("\n");
}

//void deleteInstance(Instance &instance)

Solution getRandomSolution(Instance &mainInstance) {
    static default_random_engine dre = default_random_engine(
                         static_cast<unsigned long>(std::chrono::system_clock::now().time_since_epoch().count()));

    Instance instance = copyInstance(mainInstance);
//    showInstance(instance);

    auto order = vector<int>();
    auto jobsCompleted = vector<int>();
    auto limit = static_cast<int>(instance.jobs.size());
    for (int i = 1; i <= limit; ++i) order.push_back(i);
    shuffle(order.begin(), order.end(), dre);
    int currentTime = 0;
    int secondMachineTime = 0;
    int pickedID, pickedIndex;
    float timeBonus = 1;
    for(int id: order) {
        Operation *x = instance.jobs[id - 1].first;
        auto duration = static_cast<int>(x->timeToComplete * timeBonus);

        while(!validateSpot<vector<Maintenance>>(instance.maintenances, currentTime, duration)){
            currentTime++;
            timeBonus = 1; // resetting timeBonus to 0 after maintenance
            duration = x->timeToComplete;
        };

        x->startTime = currentTime;
        x->finishTime = currentTime + duration;
        currentTime += duration;
        if (timeBonus > 0.75) timeBonus -= 0.05;
        jobsCompleted.push_back(id);
        pickedIndex = pickOneJob(static_cast<int>(jobsCompleted.size()));
        if(pickedIndex != -1) {
            pickedID = jobsCompleted[pickedIndex];
            Operation *f = instance.jobs[pickedID - 1].first;
            Operation *s = instance.jobs[pickedID - 1].second;
            int fT = f->finishTime;
            if (fT >= secondMachineTime) {
                secondMachineTime = fT + s->timeToComplete;
            } else {
                secondMachineTime += s->timeToComplete;
            }
            s->finishTime = secondMachineTime;
            jobsCompleted.erase(jobsCompleted.begin() + pickedIndex);
        }
    }

    while(!jobsCompleted.empty()) { // TODO: make a function out of it (code duplication)
        pickedIndex = pickOneJob(static_cast<int>(jobsCompleted.size()));
        pickedID = jobsCompleted[pickedIndex];
        Operation *f = instance.jobs[pickedID - 1].first;
        Operation *s = instance.jobs[pickedID - 1].second;
        int fT = f->finishTime;
        if (fT >= secondMachineTime) {
            secondMachineTime = fT + s->timeToComplete;
        } else {
            secondMachineTime += s->timeToComplete;
        }
        s->finishTime = secondMachineTime;
        jobsCompleted.erase(jobsCompleted.begin() + pickedIndex);
    }

    return Solution(secondMachineTime > currentTime ? secondMachineTime : currentTime,
                    instance.jobs,
                    instance.maintenances,
                    order);
};

vector<Solution> getPopulation(Instance &mainInstance, int numberOfSolutions) {
    auto populationVector = vector<Solution>();
    for (int i = 0; i < numberOfSolutions; ++i) populationVector.push_back(getRandomSolution(mainInstance));
    return populationVector;
};

void populateMatrix(Matrix &matrix, const vector<Solution> &solutions) {
    for(Solution solution : solutions) {
        for (int i = 0; i < solution.jobs.size() - 1; ++i) {
            matrix.antsPaths[solution.order[i] - 1][solution.order[i+1] - 1] += 1;
        }
    }
}

int findNextAntsPathIndex(vector<float> values, vector<int> used) {
    static random_device rd;
    static mt19937 mt(rd());
    for(int u: used) values[u] = 0;
    discrete_distribution<int> distribution (values.begin(), values.end());
    return distribution(mt);
};

int main() {
    Instance mainInstance = generateInstance(n);

    vector<Solution> p = getPopulation(mainInstance, population);

    auto matrix = Matrix(n);
    populateMatrix(matrix, p);
    vector<int> used;
    int index = getRandomInt(0, n-1);
    for(int i = 0; i < n; i++) {
        used.push_back(index);
        index = findNextAntsPathIndex(matrix.antsPaths[index], used);
//        printf("%d\n", index);
    }
    sort(used.begin(), used.end());
    for(auto u: used) printf("%d\n", u);
    return 0;
}
