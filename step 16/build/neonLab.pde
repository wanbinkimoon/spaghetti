
int neons = 100;
int maxNeons = 30;

// ================================================================

void renderNeons() {
	neons = (int)map(knob[15], 0, 100, 0, maxNeons);
	setColors();
	
	for (int i = 0; i < neons; ++i) {
		float x1 = 0;
		float x2 = width;

		noiseUpdate();
		float positioner = n * i;
		float y = random(0, height);

		float factor = map(knob[14], 0, 100, 0, 3);
		float thickness = random(0, 30) * factor;

		int index = i % lines;
		int colorSelector = 0;

		if(pad[12]) {
			colorSelector = (int)random(0, 3);
		}
		
		// stroke(255);
		stroke(palette[colorSelector][index]);
		strokeWeight(thickness);
		beginShape();
			vertex(x1, y);
			vertex(x2, y);
		endShape();
	}
}

// ================================================================