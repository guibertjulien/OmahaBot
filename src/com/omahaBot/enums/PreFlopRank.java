package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreFlopRank {

	// TWO PAIRS
	TWO_PAIR_HIGHT(HandType.TWO_PAIR, "TTJJ", "TTQQ", "TTKK", "JJQQ", "JJKK", "QQKK", "..AA"),
	TWO_PAIR_MEDIUM(HandType.TWO_PAIR, "..TT", "..JJ", "..QQ", "..KK"),
	TWO_PAIR_LOW(HandType.TWO_PAIR, "..99", "..88", "..77", "..66", "..55", "..44", "..33"),

	// ONE PAIR
	ONE_PAIR_HIGHT(HandType.ONE_PAIR, "AA"),
	ONE_PAIR_MEDIUM(HandType.ONE_PAIR, "KK", "QQ", "JJ", "TT"),
	ONE_PAIR_LOW(HandType.ONE_PAIR, "99", "88", "77", "66", "55", "44", "33", "22"),

	// FOUR CONNECTORS
	// par les 2 bouts
	CONNECTOR4_HIGHT(HandType.CONNECTOR, "2345", "3456", "4567", "5678", "6789", "789T", "89TJ", "9TJQ", "TJQK"),
	// par un seul bout
	CONNECTOR4_MEDIUM(HandType.CONNECTOR, "234A", "JQKA"),

	// THREE CONNECTORS
	// par les 2 bouts
	CONNECTOR3_HIGHT(HandType.CONNECTOR, "234", "345", "456", "567", "678", "789", "89T", "9TJ", "TJQ", "JQK"),
	// par un seul bout
	CONNECTOR3_MEDIUM(HandType.CONNECTOR, "AKQ", "23.A"),

	NO_POWER(HandType.NO_TYPE);

	private final String[] arrayHand;

	private final HandType handType;

	private static final Map<String, PreFlopRank> mapHand = new
			HashMap<String, PreFlopRank>();

	private static final Map<String, PreFlopRank> mapHandTwoPair = new
			HashMap<String, PreFlopRank>();

	private static final Map<String, PreFlopRank> mapHandOnePair = new
			HashMap<String, PreFlopRank>();

	private static final Map<String, PreFlopRank> mapHandConnector = new
			HashMap<String, PreFlopRank>();

	private PreFlopRank(HandType handType, String... arrayHands) {
		this.handType = handType;
		this.arrayHand = arrayHands;
	}

	public String[] getArrayHand() {
		return arrayHand;
	}

	public HandType getHandType() {
		return handType;
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PreFlopRank preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				mapHand.put(preFlopRank.getArrayHand()[i], preFlopRank);
			}
		}
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PreFlopRank preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				if (preFlopRank.handType.equals(HandType.TWO_PAIR)) {
					mapHandTwoPair.put(preFlopRank.getArrayHand()[i], preFlopRank);
				}
			}
		}
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PreFlopRank preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				if (preFlopRank.handType.equals(HandType.ONE_PAIR)) {
					mapHandOnePair.put(preFlopRank.getArrayHand()[i], preFlopRank);
				}
			}
		}
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PreFlopRank preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				if (preFlopRank.handType.equals(HandType.CONNECTOR)) {
					mapHandConnector.put(preFlopRank.getArrayHand()[i], preFlopRank);
				}
			}
		}
	}

	// récupération de l'instance
	public static PreFlopRank fromHand(String hand) {
		final PreFlopRank value = mapHand.get(hand);
		if (value != null) {
			return value;
		}

		return NO_POWER;
		// throw new IllegalArgumentException();
	}

	// récupération de l'instance
	public static PreFlopRank fromTypeAndHand(HandType handType, String hand) {
		PreFlopRank value = null;

		switch (handType) {
		case TWO_PAIR:
			value = checkPattern(hand, mapHandTwoPair);
			break;
		case ONE_PAIR:
			value = checkPattern(hand, mapHandOnePair);
			break;

		case CONNECTOR:
			value = checkPattern(hand, mapHandConnector);

			break;

		default:
			break;
		}

		if (value != null) {
			return value;
		}

		return NO_POWER;
		// throw new IllegalArgumentException();
	}

	private static PreFlopRank checkPattern(String hand, Map<String, PreFlopRank> map) {
		PreFlopRank value;
		value = map.get(hand);

		if (value == null) {
			for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
				Entry couple = (Entry) i.next();

				String handPattern = (String) couple.getKey();
				PreFlopRank powerHandRankValue = (PreFlopRank) couple.getValue();

				Pattern pattern = Pattern.compile(handPattern);
				Matcher matcher = pattern.matcher(hand);

				if (matcher.find()) {
					value = powerHandRankValue;
				}
			}
		}
		return value;
	}
}