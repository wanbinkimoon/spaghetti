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

int stageW      = 1200;
int stageH      = 400;
int bgC       = 0xff2F2F2F;
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

public void settings(){ 
	pixelDensity(displayDensity());	
	fullScreen();
	// size(stageW, stageH);

}

// ================================================================

public void setup() {
	midiSetup();
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

	audioDataUpdate();
	audioMidiValueUpdate();

	if(showHint) {
		renderHints();
	}

	if(audioPanel) {
		audioPanel();
	}

	if(!pad[8]) {
		renderCircles();
	} 
	if(pad[9]) {
		renderNeons();
	}
}

// ================================================================

public void keyPressed(){	
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

public void screenShot(){
	letsRender = true;
	if (letsRender) {
		letsRender = false;
		save(renderPATH + renderNum + ".png");
		renderNum++;
	}
}

// ================================================================

public void showHelp(){
	showHint = !showHint;
}

public void showAudioPanel(){
	audioPanel = !audioPanel;
}


// ================================================================

public void renderHints(){
	fill(75, 200); noStroke();
	rect(0, 0, width, 48);
	fill(0xff00AEFF);
	textAlign(LEFT);
	textSize(16);
	String FPS =  String.format("%.2f", frameRate);
	String helpString = "FPS: " + FPS + "    Q: Quit    S: Save screenshot in ./render folder    A: Audio panel control";
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
int section = audioRange;
PVector[] ants = new PVector[section];
int lines = 10;
boolean fader = false;

// ================================================================

public void renderCircles() {
	if(pad[3]) {
		fader = !fader;
	}
	if(fader){
		for (int i = 1; i < lines; ++i) {
			colorizer(i);
			shapeFormer(i, lines);
		}
	} else {
			colorizer(0);
			shapeFormer(0, lines);
	}

}

// ================================================================

float margin = 0;
boolean move = false;

// ================================================================


public void shapeFormer(int line, int maxLines){
	int audioIndex;
	
	// make the scene slide
	if(pad[2]) {
		move = !move;
	}

	if(move){
		audioIndex = frameCount % audioRange;
	} else {
		audioIndex = 0;
	}

	margin = map(knob[2], 0, 100, 0, 500);
	beginShape();
	vertex(-margin, height / 2);

	int points = (int)map(knob[8], 0, 100, 2, audioRange);

	for (int j = 0; j < points; ++j) {
		float step = (width / points);
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
	}

	vertex(width + margin, height / 2);
	endShape();

	audioIndex = 0;
}

// ================================================================

int[] palette = new int[lines];

// ================================================================

public void colorizer(int index){
	colorChoice();
	stroke(palette[index]); noFill();
	if(pad[0]) {
		float alpha = map(knob[9], 0, 100, 0, 255);
		noStroke(); fill(palette[index], alpha);
	}
}

// ================================================================

public void colorChoice(){
	// reds
	palette[0]  = 0xffffd700;
	palette[1]  = 0xffffca00;
	palette[2]  = 0xffffbc00;
	palette[3]  = 0xffffaf00;
	palette[4]  = 0xffffa200;
	palette[5]  = 0xffff9500;
	palette[6]  = 0xffff8700;
	palette[7]  = 0xffff7a00;
	palette[8]  = 0xffff6d00;
	palette[9]  = 0xffff6000;
	// palette[10] = #ff5200;
	// palette[11] = #ff4500;

	if(pad[7]){
		// blues
		palette[0]  = 0xff00d7ff;
		palette[1]  = 0xff00caff;
		palette[2]  = 0xff00bcff;
		palette[3]  = 0xff00afff;
		palette[4]  = 0xff00a2ff;
		palette[5]  = 0xff0095ff;
		palette[6]  = 0xff0087ff;
		palette[7]  = 0xff007aff;
		palette[8]  = 0xff006dff;
		palette[9]  = 0xff0060ff;
	}

	if(pad[6]){
		// greens
		palette[0]  = 0xff00d788;
		palette[1]  = 0xff00ca88;
		palette[2]  = 0xff00bc88;
		palette[3]  = 0xff00af88;
		palette[4]  = 0xff00a288;
		palette[5]  = 0xff009588;
		palette[6]  = 0xff008788;
		palette[7]  = 0xff007a88;
		palette[8]  = 0xff006d88;
		palette[9]  = 0xff006088;
	}

	if(pad[5]){
		// pinks
		palette[0]  = 0xffffd7ff;
		palette[1]  = 0xffffcaff;
		palette[2]  = 0xffffbcff;
		palette[3]  = 0xffffafff;
		palette[4]  = 0xffffa2ff;
		palette[5]  = 0xffff95ff;
		palette[6]  = 0xffff87ff;
		palette[7]  = 0xffff7aff;
		palette[8]  = 0xffff6dff;
		palette[9]  = 0xffff6ff0;
	}
}
 

// ================================================================

MidiBus myBus; 

// ================================================================

public void controllerChange(int channel, int number, int value) {  
	midiUpdate(channel, number, value);

	if(DEBUG && MIDI) {
  	// Receive a controllerChange
	  println();
	  println("Controller Change:");
	  println("--------");
	  println("Channel:" + channel);
	  println("Number:" + number);
	  println("Value:" + value);
	}
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

int neons = 100;

// ================================================================

public void renderNeons() {
	for (int i = 0; i < neons; ++i) {
		float x1 = 0;
		float x2 = width;
		float y1 = random(0, height);
		float y2 = random(0, height);

		stroke(255);
		beginShape();
			vertex(x1, y1);
			vertex(x2, y2);
		endShape();
	}
}

float xoff = 0.0f;
float n;
float noiseSpeed = .01f;

// ================================================================

public void noiseUpdate(){
 	xoff += noiseSpeed;
  n = noise(xoff);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
