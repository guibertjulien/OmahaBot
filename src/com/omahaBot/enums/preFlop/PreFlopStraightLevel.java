package com.omahaBot.enums.preFlop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreFlopStraightLevel {

	// FOUR CONNECTORS
	// par les 2 bouts
	CONNECTOR_OUTSIDE(PreFlopPowerPoint.MAX, "2345", "3456", "4567", "5678",
			"6789", "789T", "89TJ", "9TJQ", "TJQK"),
	// par un seul bout
	CONNECTOR4_INSIDE(PreFlopPowerPoint.HIGH, "234A", "JQKA"),

	// THREE CONNECTORS
	// par les 2 bouts
	CONNECTOR3_OUTSIDE(PreFlopPowerPoint.MEDIUM, "234", "345", "456", "567", "678",
			"789", "89T", "9TJ", "TJQ", "JQK"),
	// par un seul bout
	CONNECTOR3_INSIDE(PreFlopPowerPoint.LOW, "AKQ", "23.A"),

	// TWO CONNECTOR OR NO_CONNECTOR
	NO_CONNECTOR(PreFlopPowerPoint.MIN);

	private final PreFlopPowerPoint powerPoint;

	private final String[] arrayHand;

	private static final Map<String, PreFlopStraightLevel> mapHandConnector = new
			HashMap<String, PreFlopStraightLevel>();

	private PreFlopStraightLevel(PreFlopPowerPoint powerPoint, String... arrayHands) {
		this.powerPoint = powerPoint;
		this.arrayHand = arrayHands;
	}

	public PreFlopPowerPoint getPowerPoint() {
		return powerPoint;
	}

	public String[] getArrayHand() {
		return arrayHand;
	}

	// map permettant de récupérer le power avec une hand
	static {
		for (PreFlopStraightLevel preFlopConnectorLevel : values()) {
			for (int i = 0; i < preFlopConnectorLevel.getArrayHand().length; i++) {
				mapHandConnector.put(preFlopConnectorLevel.getArrayHand()[i], preFlopConnectorLevel);
			}
		}
	}

	// récupération de l'instance
	public static PreFlopStraightLevel fromTypeAndHand(String hand) {
		PreFlopStraightLevel value = null;

		value = checkPattern(hand, mapHandConnector);

		if (value != null) {
			return value;
		}

		return NO_CONNECTOR;
		// throw new IllegalArgumentException();
	}

	private static PreFlopStraightLevel checkPattern(String hand, Map<String, PreFlopStraightLevel> map) {
		PreFlopStraightLevel value;
		value = map.get(hand);

		if (value == null) {
			for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
				Entry couple = (Entry) i.next();

				String handPattern = (String) couple.getKey();
				PreFlopStraightLevel preFlopConnectorLevel = (PreFlopStraightLevel) couple.getValue();

				Pattern pattern = Pattern.compile(handPattern);
				Matcher matcher = pattern.matcher(hand);

				if (matcher.find()) {
					value = preFlopConnectorLevel;
				}
			}
		}
		return value;
	}
}