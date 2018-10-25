#include "Production.h"
#include <algorithm>
#include <vector>

template <class T> class Pool
{
private:
	vector<T> selected;
public:
	Pool(vector<T>);
	void add(T);
	void remove(string);
	void showAll();
	int howManyHoursPerWeek();
	bool isInPool(string);
	void readFile();
	void saveFile();

	~Pool();
};

template<class T>
Pool<T>::Pool(vector<T> set)
{
	pool = set;
}

template<class T>
void Pool<T>::add(T)
{
	pool.push_back(T);
}

template<class T>
void Pool<T>::remove(string title)
{
	for (vector<T> iterator i = pool.begin(); i != pool.end(); i++) {
		if (strcmp(i.title, title)) {
			pool.erase(remove(pool.begin(), pool.end(), i.title), pool.end())
		}
	}
}

template<class T>
void Pool<T>::edit(string)
{
}

template<class T>
void Pool<T>::showAll()
{
	for (auto const serial : pool) {
		cout << serial.getTitle() << " ma ocene: " << serial.getScore() << endl;
	}

}

template<class T>
bool Pool<T>::isInSet(string)
{

	return false;
}

template<class T>
Pool<T>::~Pool()
{
}
