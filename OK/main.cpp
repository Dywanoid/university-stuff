#include <iostream>
#include <utility>
#include <vector>
#include <random>
#include <algorithm>
#include <chrono>

// PROBLEM 3
using namespace std;

// OPERATIONS SETTINGS
#define n 50
#define k (int)(n * 0.2)
#define population 100
#define operationTimeFrom 5
#define operationTimeTo 20
#define maintenanceTimeFrom (int)(operationTimeFrom / 2)
#define maintenanceTimeTo (int)(operationTimeTo / 2)

// PROBLEM SETTINGS
#define maxTimeReduction 0.75
#define timeReductionStep 0.05
#define numberOfIterations 100

// ANTS SETTINGS
#define numberOfAnts population
#define antStepValue 1
#define evaporationRate 0.9

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
        for(int i = 0; i < dimensions; i++) antsPaths.emplace_back(dimensions, 1);
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
    int op1, op2;
    for (int i = 1; i < numberOfJobs + 1; ++i) {
        op1 = getRandomInt(operationTimeFrom, operationTimeTo);
        op2 = getRandomInt(operationTimeFrom, operationTimeTo);
        jobs.push_back(Job(i, new Operation(op1), new Operation(op2)));
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

Solution getSolution(const Instance &instance, vector<int> &order) {
    int currentTime = 0;
    int secondMachineTime = 0;
    int pickedID, pickedIndex;
    float timeBonus = 1;
    auto jobsCompleted = vector<int>();

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
        if (timeBonus > maxTimeReduction) timeBonus -= timeReductionStep;
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
}

Solution getRandomSolution(const Instance &instance) {
    static default_random_engine dre = default_random_engine(
                         static_cast<unsigned long>(std::chrono::system_clock::now().time_since_epoch().count()));

    Instance copiedInstance = copyInstance(instance);

    auto order = vector<int>();
    auto limit = static_cast<int>(copiedInstance.jobs.size());
    for (int i = 1; i <= limit; ++i) order.push_back(i);
    shuffle(order.begin(), order.end(), dre);

    return getSolution(instance, order);
};

vector<Solution> getRandomPopulation(const Instance &mainInstance, int numberOfSolutions) {
    auto populationVector = vector<Solution>();
    for (int i = 0; i < numberOfSolutions; ++i) populationVector.push_back(getRandomSolution(mainInstance));
    return populationVector;
};

void updateMatrix(Matrix &matrix, const Solution &solution) {
    for (int i = 0; i < solution.jobs.size() - 1; ++i) {
        matrix.antsPaths[solution.order[i] - 1][solution.order[i+1] - 1] += antStepValue;
    }
}

void populateMatrix(Matrix &matrix, const vector<Solution> &solutions) {
    for(const Solution &solution : solutions)
        updateMatrix(matrix, solution);
}

int findNextAntsPathIndex(const vector<float> &matrixValues, vector<int> used) {
    static random_device rd;
    static mt19937 mt(rd());
    auto values = vector<float>(matrixValues);
    for(int u: used) values[u] = 0;
    discrete_distribution<int> distribution (values.begin(), values.end());
    return distribution(mt);
};


Solution newAntPath(Matrix &matrix, const Instance &instance) { // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    vector<int> used;
    int index = getRandomInt(0, n-1);
    for(int i = 0; i < n; i++) {
        used.push_back(index);
        index = findNextAntsPathIndex(matrix.antsPaths[index], used);
    }
    return getSolution(instance, used);
}

vector<Solution> getAntPaths(Matrix &matrix, const Instance &instance, int numOfAnts){
    auto solutions = vector<Solution>();
    for (int i = 0; i < numOfAnts; ++i) {
        printf("%d\n", i);
        auto s = newAntPath(matrix, instance);
        printf("FC: %d\n", s.finishTime);
        solutions.push_back(s);
    }
    return solutions;
}

bool sortSolutions(Solution a, Solution b) {
    return a.finishTime > b.finishTime;
}

vector<Solution> choosePaths(vector<Solution> &paths ) {
    sort(paths.begin(), paths.end(), sortSolutions);
    printf("Funkcja celu: \n");
    for(const auto &path: paths) printf("%d\n", path.finishTime);
    return paths;
}

int main() {
    random_device rd;
    mt19937 mt(rd());
    auto values = vector<float>(5, 1);
    discrete_distribution<int> distribution (values.begin(), values.end());
    printf("test: %d\n", distribution(mt));
    printf("test: %d\n", distribution(mt));
    printf("test: %d\n", distribution(mt));


    Instance mainInstance = generateInstance(n);

    vector<Solution> p = getRandomPopulation(mainInstance, population);

    auto matrix = Matrix(n);
    populateMatrix(matrix, p);

    auto paths = getAntPaths(matrix, mainInstance, numberOfAnts);
    choosePaths(paths);

    return 0;
}
