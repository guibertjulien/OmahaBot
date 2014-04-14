package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public enum PowerHandRank {

	// TWO PAIRS
	TWO_PAIR_HIGHT(HandType.TWO_PAIR, "TTJJ", "TTQQ", "TTKK", "JJQQ", "JJKK", "QQKK", "..AA"),
	TWO_PAIR_MEDIUM(HandType.TWO_PAIR, "..TT", "..JJ", "..QQ", "..KK"),
	TWO_PAIR_LOW(HandType.TWO_PAIR, "AAKK", "AAQQ", "AAJJ", "AATT", "KKQQ", "KKTT", "QQJJ", "QQTT", "JJTT"),

	// ONE PAIR
	ONE_PAIR_HIGHT(HandType.ONE_PAIR, ".AA."),
	ONE_PAIR_MEDIUM(HandType.ONE_PAIR, ".KK.", ".QQ.", ".JJ.", ".TT."),
	ONE_PAIR_LOW(HandType.ONE_PAIR, ".99.", ".88.", ".77.", ".66.", ".55.", ".44.", ".33.", ".22."),
	
	// THREE_OF_A_KIND
	// TODO
	
	// FOUR CONNECTORS
	// par les 2 bouts
	CONNECTOR4_HIGHT(HandType.CONNECTOR, "2345", "3456", "4567", "5678", "6789", "789T", "89TJ", "9TJQ", "TJQK"),
	// par un seul bout
	CONNECTOR4_MEDIUM(HandType.CONNECTOR, "234A", "JQKA"),

	// THREE CONNECTORS
	CONNECTOR3_HIGHT(HandType.CONNECTOR, ".345", ".456", ".567", ".678", ".789", ".89T", ".9TJ", ".TJQ", ".JQK"),
	CONNECTOR3_MEDIUM(HandType.CONNECTOR, "AKQ.", ".32A"),

	NO_POWER(HandType.NO_TYPE);

	private final String[] arrayHand;

	private final HandType handType;

	private static final Map<String, PowerHandRank> mapHand = new
			HashMap<String, PowerHandRank>();

	private static final Map<String, PowerHandRank> mapHandTwoPair = new
			HashMap<String, PowerHandRank>();

	private static final Map<String, PowerHandRank> mapHandOnePair = new
			HashMap<String, PowerHandRank>();
	
	private static final Map<String, PowerHandRank> mapHandConnector = new
			HashMap<String, PowerHandRank>();

	private PowerHandRank(HandType handType, String... arrayHands) {
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
		for (PowerHandRank powerHandRank : values()) {
			for (int i = 0; i < powerHandRank.getArrayHand().length; i++) {
				mapHand.put(powerHandRank.getArrayHand()[i], powerHandRank);
			}
		}
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PowerHandRank powerHandRank : values()) {
			for (int i = 0; i < powerHandRank.getArrayHand().length; i++) {
				if (powerHandRank.handType.equals(HandType.TWO_PAIR)) {
					mapHandTwoPair.put(powerHandRank.getArrayHand()[i], powerHandRank);
				}
			}
		}
	}
	
	// map permettant de récupérer ke power avec une hand
	static {
		for (PowerHandRank powerHandRank : values()) {
			for (int i = 0; i < powerHandRank.getArrayHand().length; i++) {
				if (powerHandRank.handType.equals(HandType.ONE_PAIR)) {
					mapHandOnePair.put(powerHandRank.getArrayHand()[i], powerHandRank);
				}
			}
		}
	}

	// map permettant de récupérer ke power avec une hand
	static {
		for (PowerHandRank powerHandRank : values()) {
			for (int i = 0; i < powerHandRank.getArrayHand().length; i++) {
				if (powerHandRank.handType.equals(HandType.CONNECTOR)) {
					mapHandConnector.put(powerHandRank.getArrayHand()[i], powerHandRank);
				}
			}
		}
	}

	// récupération de l'instance
	public static PowerHandRank fromHand(String hand) {
		final PowerHandRank value = mapHand.get(hand);
		if (value != null) {
			return value;
		}

		return NO_POWER;
		// throw new IllegalArgumentException();
	}

	// récupération de l'instance
	public static PowerHandRank fromTypeAndHand(HandType handType, String hand) {
		PowerHandRank value = null;

		switch (handType) {
		case TWO_PAIR:
			value = mapHandTwoPair.get(hand);

			if (value == null) {
				for (Iterator i = mapHandTwoPair.entrySet().iterator(); i.hasNext();) {
					Entry couple = (Entry) i.next();

					String handPattern = (String) couple.getKey();
					PowerHandRank powerHandRankValue = (PowerHandRank) couple.getValue();

					if (Pattern.matches(handPattern, hand)) {
						value = powerHandRankValue;
					}
				}
			}

			break;
		case ONE_PAIR:
			value = mapHandOnePair.get(hand);

			if (value == null) {
				for (Iterator i = mapHandOnePair.entrySet().iterator(); i.hasNext();) {
					Entry couple = (Entry) i.next();

					String handPattern = (String) couple.getKey();
					PowerHandRank powerHandRankValue = (PowerHandRank) couple.getValue();

					if (Pattern.matches(handPattern, hand)) {
						value = powerHandRankValue;
					}
				}
			}
			
			break;
		
		case CONNECTOR:
			value = mapHandConnector.get(hand);
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
}