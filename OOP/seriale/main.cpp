#include "Database.h"

void showOptions() {
	cout << "0. Pokaz wszystko!" << endl;
	cout << "1. Dodaj.." << endl;
	cout << "2. Usun.." << endl;
	cout << "3. Edytuj.." << endl;
	cout << "9. Wyjdz!" << endl;
}

void endOption() {
	system("pause");
	system("cls");
}

template<class Type>
Type getInput() {
	Type input;
	cin >> input;
	while (!cin) {
		cin.clear();
		cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
		cin >> input;
	}
	return input;
}

void addProduction(Database& db) {
	int choice;
	string title, genre, score;
	string seasons, imdbplace, date;

	cout << "1. Dodaj.." << endl;
	cout << "Wybierz co chcesz dodac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();
	cin.ignore();
	if (choice >= 0 && choice < 3) {
		cout << "Podaj tytul: " << endl;
		getline(cin, title);
		cout << "Podaj gatunek: " << endl;
		getline(cin, genre);
		cout << "Podaj ocene: " << endl;
		score = to_string(getInput<float>());
		cin.ignore();
	}
	switch (choice) {
	case 0:
		cout << "Ile sezonow?: " << endl;
		seasons = to_string(getInput<int>());
		db.add(title + ";" + genre + ";" + score + ";" + seasons, choice);
		cout << "Dodano serial: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i liczba sezonow to: " + seasons << endl;
		break;
	case 1:
		cout << "Jaka pozycja na IMDb?: " << endl;
		imdbplace = to_string(getInput<int>());
		db.add(title + ";" + genre + ";" + score + ";" + imdbplace, choice);
		cout << "Dodano film: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i jego miejsce na IMDb to: " + imdbplace << endl;
		break;
	case 2:
		cout << "Kiedy?: " << endl;
		getline(cin, date);
		db.add(title + ";" + genre + ";" + score + ";" + date, choice);
		cout << "Dodano stream: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i odbedzie sie: " + date << endl;
		break;
	default:
		break;
	}
}

void removeProduction(Database& db) {
	int choice;
	unsigned int toDelete;

	cout << "2. Usun.." << endl;
	cout << "Wybierz co chcesz usunac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();

	if (choice >=0 && choice <= 2) {
		db.show(choice);
		cout << "Co usunac? (0 aby wyjsc)" << endl;
		toDelete = getInput<unsigned int>();
		if (toDelete != 0) { db.remove(toDelete, choice); }
	}	
}

void editProduction(Database& db) {
	int choice;
	unsigned int toEdit;

	cout << "3. Edytuj.." << endl;
	cout << "Wybierz co chcesz edytowac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();
	if (choice >= 0 && choice <= 2) {
		db.show(choice);
		cout << "Co edytowac? (0 aby wyjsc)" << endl;
		toEdit = getInput<unsigned int>();
	}
	
};

int main() {
	try {
		Database db;
		bool exit = false;
		int choice;

		cout << "Witaj w aplikacji!" << endl;
		while (!exit) {
			cout << "Co chcialbys wykonac?" << endl;
			showOptions();
			choice = getInput<int>();
			system("cls");
			switch (choice) {
			case 0: // show all data
				db.show();
				endOption();
				break;
			case 1: // add production
				try {
					addProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				endOption();
				break;
			case 2: // remove production
				try {
					removeProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				endOption();
				break;
			case 3: // edit production
				try {
					editProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				endOption();
				break;
			case 9:
				exit = true;
				break;
			default:
				cout << "Nie ma takiego!" << endl;
				endOption();
			}
		}
	}
	catch (string ex) {
		cout << ex << endl;
	}

	

	endOption();
	return 0;
}