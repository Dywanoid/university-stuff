#pragma once
#include "Pool.cpp"
#include "Series.h"
#include "Stream.h"
#include "Movie.h"

#include <fstream>
#include <sstream>

#define PATHS {"db/seriale.txt", "db/filmy.txt", "db/streamy.txt"}
#define SERIES 0
#define MOVIE 1
#define STREAM 2

class Database
{
private:
	Pool<Series> series;
	Pool<Stream> streams;
	Pool<Movie> movies;
public:
	Database();
	void add(string, int);
	void remove(unsigned int, int);
	void edit(int, int, string);
	int howMany(int);
	int countSeasons();
	int getBestPlace();
	int getWorstPlace();
	void show();
	void show(int);
	void show(int, int);
	void loadDatabases();
	~Database();
};

