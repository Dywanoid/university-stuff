//#include "Production.h"
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

	void operator-=(const int idToRemove) {
		selected.erase(selected.begin() + idToRemove);
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
	for(auto &serial: seriale)
	for(unsigned int i = 0; i < selected.size(); i++){
		T temp = selected[i];
		cout << i << ". " <<temp.getTitle() << " z gatunku "<< temp.getGenre() <<" ma ocene: " << temp.getScore() << endl;
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