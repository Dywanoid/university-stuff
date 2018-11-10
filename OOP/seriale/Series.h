#pragma once
#include "Production.h"

class Series : public Production
{	
private:
	int seasons;
public:
	Series(string, string, float, int);
	string stringify();
	string getSpecial();
	string showFull();
	void setSeasons(int);
	~Series();
};

