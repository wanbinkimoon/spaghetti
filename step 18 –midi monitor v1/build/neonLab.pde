
int neons = 100;
int maxNeons = 30;

// ================================================================
			
void renderNeons() {
	neons = (int)map(knob[15], 0, 100, 0, maxNeons);
	setColors();
	float wide = map(knob[13], 0, 100, 0, width / 2);
	float span = map(knob[12], 0, 100, 0, height / 2);

	for (int i = 0; i < neons; ++i) {

		float x1 = 0 + wide;
		float xCenter = width / 2;
		float x3 = width - wide;

		noiseUpdate();
		float positioner = n * i;
		float y;

		if(pad[11]) {
			y = height / 2;
		} else {
			y = random((height / 2) - span, (height / 2) + span);
		}
		// multiple y values for vertical shifting 
		float y1 = y;
		float y2 = y;

		if(pad[14]) {
			float shifter = map(knob[10], 0, 100, 0, height * .625);
			y1 = y - shifter; 
			y2 = y + shifter;
		}

		float factor = map(knob[14], 0, 100, 0, 10);
		float minRand = audioData[i * (audioRange / maxNeons)];
		float maxRand = audioData[i * (audioRange / maxNeons)] * 50;

		// Fix strokes for orizontal disappear
		float thickness = wide == width / 2 ? 0 : random(minRand, maxRand) * factor;
		int alpha = constrain((int)thickness, 125, 255);
		color painter = color(255, alpha);
		int index = i % lines;

		if(pad[12]) {
			int colorSelector = (int)random(0, 3);
			painter = palette[colorSelector][index];
		}

		float xDifference = 0;
		if(wide > 0){
			xDifference = thickness;
		}

		float alphaControl = map(knob[11], 0, 100, 50, 255);
		// stroke(255);
		fill(painter, alphaControl); noStroke();
		beginShape();
			vertex(x1 - xDifference, y1 - thickness / 2);
			vertex(xCenter, y1 - thickness / 2);
			vertex(xCenter, y1 + thickness / 2);
			vertex(x1 - xDifference, y1 + thickness / 2);

		endShape();

		// Double the color splitted vertically 
		// Active only if multicolor is on 
		if(pad[13] && pad[12]) {
			int colorSelector = (int)random(0, 3);
			painter = palette[colorSelector][index];
		}

		fill(painter, alphaControl);
		beginShape();
			vertex(x3 + xDifference, y2 + thickness / 2);
			vertex(xCenter, y2 + thickness / 2);
			vertex(xCenter, y2 - thickness / 2);
			vertex(x3 + xDifference, y2 - thickness / 2);
		endShape();
	}

}

// ================================================================