#pragma once
#include <string>

class TVSeries
{
private:
	std::string title, genre;
	float score;
public:
	TVSeries(std::string, std::string, float);
	~TVSeries();
};

