int keyNumb = 10;
boolean[] keyBool = new boolean[keyNumb];

// ================================================================

void keyboardSwitcher(char key){

		for (int i = 0; i < keyNumb; ++i) {
				keyBool[i] = false;
		}	

		switch (key) {
		case '1':
			keyBool[0] = !keyBool[0];
			break;
		case '2':
			keyBool[1] = !keyBool[1];
			break;
		case '3':
			keyBool[2] = !keyBool[2];
			break;
		case '4':
			keyBool[3] = !keyBool[3];
			break;
		case '5':
			keyBool[4] = !keyBool[4];
			break;
		case '6':
			keyBool[5] = !keyBool[5];
			break;
		case '7':
			keyBool[6] = !keyBool[6];
			break;
		case '8':
			keyBool[7] = !keyBool[7];
			break;
		case '9':
			keyBool[8] = !keyBool[8];
			break;
		case '0':
			keyBool[9] = !keyBool[9];
			break;
		}
}