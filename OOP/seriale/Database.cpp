#include "Database.h"

// function to split string into pieces to use
vector<string> splitLine(const string& line, char delimeter) {
	vector<string> words;
	string word;
	istringstream lineStream (line);
	while (getline(lineStream, word, delimeter)) {
		words.push_back(word);
	}
	return words;
}

// load database from file on init
Database::Database()
{
	loadDatabases();
}

// add entry
void Database::add(string info, int what)
{
	string paths[] = PATHS;
	fstream file;

	// opening file and adding entry to file
	file.open(paths[what], ios::app);
	if (file.good()) {
		file << '\n' + info;
		file.close();
	}
	vector<string> entry = splitLine(info, ';');
	float tempScore = atof(entry[2].c_str());
	if (tempScore > 10 || tempScore < 0) throw string("Ocena poza skala! 0-10 to dopuszczalny zakres!");

	// adding entry to database
	switch (what) {
	case SERIES:
		series += Series(entry[0], entry[1], tempScore, stoi(entry[3]));
		break;
	case MOVIE:
		movies += Movie(entry[0], entry[1], tempScore, stoi(entry[3]));
		break;
	case STREAM:
		streams += Stream(entry[0], entry[1], tempScore, entry[3]);
		break;
	}
}

// remove entry
void Database::remove(unsigned int toDelete, int what)
{
	string paths[] = PATHS;
	int counter = 0;
	fstream file;
	vector<string> lines;
	string line;

	// opening file and reading all lines but one to remove
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

		// writing lines that are read
		file.open(paths[what], ios::out);
		if (file.good()) {
			for (auto &addLine : lines) {
				file << '\n' + addLine;
			}
		}
		file.close();

		// removing entry from database
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

// edit entry
void Database::edit(int what, int which, string info)
{
	string paths[] = PATHS;
	int counter = 0;
	fstream file;
	vector<string> lines;
	string line;
	vector<string> entry = splitLine(info, ';');

	
	// opening file and reading every line but that one to edit
	// edited line is added instead of original entry
	file.open(paths[what], ios::in);
	if (file.good()) {
		while (getline(file, line)) {
			if (line != "") {
				if (counter != which - 1) {
					lines.push_back(line);
				}
				else {
					lines.push_back(info);
				}
				counter++;
			}

		}
		file.close();

		// writing lines that are read
		file.open(paths[what], ios::out);
		if (file.good()) {
			for (auto &addLine : lines) {
				file << '\n' + addLine;
			}
		}
		file.close();
	}

	// editing entry in database
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

// how many specified productions in database
int Database::howMany(int what)
{
	int n;

	// how many entries in database based on type of production
	switch (what) {
	case SERIES:
		n = series.howMany();
		break;
	case MOVIE:
		n = movies.howMany();
		break;
	case STREAM:
		n = streams.howMany();
		break;
	}
	return n;
}

// count number of seasons across all series
int Database::countSeasons()
{
	int total = 0;
	for (auto &seasons : series.getAllSpecials()) {
		total += stoi(seasons);
	};
	return total;
}

// get best place 
int Database::getBestPlace()
{
	int best = 0;
	for (auto &places : movies.getAllSpecials()) {
		if (!best || stoi(places) < best) best = stoi(places);
	};
	return best;
}

// get worst place
int Database::getWorstPlace()
{
	int worst = 0;
	for (auto &places : movies.getAllSpecials()) {
		if (!worst || stoi(places) > worst) worst = stoi(places);
	};
	return worst;
}

// show everything
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

// show specified production type
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

// show one entry of specified production type
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

// show sorted productions of specified type
void Database::showSorted(int what)
{
	switch (what) {
	case SERIES:
		series.showSorted();
		break;
	case MOVIE: 
		movies.showSorted();
		break;
	case STREAM:
		streams.showSorted();
		break;
	}
}

// load database
void Database::loadDatabases()
{
	fstream file;
	string paths[] = PATHS;
	for (int i = 0; i < 3; i++)
	{	
		// opening file
		file.open(paths[i], ios::in);
		if (file.good()) {
			string line;
			
			// reading line by line
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
