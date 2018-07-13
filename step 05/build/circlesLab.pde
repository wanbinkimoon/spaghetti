
float xMod = 10;
float yMod = 40;

float side = 128;

// ================================================================

void renderCircles() {
	// background(bgC, 25);

	stroke(#D34F1E); noFill();

	translate(width / 2, height /2);
	// translate(0, height /2);
	float t = frameCount * .05;
	float radius = 80;
	
	xMod = 100;
	// float x = cos(t) * xMod;

	yMod = 100;
	// float y = sin(t) * yMod;

	float angle = t * .5;
	rotate(angle);	
	
	float magicX = 20;
	float magicY = 100;
	float f = tan(10) * 500;

	float x = t * sin(t) * 10;
	float y = t * cos(t) * 10;
	float w = cos(t/magicX) * f / 2;
	float h = sin(t/magicY) * f / 20;

	ellipse(x, y, w, h);
	
}