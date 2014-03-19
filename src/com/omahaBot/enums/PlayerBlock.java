package com.omahaBot.enums;

import java.awt.Rectangle;

import org.eclipse.swt.graphics.Point;

import com.omahaBot.consts.Consts;

public enum PlayerBlock {

	PLAYER_1(Consts.BLOCK_PLAYER1, Consts.PT_PLAYER1_IN, Consts.PT_PLAYER1_TURN), 
	PLAYER_2(Consts.BLOCK_PLAYER2, Consts.PT_PLAYER2_IN, Consts.PT_PLAYER2_TURN), 
	PLAYER_3(Consts.BLOCK_PLAYER3, Consts.PT_PLAYER3_IN, Consts.PT_PLAYER3_TURN), 
	PLAYER_4(Consts.BLOCK_PLAYER4, Consts.PT_PLAYER4_IN, Consts.PT_PLAYER4_TURN), 
	PLAYER_5(Consts.BLOCK_PLAYER5, Consts.PT_PLAYER5_IN, Consts.PT_PLAYER5_TURN), 
	PLAYER_6(Consts.BLOCK_PLAYER6, Consts.PT_PLAYER6_IN, Consts.PT_PLAYER6_TURN);

	public static final int BET_WIDTH = 150;
	public static final int BET_HEIGHT = 15;

	private Rectangle block;
	private Point inPlay;
	private Point turnPlay;

	PlayerBlock(Rectangle block, Point active, Point turnPlay) {
		this.block = block;
		this.inPlay = active;
		this.turnPlay = turnPlay;
	}

	public Rectangle getBlock() {
		return block;
	}

	public void setBlock(Rectangle block) {
		this.block = block;
	}

	public Point getInPlay() {
		return inPlay;
	}

	public void setInPlay(Point inPlay) {
		this.inPlay = inPlay;
	}

	public Point getTurnPlay() {
		return turnPlay;
	}

	public void setTurnPlay(Point turnPlay) {
		this.turnPlay = turnPlay;
	}

	

}
