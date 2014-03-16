package com.omahaBot.enums;

import java.awt.Color;

import com.omahaBot.consts.PixelConsts;

public enum Suit {

	HEART("h", PixelConsts.SUIT_HEART),
	SPADE("s", PixelConsts.SUIT_SPADE), 
	DIAMOND("d", PixelConsts.SUIT_DIAMOND),
	CLUB("c", PixelConsts.SUIT_CLUB),//green
	UNKNOW("?", null);

	private String shortName;

	private Color pixelColor;

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
