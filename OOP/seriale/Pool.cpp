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
void Pool<T>::showAll()
{
	for(unsigned int i = 0; i < selected.size(); i++){
		T temp = selected[i];
		cout << i + 1<< ". " <<temp.getTitle() << " z gatunku "<< temp.getGenre() <<" ma ocene: " << temp.getScore() << endl;
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
