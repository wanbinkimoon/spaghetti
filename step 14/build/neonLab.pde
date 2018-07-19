
int neons = 100;

// ================================================================

void renderNeons() {
	neons = (int)map(knob[15], 0, 100, 0, audioRange / 2);

	for (int i = 0; i < neons; ++i) {
		float x1 = 0;
		float x2 = width;

		noiseUpdate();
		float positioner = n * i;
		float y = random(0, height);

		stroke(255);
		float thickness = audioData[i * 2] * 10;

		strokeWeight(thickness);
		beginShape();
			vertex(x1, y);
			vertex(x2, y);
		endShape();
	}
}