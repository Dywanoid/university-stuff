#include "TVSeriesSet.cpp"
#include <iostream>


int main() {
	TVSeries serial = TVSeries(string("Pamietnik"), string("romans"), 8.0);

	cout << serial.getTitle() << endl;
	cout << serial.getGenre() << endl;
	cout << serial.getScore() << endl;

	system("pause");
	return 0;
}