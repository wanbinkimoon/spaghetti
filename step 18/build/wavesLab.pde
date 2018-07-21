int section = audioRange;
PVector[] ants = new PVector[section];

boolean fader = false;

// ================================================================

void renderWaves() {
	if(pad[3]) {
		fader = !fader;
	}

	if(fader){
		for (int i = 1; i < lines; ++i) {
			colorizer(i, false, 0);
			shapeFormer(i, lines, 0);
		}
	} else {
			colorizer(1, false, 0);
			shapeFormer(0, lines, 0);
	}
}

void renderMultipleWaves() {
	
	if(pad[3]) {
		fader = !fader;
	}

	for (int i = 1; i < lines; ++i) {
		if(fader){
			for (int j = 0; j < colorNumb; ++j) {				
				int diff = 20 * j;
				colorizer(i, true, j);
				shapeFormer(i, lines, diff);
			}
		} else {
			for (int j = 0; j < colorNumb; ++j) {				
				int diff = 20 * j;
				colorizer(i, true, j);
				shapeFormer(0, lines, diff);
			}
		}
	}
}

// ================================================================

float margin = 0;
boolean move = false;

// ================================================================


void shapeFormer(int line, int maxLines, int diff){
	int audioIndex;
	strokeWeight(1);
	// make the scene slide
	if(pad[2]) {
		move = !move;
	}

	if(move){
		audioIndex = frameCount % audioRange;
	} else {
		audioIndex = 0;
	}

	margin = map(knob[1], 0, 100, 0, 500);
	beginShape();
	vertex(-margin, height / 2);

	int points = (int)map(knob[3], 0, 100, 2, audioRange);

	for (int j = 0; j < points; ++j) {
		float step = (width / points);
		float xSpread = (j + 1 + diff) * step;
		float x = (j + 1) * step;

		float audioDiff = audioData[audioIndex];
		float padding = height / 8;

		float distance = map(knob[0], 0, 100, 0, .5);
		float mover = height * distance;
		float audioCalc = map(audioDiff, 1, 2, mover, -mover); 
		float fade = audioCalc / maxLines;
		float fader = audioCalc - ((maxLines - line) * fade);
		float y;
		y = (height / 2) + audioCalc - fader;
		if(audioDiff == 1) y = (height / 2);

		ants[j] = new PVector(x, y);
		curveVertex(ants[j].x, ants[j].y);
		if(pad[1]){
			vertex(ants[j].x, ants[j].y);
		}
		if(move){
			audioIndex = (audioIndex + (audioRange / points)) % audioRange;
		} else {
			audioIndex += audioRange / points;
		}

		audioIndex = (audioIndex + diff) % audioRange;	
	}

	vertex(width + margin, height / 2);
	endShape();

	audioIndex = 0;
}

// ================================================================