
float xMod = 10;
float yMod = 40;

float side = 128;

// ================================================================

void renderCircles() {
	// background(bgC, 25);

	stroke(#D34F1E); noFill();

	translate(width / 2, height /2);
	float t = frameCount * .05;
	float radius = 80;
	
	xMod = 100;
	float x = cos(t) * xMod;

	yMod = 100;
	float y = sin(t) * yMod;

	float angle = t;
	rotate(angle);	
	
	float magicX = 20;
	float magicY = 3;
	float f = tan(2) * 500;

	// println("xMod: " + xMod + " – yMod: " + yMod);
	float w = sin(t/magicX) * f;
	float h = cos(t/magicY) * f;

	ellipse(0, 0, w, h);
	
}