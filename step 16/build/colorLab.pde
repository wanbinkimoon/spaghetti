int colorNumb = 4;
int lines = 10;
color[][] palette = new color[colorNumb][lines];

// ================================================================

void colorizer(int index, boolean multiple, int set){
	color nowColor = #FFFFFF;
	setColors();

	if(multiple){
		nowColor = palette[set][index];
	} else {
		if(pad[15]) {
			nowColor = palette[0][index];
		} else if(pad[14]) {
			nowColor = palette[1][index];
		} else if(pad[13]) {
			nowColor = palette[2][index];
		} else if(pad[12]) {
			nowColor = palette[3][index];
		}
	}

	stroke(nowColor); noFill();
	if(pad[0]) {
		float alpha = map(knob[9], 0, 100, 0, 255);
		noStroke(); fill(nowColor, alpha);
	}
}

// ================================================================

void setColors(){
	// reds
	palette[0][0]  = #ffd700;
	palette[0][1]  = #ffca00;
	palette[0][2]  = #ffbc00;
	palette[0][3]  = #ffaf00;
	palette[0][4]  = #ffa200;
	palette[0][5]  = #ff9500;
	palette[0][6]  = #ff8700;
	palette[0][7]  = #ff7a00;
	palette[0][8]  = #ff6d00;
	palette[0][9]  = #ff6000;
	// palette[10] = #ff5200;
	// palette[11] = #ff4500;

	// blues
	palette[1][0]  = #00d7ff;
	palette[1][1]  = #00caff;
	palette[1][2]  = #00bcff;
	palette[1][3]  = #00afff;
	palette[1][4]  = #00a2ff;
	palette[1][5]  = #0095ff;
	palette[1][6]  = #0087ff;
	palette[1][7]  = #007aff;
	palette[1][8]  = #006dff;
	palette[1][9]  = #0060ff;

	// greens
	palette[2][0]  = #00d788;
	palette[2][1]  = #00ca88;
	palette[2][2]  = #00bc88;
	palette[2][3]  = #00af88;
	palette[2][4]  = #00a288;
	palette[2][5]  = #009588;
	palette[2][6]  = #008788;
	palette[2][7]  = #007a88;
	palette[2][8]  = #006d88;
	palette[2][9]  = #006088;

	// pinks
	palette[3][0]  = #ffd7ff;
	palette[3][1]  = #ffcaff;
	palette[3][2]  = #ffbcff;
	palette[3][3]  = #ffafff;
	palette[3][4]  = #ffa2ff;
	palette[3][5]  = #ff95ff;
	palette[3][6]  = #ff87ff;
	palette[3][7]  = #ff7aff;
	palette[3][8]  = #ff6dff;
	palette[3][9]  = #ff6ff0;
}