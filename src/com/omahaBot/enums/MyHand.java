package com.omahaBot.enums;

import java.awt.Rectangle;

import com.omahaBot.consts.Consts;

public enum MyHand{
	
	CARD1(Consts.MY_CARD1),
	CARD2(Consts.MY_CARD2),
	CARD3(Consts.MY_CARD3),
	CARD4(Consts.MY_CARD4);

	private Rectangle block;

	private MyHand(Rectangle block) {
		this.block = block;
	}

	public Rectangle getBlock() {
		return block;
	}

	public void setBlock(Rectangle block) {
		this.block = block;
	}

}
