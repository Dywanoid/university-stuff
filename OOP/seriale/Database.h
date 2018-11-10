#pragma once
#include "Pool.cpp"
#include "Series.h"
#include "Stream.h"
#include "Movie.h"

#include <fstream>
#include <sstream>

#define PATHS {"db/seriale.txt", "db/filmy.txt", "db/streamy.txt"}

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
	void show();
	void show(int);
	void loadDatabases();
	~Database();
};

