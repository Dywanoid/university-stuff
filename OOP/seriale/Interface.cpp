#include "Interface.h"

// main program loop
Interface::Interface()
{
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
		case 4: // recommendations
			recommendations(db);
			endOption();
			break;
		case 5: // statistics
			statistics(db);
			endOption();
			break;
		case 9:
			exit = true;
			break;
		default:
			cout << "Nie ma takiej opcji!" << endl;
			endOption();
		}
	}
}

// show options to use in main menu
void Interface::showOptions() {
	cout << "0. Pokaz wszystko!" << endl;
	cout << "1. Dodaj.." << endl;
	cout << "2. Usun.." << endl;
	cout << "3. Edytuj.." << endl;
	cout << "4. Rekomendacje (ranking).." << endl;
	cout << "5. Statystyki.." << endl;
	cout << "9. Wyjdz!" << endl;
}

// pausing and clearing terminal
void Interface::endOption() {
	system("pause");
	system("cls");
}

// add production
void Interface::addProduction(Database& db) {
	int choice;
	string title, genre, score;
	string seasons, imdbplace, date;

	cout << "1. Dodaj.." << endl;
	cout << "Wybierz co chcesz dodac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();
	if (choice >= 0 && choice < 3) {
		cout << "Podaj tytul: " << endl;
		getline(cin, title);
		cout << "Podaj gatunek: " << endl;
		getline(cin, genre);
		cout << "Podaj ocene: " << endl;
		score = to_string(getInput<float>());
	}
	switch (choice) {
	case SERIES:
		cout << "Ile sezonow?: " << endl;
		seasons = to_string(getInput<int>());
		db.add(title + ";" + genre + ";" + score + ";" + seasons, choice);
		cout << "Dodano serial: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i liczba sezonow to: " + seasons << endl;
		break;
	case MOVIE:
		cout << "Jaka pozycja na IMDb?: " << endl;
		imdbplace = to_string(getInput<int>());
		db.add(title + ";" + genre + ";" + score + ";" + imdbplace, choice);
		cout << "Dodano film: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i jego miejsce na IMDb to: " + imdbplace << endl;
		break;
	case STREAM:
		cout << "Kiedy?: " << endl;
		getline(cin, date);
		db.add(title + ";" + genre + ";" + score + ";" + date, choice);
		cout << "Dodano stream: " + title + ".\nGatunek: " + genre + ".\nMa on ocene: " + score + " i odbedzie sie: " + date << endl;
		break;
	default:
		break;
	}
}

// remove production
void Interface::removeProduction(Database& db) {
	int choice;
	unsigned int toDelete;

	cout << "2. Usun.." << endl;
	cout << "Wybierz co chcesz usunac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();

	if (choice >= 0 && choice <= 2) {
		db.show(choice);
		cout << "Co usunac? (0 aby wyjsc)" << endl;
		toDelete = getInput<unsigned int>();
		if (toDelete) { db.remove(toDelete, choice); }
	}
}

// edit production
void Interface::editProduction(Database& db) {
	int choice;
	unsigned int toEdit, confirm;
	string title, genre, score, special;

	cout << "3. Edytuj.." << endl;
	cout << "Wybierz co chcesz edytowac: " << endl;
	cout << "	0 - serial\n	1 - film\n	2 - stream\n inna - wyjdz" << endl;
	choice = getInput<int>();
	if (choice >= 0 && choice <= 2) {
		db.show(choice);
		cout << "Ktora pozycje edytowac? (0 aby wyjsc)" << endl;
		toEdit = getInput<unsigned int>();
		if (toEdit) {
			db.show(choice, toEdit);
			cout << "Nowy tytul: " << endl;
			getline(cin, title);
			cout << "Nowy gatunek: " << endl;
			getline(cin, genre);
			cout << "Nowa ocene: " << endl;
			score = to_string(getInput<float>());
			switch (choice) {
			case SERIES:
				cout << "Nowa liczbe sezonow: " << endl;
				break;
			case MOVIE:
				cout << "Nowe miejsce w rankingu: " << endl;
				break;
			case STREAM:
				cout << "Nowa data: " << endl;
				break;
			}
			getline(cin, special);
			cout << "Czy jestes pewny i chcesz zmienic pozycje? \n 1 - Tak, 0 - Nie" << endl;
			confirm = getInput<unsigned int>();
			if (confirm) db.edit(choice, toEdit, title + ";" + genre + ";" + score + ";" + special);
		}
	}

};

// statistics of databases
void Interface::statistics(Database& db) {
	cout << "Statystyki:\n";
	cout << "\nSERIALE:\n";
	cout << "Liczba seriali: " << db.howMany(SERIES) << endl;
	if (db.howMany(SERIES)) cout << "Laczna liczba sezonow: " << db.countSeasons() << endl;
	cout << "\nFILMY:\n";
	cout << "Liczba filmow: " << db.howMany(MOVIE) << endl;
	if (db.howMany(MOVIE)) {
		cout << "Pozycja najlepszego filmu: " << db.getBestPlace() << endl;
		cout << "Pozycja najgorszego filmu: " << db.getWorstPlace() << endl;
	}
	cout << "\nSTREAMY:\n";
	cout << "Liczba streamow: " << db.howMany(STREAM) << endl;
};

// recommendations option, prints sorted entries
void Interface::recommendations(Database& db) {
	cout << "Najlepsze!\n";
	cout << "\nSERIALE:\n";
	db.showSorted(SERIES);
	cout << "\nFILMY:\n";
	db.showSorted(MOVIE);
	cout << "\nStreamy:\n";
	db.showSorted(STREAM);
};

Interface::~Interface()
{
}