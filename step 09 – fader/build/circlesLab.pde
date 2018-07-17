int section = audioRange;
PVector[] ants = new PVector[section];

// ================================================================

void renderCircles() {

	background(#91300A);
	stroke(#ECA106); noFill();

	if(pad[0]) {
		background(#ECA106);
		noStroke(); fill(#91300A);
	}

	int lines = 10;
	for (int i = 1; i < lines; ++i) {
		shapeFormer(i, lines);
	}
	
}

// ================================================================

void shapeFormer(int line, int maxLines){
	int audioIndex = 0;
	beginShape();
	vertex(0, height / 2);

	for (int j = 0; j < audioRange; ++j) {
		float step = (width / section);
		float x = (j + 1) * step;

		float audioDiff = audioData[audioIndex];
		float padding = height / 8;
		float mover = height / 8;
		float audioCalc = map(audioDiff, 1, 2, mover, -mover); 
		float fade = audioCalc / maxLines;
		float fader = audioCalc - (line * fade);
		float y = (height / 2) + audioCalc - fader;


		ants[j] = new PVector(x, y);
		curveVertex(ants[j].x, ants[j].y);
		++audioIndex;
	}

	vertex(width, height / 2);
	endShape(CLOSE);

	audioIndex = 0;
}