package com.omahaBot.enums.preFlop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.model.hand.HandPreFlopPower.PairType;

public enum PreFlopPairLevel {

	// TWO PAIRS
	DOUBLE_PAIR_HIGH(PreFlopPowerPoint.MAX, PairType.DOUBLE_PAIR, "TTJJ", "TTQQ", "TTKK", "JJQQ", "JJKK", "QQKK",
			"..AA"),
	DOUBLE_PAIR_MEDIUM(PreFlopPowerPoint.HIGH, PairType.DOUBLE_PAIR, "..TT", "..JJ", "..QQ", "..KK"),
	DOUBLE_PAIR_LOW(PreFlopPowerPoint.HIGH, PairType.DOUBLE_PAIR, "..99", "..88", "..77", "..66", "..55", "..44",
			"..33"),

	// ONE PAIR
	ONE_PAIR_HIGH(PreFlopPowerPoint.MEDIUM, PairType.ONE_PAIR, "AA", "KK", "QQ", "JJ", "TT"),
	ONE_PAIR_LOW(PreFlopPowerPoint.LOW, PairType.ONE_PAIR, "99", "88", "77", "66", "55", "44", "33", "22"),

	NO_PAIR(PreFlopPowerPoint.MIN, PairType.NO_PAIR);

	private final PreFlopPowerPoint powerPoint;

	private final PairType pairType;

	private final String[] arrayHand;

	private static final Map<String, PreFlopPairLevel> mapHandTwoPair = new
			HashMap<String, PreFlopPairLevel>();

	private static final Map<String, PreFlopPairLevel> mapHandOnePair = new
			HashMap<String, PreFlopPairLevel>();

	private PreFlopPairLevel(PreFlopPowerPoint powerPoint, PairType pairType, String... arrayHands) {
		this.powerPoint = powerPoint;
		this.pairType = pairType;
		this.arrayHand = arrayHands;
	}

	public PreFlopPowerPoint getPowerPoint() {
		return powerPoint;
	}

	public String[] getArrayHand() {
		return arrayHand;
	}

	public PairType getPairType() {
		return pairType;
	}

	// map permettant de récupérer le power avec une hand
	static {
		for (PreFlopPairLevel preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				if (preFlopRank.pairType.equals(PairType.DOUBLE_PAIR)) {
					mapHandTwoPair.put(preFlopRank.getArrayHand()[i], preFlopRank);
				}
			}
		}
	}

	// map permettant de récupérer le power avec une hand
	static {
		for (PreFlopPairLevel preFlopRank : values()) {
			for (int i = 0; i < preFlopRank.getArrayHand().length; i++) {
				if (preFlopRank.pairType.equals(PairType.ONE_PAIR)) {
					mapHandOnePair.put(preFlopRank.getArrayHand()[i], preFlopRank);
				}
			}
		}
	}

	// récupération de l'instance
	public static PreFlopPairLevel fromTypeAndHand(PairType pairType, String hand) {
		PreFlopPairLevel value = null;

		switch (pairType) {
		case DOUBLE_PAIR:
			value = checkPattern(hand, mapHandTwoPair);
			break;
		case ONE_PAIR:
			value = checkPattern(hand, mapHandOnePair);
			break;

		default:
			break;
		}

		if (value != null) {
			return value;
		}

		return NO_PAIR;
		// throw new IllegalArgumentException();
	}

	private static PreFlopPairLevel checkPattern(String hand, Map<String, PreFlopPairLevel> map) {
		PreFlopPairLevel value;
		value = map.get(hand);

		if (value == null) {
			for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
				Entry couple = (Entry) i.next();

				String handPattern = (String) couple.getKey();
				PreFlopPairLevel pFlopPairLevel = (PreFlopPairLevel) couple.getValue();

				Pattern pattern = Pattern.compile(handPattern);
				Matcher matcher = pattern.matcher(hand);

				if (matcher.find()) {
					value = pFlopPairLevel;
				}
			}
		}
		return value;
	}
	
	@Override
    public String toString() {
		return this.name().concat("(" + this.getPowerPoint().getPoint() + ")");
	}
}