#include "Production.h"



Production::Production()
{
}

Production::Production(string t, string g, float s)
{
	title = t;
	genre = g;
	score = s;
}

void Production::setTitle(string t)
{
	title = t;
}

void Production::setGenre(string g)
{
	genre = g;
}


void Production::setScore(float s)
{
	score = s;
}


string Production::getTitle() const
{
	return title;
}

string Production::getGenre() const
{
	return genre;
}

float Production::getScore() const
{
	return score;
}

string Production::getSpecial() const
{
	return string();
}

// return string to display with all Production class fields
string Production::showFull()
{
	stringstream stream;
	stream << fixed << setprecision(1) << score;
	string scr = stream.str();
	return title + " z gatunku " + genre + " ma ocene: " + scr;
}

string Production::stringify()
{
	string result = getTitle() + getGenre() + to_string(getScore());
	return result;
}

Production::~Production()
{
}
