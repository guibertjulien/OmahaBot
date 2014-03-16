package com.omahaBot.enums;

import java.awt.Color;

public enum Suit {

	HEART("h", new Color(122, 48, 46)),//red
	SPADE("s", new Color(59, 59, 59)),//black 
	DIAMOND("d", new Color(66, 96, 106)),//blue
	CLUB("c", new Color(76, 118, 47)),//green
	UNKNOW("?", null);

	private String shortName;

	private Color pixelColor;
	
	public static int PIXEL_POSX = 49;
	
	public static  int PIXEL_POSY = 20;

	private Suit(String shortName, Color pixelColor) {
		this.shortName = shortName;
		this.pixelColor = pixelColor;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Color getPixelColor() {
		return pixelColor;
	}

	public void setPixelColor(Color pixelColor) {
		this.pixelColor = pixelColor;
	}

}
