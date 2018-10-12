#include "TVShow.h"
#include <algorithm>

template <class T> class TVShowSet
{
private:
	vector<T> pool;
public:
	TVShowSet(vector<T>);
	void add(T);
	void remove(string);
	void edit(string);
	void showAll();
	bool isInSet(string);

	~TVShowSet();
};

template<class T>
TVShowSet<T>::TVShowSet(vector<T> set)
{
	pool = set;
}

template<class T>
void TVShowSet<T>::add(T)
{
	pool.push_back(T);
}

template<class T>
void TVShowSet<T>::remove(string title)
{
	for (vector<T> iterator i = pool.begin(); i != pool.end(); i++) {
		if (strcmp(i.title, title)) {
			pool.erase(remove(pool.begin(), pool.end(), i.title), pool.end())
		}
	}
}

template<class T>
void TVShowSet<T>::edit(string)
{
}

template<class T>
void TVShowSet<T>::showAll()
{
	for (auto const serial : pool) {
		cout << serial.getTitle() << " ma ocene: " << serial.getScore() << endl;
	}

}

template<class T>
bool TVShowSet<T>::isInSet(string)
{

	return false;
}

template<class T>
TVShowSet<T>::~TVShowSet()
{
}
