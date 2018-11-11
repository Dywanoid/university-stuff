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
	case SERIES:
		series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		break;
	case MOVIE:
		movies += Movie(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
		break;
	case STREAM:
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
			if (line != "") {
				if (counter != toDelete - 1) {
					lines.push_back(line);
				}
				counter++;
			}
			
		}
		file.close();
		file.open(paths[what], ios::out);
		if (file.good()) {
			for (auto &addLine : lines) {
				file << '\n' + addLine;
			}
		}
		file.close();
		switch (what) {
		case SERIES:
			series -= toDelete;
			break;
		case MOVIE:
			movies -= toDelete;
			break;
		case STREAM:
			streams -= toDelete;
			break;
		}
		
	}
	else {
		throw string("Nie uda³o otworzyæ siê pliku z danymi!");
	}
}

void Database::edit(int what, int which, string info)
{
	vector<string> entry = splitLine(info, ';');
	switch (what) {
	case SERIES:
		series.edit(Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3])), which);
		break;
	case MOVIE:
		movies.edit(Movie(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3])), which);
		break;
	case STREAM:
		streams.edit(Stream(entry[0], entry[1], ::atof(entry[2].c_str()), entry[3]), which);
		break;
	}
	
}

void Database::show()
{	
	cout << "=====================" << endl;
	cout << "SERIALE" << endl;
	cout << "=====================" << endl;
	show(SERIES);
	cout << "\n=====================" << endl;
	cout << "FILMY" << endl;
	cout << "=====================" << endl;
	show(MOVIE);
	cout << "\n=====================" << endl;
	cout << "STREAMY" << endl;
	cout << "=====================" << endl;
	show(STREAM);
	cout << "\n\n";
}

void Database::show(int what)
{
	switch (what) {
	case SERIES:
		series.showAll();
		break;
	case MOVIE:
		movies.showAll();
		break;
	case STREAM:
		streams.showAll();
		break;
	}
}

void Database::show(int what, int which)
{
	switch (what) {
	case SERIES:
		series.showAll(which);
		break;
	case MOVIE:
		movies.showAll(which);
		break;
	case STREAM:
		streams.showAll(which);
		break;
	}
}

void Database::loadDatabases()
{
	fstream file;
	string paths[] = PATHS;
	for (int i = 0; i < 3; i++)
	{
		file.open(paths[i], ios::in);
		if (file.good()) {
			string line;
			while (getline(file, line)) {
				if (line != "") {
					vector<string> entry = splitLine(line, ';');
					switch (i) {
					case SERIES:
						series += Series(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
						break;
					case MOVIE:
						movies += Movie(entry[0], entry[1], ::atof(entry[2].c_str()), stoi(entry[3]));
						break;
					case STREAM:
						streams += Stream(entry[0], entry[1], ::atof(entry[2].c_str()), entry[3]);
						break;
					}
				}
			}
			file.close();
		}
		else {
			throw string("Nie udalo sie otworzyc pliku!");
		}
	}
}


Database::~Database()
{
}
