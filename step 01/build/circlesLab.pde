
// ================================================================

void renderCircles() {
	stroke(#D34F1E); noFill();

	pushMatrix();
		translate(0, height /2);

		float frequence = frameCount * .25;
		float x = frequence * 10;
		float y = sin(frequence) * 30;
		ellipse(x, y, 10, 10);
 	popMatrix();
	
}