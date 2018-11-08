#include "Stream.h"



Stream::Stream(string iTitle, string iGenre, float iScore, string iTime) : Production(iTitle, iGenre, iScore)
{
	time = iTime;
}

string Stream::getTime()
{
	return time;
}

string Stream::stringify()
{
	string result = Production::stringify();
	result += getTime();
	return result;
}


Stream::~Stream()
{
}
