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
#define operationTimeFrom 5
#define operationTimeTo 20
#define maintenanceTimeFrom (int)(operationTimeFrom / 2)
#define maintenanceTimeTo (int)(operationTimeTo / 2)


int getRandomInt(int from, int to) {
    static random_device rd;
    static mt19937 mt(rd());

    uniform_int_distribution<int> random(from, to);
    return random(mt);
};

struct Operation {
    int id;
    int timeToComplete;
    int startTime;
    int finishTime;
    bool finished;
    Operation *otherOperation;

    Operation(int id, int ttc) : id(id),
                                 timeToComplete(ttc),
                                 startTime(-1),
                                 finishTime(-1),
                                 otherOperation(nullptr),
                                 finished(false) {}
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

    Solution(int finishT, vector<Job> jobs, vector<Maintenance> maintenances) : finishTime(finishT),
                                                                                jobs(std::move(jobs)),
                                                                                maintenances(std::move(
                                                                                        maintenances)) {}
};

vector<Job> generateJobs(int numberOfJobs) {
    auto jobs = vector<Job>();
    int r1, r2;
    for (int i = 1; i < numberOfJobs + 1; ++i) {
        r1 = getRandomInt(operationTimeFrom, operationTimeTo);
        r2 = getRandomInt(operationTimeFrom, operationTimeTo);
        jobs.push_back(Job(i, new Operation(i, r1), new Operation(i, r2)));
    }
    return jobs;
};

template <class T>
bool validateSpot(T vector, int startTime, int duration) {
    int finishTime = startTime + duration;
    for(auto o : vector) {
        if((o.startTime <= startTime && o.finishTime >= startTime) ||
           (o.startTime <= finishTime && o.finishTime >= finishTime) ||
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

Solution getRandomSolution(Instance instance, int numberOfJobs) {
    auto order = vector<int>();
    auto jobsCompleted = vector<int>();
    for (int i = 1; i < numberOfJobs + 1; ++i) order.push_back(i);
    shuffle(order.begin(), order.end(), default_random_engine(
            static_cast<unsigned long>(std::chrono::system_clock::now().time_since_epoch().count())));
    int currentTime = 0;
    float timeBonus = 1;
    for(int id: order) {
        Operation *x = instance.jobs[id - 1].first;
        auto duration = static_cast<int>(x->timeToComplete * timeBonus);

        while(!validateSpot<vector<Maintenance>>(instance.maintenances, currentTime, duration)){
            currentTime++;
            timeBonus = 1; // resetting timeBonus to 0 after maintenance
        };
        x->startTime = currentTime;
        x->finishTime = currentTime + duration;
        currentTime += duration;
    }

    for(int i: order) {
        Operation *o = instance.jobs[i-1].first;

        printf("id: %d sT: %d fT: %d dur: %d\n", o->id, o->startTime, o->finishTime, o->timeToComplete);
    }
    for(auto m: instance.maintenances) {
        printf("ST: %d FT: %d\n", m.startTime, m.finishTime);
    }

    return Solution(currentTime, instance.jobs, instance.maintenances);
};

int main() {
    vector<Job> jobs = generateJobs(n);

    Instance i = generateInstance(n);

    Solution s = getRandomSolution(i, n);

    return 0;
}