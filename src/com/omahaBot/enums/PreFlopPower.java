package com.omahaBot.enums;

public enum PreFlopPower {
	TWO_PAIRS_SUITED_H(PowerHandLevel.MAX, PreFlopRank.TWO_PAIR_HIGHT, PreFlopSuit.TWO_COLORS),//AA
	TWO_PAIRS_SUITED_M(PowerHandLevel.HIGHT, PreFlopRank.TWO_PAIR_MEDIUM, PreFlopSuit.TWO_COLORS),
	TWO_PAIRS_SUITED_L(PowerHandLevel.MEDIUM, PreFlopRank.TWO_PAIR_LOW, PreFlopSuit.TWO_COLORS),

	TWO_PAIRS_ONE_COLOR_H(PowerHandLevel.HIGHT, PreFlopRank.TWO_PAIR_HIGHT, PreFlopSuit.ONE_COLOR),
	TWO_PAIRS_ONE_COLOR_M(PowerHandLevel.MEDIUM, PreFlopRank.TWO_PAIR_MEDIUM, PreFlopSuit.ONE_COLOR),
	TWO_PAIRS_ONE_COLOR_L(PowerHandLevel.LOW, PreFlopRank.TWO_PAIR_LOW, PreFlopSuit.ONE_COLOR),

	TWO_PAIRS_H(PowerHandLevel.HIGHT, PreFlopRank.TWO_PAIR_HIGHT, PreFlopSuit.NO_COLOR),
	TWO_PAIRS_M(PowerHandLevel.MEDIUM, PreFlopRank.TWO_PAIR_MEDIUM, PreFlopSuit.NO_COLOR),
	TWO_PAIRS_L(PowerHandLevel.LOW, PreFlopRank.TWO_PAIR_LOW, PreFlopSuit.NO_COLOR),

	ONE_PAIR_TWO_COLOR_H(PowerHandLevel.HIGHT, PreFlopRank.ONE_PAIR_HIGHT, PreFlopSuit.TWO_COLORS),
	ONE_PAIR_TWO_COLOR_M(PowerHandLevel.MEDIUM, PreFlopRank.ONE_PAIR_MEDIUM, PreFlopSuit.TWO_COLORS),
	ONE_PAIR_TWO_COLOR_L(PowerHandLevel.LOW, PreFlopRank.ONE_PAIR_LOW, PreFlopSuit.TWO_COLORS),

	ONE_PAIR_ONE_COLOR_H(PowerHandLevel.HIGHT, PreFlopRank.ONE_PAIR_HIGHT, PreFlopSuit.ONE_COLOR),
	ONE_PAIR_ONE_COLOR_M(PowerHandLevel.MEDIUM, PreFlopRank.ONE_PAIR_MEDIUM, PreFlopSuit.ONE_COLOR),
	ONE_PAIR_ONE_COLOR_L(PowerHandLevel.LOW, PreFlopRank.ONE_PAIR_LOW, PreFlopSuit.ONE_COLOR),

	ONE_PAIR_H(PowerHandLevel.HIGHT, PreFlopRank.ONE_PAIR_HIGHT, PreFlopSuit.NO_COLOR),
	ONE_PAIR_M(PowerHandLevel.MEDIUM, PreFlopRank.ONE_PAIR_MEDIUM, PreFlopSuit.NO_COLOR),
	ONE_PAIR_L(PowerHandLevel.LOW, PreFlopRank.ONE_PAIR_LOW, PreFlopSuit.NO_COLOR),

	FOUR_CARD_CONNECTED_OPEN_ENDED_TWO_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR4_HIGHT, PreFlopSuit.TWO_COLORS),
	FOUR_CARD_CONNECTED_OPEN_ENDED_ONE_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR4_HIGHT, PreFlopSuit.ONE_COLOR),
	FOUR_CARD_CONNECTED_OPEN_ENDED_NO_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR4_HIGHT, PreFlopSuit.NO_COLOR),

	FOUR_CARD_CONNECTED_TWO_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR4_MEDIUM, PreFlopSuit.TWO_COLORS),
	FOUR_CARD_CONNECTED_ONE_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR4_MEDIUM, PreFlopSuit.ONE_COLOR),
	FOUR_CARD_CONNECTED_NO_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR4_MEDIUM, PreFlopSuit.NO_COLOR),

	THREE_CARD_CONNECTED_OPEN_ENDED_TWO_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR3_HIGHT,
			PreFlopSuit.TWO_COLORS),
	THREE_CARD_CONNECTED_OPEN_ENDED_ONE_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR3_HIGHT, PreFlopSuit.ONE_COLOR),
	THREE_CARD_CONNECTED_OPEN_ENDED_NO_COLOR(PowerHandLevel.HIGHT, PreFlopRank.CONNECTOR3_HIGHT, PreFlopSuit.NO_POWER),

	THREE_CARD_CONNECTED_TWO_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR3_MEDIUM, PreFlopSuit.TWO_COLORS),
	THREE_CARD_CONNECTED_ONE_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR3_MEDIUM, PreFlopSuit.ONE_COLOR),
	THREE_CARD_CONNECTED_NO_COLOR(PowerHandLevel.MEDIUM, PreFlopRank.CONNECTOR3_MEDIUM, PreFlopSuit.NO_POWER),

	NO_POWER(PowerHandLevel.ZERO, PreFlopRank.NO_POWER, PreFlopSuit.NO_COLOR);

	private final PowerHandLevel level;
	private final PreFlopRank preFlopRank;
	private final PreFlopSuit preFlopSuit;

	private PreFlopPower(PowerHandLevel level, PreFlopRank preFlopRank, PreFlopSuit preFlopSuit) {
		this.level = level;
		this.preFlopRank = preFlopRank;
		this.preFlopSuit = preFlopSuit;
	}

	public PowerHandLevel getLevel() {
		return level;
	}

	public PreFlopRank getPreFlopRank() {
		return preFlopRank;
	}

	public PreFlopSuit getPreFlopSuit() {
		return preFlopSuit;
	}

	public static PreFlopPower fromRankAndSuit(PreFlopRank preFlopRank, PreFlopSuit preFlopSuit) {

		for (int i = 0; i < PreFlopPower.values().length; i++) {
			PreFlopPower preFlopPower = PreFlopPower.values()[i];

			if (preFlopPower.getPreFlopRank().equals(preFlopRank)
					&& preFlopPower.getPreFlopSuit().equals(preFlopSuit)) {
				return preFlopPower;
			}
		}

		return NO_POWER;
	}

}