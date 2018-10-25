#include "Pool.cpp"
#include "Series.h"
#include <iostream>


int main() {
	vector<Series> seriale;
	seriale.push_back(Series(string("Pamietnik"), string("romans"), 8.0f));
	seriale.push_back(Series(string("Star Wars"), string("sci-fi"), 10.0f));
	seriale.push_back(Series(string("XXX"), string("porno"), 6.9f));

	Pool<Series> pula = Pool<Series>(seriale);

	pula.showAll();
	system("pause");
	return 0;
}