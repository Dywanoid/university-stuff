#include "TVShow.h"


TVShow::TVShow(string t="title", string g="genre", float s=0.0f)
{
	title = t;
	genre = g;
	score = s;
}

bool TVShow::setTitle(string t)
{
	return false;
}

bool TVShow::setGenre(string g)
{
	return false;
}


bool TVShow::setScore(float s)
{
	return false;
}


string TVShow::getTitle() const
{
	return title;
}

string TVShow::getGenre() const
{
	return genre;
}

float TVShow::getScore() const
{
	return score;
}

TVShow::~TVShow()
{
}
