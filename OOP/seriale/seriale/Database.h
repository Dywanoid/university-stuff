#pragma once
#include "Pool.cpp"
#include "Series.h"
#include "Stream.h"
#include "Movie.h"
#include <fstream>
#include <sstream>

class Database
{
private:
	Pool<Series> series;
	Pool<Stream> streams;
	Pool<Movie> movies;
public:
	Database();
	void saveDatabases();
	void show();
	void loadDatabases();
	~Database();
};

