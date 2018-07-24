void midiMonitoSetup(){
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

	color knobColor = #50E3C2;
	color padColor = #F78F1E;
	color baseColor = color(50, 200);

	// ================================================================
	
  public void draw() {
  	background(#432160);
		String controlOne = myBus.availableInputs()[0];
  	surface.setTitle("Midi Monitor – Controller: " +  controlOne);

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

  void knobData(int index){
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
  
  void knobLabels(){
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
  
  void padData(int index){
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
  
  void padLabels(){
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