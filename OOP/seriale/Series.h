#pragma once
#include "Production.h"

class Series : public Production
{	
private:
	int seasons;
public:
	Series(string, string, float, int);
	string stringify();
	int getSeasons();
	void setSeasons(int);
	~Series();
};

