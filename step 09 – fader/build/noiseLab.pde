
float xoff = 0.0;
float n;
float noiseSpeed = .01;

// ================================================================

void noiseUpdate(){
 	xoff += noiseSpeed;
  n = noise(xoff);
}