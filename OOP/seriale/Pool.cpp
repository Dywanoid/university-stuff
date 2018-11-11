#include <vector>
#include <iostream>
#include <algorithm> 
#include "Series.h"
#include "Stream.h"
#include "Movie.h"

using namespace std;


template <class T> class Pool
{
private:
	vector<T> selected;
public:	
	Pool();
	Pool(vector<T>);
	void edit(const T&, int);
	void showAll();
	void showAll(int);
	static bool cmpScore(T, T);
	void showSorted();
	int howMany();
	vector<string> getAllSpecials();
	void operator+=(const T& obj) {
		selected.push_back(obj);
	}
	void operator-=(const unsigned int idToRemove) {
		if (idToRemove - 1 >= selected.size()) throw string("Usuwanie poza podanym zbiorem!");
		selected.erase(selected.begin() + idToRemove - 1);
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
void Pool<T>::edit(const T& obj, int which)
{
	selected[which - 1] = obj;
}

template<class T>
void Pool<T>::showAll()
{
	for(unsigned int i = 0; i < selected.size(); i++){
		cout << i + 1 << ". " << selected[i].showFull() << endl;
	}
}

template<class T>
void Pool<T>::showAll(int which)
{
	cout << selected[which - 1].showFull() << endl;
}

template<class T>
bool Pool<T>::cmpScore(T a, T b)
{
	return a.getScore() > b.getScore();
}

template<class T>
void Pool<T>::showSorted()
{
	vector<T> sorted = selected;
	sort(sorted.begin(), sorted.end(), cmpScore);

	for (unsigned int i = 0; i < selected.size(); i++) {
		cout << i + 1 << ". " << sorted[i].showFull() << endl;
	}
}

template<class T>
int Pool<T>::howMany()
{
	return selected.size();
}

template<class T>
vector<string> Pool<T>::getAllSpecials()
{
	vector<string> results;
	for (auto &select : selected) {
		results.push_back(select.getSpecial());
	}
	return results;
}

template<class T>
Pool<T>::~Pool()
{
}
