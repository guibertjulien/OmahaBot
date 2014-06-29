package com.omahaBot.enums.preFlop;

import java.util.List;

import com.omahaBot.enums.Rank;
import com.omahaBot.model.hand.HandPreFlopPower.SuitedType;
import com.omahaBot.model.hand.HandSuit;

public enum PreFlopSuitLevel {

	// DOUBLE SUITED
	DOUBLE_SUITED_AA(PreFlopPowerPoint.MAX),
	DOUBLE_SUITED_AK(PreFlopPowerPoint.MAX),
	DOUBLE_SUITED_KK(PreFlopPowerPoint.HIGH),
	DOUBLE_SUITED_LOW(PreFlopPowerPoint.MEDIUM),

	// ONE SUIT
	ONE_SUIT_A(PreFlopPowerPoint.HIGH),
	ONE_SUIT_K(PreFlopPowerPoint.MEDIUM),
	ONE_SUIT_LOW(PreFlopPowerPoint.LOW),

	UNSUITED(PreFlopPowerPoint.MIN);

	private final PreFlopPowerPoint powerPoint;

	private PreFlopSuitLevel(PreFlopPowerPoint powerPoint) {
		this.powerPoint = powerPoint;
	}

	public PreFlopPowerPoint getPowerPoint() {
		return powerPoint;
	}

	// récupération de l'instance
	public static PreFlopSuitLevel fromTypeAndKicker(SuitedType suitedType, List<HandSuit> handSuits) {
		PreFlopSuitLevel value = null;

		switch (suitedType) {
		case DOUBLE_SUITED:
			if (handSuits.size() == 2) {
				Rank kicker1 = handSuits.get(0).getKicker();
				Rank kicker2 = handSuits.get(1).getKicker();

				if (kicker1.equals(Rank.ACE) && kicker2.equals(Rank.ACE)) {
					value = DOUBLE_SUITED_AA;
				} else if (kicker1.equals(Rank.ACE) && kicker2.equals(Rank.KING) || kicker1.equals(Rank.KING)
						&& kicker2.equals(Rank.ACE)) {
					value = DOUBLE_SUITED_AK;
				} else if (kicker1.equals(Rank.KING) && kicker2.equals(Rank.KING)) {
					value = DOUBLE_SUITED_KK;
				} else {
					value = DOUBLE_SUITED_LOW;
				}
			}
			break;

		case ONE_SUIT:
			if (handSuits.size() == 1) {
				Rank kicker = handSuits.get(0).getKicker();

				if (kicker.equals(Rank.ACE)) {
					value = ONE_SUIT_A;
				} else if (kicker.equals(Rank.KING)) {
					value = ONE_SUIT_K;
				} else {
					value = ONE_SUIT_LOW;
				}
			}
			break;

		default:
			break;
		}

		if (value != null) {
			return value;
		}

		return UNSUITED;
		// throw new IllegalArgumentException();
	}
	
	@Override
    public String toString() {
		return this.name().concat("(" + this.getPowerPoint().getPoint() + ")");
	}
}