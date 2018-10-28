#include "Database.h"

void showOptions() {
	cout << "0. Pokaz wszystko!" << endl;
	cout << "1. Dodaj.." << endl;
	cout << "9. Wyjdz!" << endl;

}

int main() {
	Database db;
	bool exit = false;
	int choice;
	string newProduction;

	cout << "Witaj w aplikacji!" << endl; 
	while (!exit) {
		cout << "Co chcialbys wykonac?" << endl;
		showOptions();
		cin >> choice;
		system("cls");
		switch (choice) {
		case 0:
			db.show();
			system("pause");
			system("cls");
			break;
		case 1:
			cout << "1. Dodaj.." << endl;
			cout << "Wybierz co chcesz dodac: " << endl;
			cout << "	0 - serial\n	1 - film\n	2 - stream\n 3 - wyjdz" << endl;
			cin >> choice;
			switch (choice) {
			case 0:
				db.addSeries();
			case 1:
				break;
			case 2:
				break;
			}
			system("pause");
			//db.addSeries();
			break;
		case 9:
			exit = true;
			break;
		default:
			cout << "Nie ma takiego!" << endl;
		}
	}


	return 0;
}