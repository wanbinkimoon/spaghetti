void keyboardMonitoSetup(){
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

	color keyColor = #FF006C;
	color baseColor = color(50, 200);

	// ================================================================
	
  public void draw() {
  	background(#432160);

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

  void keyData(int index){
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
  
  void keyLabels(){
    keyDesc[0] = "Palette 1 – Oranges";
    keyDesc[1] = "Palette 2 – Blues";
    keyDesc[2] = "Palette 3 – Teals";
    keyDesc[3] = "Palette 4 – Pinks";
    keyDesc[4] = "Palette 5 – N/A";
    keyDesc[5] = "Palette 6 – N/A";
    keyDesc[6] = "Palette 7 – N/A";
    keyDesc[7] = "Palette 8 – N/A";
    keyDesc[8] = "Palette 9 – N/A";
    keyDesc[9] = "Palette 10 – N/A";
  }
}