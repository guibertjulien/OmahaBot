package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public enum PreFlopSuit {
	
	// TWO COLORS
	TWO_COLORS("sshh","ssdd","sscc","hhdd","hhcc", "ddcc"),
	
	// ONE COLOR
	ONE_COLOR("ss..","hh..",".hh.",".dd.", "..dd","..cc"),

	// NO COLOR
	NO_COLOR("shdc"),
	
	NO_POWER();

	private final String[] arrayHand;

	private static final Map<String, PreFlopSuit> mapHand = new
			HashMap<String, PreFlopSuit>();

	private PreFlopSuit(String... arrayHand) {
		this.arrayHand = arrayHand;
	}

	public String[] getArrayHand() {
		return arrayHand;
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PreFlopSuit preFlopSuit : values()) {
			for (int i = 0; i < preFlopSuit.getArrayHand().length; i++) {
				mapHand.put(preFlopSuit.getArrayHand()[i], preFlopSuit);
			}
		}
	}

	// récupération de l'instance
	public static PreFlopSuit fromHand(String hand) {
		PreFlopSuit value = mapHand.get(hand);
		
		if (value == null) {
			for (Iterator i = mapHand.entrySet().iterator(); i.hasNext();) {
				Entry couple = (Entry) i.next();

				String handPattern = (String) couple.getKey();
				PreFlopSuit powerHandSuitValue = (PreFlopSuit) couple.getValue();

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