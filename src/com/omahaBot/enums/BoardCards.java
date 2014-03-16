package com.omahaBot.enums;

public enum BoardCards {

	CARD1_FLOP(424),
	CARD2_FLOP(500),
	CARD3_FLOP(576),
	CARD4_TURN(654),
	CARD5_RIVER(728);

	private int posX;
	public static int POSY = 307;
	public static int WIDTH = 65;
	public static int HEIGHT = 48;

	BoardCards(int posX) {
		this.posX = posX;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

}
