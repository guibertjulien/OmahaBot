package com.omahaBot.consts;

import java.awt.Rectangle;

import org.eclipse.swt.graphics.Point;

public final class Consts {

	public static final String CAPS_DIRECTORY = "C:/_DEV/caps/";

	/* peut varier */
	public static final int TABLE_X = 391;// 393, bordure noire
	public static final int TABLE_Y = 0;
	
	/* invariants */
	private static final int TABLE_WIDTH = 1209;// 1207, capture de la table
	private static final int TABLE_HEIGHT = 860;

	public final static String WHITELIST_DEAL_ID = "0123456789#";

	public final static int HAND_NUM_LENGHT = 10;
	
	public static final Rectangle BLOCK_TABLE = new Rectangle(TABLE_X, TABLE_Y, TABLE_WIDTH, TABLE_HEIGHT);
	public static final Rectangle BLOCK_DEAL_ID = new Rectangle(TABLE_X + 52, TABLE_Y + 40, 116, 18);
	public static final Rectangle BLOCK_POT = new Rectangle(TABLE_X + 477, TABLE_Y + 97, 270, 22);

	public static final int BLOCK_PLAYER_WIDTH = 140;
	public static final int BLOCK_PLAYER_HEIGHT = 45;
	public static final int BLOCK_PLAYER_DATA_HEIGHT = 20;
	public static final int BLOCK_PLAYER_LINE2_Y = 25;	
	
	public static final Rectangle BLOCK_PLAYER1 = new Rectangle(860, 107, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER2 = new Rectangle(1007, 415, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER3 = new Rectangle(860, 580, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER4 = new Rectangle(193, 580, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER5 = new Rectangle(63, 415, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER6 = new Rectangle(206, 107, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);

	public static final Point PT_PLAYER1_IN = new Point(TABLE_X + 823, TABLE_Y + 236);
	public static final Point PT_PLAYER2_IN = new Point(TABLE_X + 982, TABLE_Y + 379);
	public static final Point PT_PLAYER3_IN = new Point(TABLE_X + 823, TABLE_Y + 519);
	public static final Point PT_PLAYER4_IN = new Point(TABLE_X + 415, TABLE_Y + 519);
	public static final Point PT_PLAYER5_IN = new Point(TABLE_X + 249, TABLE_Y + 379);
	public static final Point PT_PLAYER6_IN = new Point(TABLE_X + 415, TABLE_Y + 236);

	public static final int BLOCK_CARD_Y = TABLE_Y + 307;
	public static final int BLOCK_CARD_WIDTH = 65;
	public static final int BLOCK_CARD_HEIGHT = 48;
	
	public static final Rectangle BLOCK_CARD1 = new Rectangle(TABLE_X + 426, BLOCK_CARD_Y, BLOCK_CARD_WIDTH, BLOCK_CARD_HEIGHT);
	public static final Rectangle BLOCK_CARD2 = new Rectangle(TABLE_X + 502, BLOCK_CARD_Y, BLOCK_CARD_WIDTH, BLOCK_CARD_HEIGHT);
	public static final Rectangle BLOCK_CARD3 = new Rectangle(TABLE_X + 578, BLOCK_CARD_Y, BLOCK_CARD_WIDTH, BLOCK_CARD_HEIGHT);
	public static final Rectangle BLOCK_CARD4 = new Rectangle(TABLE_X + 656, BLOCK_CARD_Y, BLOCK_CARD_WIDTH, BLOCK_CARD_HEIGHT);
	public static final Rectangle BLOCK_CARD5 = new Rectangle(TABLE_X + 730, BLOCK_CARD_Y, BLOCK_CARD_WIDTH, BLOCK_CARD_HEIGHT);
	
	public static final Point PT_SUIT = new Point(49, 20);
}