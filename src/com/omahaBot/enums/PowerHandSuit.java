package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public enum PowerHandSuit {
	
	// TWO COLORS
	TWO_COLORS("sshh","ssdd","sscc","hhdd","hhcc", "ddcc"),
	
	// ONE COLOR
	ONE_COLOR("ss..","hh..",".hh.",".dd.", "..dd","..cc"),

	// ZERO COLOR
	ZERO_COLOR("shdc"),
	
	NO_POWER();

	private final String[] arrayHand;

	private static final Map<String, PowerHandSuit> mapHand = new
			HashMap<String, PowerHandSuit>();

	private PowerHandSuit(String... arrayHand) {
		this.arrayHand = arrayHand;
	}

	public String[] getArrayHand() {
		return arrayHand;
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PowerHandSuit powerHandSuit : values()) {
			for (int i = 0; i < powerHandSuit.getArrayHand().length; i++) {
				mapHand.put(powerHandSuit.getArrayHand()[i], powerHandSuit);
			}
		}
	}

	// récupération de l'instance
	public static PowerHandSuit fromHand(String hand) {
		PowerHandSuit value = mapHand.get(hand);
		
		if (value == null) {
			for (Iterator i = mapHand.entrySet().iterator(); i.hasNext();) {
				Entry couple = (Entry) i.next();

				String handPattern = (String) couple.getKey();
				PowerHandSuit powerHandSuitValue = (PowerHandSuit) couple.getValue();

				if (Pattern.matches(handPattern, hand)) {
					value = powerHandSuitValue;
				}
			}
		}
		
		if (value != null) {
			return value;
		}

		return NO_POWER;
	}
}