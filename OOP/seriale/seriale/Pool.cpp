//#include "Production.h"
#include <algorithm>
#include <vector>
#include <iostream>
using namespace std;


template <class T> class Pool
{
private:
	vector<T> selected;
public:	
	Pool();
	Pool(vector<T>);
	void showAll();
	bool isInPool(string);
	void operator+=(const T& obj) {
		selected.push_back(obj);
	}

	void operator-=(const T& obj) {
		selected.erase(remove(selected.begin(), selected.end(), obj), selected.end());
	}

	~Pool();
};

template<class T>
Pool<T>::Pool()
{
}

template<class T>
Pool<T>::Pool(vector<T> arr)
{
	selected = arr;
}


template<class T>
void Pool<T>::showAll()
{
	for (auto const serial : selected) {
		cout << serial.getTitle() << " ma ocene: " << serial.getScore() << endl;
	}

}

template<class T>
bool Pool<T>::isInPool(string)
{

	return false;
}

template<class T>
Pool<T>::~Pool()
{
}
