#include "Series.h"


Series::Series(string iTitle, string iGenre, float iScore, int iSeasons) : Production(iTitle, iGenre, iScore)
{
	seasons = iSeasons;
}

int Series::getSeasons()
{
	return seasons;
}

void Series::setSeasons(int s)
{
	seasons = s;
}

string Series::stringify()
{
	string result = Production::stringify();
	result += to_string(getSeasons());
	return result;
}


Series::~Series()
{
}
