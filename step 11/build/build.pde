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
	surface.setTitle("❀ – Spaghetti – FPS: " + FPS);
	panelsControl();

	audioDataUpdate();
	audioMidiValueUpdate();

	
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
		case 'i':
			showInfoPanel();
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

boolean showHint = false;
boolean audioPanel = false;
boolean infoPanel = false;

// ================================================================

void showHelp(){
	showHint = !showHint;
}

void showAudioPanel(){
	audioPanel = !audioPanel;
}

void showInfoPanel(){
	infoPanel = !infoPanel;
}

// ================================================================

void panelsControl(){
	if(showHint) {
		renderHints();
	}

	if(infoPanel) {
		renderInfos();
	}

	if(audioPanel) {
		audioPanel();
	}

}

// ================================================================

void renderInfos(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(#00AEFF);
	textAlign(LEFT);
	textSize(16);
	String helpString = "noiseSpeed";
	text(helpString, 12, 28);
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