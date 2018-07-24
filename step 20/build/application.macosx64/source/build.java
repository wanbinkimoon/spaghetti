import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int stageW      = 1000;
int stageH      = 480;
int bgC       = 0xff2F2F2F;
String dataPATH = "../../data";

// new palette
// #ECA106 #D34F1E #91300A #5F1B00

// ============================================================

public void settings(){ 
	pixelDensity(displayDensity());	
	fullScreen(P2D, 2);
	// size(stageW, stageH);
}

// ================================================================

public void setup() {
	midiSetup();
	midiMonitoSetup();
	keyboardMonitoSetup();
	audioSettings();

	surface.setResizable(true);
  // surface.setIconImage(icon.image);
	background(bgC, 20);
}

// ================================================================
public void draw() {
	background(bgC, 20);
	
	String FPS =  String.format("%.2f", frameRate);
	surface.setTitle("\u2740 \u2013 Spaghetti \u2013 FPS: " + FPS);
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

public void keyPressed(){	
	keyboardSwitcher(key);
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

public void screenShot(){
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

public void showHelp(){
	showHint = !showHint;
}

public void showAudioPanel(){
	audioPanel = !audioPanel;
}

public void showInfoPanel(){
	infoPanel = !infoPanel;
}

// ================================================================

public void panelsControl(){
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

public void renderInfos(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(0xff00AEFF);
	textAlign(LEFT);
	textSize(16);	
	String FPS =  String.format("%.2f", frameRate);
	String noiseString =  String.format("%.2f", noiseSpeed);
	String helpString = "FPS: " + FPS + "  \u2013 Noise speed: " + noiseString;
	text(helpString, 12, 28);
}

// ================================================================

public void renderHints(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(0xff00AEFF);
	textAlign(LEFT);
	textSize(16);
	String helpString = "Q: Quit    S: Save screenshot in ./render folder    A: Audio panel control";
	text(helpString, 12, 28);
}


// ================================================================

public void mousePressed() {
  noLoop();
}

public void mouseReleased() {
  loop();
}




// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;

// ================================================================

int audioRange  = 128;
int audioMax = 100;

float audioAmp = 69.0f;
float audioIndex = 0.09f;
float audioIndexStep = 0.350f;
float audioIndexAmp = audioIndex;

float[] audioData = new float[audioRange];

// ================================================================

public void audioSettings(){
  minim = new Minim(this);
  audio = minim.getLineIn(Minim.STEREO);
  // audio = minim.getInputStream();

  audioFFT = new FFT(audio.bufferSize(), audio.sampleRate());
  audioFFT.linAverages(audioRange);

  audioFFT.window(FFT.NONE);
  // audioFFT.window(FFT.BARTLETT);
  // audioFFT.window(FFT.BARTLETTHANN);
  // audioFFT.window(FFT.BLACKMAN);
  // audioFFT.window(FFT.COSINE);
  // audioFFT.window(FFT.GAUSS);
  // audioFFT.window(FFT.HAMMING);
  // audioFFT.window(FFT.HANN);
  // audioFFT.window(FFT.LANCZOS);
  // audioFFT.window(FFT.TRIANGULAR);
}

// ================================================================

public void audioDataUpdate(){
  audioFFT.forward(audio.mix);
  updateAudio();
}

// ================================================================

  public void updateAudio(){
    for (int i = 0; i < audioRange; ++i) {
      float indexAvg = (audioFFT.getAvg(i) * audioAmp) * audioIndexAmp;
      float indexCon = constrain(indexAvg, audioMax, audioMax * 2);
      
      if(indexAvg > audioMax) audioData[i] = indexCon;
      else audioData[i] = 100;

      audioData[i] = audioData[i] / 100;
      audioIndexAmp += audioIndexStep;
    }

    audioIndexAmp = audioIndex;
  }

  // ================================================================
  
  public void audioMidiValueUpdate(){
    if(arrow[3]) {
      audioAmp = map(knob[5], 0, 100, 20.0f, 200.1f);
      audioIndex = map(knob[6], 0, 100, 0.0f, 0.1f);
      audioIndexStep = map(knob[7], 0, 100, 0.0f, 0.5f);
    }
  }

  public void audioPanel(){
    fill(75, 200); noStroke();
    rect(0, 0, width, 48);
    fill(0xff00AEFF);
    textAlign(LEFT);
    textSize(16);
    String helpString = "AMP: " + audioAmp + " \u2013 INDEX: " + audioIndex + " \u2013 STEP: " + audioIndexStep;
    text(helpString, 12, 28);
  }
int colorNumb = 4;
int lines = 10;
int[][] palette = new int[colorNumb][lines];

// ================================================================

public void colorizer(int index, boolean multiple, int set){
	int nowColor = 0xffFFFFFF;
	setColors();

	if(multiple){
		nowColor = palette[set][index];
	} else {
		if(keyBool[0]) {
			nowColor = palette[0][index];
		} else if(keyBool[1]) {
			nowColor = palette[1][index];
		} else if(keyBool[2]) {
			nowColor = palette[2][index];
		} else if(keyBool[3]) {
			nowColor = palette[3][index];
		}
	}
	
	stroke(nowColor); noFill();
	if(pad[0]) {
		noStroke(); fill(nowColor);
	}
}

// ================================================================

public void setColors(){
	// reds
	palette[0][0]  = 0xffffd700;
	palette[0][1]  = 0xffffca00;
	palette[0][2]  = 0xffffbc00;
	palette[0][3]  = 0xffffaf00;
	palette[0][4]  = 0xffffa200;
	palette[0][5]  = 0xffff9500;
	palette[0][6]  = 0xffff8700;
	palette[0][7]  = 0xffff7a00;
	palette[0][8]  = 0xffff6d00;
	palette[0][9]  = 0xffff6000;
	// palette[10] = #ff5200;
	// palette[11] = #ff4500;

	// blues
	palette[1][0]  = 0xff00d7ff;
	palette[1][1]  = 0xff00caff;
	palette[1][2]  = 0xff00bcff;
	palette[1][3]  = 0xff00afff;
	palette[1][4]  = 0xff00a2ff;
	palette[1][5]  = 0xff0095ff;
	palette[1][6]  = 0xff0087ff;
	palette[1][7]  = 0xff007aff;
	palette[1][8]  = 0xff006dff;
	palette[1][9]  = 0xff0060ff;

	// greens
	palette[2][0]  = 0xff00d788;
	palette[2][1]  = 0xff00ca88;
	palette[2][2]  = 0xff00bc88;
	palette[2][3]  = 0xff00af88;
	palette[2][4]  = 0xff00a288;
	palette[2][5]  = 0xff009588;
	palette[2][6]  = 0xff008788;
	palette[2][7]  = 0xff007a88;
	palette[2][8]  = 0xff006d88;
	palette[2][9]  = 0xff006088;

	// pinks
	palette[3][0]  = 0xffffd7ff;
	palette[3][1]  = 0xffffcaff;
	palette[3][2]  = 0xffffbcff;
	palette[3][3]  = 0xffffafff;
	palette[3][4]  = 0xffffa2ff;
	palette[3][5]  = 0xffff95ff;
	palette[3][6]  = 0xffff87ff;
	palette[3][7]  = 0xffff7aff;
	palette[3][8]  = 0xffff6dff;
	palette[3][9]  = 0xffff6ff0;
}
int keyNumb = 10;
boolean[] keyBool = new boolean[keyNumb];

// ================================================================

public void keyboardSwitcher(char key){

		for (int i = 0; i < keyNumb; ++i) {
				keyBool[i] = false;
		}	

		switch (key) {
		case '1':
			keyBool[0] = !keyBool[0];
			break;
		case '2':
			keyBool[1] = !keyBool[1];
			break;
		case '3':
			keyBool[2] = !keyBool[2];
			break;
		case '4':
			keyBool[3] = !keyBool[3];
			break;
		case '5':
			keyBool[4] = !keyBool[4];
			break;
		case '6':
			keyBool[5] = !keyBool[5];
			break;
		case '7':
			keyBool[6] = !keyBool[6];
			break;
		case '8':
			keyBool[7] = !keyBool[7];
			break;
		case '9':
			keyBool[8] = !keyBool[8];
			break;
		case '0':
			keyBool[9] = !keyBool[9];
			break;
		}
}
public void keyboardMonitoSetup(){
	String[] args = {"Keyboard Monitor"};
	KeyMonitor keyDisplay = new KeyMonitor();
  PApplet.runSketch(args, keyDisplay);
}

// ================================================================

public class KeyMonitor extends PApplet {
  int stageM = 40;
  int rectS = 44;
  int keyPadding = 20;
  int monitorW = (stageM * 2) + (rectS * keyNumb) + (keyPadding * keyNumb) - keyPadding;
  int monitorH = (stageM * 2) + (rectS * 2);

  public void settings() {
    size(monitorW, monitorH);
  }

  // ================================================================

	int keyColor = 0xffFF006C;
	int baseColor = color(50, 200);

	// ================================================================
	
  public void draw() {
  	background(0xff432160);

    for (int i = 0; i < keyNumb; ++i) {
  		keyData(i);
      if(keyBool[i]) {
        fill(255);
        text(keyDesc[i], stageM, stageM + (rectS / 4));
      }
  	}
  }

  // ================================================================

  String[] keyDesc = new String[keyNumb];

  
  // ================================================================

  public void keyData(int index){
    keyLabels();
    int x = stageM + ((rectS + keyPadding) * index);
    int y = stageM + rectS;
     
    if(keyBool[index]){
      fill(keyColor);
    } else {
      blendMode(MULTIPLY);
      fill(baseColor);
      blendMode(BLEND);
    }
    rect(x, y , rectS, rectS);

    if(!keyBool[index]){
      fill(keyColor);
    } else {
      blendMode(MULTIPLY);
      fill(baseColor);
      blendMode(BLEND);
    }
    text(Integer.toString(index + 1), x + (rectS / 4), y + (rectS / 2) + (rectS / 4));
  }

  // ================================================================
  
  public void keyLabels(){
    keyDesc[0] = "Palette 1 \u2013 Oranges";
    keyDesc[1] = "Palette 2 \u2013 Blues";
    keyDesc[2] = "Palette 3 \u2013 Teals";
    keyDesc[3] = "Palette 4 \u2013 Pinks";
    keyDesc[4] = "Palette 5 \u2013 N/A";
    keyDesc[5] = "Palette 6 \u2013 N/A";
    keyDesc[6] = "Palette 7 \u2013 N/A";
    keyDesc[7] = "Palette 8 \u2013 N/A";
    keyDesc[8] = "Palette 9 \u2013 N/A";
    keyDesc[9] = "Palette 10 \u2013 N/A";
  }
}
 

// ================================================================

MidiBus myBus; 

// ================================================================

public void controllerChange(int channel, int number, int value) {  
	midiUpdate(channel, number, value);

  	// Receive a controllerChange
	  // println();
	  // println("Controller Change:");
	  // println("--------");
	  // println("Channel:" + channel);
	  // println("Number:" + number);
	  // println("Value:" + value);
}

// ================================================================


int knobNumb = 16;
int[] knob = new int[knobNumb];
String knobTable;

// ================================================================

public void midiSetup(){
  MidiBus.list(); 
  myBus = new MidiBus(this, 0, 1);
}

public void midiUpdate(int channel, int number, int value){
	if(number == 21) knob[0] = (int)map(value, 0, 127, 0, 100);
	if(number == 22) knob[1] = (int)map(value, 0, 127, 0, 100);
	if(number == 23) knob[2] = (int)map(value, 0, 127, 0, 100);
	if(number == 24) knob[3] = (int)map(value, 0, 127, 0, 100);
	if(number == 25) knob[4] = (int)map(value, 0, 127, 0, 100);
	if(number == 26) knob[5] = (int)map(value, 0, 127, 0, 100);
	if(number == 27) knob[6] = (int)map(value, 0, 127, 0, 100);
	if(number == 28) knob[7] = (int)map(value, 0, 127, 0, 100);
	if(number == 41) knob[8] = (int)map(value, 0, 127, 0, 100);
	if(number == 42) knob[9] = (int)map(value, 0, 127, 0, 100);
	if(number == 43) knob[10] = (int)map(value, 0, 127, 0, 100);
	if(number == 44) knob[11] = (int)map(value, 0, 127, 0, 100);
	if(number == 45) knob[12] = (int)map(value, 0, 127, 0, 100);
	if(number == 46) knob[13] = (int)map(value, 0, 127, 0, 100);
	if(number == 47) knob[14] = (int)map(value, 0, 127, 0, 100);
	if(number == 48) knob[15] = (int)map(value, 0, 127, 0, 100);
}

public void midiMonitor(){
	knobTable = "\n\n_________________________________________________________________________________________________________________________________\n|  001  |  002  |  003  |  004  |  005  |  006  |  007  |  008  |  009  |  010  |  011  |  012  |  013  |  014  |  015  |  016  |\n|  "+ String.format("%03d", knob[0]) +"  |  "+ String.format("%03d", knob[1]) +"  |  "+ String.format("%03d", knob[2]) +"  |  "+ String.format("%03d", knob[3]) +"  |  "+ String.format("%03d", knob[4]) +"  |  "+ String.format("%03d", knob[5]) +"  |  "+ String.format("%03d", knob[6]) +"  |  "+ String.format("%03d", knob[7]) +"  |  "+ String.format("%03d", knob[8]) +"  |  "+ String.format("%03d", knob[9]) +"  |  "+ String.format("%03d", knob[10]) +"  |  "+ String.format("%03d", knob[11]) +"  |  "+ String.format("%03d", knob[12]) +"  |  "+ String.format("%03d", knob[13]) +"  |  "+ String.format("%03d", knob[14]) +"  |  "+ String.format("%03d", knob[15]) +"  |\n_________________________________________________________________________________________________________________________________";
	println(knobTable);
}

// ================================================================

int padNumb = 8 * 2;
boolean[] pad = new boolean[padNumb];

// ================================================================

public void noteOn(int channel, int number, int value) {
	padSwitch(channel, number, value);

  // Receive a controllerChange
  // println();
  // println("Controller Change:");
  // println("--------");
  // println("Channel:" + channel);
  // println("Number:" + number);
  // println("Value:" + value);
}

public void padSwitch(int channel, int number, int value){

	if(arrow[2]) {
		for (int i = 0; i < padNumb; ++i) {
				pad[i] = false;
		}	
	}
	
	if(arrow[1]) {
		if(number ==  9) pad[8] = !pad[8];
		if(number == 10) pad[9] = !pad[9];
		if(number == 11) pad[10] = !pad[10];
		if(number == 12) pad[11] = !pad[11];
		if(number == 25) pad[12] = !pad[12];
		if(number == 26) pad[13] = !pad[13];
		if(number == 27) pad[14] = !pad[14];
		if(number == 28) pad[15] = !pad[15];
	} else {
		if(number ==  9) pad[0] = !pad[0];
		if(number == 10) pad[1] = !pad[1];
		if(number == 11) pad[2] = !pad[2];
		if(number == 12) pad[3] = !pad[3];
		if(number == 25) pad[4] = !pad[4];
		if(number == 26) pad[5] = !pad[5];
		if(number == 27) pad[6] = !pad[6];
		if(number == 28) pad[7] = !pad[7];
	}

	// NOTE: using this method is possibile to 
	// trigger midi pad colors read the documentation in order to understand how
	// https://d2xhy469pqj8rc.cloudfront.net/sites/default/files/novation/downloads/6958/launch-control-programmers-reference-guide.pdf
	// myBus.sendMessage(
 	//    new byte[] {
 	//      (byte)0xF0, (byte)0x1, (byte)0x2, (byte)0x3, (byte)0x4, (byte)0xF7
 	// }
  // );

	// padMonitor();
}

public void padMonitor(){
	print("  0: " + pad[0]);
	print("  1: " + pad[1]);
	print("  2: " + pad[2]);
	print("  3: " + pad[3]);
	print("  4: " + pad[4]);
	print("  5: " + pad[5]);
	print("  6: " + pad[6]);
	print("  7: " + pad[7] + "\n");
	print("  8: " + pad[8]);
	print("  9: " + pad[9]);
	print("  10: " + pad[10]);
	print("  11: " + pad[11]);
	print("  12: " + pad[12]);
	print("  13: " + pad[13]);
	print("  14: " + pad[14]);
	print("  15: " + pad[15] + "\n");
	println();
	println("____________________\n");
	println();
}

// ================================================================

int arrowNumb = 4;
boolean[] arrow = new boolean[arrowNumb];

// ================================================================

public void rawMidi(byte[] data) {
	int number = (int)(data[1] & 0xFF);
	int value = (int)(data[2] & 0xFF);

	arrowSwitch(number);
	// padSelector((int)(data[0] & 0xFF), number);

  // Receive some raw data
  // data[0] will be the status byte
  // data[1] and data[2] will contain the parameter of the message (e.g. pitch and volume for noteOn noteOff)
 //  println();
 //  println("Raw Midi Data:");
 //  println("--------");
 //  println("Status Byte/MIDI Command:"+(int)(data[0] & 0xFF));
	// println("Number: " + number);	
	// println("Value: " + value);	
}


public void arrowSwitch(int number){
	if(number == 114) arrow[0] = !arrow[0];
	if(number == 115) arrow[1] = !arrow[1];
	if(number == 116) arrow[2] = !arrow[2];
	if(number == 117) arrow[3] = !arrow[3];

	// arrowMonitor();
}

public void arrowMonitor(){
	print("  0: " + arrow[0]);
	print("  1: " + arrow[1]);
	print("  2: " + arrow[2]);
	print("  3: " + arrow[3]);
	println();
	println("____________________\n");
	println();
}


// ================================================================

int setNumb = 8;
boolean[] set = new boolean[padNumb];
  
// ================================================================

public void padSelector(int command, int number){
	// println("command: "+ command);

	// if(command == 247){

	// }

	// if(number ==  9) set[0] = !set[0];
	// if(number == 10) set[1] = !set[1];
	// if(number == 11) set[2] = !set[2];
	// if(number == 12) set[3] = !set[3];
	// if(number == 25) set[4] = !set[4];
	// if(number == 26) set[5] = !set[5];
	// if(number == 27) set[6] = !set[6];
	// if(number == 28) set[7] = !set[7];		

	// setMonitor();
}

public void setMonitor(){
	print("  - Set 0: " + set[0]);
	print("  - Set 1: " + set[1]);
	print("  - Set 2: " + set[2]);
	print("  - Set 3: " + set[3]);
	print("  - Set 4: " + set[4]);
	print("  - Set 5: " + set[5]);
	print("  - Set 6: " + set[6]);
	print("  - Set 7: " + set[7] + "\n");
	println();
	println("____________________\n");
	println();
}
public void midiMonitoSetup(){
	String[] args = {"Midi Monitor"};
	MidiMonitor sa = new MidiMonitor();
  PApplet.runSketch(args, sa);
}

// ================================================================

public class MidiMonitor extends PApplet {
 
  public void settings() {
    size(600, 860);
  }

  // ================================================================
 
	int stageM = 40;
	int barH = 44;
	int barPadding = 28;
	int textMargin = 8;
	
	// ================================================================

	int knobColor = 0xff50E3C2;
	int padColor = 0xffF78F1E;
	int baseColor = color(50, 200);

	// ================================================================
	
  public void draw() {
  	background(0xff432160);
		String controlOne = myBus.availableInputs()[0];
  	surface.setTitle("Midi Monitor \u2013 Controller: " +  controlOne);

  	for (int i = 0; i < knobNumb / 2; ++i) {
  		knobData(i);
  	}
  	for (int i = 0; i < padNumb / 2; ++i) {
  		padData(i);
  	}
  }

  // ================================================================

  String[] knobDesc = new String[knobNumb];
  String[] padDesc = new String[padNumb];

  // ================================================================

  public void knobData(int index){
    knobLabels();

  	fill(knobColor); noStroke();
  	String knobOneData = Integer.toString(knob[index]);
  	String knobTwoData = Integer.toString(knob[index + 8]);
  	String knobOneString = "Knob " + Integer.toString(index) + ": " + knobOneData;
  	String knobTwoString = "Knob " + Integer.toString(index + 8) + ": " + knobTwoData;

  	int x = stageM;
  	int y = stageM + (barH * index) + barPadding;
  	int rectW = (width / 2) - barPadding - stageM;
  	int rectH = 20;
  	int rectPerc = (int)map(knob[index], 0, 100, 0, rectW); 

    String knobOneDesc = knobDesc[index];
  	fill(baseColor);
		rect(x, y + (barPadding / 2) , rectW, rectH);
    fill(knobColor);
    rect(x, y + (barPadding / 2) , rectPerc, rectH);
    blendMode(DIFFERENCE);
    text(knobOneDesc, x + (barPadding / 4), y + (barPadding * 1));
    blendMode(BLEND);

    fill(255);
		text(knobOneString, x, y + textMargin);
		
    String knobTwoDesc = knobDesc[index + 8];
  	x = width / 2;
  	rectPerc = (int)map(knob[index + 8], 0, 100, 0, rectW); 

  	fill(baseColor);
    rect(x, y + (barPadding / 2), rectW, rectH);
    fill(knobColor);
    rect(x, y + (barPadding / 2), rectPerc, rectH);
    blendMode(DIFFERENCE);
    text(knobTwoDesc, x + (barPadding / 4), y + (barPadding * 1));
    blendMode(BLEND);

    fill(255);
		text(knobTwoString, x, y + textMargin);
  }

  // ================================================================
  
  public void knobLabels(){
    knobDesc[0] = "Controls waves height";
    knobDesc[1] = "Controls waves lenght";
    knobDesc[2] = "Controls lines number";
    knobDesc[3] = "Controls waves number";
    knobDesc[4] = "Controls noise speed";
    knobDesc[5] = "Controls **audioAmp** value";
    knobDesc[6] = "Controls **audioIndex** value";
    knobDesc[7] = "Controls **audioIndexStep** value";
    knobDesc[8] = "---";
    knobDesc[9] = "---";
    knobDesc[10] = "Controls neon's shifting";
    knobDesc[11] = "Controls neon's alpha";
    knobDesc[12] = "controls neon's y axis span from center ";
    knobDesc[13] = "Controls neon's widness";
    knobDesc[14] = "Controls audio factor on neons";
    knobDesc[15] = "Controls neons number";
  }

  // ================================================================
  
  public void padData(int index){
    padLabels();

  	fill(padColor); noStroke();
  	String padOneData = pad[index] ? "ON" : "OFF";
  	String padTwoData = pad[index + 8] ? "ON" : "OFF";
  	String padOneString = "Pad " + Integer.toString(index) + ": " + padOneData;
  	String padTwoString = "Pad " + Integer.toString(index + 8) + ": " + padTwoData;

  	int x = stageM;
  	int y = stageM + (barH * index) + barPadding + (height / 2);
  	int rectW = (width / 2) - barPadding - stageM;
  	int rectH = 20;
     
    if(pad[index]){
      fill(padColor);
    } else {
      fill(baseColor);
    }
    rect(x, y + (barPadding / 2) , rectW, rectH);
    String padOneDesc = padDesc[index];

    if(!pad[index]){
      fill(padColor);
    } else {
      fill(baseColor);
    }
    text(padOneDesc, x + (barPadding / 4), y + (barPadding * 1));

		fill(255);
		text(padOneString, x, y + textMargin);
		
  	x = width / 2;

  	if(pad[index + 8]){
  		fill(padColor);
  	} else {
  		fill(baseColor);
  	}
		rect(x, y + (barPadding / 2), rectW, rectH);

    String padTwoDesc = padDesc[index + 8];
    if(!pad[index + 8]){
      fill(padColor);
    } else {
      fill(baseColor);
    }
    text(padTwoDesc, x + (barPadding / 4), y + (barPadding * 1));

		fill(255);
		text(padTwoString, x, y + textMargin);
  }

  // ================================================================
  
  public void padLabels(){
    padDesc[0] = "Triggers waves fill color"; 
    padDesc[1] = "Triggers wave curve"; 
    padDesc[2] = "Triggers wave slider"; 
    padDesc[3] = "Triggers wave fader"; 
    padDesc[4] = "---"; 
    padDesc[5] = "---"; 
    padDesc[6] = "---"; 
    padDesc[7] = "---"; 
    padDesc[8] = "Triggers Wave scene"; 
    padDesc[9] = "Triggers Multiple Wave scene"; 
    padDesc[10] = "Triggers Neon scene";
    padDesc[11] = "Triggers neons height";
    padDesc[12] = "Triggers neons multicolor";
    padDesc[13] = "Triggers neons multicolor specular";
    padDesc[14] = "Triggers vertically shifted neons ";
    padDesc[15] = "---";
  }

  // ================================================================
  public void keyPressed(){	
		switch (key) {
			case 'q':
				exit();
				break;
		}
	}

}

int neons = 100;
int maxNeons = 30;

// ================================================================
			
public void renderNeons() {
	neons = (int)map(knob[15], 0, 100, 0, maxNeons);
	setColors();
	float wide = map(knob[13], 0, 100, 0, width / 2);
	float span = map(knob[12], 0, 100, 0, height / 2);

	for (int i = 0; i < neons; ++i) {

		float x1 = 0 + wide;
		float xCenter = width / 2;
		float x3 = width - wide;

		noiseUpdate();
		float positioner = n * i;
		float y;

		if(pad[11]) {
			y = height / 2;
		} else {
			y = random((height / 2) - span, (height / 2) + span);
		}
		// multiple y values for vertical shifting 
		float y1 = y;
		float y2 = y;

		if(pad[14]) {
			float shifter = map(knob[10], 0, 100, 0, height * .625f);
			y1 = y - shifter; 
			y2 = y + shifter;
		}

		float factor = map(knob[14], 0, 100, 0, 10);
		float minRand = audioData[i * (audioRange / maxNeons)];
		float maxRand = audioData[i * (audioRange / maxNeons)] * 50;

		// Fix strokes for orizontal disappear
		float thickness = wide == width / 2 ? 0 : random(minRand, maxRand) * factor;
		int alpha = constrain((int)thickness, 125, 255);
		int painter = color(255, alpha);
		int index = i % lines;

		if(pad[12]) {
			int colorSelector = (int)random(0, 3);
			painter = palette[colorSelector][index];
		}

		float xDifference = 0;
		if(wide > 0){
			xDifference = thickness;
		}

		float alphaControl = map(knob[11], 0, 100, 50, 255);
		// stroke(255);
		fill(painter, alphaControl); noStroke();
		beginShape();
			vertex(x1 - xDifference, y1 - thickness / 2);
			vertex(xCenter, y1 - thickness / 2);
			vertex(xCenter, y1 + thickness / 2);
			vertex(x1 - xDifference, y1 + thickness / 2);

		endShape();

		// Double the color splitted vertically 
		// Active only if multicolor is on 
		if(pad[13] && pad[12]) {
			int colorSelector = (int)random(0, 3);
			painter = palette[colorSelector][index];
		}

		fill(painter, alphaControl);
		beginShape();
			vertex(x3 + xDifference, y2 + thickness / 2);
			vertex(xCenter, y2 + thickness / 2);
			vertex(xCenter, y2 - thickness / 2);
			vertex(x3 + xDifference, y2 - thickness / 2);
		endShape();
	}

}

// ================================================================

float xoff = 0.0f;
float n;
float noiseSpeed = .01f;

// ================================================================

public void noiseUpdate(){
	noiseSpeed = map(knob[4], 0, 100, .01f, 1);
 	xoff += noiseSpeed;
  n = noise(xoff);
}
int section = audioRange;
PVector[] ants = new PVector[section];

boolean fader = false;

// ================================================================

public void renderWaves() {
	if(pad[3]) {
		fader = !fader;
	}

	if(fader){
		for (int i = 1; i < lines; ++i) {
			colorizer(i, false, 0);
			shapeFormer(i, lines, 0);
		}
	} else {
			colorizer(1, false, 0);
			shapeFormer(0, lines, 0);
	}
}

public void renderMultipleWaves() {
	
	if(pad[3]) {
		fader = !fader;
	}

	for (int i = 1; i < lines; ++i) {
		if(fader){
			for (int j = 0; j < colorNumb; ++j) {				
				int diff = 20 * j;
				colorizer(i, true, j);
				shapeFormer(i, lines, diff);
			}
		} else {
			for (int j = 0; j < colorNumb; ++j) {				
				int diff = 20 * j;
				colorizer(i, true, j);
				shapeFormer(0, lines, diff);
			}
		}
	}
}

// ================================================================

float margin = 0;
boolean move = false;

// ================================================================


public void shapeFormer(int line, int maxLines, int diff){
	int audioIndex;
	strokeWeight(1);
	// make the scene slide
	if(pad[2]) {
		move = !move;
	}

	if(move){
		audioIndex = frameCount % audioRange;
	} else {
		audioIndex = 0;
	}

	margin = map(knob[1], 0, 100, 0, 500);
	beginShape();
	vertex(-margin, height / 2);

	int points = (int)map(knob[3], 0, 100, 2, audioRange);

	for (int j = 0; j < points; ++j) {
		float step = (width / points);
		float xSpread = (j + 1 + diff) * step;
		float x = (j + 1) * step;

		float audioDiff = audioData[audioIndex];
		float padding = height / 8;

		float distance = map(knob[0], 0, 100, 0, .5f);
		float mover = height * distance;
		float audioCalc = map(audioDiff, 1, 2, mover, -mover); 
		float fade = audioCalc / maxLines;
		float fader = audioCalc - ((maxLines - line) * fade);
		float y;
		y = (height / 2) + audioCalc - fader;
		if(audioDiff == 1) y = (height / 2);

		ants[j] = new PVector(x, y);
		curveVertex(ants[j].x, ants[j].y);
		if(pad[1]){
			vertex(ants[j].x, ants[j].y);
		}
		if(move){
			audioIndex = (audioIndex + (audioRange / points)) % audioRange;
		} else {
			audioIndex += audioRange / points;
		}

		audioIndex = (audioIndex + diff) % audioRange;	
	}

	vertex(width + margin, height / 2);
	endShape();

	audioIndex = 0;
}

// ================================================================
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
