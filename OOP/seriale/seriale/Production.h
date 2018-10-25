#pragma once
#include <string>
#include <vector>

using namespace std;

class Production
{
private:
	string title, genre;
	float score;
public:
	Production();
	bool setTitle(string);
	bool setGenre(string);
	bool setScore(float);
	string getTitle() const;
	string getGenre() const;
	float getScore() const;
	~Production();
};

