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

// edit entry
template<class T>
void Pool<T>::edit(const T& obj, int which)
{
	selected[which - 1] = obj;
}

// shows every entry
template<class T>
void Pool<T>::showAll() 
{
	for(unsigned int i = 0; i < selected.size(); i++){
		cout << i + 1 << ". " << selected[i].showFull() << endl;
	}
}

// shows one entry by id = which - 1
template<class T>
void Pool<T>::showAll(int which) 
{
	cout << selected[which - 1].showFull() << endl;
}

// function to compare scores
template<class T>
bool Pool<T>::cmpScore(T a, T b)
{
	return a.getScore() > b.getScore();
}

// shows sorted entries
template<class T>
void Pool<T>::showSorted()
{
	vector<T> sorted = selected;
	sort(sorted.begin(), sorted.end(), cmpScore);

	for (unsigned int i = 0; i < selected.size(); i++) {
		cout << i + 1 << ". " << sorted[i].showFull() << endl;
	}
}

// how many entries
template<class T>
int Pool<T>::howMany()
{
	return selected.size();
}

// get all special fields (seasons, imdbplace, date)
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
