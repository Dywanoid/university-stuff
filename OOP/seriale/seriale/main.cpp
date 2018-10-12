#include "TVShowSet.cpp"
#include <iostream>


int main() {
	//TVShow(string("Pamietnik"), string("romans"), 8.0);

	vector<TVShow> seriale;
	seriale.push_back(TVShow(string("Pamietnik"), string("romans"), 8.0f));
	seriale.push_back(TVShow(string("Star Wars"), string("sci-fi"), 10.0f));
	seriale.push_back(TVShow(string("XXX"), string("porno"), 6.9f));

	TVShowSet<TVShow> pula = TVShowSet<TVShow>(seriale);

	pula.showAll();
	system("pause");
	return 0;
}