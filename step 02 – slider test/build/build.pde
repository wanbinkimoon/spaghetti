int stageW      = 800;
int stageH      = 400;
color bgC       = #ECA106;
String dataPATH = "../../data";

// new palette
// #ECA106 #D34F1E #91300A #5F1B00
// ================================================================

boolean DEBUG = false;
boolean GRID = false;
boolean MIDI = false;
boolean showHint = false;
boolean showPanel = true;

// ============================================================

void settings(){ 
	pixelDensity(displayDensity());	
	// fullScreen(P3D, 2);
	// fullScreen(P3D, SPAN);
	size(stageW, stageH);

}

// ================================================================

void setup() {
	// midiSetup();
	// audioSettings();

	surface.setResizable(true);
  // surface.setIconImage(icon.image);

	if(showPanel) panelSetup();
	if(showPanel) panelRender();	

	background(bgC);
}

// ================================================================
void draw() {
	String FPS =  String.format("%.2f", frameRate);
	surface.setTitle("🕸 – Spaghetti – FPS: " + FPS);
	if(showHint)
		renderHints();



	// midiMapper();
	// audioDataUpdate();

	// background(bgC);

	renderCircles();
}

// ================================================================

void keyPressed(){	
	switch (key) {
		case 'q':
			exit();
			break;
		case 'c':
			showPanel();
			break;
		case 'p':
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

void showPanel(){
	showPanel = !showPanel;
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