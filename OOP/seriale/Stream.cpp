#include "Stream.h"

Stream::Stream(string iTitle, string iGenre, float iScore, string iTime) : Production(iTitle, iGenre, iScore)
{
	time = iTime;
}

string Stream::getSpecial()
{
	return time;
}

string Stream::showFull()
{
	return Production::showFull() + " rozpocznie sie: " + time;
}

string Stream::stringify()
{
	string result = Production::stringify();
	result += getSpecial();
	return result;
}


Stream::~Stream()
{
}
