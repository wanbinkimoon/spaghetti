
float xoff = 0.0;
float n;
float noiseSpeed = .01;

// ================================================================

void noiseUpdate(){
	noiseSpeed = map(knob[4], 0, 100, .01, 1);
 	xoff += noiseSpeed;
  n = noise(xoff);
}