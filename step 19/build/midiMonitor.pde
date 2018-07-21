void midiMonitoSetup(){
	String[] args = {"Midi Monitor"};
	SecondApplet sa = new SecondApplet();
  PApplet.runSketch(args, sa);
}

// ================================================================

public class SecondApplet extends PApplet {
 
  public void settings() {
    size(600, 800);
  }

  // ================================================================
 
	int stageM = 40;
	int barH = 40;
	int barPadding = 16;
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

  void knobData(int index){
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
  	fill(baseColor);
		rect(x, y + (barPadding / 2) , rectW, rectH);
  	fill(knobColor);
		rect(x, y + (barPadding / 2) , rectPerc, rectH);
		text(knobOneString, x, y + textMargin);
		
  	x = width / 2;
  	rectPerc = (int)map(knob[index + 8], 0, 100, 0, rectW); 
  	fill(baseColor);
		rect(x, y + (barPadding / 2), rectW, rectH);
  	fill(knobColor);
		rect(x, y + (barPadding / 2), rectPerc, rectH);
		text(knobTwoString, x, y + textMargin);
  }

  // ================================================================
  
  void padData(int index){
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
		fill(padColor);
		text(padOneString, x, y + textMargin);
		
  	x = width / 2;

  	if(pad[index + 8]){
  		fill(padColor);
  	} else {
  		fill(baseColor);
  	}
		rect(x, y + (barPadding / 2), rectW, rectH);
		fill(padColor);
		text(padTwoString, x, y + textMargin);
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