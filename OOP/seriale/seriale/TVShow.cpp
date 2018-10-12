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

bool TVShow::test()
{
	return false;
}

string TVShow::getTitle()
{
	return title;
}

string TVShow::getGenre()
{
	return genre;
}

float TVShow::getScore()
{
	return score;
}

TVShow::~TVShow()
{
}
