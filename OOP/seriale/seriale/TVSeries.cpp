#include "TVSeries.h"


TVSeries::TVSeries(string t="title", string g="genre", float s=0.0f)
{
	title = t;
	genre = g;
	score = s;
}

bool TVSeries::setTitle(string t)
{
	return false;
}

bool TVSeries::setGenre(string g)
{
	return false;
}


bool TVSeries::setScore(float s)
{
	return false;
}

string TVSeries::getTitle()
{
	return title;
}

string TVSeries::getGenre()
{
	return genre;
}

float TVSeries::getScore()
{
	return score;
}

TVSeries::~TVSeries()
{
}
