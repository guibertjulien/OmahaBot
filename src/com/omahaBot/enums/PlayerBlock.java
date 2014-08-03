package com.omahaBot.enums;

import java.awt.Color;
import java.awt.Rectangle;

import org.eclipse.swt.graphics.Point;

import com.omahaBot.consts.Consts;
import com.omahaBot.consts.PixelConsts;

public enum PlayerBlock {

	PLAYER_1(Consts.BLOCK_PLAYER1, Consts.PT_PLAYER1_ACTIVE, Consts.PT_PLAYER1_TURN, Consts.PT_PLAYER1_DEALER),
	PLAYER_2(Consts.BLOCK_PLAYER2, Consts.PT_PLAYER2_ACTIVE, Consts.PT_PLAYER2_TURN, Consts.PT_PLAYER2_DEALER),
	PLAYER_3(Consts.BLOCK_PLAYER3, Consts.PT_PLAYER3_ACTIVE, Consts.PT_PLAYER3_TURN, Consts.PT_PLAYER3_DEALER),
	PLAYER_4(Consts.BLOCK_PLAYER4, Consts.PT_PLAYER4_ACTIVE, Consts.PT_PLAYER4_TURN, Consts.PT_PLAYER4_DEALER),
	PLAYER_5(Consts.BLOCK_PLAYER5, Consts.PT_PLAYER5_ACTIVE, Consts.PT_PLAYER5_TURN, Consts.PT_PLAYER5_DEALER),
	PLAYER_6(Consts.BLOCK_PLAYER6, Consts.PT_PLAYER6_ACTIVE, Consts.PT_PLAYER6_TURN, Consts.PT_PLAYER6_DEALER);

	public static final int BET_WIDTH = 150;
	public static final int BET_HEIGHT = 15;

	private Rectangle block;
	private Point active;
	private Point turnPlay;
	private Point dealer;

	PlayerBlock(Rectangle block, Point active, Point turnPlay, Point dealer) {
		this.block = block;
		this.active = active;
		this.turnPlay = turnPlay;
		this.dealer = dealer;
	}

	public Rectangle getBlock() {
		return block;
	}

	public void setBlock(Rectangle block) {
		this.block = block;
	}

	public Point getActive() {
		Point result;

		if (itsMe()) {
			result = Consts.PT_IAM_ACTIVE;
		}
		else {
			result = active;
		}

		return result;
	}

	public void setActive(Point active) {
		this.active = active;
	}

	public Point getTurnPlay() {
		return turnPlay;
	}

	public void setTurnPlay(Point turnPlay) {
		this.turnPlay = turnPlay;
	}
	
	public Point getDealer() {
		return dealer;
	}

	public void setDealer(Point dealer) {
		this.dealer = dealer;
	}

	public boolean itsMe() {
		if (!Consts.register) {
			return false;
		}

		return ((this.ordinal() + 1) == Consts.MY_TABLEPOSITION);
	}

	/**
	 * return true if player active in deal
	 * 
	 * @param colorScaned
	 * @return
	 */
	public boolean isActivePlayer(Color colorScaned) {

		boolean active = false;

		if (itsMe() && !colorScaned.equals(PixelConsts.IAM_OUT)) {
			active = true;
		}
		else if (colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
			active = true;
		}

		return active;
	}

	/**
	 * return true if player is in turn to play
	 * 
	 * @param colorScaned
	 * @return
	 */
	public boolean isPlayerTurnPlay(Color colorScaned) {
		return (!colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR1)
		&& !colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR2));
	}
	
	/**
	 * return true if player is the dealer
	 * 
	 * @param colorScaned
	 * @return
	 */
	public boolean isPlayerDealer(Color colorScaned) {
		return (colorScaned.equals(PixelConsts.PLAYER_IS_DEALER));
	}
}
