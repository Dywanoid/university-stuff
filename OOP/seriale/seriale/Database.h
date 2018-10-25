#pragma once
#include "Series.h"

class Database
{
private:
	Series db_series;
public:
	Database(string);
	~Database();
	string getTitles();
	int getTitleIndex(string);
};

