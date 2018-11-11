#include "Interface.h"

int main() {
	try {
		Interface start;
	}
	catch (string ex) {
		cout << ex << endl;
	}

	Interface::endOption();
	return 0;
}