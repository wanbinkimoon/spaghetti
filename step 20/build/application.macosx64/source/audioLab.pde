
import ddf.minim.*;
import ddf.minim.analysis.*;

// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;

// ================================================================

int audioRange  = 128;
int audioMax = 100;

float audioAmp = 69.0;
float audioIndex = 0.09;
float audioIndexStep = 0.350;
float audioIndexAmp = audioIndex;

float[] audioData = new float[audioRange];

// ================================================================

void audioSettings(){
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

void audioDataUpdate(){
  audioFFT.forward(audio.mix);
  updateAudio();
}

// ================================================================

  void updateAudio(){
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
  
  void audioMidiValueUpdate(){
    if(arrow[3]) {
      audioAmp = map(knob[5], 0, 100, 20.0, 200.1);
      audioIndex = map(knob[6], 0, 100, 0.0, 0.1);
      audioIndexStep = map(knob[7], 0, 100, 0.0, 0.5);
    }
  }

  void audioPanel(){
    fill(75, 200); noStroke();
    rect(0, 0, width, 48);
    fill(#00AEFF);
    textAlign(LEFT);
    textSize(16);
    String helpString = "AMP: " + audioAmp + " – INDEX: " + audioIndex + " – STEP: " + audioIndexStep;
    text(helpString, 12, 28);
  }