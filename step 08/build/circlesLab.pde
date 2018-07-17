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

	shapeFormer();
	
}

// ================================================================

void shapeFormer(){
	int audioIndex = 0;

	beginShape();
	vertex(0, height / 2);

	for (int i = 0; i < audioRange; ++i) {
		float step = (width / section);
		float x = (i + 1) * step;

		float audioDiff = audioData[audioIndex];
		float padding = height / 8;
		float audioCalc = map(audioDiff, 1, 2, height - padding, 0 + padding);
		float y = audioCalc;


		ants[i] = new PVector(x, y);
		curveVertex(ants[i].x, ants[i].y);
		++audioIndex;
	}

	vertex(width, height / 2);
	endShape(CLOSE);

	audioIndex = 0;
}