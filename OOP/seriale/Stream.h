#pragma once
#include "Production.h"

class Stream : public Production
{
private:
	string time;
public:
	Stream(string, string, float, string);
	string getSpecial();
	string showFull();
	string stringify();
	~Stream();
};

