#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <utility>
#include <vector>
#include <random>
#include <algorithm>
#include <chrono>


// PROBLEM 3
using namespace std;

// OPERATIONS SETTINGS
#define n 50
#define k (int)(n * 0.3)
#define population 50
#define operationTimeFrom 10
#define operationTimeTo 30
#define maintenanceTimeFrom (int)(operationTimeFrom / 2)
#define maintenanceTimeTo (int)(operationTimeTo / 2)

// PROBLEM SETTINGS
#define maxTimeReduction 0.75f
#define timeReductionStep 0.05f
#define numberOfIterations 200

// ANTS SETTINGS
#define numberOfAnts 150
#define antStepValue 20
#define evaporationRate 0.6f
#define positionMaxBonus 8.5f
#define positionStep 0.2f



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

vector<string> split(const string& s, char delimiter)
{
    vector<std::string> tokens;
    string token;
    istringstream tokenStream(s);
    while (getline(tokenStream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
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

template <class T>
vector<T> connectVectors(vector<T> A, vector<T> B){
    vector<T> C;
    C.reserve(A.size() + B.size());
    C.insert(C.end(), A.begin(), A.end());
    C.insert(C.end(), B.begin(), B.end());
    return C;
};

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

Solution getSolution(const Instance &mainInstance, vector<int> &order) {
    Instance instance = copyInstance(mainInstance);

    int currentTime = 0;
    int secondMachineTime = 0;
    int pickedID, pickedIndex;
    float timeBonus = 1;
    auto jobsCompleted = vector<int>();

    for(int id: order) {
        Operation *x = instance.jobs[id].first;
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
            Operation *f = instance.jobs[pickedID].first;
            Operation *s = instance.jobs[pickedID].second;
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
        Operation *f = instance.jobs[pickedID].first;
        Operation *s = instance.jobs[pickedID].second;
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
    for (int i = 0; i < limit; ++i) order.push_back(i);
    shuffle(order.begin(), order.end(), dre);

    return getSolution(instance, order);
};

bool sortSolutions(Solution a, Solution b) {
    return a.finishTime < b.finishTime;
}

bool sortJobsForSecondMachine(Job A, Job B) {
    return A.second->startTime < B.second->startTime;
}

vector<Solution> getRandomPopulation(const Instance &mainInstance, int numberOfSolutions) {
    auto populationVector = vector<Solution>();
    for (int i = 0; i < numberOfSolutions; ++i) populationVector.push_back(getRandomSolution(mainInstance));
    return populationVector;
};

void addSolutionToMatrix(Matrix &matrix, const Solution &solution, int position) {
    float multiplier = positionMaxBonus - position * positionStep;
    for (int i = 0; i < solution.jobs.size() - 1; ++i) {
        matrix.antsPaths[solution.order[i]][solution.order[i+1]] += antStepValue * multiplier < 1 ? 1: multiplier;
    }
}

void updateMatrix(Matrix &matrix, const vector<Solution> &solutions) {
    for (int i = 0; i < solutions.size(); ++i) {
        addSolutionToMatrix(matrix, solutions[i], i + 1);
    }
}

void evaporateMatrix(Matrix &matrix) {
    auto size = static_cast<int>(matrix.antsPaths.size());
    for (int x = 0; x < size; ++x) {
        for (int y = 0; y < size; ++y) {
            float val = matrix.antsPaths[x][y] * evaporationRate;
            matrix.antsPaths[x][y] = val < 1 ? ceil(val) :  val;
        }
    }
}

int findNextAntsPathIndex(const vector<float> &matrixValues, vector<int> used) {
    static random_device rd;
    static mt19937 mt(rd());
    auto values = vector<float>(matrixValues);
    for(int u: used) values[u] = 0;
    discrete_distribution<int> distribution (values.begin(), values.end());
    return distribution(mt);
};

Solution newAntPath(Matrix &matrix, const Instance &instance) {
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
    for (int i = 0; i < numOfAnts; ++i)
        solutions.push_back(newAntPath(matrix, instance));
    return solutions;
}

vector<Solution> choosePaths(vector<Solution> paths, int number ) {
    static default_random_engine dre = default_random_engine(
            static_cast<unsigned long>(std::chrono::system_clock::now().time_since_epoch().count()));

    sort(paths.begin(), paths.end(), sortSolutions);
    vector<Solution> output = vector<Solution>(paths.begin(), paths.begin() + (int) (number * 0.75));

    paths.erase(paths.begin(), paths.begin() + (int) (number * 0.75));
    shuffle(paths.begin(), paths.end(), dre);
    paths.erase(paths.begin() + (int) (number * 0.75), paths.end());
    auto picked = connectVectors(output, paths);
    sort(picked.begin(), picked.end(), sortSolutions);
    return picked;
}

void antColonyOptimization(const Instance &instance, int dimensions, int numOfSolutions, int iterations, int numOfAnts) {
    Matrix matrix = Matrix(dimensions);
    vector<Solution> randomSolutions = getRandomPopulation(instance, numOfSolutions);
    sort(randomSolutions.begin(), randomSolutions.end(), sortSolutions);
    updateMatrix(matrix, randomSolutions);
    int B1 = randomSolutions[getRandomInt(0, numOfSolutions - 1)].finishTime;

    printf("Best: %d\n", B1);
    vector<Solution> pathsOne = getAntPaths(matrix, instance, numOfAnts);
    vector<Solution> connected = connectVectors<Solution>(randomSolutions, pathsOne);
    vector<Solution> bestSolutions = choosePaths(connected, numOfSolutions);


    updateMatrix(matrix, bestSolutions);
    for (int iter = 0; iter < iterations; ++iter) {
        pathsOne = getAntPaths(matrix, instance, numOfAnts);
        connected = connectVectors(pathsOne, bestSolutions);
        bestSolutions = choosePaths(connected, numOfSolutions);
        updateMatrix(matrix, bestSolutions);
        evaporateMatrix(matrix);
    }
    sort(bestSolutions.begin(), bestSolutions.end(), sortSolutions);
    int B2 = bestSolutions[0].finishTime;
    printf("Best after: %d\n", B2);
    printf("Difference: %d, Gain: %.02f%%\n", B1-B2,(B1-B2)*100/(float)B1);

}

void saveInstance(const Instance &instance, int instanceNumber) {
    ofstream instanceFile;
    ostringstream  streamName;
    streamName << "instances/instance" << instanceNumber << ".txt";
    string var = streamName.str();
    instanceFile.open (var.c_str(), ios::trunc);
    unsigned long long int numOp = instance.jobs.size();
    instanceFile << "****" << instanceNumber << "****" <<endl;
    instanceFile << numOp << endl;
    for(auto job :instance.jobs) {
        instanceFile << job.first->timeToComplete << ";" << job.second->timeToComplete << ";" << "1;" << "2;" << endl;
    }
    int maintenanceIndex = 1;
    for(auto maintenance : instance.maintenances) {
        instanceFile << maintenanceIndex++ << ";" << "1;" << maintenance.duration << ";" << maintenance.startTime << endl;
    }
    instanceFile << "***EOF***";
    instanceFile.close();
}

Instance loadInstance(int instanceNumber) {
    ifstream instanceFile;
    string line, seg;
    ostringstream  streamName;
    streamName << "instances/instance" << instanceNumber << ".txt";
    string var = streamName.str();

    vector<Job> jobs = vector<Job>();
    int jobIndex = 1;
    vector<Maintenance> maintenances = vector<Maintenance>();

    instanceFile.open(var.c_str(), ios::out);
    getline(instanceFile, line); //first line (with instance number)
    getline(instanceFile, line); //getting number of jobs
    int numOfJobs = stoi(line);
    for (int i = 0; i < numOfJobs; ++i) {
        getline(instanceFile, line);
        auto pieces = split(line, ';');
        jobs.push_back(Job(jobIndex++, new Operation(stoi(pieces[0])), new Operation(stoi(pieces[1]))));
    }

    while(getline(instanceFile, line)) {
        auto pieces = split(line, ';');
        if(pieces.size() == 1) break;
        maintenances.push_back(Maintenance(stoi(pieces[3]), stoi(pieces[2]))); // NOLINT(modernize-use-emplace)
    }
    instanceFile.close();
    return Instance(jobs, maintenances);
};

void saveOutput(const Instance &instance, const Solution &bestSolution, int previousTime, int instanceNumber) {
    ofstream outputFile;
    ostringstream  streamName;
    streamName << "tests/instance" << instanceNumber << ".txt";
    string var = streamName.str();
    outputFile.open (var.c_str(), ios::trunc);
    outputFile << "****" << instanceNumber << "****" <<endl;
    outputFile << bestSolution.finishTime << ", " << previousTime << endl;
    string M1, M2;
    ostringstream  helperStream;

    int numOfIdlesM1 = 0,
            totalIdleTimeM1 = 0,
            numOfIdlesM2 = 0,
            totalIdleTimeM2 = 0;

    bool idle = false;
    bool done = false;
    int idleStartTime = 0;
    int currentTime = 0,
        maintenanceIndex = 1,
        idleIndex = 1;
    for(int index : bestSolution.order) { // first machine
        Job job = bestSolution.jobs[index];
        while(!done) {
            if (job.first->startTime == currentTime) {
                if (idle) {
                    helperStream << "idle" << idleIndex++ << "_M1, " <<
                                 idleStartTime << ", " << currentTime - idleStartTime << "; ";
                    M1 += helperStream.str();
                    helperStream.str("");
                    idle = false;
                    numOfIdlesM1++;
                    totalIdleTimeM1 += currentTime - idleStartTime;
                }
                helperStream << "op1_" << job.id << ", " <<
                             job.first->timeToComplete << ", " << job.first->finishTime - job.first->startTime << "; ";
                currentTime = job.first->finishTime;
                M1 += helperStream.str();
                helperStream.str("");
                break;
            }

            int idleTest = maintenanceIndex;
            for (auto maintenance : instance.maintenances) {
                if (maintenance.startTime == currentTime) {
                    if (idle) {
                        helperStream << "idle" << idleIndex++ << "_M1, " <<
                                     idleStartTime << ", " << currentTime - idleStartTime << "; ";
                        M1 += helperStream.str();
                        helperStream.str("");
                        idle = false;
                        numOfIdlesM1++;
                        totalIdleTimeM1 += currentTime - idleStartTime;
                    }
                    helperStream << "maint" << maintenanceIndex++ << "_M1, " <<
                                 maintenance.startTime << ", " << maintenance.duration << "; ";
                    M1 += helperStream.str();
                    helperStream.str("");
                    currentTime = maintenance.finishTime;
                    break;
                }
            }

            if(idleTest == maintenanceIndex) {
                idle = true;
                idleStartTime = currentTime;
            }
            currentTime++;
        }

    }
    vector<Job> secondMachine = vector<Job>(bestSolution.jobs);
    sort(secondMachine.begin(), secondMachine.end(), sortJobsForSecondMachine);
    for(Job job : secondMachine) {

    }
    outputFile << "M1: " << M1 << endl;
    outputFile << "M2: " << M2 << endl;
    outputFile << instance.maintenances.size() << ";" << endl;
    outputFile << "0;0" << endl;
    outputFile << numOfIdlesM1 << ";" << totalIdleTimeM1 << endl;
    outputFile << numOfIdlesM2 << ";" << totalIdleTimeM2 << endl;
    outputFile << "***EOF***";
    outputFile.close();
};

void testFunction() {};


int main() {
//    for(int i = 1; i <= 10; i++) {
//        printf("\n\nNr %d:\n", i);
//        Instance mainInstance = generateInstance(n);
//      antColonyOptimization(mainInstance, n, population, numberOfIterations, numberOfAnts);
//    }
    Instance mainInstance = generateInstance(n);
//    antColonyOptimization(mainInstance, n, population, numberOfIterations, numberOfAnts);
    saveInstance(mainInstance, 3);
    Instance testing = loadInstance(5);
    saveInstance(testing, 4);
    antColonyOptimization(testing, n, population, numberOfIterations, numberOfAnts);

    return 0;
}
