package com.omahaBot.consts;

import java.awt.Rectangle;

import org.eclipse.swt.graphics.Point;

public final class Consts {

	public static final String CAPS_DIRECTORY = "C:/_DEV/caps/";

	public static final int MY_TABLEPOSITION = 4;
	
	public static final boolean register = false;
	
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

	public static final int BLOCK_PLAYER_WIDTH = 120;
	public static final int BLOCK_PLAYER_HEIGHT = 45;
	public static final int BLOCK_PLAYER_NAME_HEIGHT = 20;
	public static final int BLOCK_PLAYER_STACK_Y = 25;	
	public static final int BLOCK_PLAYER_STACK_HEIGHT = 20;
	
	public static final Rectangle BLOCK_PLAYER1 = new Rectangle(TABLE_X + 860, TABLE_Y + 107, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER2 = new Rectangle(TABLE_X + 1007, TABLE_Y + 415, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER3 = new Rectangle(TABLE_X + 860, TABLE_Y + 580, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER4 = new Rectangle(TABLE_X + 193, TABLE_Y + 580, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER5 = new Rectangle(TABLE_X + 63, TABLE_Y + 415, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);
	public static final Rectangle BLOCK_PLAYER6 = new Rectangle(TABLE_X + 206, TABLE_Y + 107, BLOCK_PLAYER_WIDTH, BLOCK_PLAYER_HEIGHT);

	public static final Point PT_PLAYER1_ACTIVE = new Point(TABLE_X + 823, TABLE_Y + 236);
	public static final Point PT_PLAYER2_ACTIVE = new Point(TABLE_X + 982, TABLE_Y + 379);
	public static final Point PT_PLAYER3_ACTIVE = new Point(TABLE_X + 823, TABLE_Y + 519);
	public static final Point PT_PLAYER4_ACTIVE = new Point(TABLE_X + 415, TABLE_Y + 519);
	public static final Point PT_PLAYER5_ACTIVE = new Point(TABLE_X + 249, TABLE_Y + 379);
	public static final Point PT_PLAYER6_ACTIVE = new Point(TABLE_X + 415, TABLE_Y + 236);
	public static final Point PT_IAM_ACTIVE = new Point(TABLE_X + 400, TABLE_Y + 600);
	
	public static final Point PT_PLAYER1_TURN = new Point(TABLE_X + 929, TABLE_Y + 158);
	public static final Point PT_PLAYER2_TURN = new Point(TABLE_X + 1074, TABLE_Y + 466);
	public static final Point PT_PLAYER3_TURN = new Point(TABLE_X + 932, TABLE_Y + 631);
	public static final Point PT_PLAYER4_TURN = new Point(TABLE_X + 297, TABLE_Y + 631);
	public static final Point PT_PLAYER5_TURN = new Point(TABLE_X + 132, TABLE_Y + 466);
	public static final Point PT_PLAYER6_TURN = new Point(TABLE_X + 272, TABLE_Y + 158);

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