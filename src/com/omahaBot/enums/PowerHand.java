package com.omahaBot.enums;

public enum PowerHand {
	TWO_PAIRS_SUITED_MAX(PowerHandLevel.MAX, PowerHandRank.TWO_PAIR_HIGHT, PowerHandSuit.NO_POWER), // KKAA
	TWO_PAIRS_SUITED(PowerHandLevel.HIGHT, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	TWO_PAIRS(PowerHandLevel.HIGHT, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	ONE_PAIR_AND_AS_SUITED(PowerHandLevel.MEDIUM, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	ONE_PAIR_AND_KING_SUITED(PowerHandLevel.MEDIUM, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),	
	ONE_PAIR(PowerHandLevel.MEDIUM, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),	
	FOUR_CARD_CONNECTED_OPEN_ENDED(PowerHandLevel.HIGHT, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	FOUR_CARD_CONNECTED(PowerHandLevel.MEDIUM, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	THREE_CARD_CONNECTED_OPEN_ENDED(PowerHandLevel.LOW, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	THREE_CARD_CONNECTED(PowerHandLevel.LOW, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER),
	NO_POWER(PowerHandLevel.ZERO, PowerHandRank.NO_POWER, PowerHandSuit.NO_POWER);

	private final PowerHandLevel level;
	private final PowerHandRank powerHandRank;
	private final PowerHandSuit powerHandSuit;

	private PowerHand(PowerHandLevel level, PowerHandRank powerHandRank, PowerHandSuit powerHandSuit) {
		this.level = level;
		this.powerHandRank = powerHandRank;
		this.powerHandSuit = powerHandSuit;
	}

	public PowerHandLevel getLevel() {
		return level;
	}

	public PowerHandRank getPowerHandRank() {
		return powerHandRank;
	}

	public PowerHandSuit getPowerHandSuit() {
		return powerHandSuit;
	}

	public static PowerHand fromRankAndSuit(PowerHandRank powerHandRank, PowerHandSuit powerHandSuit) {

		for (int i = 0; i < PowerHand.values().length; i++) {
			PowerHand powerHand = PowerHand.values()[i];

			if (powerHand.getPowerHandRank().equals(powerHandRank)
					&& powerHand.getPowerHandSuit().equals(powerHandSuit)) {
				return powerHand;
			}
		}

		return NO_POWER;
	}
}