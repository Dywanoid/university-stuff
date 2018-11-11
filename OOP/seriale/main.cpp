#include "Interface.h"

int main() {
	try {
		Database db;
		bool exit = false;
		int choice;

		cout << "Witaj w aplikacji!" << endl;
		while (!exit) {
			cout << "Co chcialbys wykonac?" << endl;
			Interface::showOptions();
			choice = Interface::getInput<int>();
			system("cls");
			switch (choice) {
			case 0: // show all data
				db.show();
				Interface::endOption();
				break;
			case 1: // add production
				try {
					Interface::addProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				Interface::endOption();
				break;
			case 2: // remove production
				try {
					Interface::removeProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				Interface::endOption();
				break;
			case 3: // edit production
				try {
					Interface::editProduction(db);
				}
				catch (string ex) {
					cout << ex << endl;
				}
				Interface::endOption();
				break;
			case 4: // recommendations
				Interface::recommendations(db);
				Interface::endOption();
				break;
			case 5: // statistics
				Interface::statistics(db);
				Interface::endOption();
				break;
			case 9:
				exit = true;
				break;
			default:
				cout << "Nie ma takiego!" << endl;
				Interface::endOption();
			}
		}
	}
	catch (string ex) {
		cout << ex << endl;
	}

	

	Interface::endOption();
	return 0;
}