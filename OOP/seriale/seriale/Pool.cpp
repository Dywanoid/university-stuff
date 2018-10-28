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

	void operator-=(const string& titleToRemove) {
		//selected.erase(remove(selected.begin(), selected.end(), obj), selected.end());
		for (vector<T> iterator i = pool.begin(); i != pool.end(); i++) {
			if (strcmp(i.title, titleToRemove)) {
				pool.erase(remove(pool.begin(), pool.end(), i.title), pool.end())
			}
		}
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
