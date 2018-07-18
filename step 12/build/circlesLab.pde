int section = audioRange;
PVector[] ants = new PVector[section];
int lines = 10;

// ================================================================

void renderCircles() {
	for (int i = 1; i < lines; ++i) {
		colorizer(i);
		shapeFormer(i, lines);
	}
}

// ================================================================

void shapeFormer(int line, int maxLines){
	int audioIndex = 0;
	beginShape();
	vertex(0, height / 2);

	int points = (int)map(knob[1], 0, 100, 2, audioRange);

	for (int j = 0; j < points; ++j) {
		float step = (width / points);
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
		audioIndex += audioRange / points;
	}

	vertex(width, height / 2);
	endShape();

	audioIndex = 0;
}

// ================================================================

color[] palette = new color[lines];

// ================================================================

void colorizer(int index){
	colorChoice();
	stroke(palette[index]); noFill();
	if(pad[0]) {
		noStroke(); fill(palette[index]);
	}
}

// ================================================================

void colorChoice(){
	// reds
	palette[0]  = #ffd700;
	palette[1]  = #ffca00;
	palette[2]  = #ffbc00;
	palette[3]  = #ffaf00;
	palette[4]  = #ffa200;
	palette[5]  = #ff9500;
	palette[6]  = #ff8700;
	palette[7]  = #ff7a00;
	palette[8]  = #ff6d00;
	palette[9]  = #ff6000;
	// palette[10] = #ff5200;
	// palette[11] = #ff4500;

	if(pad[7]){
		// blues
		palette[0]  = #00d7ff;
		palette[1]  = #00caff;
		palette[2]  = #00bcff;
		palette[3]  = #00afff;
		palette[4]  = #00a2ff;
		palette[5]  = #0095ff;
		palette[6]  = #0087ff;
		palette[7]  = #007aff;
		palette[8]  = #006dff;
		palette[9]  = #0060ff;
	}

	if(pad[6]){
		// greens
		palette[0]  = #00d700;
		palette[1]  = #00ca00;
		palette[2]  = #00bc00;
		palette[3]  = #00af00;
		palette[4]  = #00a200;
		palette[5]  = #009500;
		palette[6]  = #008700;
		palette[7]  = #007a00;
		palette[8]  = #006d00;
		palette[9]  = #006000;
	}

	if(pad[5]){
		// pinks
		palette[0]  = #ffd7ff;
		palette[1]  = #ffcaff;
		palette[2]  = #ffbcff;
		palette[3]  = #ffafff;
		palette[4]  = #ffa2ff;
		palette[5]  = #ff95ff;
		palette[6]  = #ff87ff;
		palette[7]  = #ff7aff;
		palette[8]  = #ff6dff;
		palette[9]  = #ff6ff0;
	}
}