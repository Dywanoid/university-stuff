#pragma once
#include "Database.h"

class Interface
{
public:
	Interface();
	static void showOptions();
	static void endOption();
	static void addProduction(Database&);
	static void removeProduction(Database&);
	static void editProduction(Database&);
	static void recommendations(Database&);
	static void statistics(Database&);
	template<class Type>
	static Type getInput();
	
	~Interface();
};

template<class Type>
Type Interface::getInput() {
	Type input;
	cin >> input;
	while (!cin) {
		cin.clear();
		cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
		cin >> input;
	}
	cin.ignore();
	return input;
}

