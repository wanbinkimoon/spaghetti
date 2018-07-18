int stageW      = 1200;
int stageH      = 400;
color bgC       = #2F2F2F;
String dataPATH = "../../data";

// new palette
// #ECA106 #D34F1E #91300A #5F1B00
// ================================================================

boolean DEBUG = false;
boolean GRID = false;
boolean MIDI = false;
boolean showHint = false;
boolean audioPanel = false;

// ============================================================

void settings(){ 
	pixelDensity(displayDensity());	
	fullScreen();
	// size(stageW, stageH);

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
	surface.setTitle("❀ – Spaghetti – FPS: " + FPS);

	audioDataUpdate();
	audioMidiValueUpdate();

	if(showHint) {
		renderHints();
	}

	if(audioPanel) {
		audioPanel();
	}

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
		case 'a':
			showAudioPanel();
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

void showAudioPanel(){
	audioPanel = !audioPanel;
}


// ================================================================

void renderHints(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(#00AEFF);
	textAlign(LEFT);
	textSize(16);
	String FPS =  String.format("%.2f", frameRate);
	String helpString = "FPS: " + FPS + "    Q: Quit    S: Save screenshot in ./render folder    A: Audio panel control";
	text(helpString, 12, 28);
}

// ================================================================

void mousePressed() {
  noLoop();
}

void mouseReleased() {
  loop();
}