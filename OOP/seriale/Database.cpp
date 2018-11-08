#include "Database.h"

vector<string> splitLine(const string& line, char delimeter) {
	vector<string> words;
	string word;
	istringstream lineStream (line);
	while (getline(lineStream, word, delimeter)) {
		words.push_back(word);
	}
	return words;
}

Database::Database()
{
	loadDatabases();
}

void Database::add(string info, int what)
{
	string paths[] = PATHS;
	fstream file;
	file.open(paths[what], ios::app);
	if (file.good()) {
		file << '\n' + info;
		file.close();
	}
	vector<string> entry = splitLine(info, ';');
	switch (what) {
	case 0:
		series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		break;
	case 1:
		movies += Movie(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		break;
	case 2:
		streams += Stream(entry[0], entry[1], ::atof(entry[2].c_str()), entry[3]);
		break;
	}
}

void Database::remove(unsigned int toDelete, int what)
{
	string paths[] = PATHS;
	int counter = 0;
	fstream file;
	vector<string> lines;
	string line;

	file.open(paths[what], ios::in);
	if (file.good()) {
		while (getline(file, line)) {
			if (counter != toDelete) {
				lines.push_back(line);
			}
			counter++;
		}
		file.close();
		file.open(paths[what], ios::out);
		if (file.good()) {
			for (auto &addLine : lines) {
				file << addLine << '\n';
			}
		}
		file.close();
		switch (what) {
		case 0:
			series -= toDelete;
			break;
		case 1:
			movies -= toDelete;
			break;
		case 2:
			streams -= toDelete;
			break;
		}
		
	}
	else {
		throw string("Nie uda³o otworzyæ siê pliku z danymi!");
	}
}

void Database::show()
{	
	cout << "=====================" << endl;
	cout << "SERIALE" << endl;
	cout << "=====================" << endl;
	show(0);
	cout << "\n=====================" << endl;
	cout << "FILMY" << endl;
	cout << "=====================" << endl;
	show(1);
	cout << "\n=====================" << endl;
	cout << "STREAMY" << endl;
	cout << "=====================" << endl;
	show(2);
	cout << "\n\n";
}

void Database::show(int what)
{
	switch (what) {
	case 0:
		series.showAll();
		break;
	case 1:
		movies.showAll();
		break;
	case 2:
		streams.showAll();
		break;
	}
}

void Database::loadDatabases()
{
	fstream file;
	
	// seriale
	file.open("db/seriale.txt", ios::in);
	if (file.good()) {
		string line;
		while (getline(file, line)) {
			vector<string> entry = splitLine(line, ';');
			series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		}
		file.close();
	}
	else {
		throw string("Nie uda³o otworzyæ siê pliku z serialami!");
	}

	// filmy
	file.open("db/filmy.txt", ios::in);
	if (file.good()) {
		string line;
		while (getline(file, line)) {
			vector<string> entry = splitLine(line, ';');
			movies += Movie(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		}
		file.close();
	}
	else {
		throw string("Nie uda³o otworzyæ siê pliku z filami!");
	}

	// streamy
	file.open("db/streamy.txt", ios::in);
	if (file.good()) {
		string line;
		while (getline(file, line)) {
			vector<string> entry = splitLine(line, ';');
			streams += Stream(entry[0], entry[1], ::atof(entry[2].c_str()), entry[3]);
		}
		file.close();
	}
	else {
		throw string("Nie uda³o otworzyæ siê pliku ze streamami!");
	}
}


Database::~Database()
{
}
