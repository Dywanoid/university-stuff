#include "Stream.h"



Stream::Stream(string iTitle, string iGenre, float iScore, time_t iTime) : Production(iTitle, iGenre, iScore)
{
	time = iTime;
}

time_t Stream::getTime()
{
	return time;
}

string Stream::stringify()
{
	string result = Production::stringify();
	result += to_string(getTime());
	return result;
}


Stream::~Stream()
{
}
