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
#define n {50, 80, 100, 120}
#define kRate {0.3f, 0.4f, 0.5f, 0.6f}
#define population {50, 100}
#define operationTimeFrom 15
#define operationTimeTo 60
#define maintenanceTimeFrom (int)(operationTimeFrom / 2)
#define maintenanceTimeTo (int)(operationTimeTo / 2)

// PROBLEM SETTINGS
#define maxTimeReduction 0.75f
#define timeReductionStep 0.05f
#define numberOfIterations {50, 150}

// ANTS SETTINGS
#define numberOfAnts {50, 150, 200}
#define antStepValue {10, 20}
#define evaporationRate {0.5f, 0.6f, 0.7f, 0.8f}
#define positionMaxBonus 8.5f
#define positionStep 0.2f

struct Operation {
    int timeToComplete;
    int startTime;
    int finishTime;

    Operation() {
        timeToComplete = -1;
        startTime = -1;
        finishTime = -1;
    }
    explicit Operation(int ttc) : timeToComplete(ttc),
                         startTime(-1),
                         finishTime(-1){}
};

struct Job {
    int id;
    Operation first;
    Operation second;

    Job(int jobID, Operation one, Operation two){
        id = jobID;
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
    int previousTime = -1;

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
        jobs.emplace_back(i, Operation(op1), Operation(op2));
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
    for(auto job: jobs) operationsTotalTime += job.first.timeToComplete;

    for (int i = 0; i < numberOfMaintenances; ++i) {
        do{
            duration = getRandomInt(maintenanceTimeFrom, maintenanceTimeTo);
            startTime = getRandomInt(0, operationsTotalTime);
        }while(!validateSpot<vector<Maintenance>>(maintenances, startTime, duration));

        maintenances.emplace_back(startTime, duration);
    }
    return maintenances;
};

Instance generateInstance(int numberOfJobs, int numberOfMaintenances) {
    auto jobs = generateJobs(numberOfJobs);
    auto mains = generateMaintenance(numberOfMaintenances, jobs);

    return Instance(jobs, mains);
};

int pickOneJob(int size) {
    if(size > 0) return getRandomInt(0, size - 1);
    return -1;
}

Instance copyInstance(const Instance &instance) {
    auto jobs = vector<Job>();
    for(Job j : instance.jobs)
        jobs.emplace_back(j.id, Operation(j.first.timeToComplete), Operation(j.second.timeToComplete));
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
        auto duration = static_cast<int>(ceil(instance.jobs[id].first.timeToComplete * timeBonus));

        while(!validateSpot<vector<Maintenance>>(instance.maintenances, currentTime, duration)){
            currentTime++;
            timeBonus = 1; // resetting timeBonus to 0 after maintenance
            duration = instance.jobs[id].first.timeToComplete;
        };

        instance.jobs[id].first.startTime = currentTime;
        instance.jobs[id].first.finishTime = currentTime + duration;
        currentTime += duration;
        if (timeBonus > maxTimeReduction) timeBonus -= timeReductionStep;
        jobsCompleted.push_back(id);
        pickedIndex = pickOneJob(static_cast<int>(jobsCompleted.size()));
        if(pickedIndex != -1) {
            pickedID = jobsCompleted[pickedIndex];
            int fT = instance.jobs[pickedID].first.finishTime;
            if (fT >= secondMachineTime) {
                secondMachineTime = fT + instance.jobs[pickedID].second.timeToComplete;
            } else {
                secondMachineTime += instance.jobs[pickedID].second.timeToComplete;
            }
            instance.jobs[pickedID].second.startTime = secondMachineTime - instance.jobs[pickedID].second.timeToComplete;
            instance.jobs[pickedID].second.finishTime = secondMachineTime;
            jobsCompleted.erase(jobsCompleted.begin() + pickedIndex);
        }
    }

