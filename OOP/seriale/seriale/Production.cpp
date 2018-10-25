#include "Production.h"



Production::Production()
{
}

bool Production::setTitle(string t)
{
	return false;
}

bool Production::setGenre(string g)
{
	return false;
}


bool Production::setScore(float s)
{
	return false;
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

Production::~Production()
{
}
