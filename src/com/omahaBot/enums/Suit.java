package com.omahaBot.enums;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.omahaBot.consts.PixelConsts;

public enum Suit {

	SPADE("s", PixelConsts.SUIT_SPADE),
	HEART("h", PixelConsts.SUIT_HEART),
	DIAMOND("d", PixelConsts.SUIT_DIAMOND),
	CLUB("c", PixelConsts.SUIT_CLUB), // green
	UNKNOW("?", null);

	public static String ALL_SUIT = "shdc";
	
	private static final Map<String, Suit> map = new
			HashMap<String, Suit>();

	// map permettant de récupérer ke power avec une hand
	static {
		for (Suit suit : values()) {
			map.put(suit.getShortName(), suit);
		}
	}

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

	public static Suit fromShortName(String shortName) {
		final Suit value = map.get(shortName);
		if (value != null) {
			return value;
		}

		throw new IllegalArgumentException();
	}

}
