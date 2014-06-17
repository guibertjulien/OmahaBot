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
	@Deprecated
	public static Rank fromShortName(String shortName) {
		final Rank value = map.get(shortName);
		if (value != null) {
			return value;
		}

		throw new IllegalArgumentException();
	}

	// récupération de l'instance
	public static Rank fromShortName(char shortName) {
		final Rank value = map.get(String.valueOf(shortName));
		if (value != null) {
			return value;
		}

		throw new IllegalArgumentException();
	}
	
	public boolean isConnected(Rank o) {
		// cas particulier A2
		if (this.equals(Rank.ACE) && o.equals(Rank.TWO)) {
			return true;
		}
		else {
			return (this.ordinal() - o.ordinal() == -1);
		}
	}
	
	public int diff(Rank o) {
		// cas particulier A2
		if (this.equals(Rank.ACE) && o.equals(Rank.TWO)) {
			return 1;
		}
		if (this.equals(Rank.ACE) && o.equals(Rank.THREE)) {
			return 2;
		}
		if (this.equals(Rank.ACE) && o.equals(Rank.FOUR)) {
			return 3;
		}
		if (this.equals(Rank.ACE) && o.equals(Rank.FIVE)) {
			return 4;
		}
		else {
			return (o.ordinal() - this.ordinal());
		}
	}
}