    while(!jobsCompleted.empty()) { // TODO: make a function out of it (code duplication)
        pickedIndex = pickOneJob(static_cast<int>(jobsCompleted.size()));
        pickedID = jobsCompleted[pickedIndex];
        int fT = instance.jobs[pickedID].first.finishTime;
        if (fT >= secondMachineTime) {
            secondMachineTime = fT + instance.jobs[pickedID].second.timeToComplete;
        } else {
            secondMachineTime += instance.jobs[pickedID].second.timeToComplete;
        }
        instance.jobs[pickedID].second.startTime = secondMachineTime - instance.jobs[pickedID].second.timeToComplete;
        instance.jobs[pickedID].second.finishTime = secondMachineTime;
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
    return A.second.startTime < B.second.startTime;
}

bool sortJobsForFirstMachine(Job A, Job B) {
    return A.first.startTime < B.first.startTime;
}

bool sortMaintenances(Maintenance A, Maintenance B) {
    return A.startTime < B.startTime;
}

vector<Solution> getRandomPopulation(const Instance &mainInstance, int numberOfSolutions) {
    auto populationVector = vector<Solution>();
    for (int i = 0; i < numberOfSolutions; ++i) populationVector.push_back(getRandomSolution(mainInstance));
    return populationVector;
};

void addSolutionToMatrix(Matrix &matrix, const Solution &solution, int position, int antStep) {
    float multiplier = positionMaxBonus - position * positionStep;
    for (int i = 0; i < solution.jobs.size() - 1; ++i) {
        matrix.antsPaths[solution.order[i]][solution.order[i+1]] += antStep * multiplier < 1 ? 1: multiplier;
    }
}

void updateMatrix(Matrix &matrix, const vector<Solution> &solutions, int antStep) {
    for (int i = 0; i < solutions.size(); ++i) {
        addSolutionToMatrix(matrix, solutions[i], i + 1, antStep);
    }
}

void evaporateMatrix(Matrix &matrix, float evaporation) {
    auto size = static_cast<int>(matrix.antsPaths.size());
    for (int x = 0; x < size; ++x) {
        for (int y = 0; y < size; ++y) {
            float val = matrix.antsPaths[x][y] * evaporation;
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

Solution newAntPath(Matrix &matrix, const Instance &instance, int N) {
    vector<int> used;
    int index = getRandomInt(0, N - 1);
    for(int i = 0; i < N; i++) {
        used.push_back(index);
        index = findNextAntsPathIndex(matrix.antsPaths[index], used);
    }
    return getSolution(instance, used);
}

vector<Solution> getAntPaths(Matrix &matrix, const Instance &instance, int numOfAnts, int N){
    auto solutions = vector<Solution>();
    for (int i = 0; i < numOfAnts; ++i)
        solutions.push_back(newAntPath(matrix, instance, N));
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

Solution antColonyOptimization(const Instance &instance,
                                int dimensions,
                                int numOfSolutions,
                                int iterations,
                                int numOfAnts,
                                int antStep,
                                float evaporation) {

    Matrix matrix = Matrix(dimensions);
    vector<Solution> randomSolutions = getRandomPopulation(instance, numOfSolutions);
    sort(randomSolutions.begin(), randomSolutions.end(), sortSolutions);
    updateMatrix(matrix, randomSolutions, antStep);
    int B1 = randomSolutions[getRandomInt(0, numOfSolutions - 1)].finishTime;

    vector<Solution> pathsOne = getAntPaths(matrix, instance, numOfAnts, dimensions);
    vector<Solution> connected = connectVectors<Solution>(randomSolutions, pathsOne);
    vector<Solution> bestSolutions = choosePaths(connected, numOfSolutions);


    updateMatrix(matrix, bestSolutions, antStep);
    for (int iter = 0; iter < iterations; ++iter) {
        pathsOne = getAntPaths(matrix, instance, numOfAnts, dimensions);
        connected = connectVectors(pathsOne, bestSolutions);
        bestSolutions = choosePaths(connected, numOfSolutions);
        updateMatrix(matrix, bestSolutions, antStep);
        evaporateMatrix(matrix, evaporation);
    }
    sort(bestSolutions.begin(), bestSolutions.end(), sortSolutions);
    int B2 = bestSolutions[0].finishTime;
    printf("Best before/after: %d/%d Difference: %d, Gain: %.02f%%\n", B1, B2, B1-B2,(B1-B2)*100/(float)B1);
    bestSolutions[0].previousTime = B1;
    return bestSolutions[0];
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
        instanceFile << job.first.timeToComplete << ";" << job.second.timeToComplete << ";" << "1;" << "2;" << endl;
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
        jobs.push_back(Job(jobIndex++, Operation(stoi(pieces[0])), Operation(stoi(pieces[1])))); // NOLINT(modernize-use-emplace)
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
    streamName << "tests/test" << instanceNumber<< ".txt";
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
    vector<Job> firstMachine = vector<Job>(bestSolution.jobs);
    vector<Maintenance> firstMachineMaintenances = vector<Maintenance>(instance.maintenances);
    sort(firstMachine.begin(), firstMachine.end(), sortJobsForFirstMachine);
    sort(firstMachineMaintenances.begin(), firstMachineMaintenances.end(), sortMaintenances);
    for(Job job : firstMachine) { // first machine
        while (!done) {
            if (job.first.startTime == currentTime) {
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
                             currentTime << ", " << job.first.timeToComplete <<
                             ", " << currentTime + job.first.finishTime - job.first.startTime << "; ";
                currentTime += job.first.finishTime - job.first.startTime;
                M1 += helperStream.str();
                helperStream.str("");
                break;
            }

            int maintenanceChange = maintenanceIndex;

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
                    currentTime += maintenance.duration;
                    break;
                }
            }
            if (maintenanceChange != maintenanceIndex) {
                break;
            }
            if (maintenanceChange == maintenanceIndex && !idle) {
                idle = true;
                idleStartTime = currentTime;
            }
            currentTime++;
        }
    }
    vector<Job> secondMachine = vector<Job>(bestSolution.jobs);
    sort(secondMachine.begin(), secondMachine.end(), sortJobsForSecondMachine);
    currentTime = 0;
    idleIndex = 1;
    idle = false;
    done = false;
    for(Job job : secondMachine) {
        while(!done) {
            if (job.second.startTime == currentTime) {
                if(idle) {
                    helperStream << "idle" << idleIndex++ << "_M2, " <<
                                 idleStartTime << ", " << currentTime - idleStartTime << "; ";
                    M2 += helperStream.str();
                    helperStream.str("");
                    idle = false;
                    numOfIdlesM2++;
                    totalIdleTimeM2 += currentTime - idleStartTime;
                }

                helperStream << "op1_" << job.id << ", " <<
                             currentTime << ", " << job.second.timeToComplete <<
                             ", " <<  currentTime + job.second.finishTime - job.second.startTime << "; ";
                currentTime += job.second.finishTime - job.second.startTime;
                M2 += helperStream.str();
                helperStream.str("");
                break;
            }
            if(!idle) idleStartTime = currentTime;
            idle = true;
            currentTime++;
        }
    }
    int maintenancesTotalTime = 0;
    for(Maintenance maintenance : instance.maintenances) {
        maintenancesTotalTime += maintenance.duration;
    }
    outputFile << "M1: " << M1 << endl;
    outputFile << "M2: " << M2 << endl;
    outputFile << instance.maintenances.size() << ", " << maintenancesTotalTime << endl;
    outputFile << "0, 0" << endl;
    outputFile << numOfIdlesM1 << ", " << totalIdleTimeM1 << endl;
    outputFile << numOfIdlesM2 << ", " << totalIdleTimeM2 << endl;
    outputFile << "***EOF***";
    outputFile.close();
};

void testFunction(int ID, int N, int K, int POPULATION, int NUMBEROFITERATIONS, int NUMBEROFANTS, int ANTSTEPVALUE, float EVAPORATIONRATE) {
    printf("Nr %d\n", ID);
    printf("N: %d K: %d POPULATION: %d ITERATIONS: %d ANTS: %d STEP: %d EVAPORATION: %.2f\n", N, K, POPULATION, NUMBEROFITERATIONS, NUMBEROFANTS, ANTSTEPVALUE, EVAPORATIONRATE);
    Instance mainInstance = generateInstance(N, K);
    saveInstance(mainInstance, ID);
    Solution bestSolution = antColonyOptimization(mainInstance, N, POPULATION, NUMBEROFITERATIONS, NUMBEROFANTS, ANTSTEPVALUE, EVAPORATIONRATE);
    saveOutput(mainInstance, bestSolution, bestSolution.previousTime, ID);
};

int main() {

    int ns[] = n;
    vector<int> nsVector (ns, ns + sizeof(ns) / sizeof(int) );

    float kRates[] = kRate;
    vector<float> kRatesVector (kRates, kRates + sizeof(kRates) / sizeof(float) );

    int populations[] = population;
    vector<int> populationsVector (populations, populations + sizeof(populations) / sizeof(int) );

    int numberOfAntsArray[] = numberOfAnts;
    vector<int> numberOfAntsVector (numberOfAntsArray, numberOfAntsArray + sizeof(numberOfAntsArray) / sizeof(int) );

    int numsOfIters[] = numberOfIterations;
    vector<int> numsOfItersVector (numsOfIters, numsOfIters + sizeof(numsOfIters) / sizeof(int) );

    int antStepValues[] = antStepValue;
    vector<int> antStepValuesVector (antStepValues, antStepValues + sizeof(antStepValues) / sizeof(int) );

    float evaporationRates[] = evaporationRate;
    vector<float> evaporationRatesVector (evaporationRates, evaporationRates + sizeof(evaporationRates) / sizeof(float));



    int ID = 1;
    int POPULATION = 100;
    int NUMBEROFITERATIONS = 100;
    int NUMBEROFANTS = 100;
    int ANTSTEPVALUE = 15;
    float EVAPORATIONRATE = 0.55;
    for(int N : nsVector) {
        printf("\n\nTest N dla: %d\n", N);
        for(float KRATE : kRatesVector) {
            int K = (int)(KRATE * N);
            printf("\nTest K dla: %d\n\n", K);
            printf("Population: \n");
            for(int POP : populationsVector) {
                testFunction(ID++, N, K, POP, NUMBEROFITERATIONS, NUMBEROFANTS, ANTSTEPVALUE, EVAPORATIONRATE);
            }

            printf("Iterations: \n");
            for(int ITER : numsOfItersVector) {
                testFunction(ID++, N, K, POPULATION, ITER, NUMBEROFANTS, ANTSTEPVALUE, EVAPORATIONRATE);

            }

            printf("Ants: \n");
            for(int ANTS : numberOfAntsVector) {
                testFunction(ID++, N, K, POPULATION, NUMBEROFITERATIONS, ANTS, ANTSTEPVALUE, EVAPORATIONRATE);

            }

            printf("Step: \n");
            for(int STEP : antStepValuesVector) {
                testFunction(ID++, N, K, POPULATION, NUMBEROFITERATIONS, NUMBEROFANTS, STEP, EVAPORATIONRATE);

            }

            printf("Evaporation: \n");
            for(float EVAPORATION : evaporationRatesVector) {
                testFunction(ID++, N, K, POPULATION, NUMBEROFITERATIONS, NUMBEROFANTS, ANTSTEPVALUE, EVAPORATION);

            }
        }
    }

    return 0;
}
