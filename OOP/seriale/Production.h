#pragma once
#include <string>

using namespace std;

class Production
{
private:
	string title, genre;
	float score;
public:
	Production();
	Production(string, string, float);
	void setTitle(string);
	void setGenre(string);
	void setScore(float);
	string getTitle() const;
	string getGenre() const;
	float getScore() const;
	string stringify();
	~Production();
};

