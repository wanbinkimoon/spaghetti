int section = audioRange;
PVector[] ants = new PVector[section];

// ================================================================

void renderCircles() {
	int audioIndex = 0;

	
	for (int i = 0; i < audioRange; ++i) {
		float step = (width / section);
		float x = (i + 1) * step;
		float audioDiff = audioData[audioIndex];

		float padding = height / 8;
		float audioCalc = map(audioDiff, 0, 2, height + padding, 0 - padding);
		float mover = audioCalc;
		float y = audioCalc - 1;
		ants[i] = new PVector(x, y);

		float diameter = 2.0;
		fill(0);
		ellipse(ants[i].x, ants[i].y, diameter, diameter);
		++audioIndex;
	}
	audioIndex = 0;
}