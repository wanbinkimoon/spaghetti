
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
		float minRand = audioData[i * (audioRange / maxNeons)];
		float maxRand = audioData[i * (audioRange / maxNeons)] * 50;
		float thickness = random(minRand, maxRand) * factor;

		int index = i % lines;

		int alpha = constrain((int)thickness, 75, 255);
		color painter = color(255, alpha);
		if(pad[12]) {
			int colorSelector = (int)random(0, 3);
			painter = palette[colorSelector][index];
		}
		
		// stroke(255);
		stroke(painter);
		strokeWeight(thickness);
		beginShape();
			vertex(x1, y);
			vertex(x2, y);
		endShape();
	}
}

// ================================================================