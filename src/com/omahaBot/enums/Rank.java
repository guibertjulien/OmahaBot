package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Cf. GUAVA
 * 
 * @author Julien
 * 
 */
public enum Rank {

	// !!! do not modify order
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	HEIGHT("8"),
	NINE("9"),
	TEN("T"),
	JACK("J"),
	QUEEN("Q"),
	KING("K"),
	ACE("A"),
	UNKNOWN("?");
	
	public static String TEN_SCANED = "10";

	private static final Map<String, Rank> map = new
			HashMap<String, Rank>();

	// map permettant de récupérer ke power avec une hand
	static {
		for (Rank rank : values()) {
			map.put(rank.getShortName(), rank);
		}
	}

	private String shortName;

	private Rank(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	// récupération de l'instance
	public static Rank fromShortName(String shortName) {
		final Rank value = map.get(shortName);
		if (value != null) {
			return value;
		}

		throw new IllegalArgumentException();
	}
}
