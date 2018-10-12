#include "TVShow.h"

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
}

template<class T>
void TVShowSet<T>::remove(string)
{
}

template<class T>
void TVShowSet<T>::edit(string)
{
}

template<class T>
void TVShowSet<T>::showAll()
{
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
