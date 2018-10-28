#pragma once
#include "Production.h"

class Movie : public Production
{
private:
	int imdbPlace;
public:
	Movie(string, string, float, int);
	float getImdbPlace();
	string stringify();
	~Movie();
};

