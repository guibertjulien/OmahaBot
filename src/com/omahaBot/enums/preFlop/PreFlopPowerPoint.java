package com.omahaBot.enums.preFlop;

public enum PreFlopPowerPoint {
	MAX(10),
	HIGH(8),
	MEDIUM(5),
	LOW(2),
	MIN(0);

	private final int point;

	private PreFlopPowerPoint(int point) {
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

}
