package com.omahaBot.enums;

public enum StraightDrawType {
	NO_DRAW(0),
	GUT_SHOT(4),
	OPEN_ENDED(8),
	INSIDE_BROADWAY(9),
	CARD12_DRAW(12),
	CARD13_WRAP(13),
	CARD17_WRAP(17),
	CARD20_WRAP(20);

	private final int outs;

	private StraightDrawType(int outs) {
		this.outs = outs;
	}

	public int getOuts() {
		return outs;
	}
	
	
}
