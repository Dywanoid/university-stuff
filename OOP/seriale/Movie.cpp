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
	stringstream stream;
	stream << fixed << setprecision(1) << score;
	string scr = stream.str();
	return title + " z gatunku " + genre + " ma ocene: " + scr + " a jego pozycja w rankingu IMDb to: " + to_string(imdbPlace);
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
