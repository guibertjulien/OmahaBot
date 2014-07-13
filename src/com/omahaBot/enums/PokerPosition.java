package com.omahaBot.enums;

import java.util.HashMap;
import java.util.Map;

public enum PokerPosition {
	CUT_OFF(1),
	BUTTON(0),
	SMALL_BLIND(-1),
	BIG_BLIND(-2),
	UNDER_THE_GUN(-3),
	MIDDLE_POSITION(-9);

	private int valueDiff;

	PokerPosition(int valueDiff) {
		this.valueDiff = valueDiff;
	}

	private static final Map<Integer, PokerPosition> map = new
			HashMap<Integer, PokerPosition>();

	static {
		for (PokerPosition pokerPosition : values()) {
			map.put(pokerPosition.getValueDiff(), pokerPosition);
		}
	}

	// récupération de l'instance
	public static PokerPosition fromValueDiff(int valueDiff) {
		final PokerPosition pokerPosition = map.get(valueDiff);
		if (pokerPosition != null) {
			return pokerPosition;
		}
		else {
			return PokerPosition.MIDDLE_POSITION;
		}
	}

	public int getValueDiff() {
		return valueDiff;
	}

	public void setValueDiff(int valueDiff) {
		this.valueDiff = valueDiff;
	}

}
