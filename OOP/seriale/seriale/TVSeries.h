#pragma once
#include <string>
using namespace std;

class TVSeries
{
private:
	string title, genre;
	float score;
public:
	TVSeries(string, string, float);
	bool setTitle(string);
	bool setGenre(string);
	bool setScore(float);
	string getTitle();
	string getGenre();
	float getScore();
	~TVSeries();
};

