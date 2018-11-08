#include "Database.h"

void showOptions() {
	cout << "0. Pokaz wszystko!" << endl;
	cout << "1. Dodaj.." << endl;
	cout << "2. Usun.." << endl;
	cout << "9. Wyjdz!" << endl;
}

void endOption() {
	system("pause");
	system("cls");
}

// TODO: input check
void addProduction(Database& db) {
	int choice;
	string title, genre, score;
	string seasons, imdbplace, date;
	cout << "1. Dodaj.." << endl;
	cout << "Wybierz co chcesz dodac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n 3 - wyjdz" << endl;
	cin >> choice;
	if (choice != 3) {
		cout << "Podaj tytul: " << endl;
		cin.ignore();
		getline(cin, title);
		cout << "Podaj gatunek: " << endl;
		getline(cin, genre);
		cout << "Podaj ocene: " << endl;
		getline(cin, score);
	}
	switch (choice) {
	case 0:
		cout << "Ile sezonow?: " << endl;
		getline(cin, seasons);
		db.add(title + ";" + genre + ";" + score + ";" + seasons, choice);
		cout << "Dodano serial: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i liczba sezonow to: " + seasons << endl;
		endOption();
		break;
	case 1:
		cout << "Jaka pozycja na IMDb?: " << endl;
		getline(cin, imdbplace);
		db.add(title + ";" + genre + ";" + score + ";" + imdbplace, choice);
		cout << "Dodano film: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i jego miejsce na IMDb to: " + imdbplace << endl;
		endOption();
		break;
	case 2:
		cout << "Kiedy?: " << endl;
		getline(cin, date);
		db.add(title + ";" + genre + ";" + score + ";" + date, choice);
		cout << "Dodano stream: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i odbedzie sie: " + date << endl;
		endOption();
		break;
	case 3:
		endOption();
		break;
	}
}

void removeProduction(Database& db) {
	int choice, toDelete;
	cout << "2. Usun.." << endl;
	cout << "Wybierz co chcesz usunac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n 3 - wyjdz" << endl;
	cin >> choice;
	if (choice != 3) {
		db.show(choice);
		cout << "Co usunac? (-1 aby wyjsc)" << endl;
		cin >> toDelete;
		if (toDelete != -1) { db.remove(toDelete, choice); }
	}
	
	endOption();
}

int main() {
	Database db;
	bool exit = false;
	int choice;

	cout << "Witaj w aplikacji!" << endl; 
	while (!exit) {
		cout << "Co chcialbys wykonac?" << endl;
		showOptions();
		cin >> choice;
		system("cls");
		switch (choice) {
		case 0: // show all data
			db.show();
			endOption();
			break;
		case 1: // add production
			addProduction(db);
			break;
		case 2: // remove production
			removeProduction(db);
			break;
		case 9:
			exit = true;
			break;
		default:
			cout << "Nie ma takiego!" << endl;
			endOption();
		}
	}


	return 0;
}