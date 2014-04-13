package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Map;

public enum PowerHandSuit {

	// TWO PAIRS
	TWO_PAIRS_HIGHT("TTJJ", "TTQQ", "TTKK", "JJQQ", "JJKK", "QQKK", "??AA"),
	TWO_PAIRS_MEDIUM("??TT", "??JJ", "??QQ", "??KK"),
	TWO_PAIRS_LOW("AAKK", "AAQQ", "AAJJ", "AATT", "KKQQ", "KKTT", "QQJJ", "QQTT", "JJTT"),
	
//	// TWO COLORS
//	TWO_COLORS_HIGHT("A?A?"),
//	TWO_COLORS_MEDIUM("K?A?"),
//	TWO_COLORS_LOW("A?A?"),
	
	// SEQUENCE de 4
	// par les 2 bouts	
	SEQUENCE4_HIGHT("2345", "3456", "4567", "5678", "6789", "789T", "89TJ", "9TJQ", "TJQK"),
	// par un seul bout
	SEQUENCE4_MEDIUM("234A", "JQKA"),
	
	// SEQUENCE de 3
	SEQUENCE3_HIGHT("?345", "?456", "?567", "?678", "?789", "?89T", "?9TJ", "?TJQ", "?JQK"),
	SEQUENCE3_MEDIUM("AKQ?", "?32A"),
	
	NO_POWER();

	private final String[] arrayHands;

	private static final Map<String, PowerHandSuit> mapHands = new
			HashMap<String, PowerHandSuit>();

	private PowerHandSuit(String... arrayHands) {
		this.arrayHands = arrayHands;
	}

	public String[] getArrayHands() {
		return arrayHands;
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PowerHandSuit powerHand : values()) {
			for (int i = 0; i < powerHand.getArrayHands().length; i++) {
				mapHands.put(powerHand.getArrayHands()[i], powerHand);
			}
		}
	}

	// récupération de l'instance
	public static PowerHandSuit fromHand(String hand) {
		final PowerHandSuit value = mapHands.get(hand);
		if (value != null) {
			return value;
		}
		
		return NO_POWER;
		//throw new IllegalArgumentException();
	}
}