int stageW      = 1200;
int stageH      = 400;
color bgC       = #2F2F2F;
String dataPATH = "../../data";

// new palette
// #ECA106 #D34F1E #91300A #5F1B00

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
	noiseUpdate();
	panelsControl();
	audioDataUpdate();
	audioMidiValueUpdate();

	if(pad[10]) {
		renderNeons();
	}

	if(pad[8]) {
		renderWaves();
	}

	if(pad[9]) {
		renderMultipleWaves();
	}
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
	String FPS =  String.format("%.2f", frameRate);
	String noiseString =  String.format("%.2f", noiseSpeed);
	String helpString = "FPS: " + FPS + "  – Noise speed: " + noiseString;
	text(helpString, 12, 28);
}

// ================================================================

void renderHints(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(#00AEFF);
	textAlign(LEFT);
	textSize(16);
	String helpString = "Q: Quit    S: Save screenshot in ./render folder    A: Audio panel control";
	text(helpString, 12, 28);
}


// ================================================================

void mousePressed() {
  noLoop();
}

void mouseReleased() {
  loop();
}