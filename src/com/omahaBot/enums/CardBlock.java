package com.omahaBot.enums;

import java.awt.Rectangle;

import com.omahaBot.consts.Consts;

public enum CardBlock {

	// BOARD :
	CARD1_FLOP(Consts.BLOCK_CARD1),
	CARD2_FLOP(Consts.BLOCK_CARD2),
	CARD3_FLOP(Consts.BLOCK_CARD3),
	CARD4_TURN(Consts.BLOCK_CARD4),
	CARD5_RIVER(Consts.BLOCK_CARD5),
	// MY HAND :
	MY_CARD1(Consts.MY_CARD1),
	MY_CARD2(Consts.MY_CARD2),
	MY_CARD3(Consts.MY_CARD3),
	MY_CARD4(Consts.MY_CARD4);

	private Rectangle block;

	private CardBlock(Rectangle block) {
		this.block = block;
	}

	public Rectangle getBlock() {
		return block;
	}

	public void setBlock(Rectangle block) {
		this.block = block;
	}

}
