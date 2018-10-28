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

void Database::addSeries(string& seriesInfo)
{
	vector<string> entry = splitLine(seriesInfo, ';');
	series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
}

void Database::saveDatabases()
{
}

void Database::show()
{
	series.showAll();
	streams.showAll();
	movies.showAll();
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
			series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
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
			series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
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
