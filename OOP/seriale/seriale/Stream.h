#pragma once
#include "Production.h"

class Stream : public Production
{
private:
	time_t time;
public:
	Stream(string, string, float, time_t);
	time_t getTime();
	string stringify();
	~Stream();
};

