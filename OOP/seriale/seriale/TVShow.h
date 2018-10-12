#pragma once
#include <string>
#include <vector>

using namespace std;

class TVShow
{
private:
	string title, genre;
	float score;
public:
	TVShow(string, string, float);
	bool setTitle(string);
	bool setGenre(string);
	bool setScore(float);
	string getTitle() const;
	string getGenre() const;
	float getScore() const;
	~TVShow();
};

