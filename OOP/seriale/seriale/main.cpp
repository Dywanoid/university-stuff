#include "Database.h"


int main() {
	Database db;
	Pool<Series> pula;
	pula += Series(Series("Tytul1", "horror", 5.2f, 5));
	
	pula.showAll();
	system("pause");
	db.loadDatabases();
	system("pause");
	db.show();
	system("pause");
	return 0;
}