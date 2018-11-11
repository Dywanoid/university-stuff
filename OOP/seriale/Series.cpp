#include "Series.h"


Series::Series(string iTitle, string iGenre, float iScore, int iSeasons) : Production(iTitle, iGenre, iScore)
{
	seasons = iSeasons;
}

void Series::setSeasons(int s)
{
	seasons = s;
}

string Series::stringify()
{
	string result = Production::stringify();
	result += getSpecial();
	return result;
}

string Series::getSpecial()
{
	return to_string(seasons);
}

string Series::showFull()
{
	stringstream stream;
	stream << fixed << setprecision(1) << score;
	string scr = stream.str();
	return title + " z gatunku " + genre + " ma ocene: " + scr + " i liczbe sezonow: " + to_string(seasons);
}


Series::~Series()
{
}
