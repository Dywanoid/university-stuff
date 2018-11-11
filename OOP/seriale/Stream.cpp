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
	stringstream stream;
	stream << fixed << setprecision(1) << score;
	string scr = stream.str();
	return title + " z gatunku " + genre + " ma ocene: " + scr + " rozpocznie sie: " + time;
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
