int stageW      = 1200;
int stageH      = 400;
color bgC       = #FFFFFF;
String dataPATH = "../../data";

// new palette
// #ECA106 #D34F1E #91300A #5F1B00
// ================================================================

boolean DEBUG = false;
boolean GRID = false;
boolean MIDI = false;
boolean showHint = false;

// ============================================================

void settings(){ 
	pixelDensity(displayDensity());	
	// fullScreen(P3D, 2);
	size(stageW, stageH);

}

// ================================================================

void setup() {
	midiSetup();
	audioSettings();

	surface.setResizable(true);
  // surface.setIconImage(icon.image);

	background(bgC, 20);
}

// ================================================================
void draw() {
	background(bgC, 20);
	String FPS =  String.format("%.2f", frameRate);
	surface.setTitle("🍝 – Spaghetti – FPS: " + FPS);
	if(showHint)
		renderHints();

	audioDataUpdate();
	renderCircles();
}

// ================================================================

void keyPressed(){	
	switch (key) {
		case 'q':
			exit();
			break;
		case 's':
			screenShot();
			break;
		case 'h':
			showHelp();
			break;
	}
}

// ================================================================

boolean letsRender = false;
int     renderNum  = 0;
String  renderPATH = "../render/";

// ================================================================

void screenShot(){
	letsRender = true;
	if (letsRender) {
		letsRender = false;
		save(renderPATH + renderNum + ".png");
		renderNum++;
	}
}

// ================================================================

void showHelp(){
	showHint = !showHint;
}


// ================================================================

void renderHints(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(#00AEFF);
	textAlign(LEFT);
	textSize(16);
	String helpString = "Q: Quit    P: Save screenshot in ./render folder ";
	text(helpString, 12, 28);
}

// ================================================================

void mousePressed() {
  noLoop();
}

void mouseReleased() {
  loop();
}