#pragma once
#include "Production.h"

class Movie : public Production
{
private:
	float IMDb;
public:
	Movie();
	~Movie();
};

