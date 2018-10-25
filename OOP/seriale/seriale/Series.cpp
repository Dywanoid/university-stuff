#include "Series.h"


Series::Series(string t="title", string g="genre", float s=0.0f)
{
	title = t;
	genre = g;
	score = s;
}

bool Series::setTitle(string t)
{
	return false;
}

bool Series::setGenre(string g)
{
	return false;
}


bool Series::setScore(float s)
{
	return false;
}


string Series::getTitle() const
{
	return title;
}

string Series::getGenre() const
{
	return genre;
}

float Series::getScore() const
{
	return score;
}

Series::~Series()
{
}
