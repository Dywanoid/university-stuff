#pragma once
#include "Production.h"

class Movie : public Production
{
private:
	int imdbPlace;
public:
	Movie(string, string, float, int);
	int getImdbPlace();
	string stringify();
	~Movie();
};

