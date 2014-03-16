package com.omahaBot.enums;

import java.awt.Rectangle;

import org.eclipse.swt.graphics.Point;

import com.omahaBot.consts.Consts;

public enum PlayerBlock {

	PLAYER_1(Consts.BLOCK_PLAYER1, Consts.PT_PLAYER1_IN), PLAYER_2(Consts.BLOCK_PLAYER2, Consts.PT_PLAYER2_IN), PLAYER_3(Consts.BLOCK_PLAYER3, Consts.PT_PLAYER3_IN), PLAYER_4(
			Consts.BLOCK_PLAYER4, Consts.PT_PLAYER4_IN), PLAYER_5(Consts.BLOCK_PLAYER5, Consts.PT_PLAYER5_IN), PLAYER_6(Consts.BLOCK_PLAYER6, Consts.PT_PLAYER6_IN);

	public static final int BET_WIDTH = 150;
	public static final int BET_HEIGHT = 15;

	private Rectangle block;
	private Point playIN;

	PlayerBlock(Rectangle block, Point playin) {
		this.block = block;
		this.playIN = playin;
	}

	public Rectangle getBlock() {
		return block;
	}

	public void setBlock(Rectangle block) {
		this.block = block;
	}

	public Point getPlayIN() {
		return playIN;
	}

	public void setPlayIN(Point playin) {
		this.playIN = playin;
	}

}
