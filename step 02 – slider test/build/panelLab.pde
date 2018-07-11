import controlP5.*;

// ================================================================

String[] args = {"TwoFrameTest"};
SecondApplet sa = new SecondApplet();

// ================================================================

ControlP5 sliders;
ControlP5 button;
// ================================================================

void panelSetup(){
}

// ================================================================

void panelRender(){
  PApplet.runSketch(args, sa);
}

// ================================================================

int barW = 232;
int barH = 60;
int paddingP = 12;

// ================================================================

int panelW = 280;
int panelH = 400;
int panelM = 24;

color paneBg = #2F2F2F;

// ================================================================

public class SecondApplet extends PApplet {

  public void settings() {
    size(panelW, panelH);
  }

  public void setup(){
  	surface.setResizable(true);
  }

  public void draw() {
  	// background(paneBg);
		controlPanel();
		stroke(255);
		ellipse(0, frameCount, 10, 10);
  }

  public void controlPanel(){
		sliders = new ControlP5(sa);

		sliders
	    .addSlider("xMultiplier")
	    .setPosition(panelM, panelM)
	    .setSize(barW, barH)
	    .setRange(10, 100)
	    .setValue(25)
	    .setColorCaptionLabel(color(200));

		sliders
	    .addSlider("yMultiplier")
	    .setPosition(panelM, (panelM + paddingP) + barH)
	    .setSize(barW, barH)
	    .setRange(0, 50)
	    .setValue(12)
	    .setColorCaptionLabel(color(200));
	}
}
