#include "Movie.h"



Movie::Movie(string iTitle, string iGenre, float iScore, int iImdbPlace) : Production(iTitle, iGenre, iScore)
{
	imdbPlace = iImdbPlace;
}

string Movie::getSpecial()
{
	return to_string(imdbPlace);
}

string Movie::showFull()
{
	return title + " z gatunku " + genre + " ma ocene: " + to_string(score) + " a jego pozycja w rankingu IMDb to: " + to_string(imdbPlace);
}


string Movie::stringify()
{
	string result = Production::stringify();
	result += getSpecial();
	return result;
}


Movie::~Movie()
{
}
