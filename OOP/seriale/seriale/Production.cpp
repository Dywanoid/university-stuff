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

string Production::stringify()
{
	string result = getTitle() + getGenre() + to_string(getScore());
	return result;
}

Production::~Production()
{
}
