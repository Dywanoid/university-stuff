#include "Movie.h"



Movie::Movie(string iTitle, string iGenre, float iScore, int iImdbPlace) : Production(iTitle, iGenre, iScore)
{
	imdbPlace = iImdbPlace;
}

float Movie::getImdbPlace()
{
	return imdbPlace;
}

string Movie::stringify()
{
	string result = Production::stringify();
	result += to_string(getImdbPlace());
	return result;
}


Movie::~Movie()
{
}
