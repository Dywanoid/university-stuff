#pragma once
#include <string>

using namespace std;

class Production
{
protected:
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
	virtual string getSpecial() const;
	string stringify();
	~Production();
};

