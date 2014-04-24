package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.DrawModel;
import com.omahaBot.model.DrawModel.Type;
import com.omahaBot.model.HandModel;

public class CardPackTest {

	@Test
	public void testIfTwoPair() {
		HandModel handModel = new HandModel("AhAs3c3h");
		assertTrue(handModel.isTwoPair());

		BoardModel boardModel = new BoardModel("KsKh3d8d8c", DealStep.RIVER);
		assertTrue(boardModel.isTwoPair());
	}

	@Test
	public void testIfNotTwoPair() {
		HandModel handModel = new HandModel("AhAs5c3h");
		assertTrue(!handModel.isTwoPair());

		BoardModel boardModel = new BoardModel("KsKh3d8d5c", DealStep.RIVER);
		assertTrue(!boardModel.isTwoPair());
	}

	@Test
	public void testIfFull() {
		BoardModel boardModel = new BoardModel("KsKh8s8d8c", DealStep.RIVER);
		assertTrue(boardModel.isFull());

		boardModel = new BoardModel("KsKhKd8d8c", DealStep.RIVER);
		assertTrue(boardModel.isFull());
	}

	@Test
	public void testIfThreeOfAKind() {
		HandModel handModel = new HandModel("AhAsAc3h");
		assertTrue(handModel.isThreeOfAKind());

		BoardModel boardModel = new BoardModel("KsKhKd8d7c", DealStep.RIVER);
		assertTrue(boardModel.isThreeOfAKind());
	}

	@Test
	public void testIfTwoSuit() {
		HandModel handModel = new HandModel("AhKh3c4c");
		assertTrue(handModel.isTwoSuit());

		BoardModel boardModel = new BoardModel("AhKh3d4c8c", DealStep.RIVER);
		assertTrue(boardModel.isTwoSuit());
	}

	@Test
	public void testIfFlush() {
		BoardModel boardModel = new BoardModel("AhKh3h4h8h", DealStep.RIVER);
		assertTrue(boardModel.isFlush());
	}

	@Test
	public void testIfNotFlush() {
		BoardModel boardModel = new BoardModel("AhKh3h4c8h", DealStep.RIVER);
		assertTrue(!boardModel.isFlush());
	}

	@Test
	public void testIfNotTwoSuit() {
		HandModel handModel = new HandModel("AcKc3c4c");
		assertTrue(!handModel.isTwoSuit());

		handModel = new HandModel("AhKh3d4c");
		assertTrue(!handModel.isTwoSuit());

		handModel = new HandModel("AhKs3d4c");
		assertTrue(!handModel.isTwoSuit());

		BoardModel boardModel = new BoardModel("AhKh3h", DealStep.FLOP);
		assertTrue(!boardModel.isTwoSuit());

		boardModel = new BoardModel("AhKh3d4c8h", DealStep.RIVER);
		assertTrue(!boardModel.isTwoSuit());
	}

	@Test
	public void testIfFourOfAKind() {
		HandModel handModel = new HandModel("AcAdAhAs");
		assertTrue(handModel.isFourOfAKind());

		BoardModel boardModel = new BoardModel("AhKhKdKcKs", DealStep.RIVER);
		assertTrue(boardModel.isFourOfAKind());
	}

	@Test
	public void testIfNotFourOfAKind() {
		HandModel handModel = new HandModel("AcAdAhKs");
		assertTrue(!handModel.isFourOfAKind());

		BoardModel boardModel = new BoardModel("AhKhKdKcQs", DealStep.RIVER);
		assertTrue(!boardModel.isFourOfAKind());
	}

	@Test
	public void testIfConnected() {
		HandModel handModel = new HandModel("AcKdQhJs");
		assertTrue(handModel.isNbConnected(4));

		handModel = new HandModel("Ac2d3h4s");
		assertTrue(handModel.isNbConnected(4));

		BoardModel boardModel = new BoardModel("6h2h3d4c5s", DealStep.RIVER);
		assertTrue(boardModel.isStraight());

		boardModel = new BoardModel("Ah2h3d4c5s", DealStep.RIVER);
		assertTrue(boardModel.isStraight());
	}

	// @Test
	// public void testFindFlush() {
	// BoardModel boardModel = new BoardModel("7s4s6s", DealStep.FLOP);
	// FlushDraw flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.SPADE));
	// assertTrue(flushDraw.getNbSuitedCard() == 3);
	// assertTrue(flushDraw.getKicker().equals(Rank.SEVEN));
	// System.out.println(flushDraw);
	//
	// boardModel = new BoardModel("7c4c6hAc", DealStep.TURN);
	// flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.CLUB));
	// assertTrue(flushDraw.getNbSuitedCard() == 3);
	// assertTrue(flushDraw.getKicker().equals(Rank.ACE));
	// System.out.println(flushDraw);
	//
	// boardModel = new BoardModel("7c4c6cAc", DealStep.TURN);
	// flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.CLUB));
	// assertTrue(flushDraw.getNbSuitedCard() == 4);
	// assertTrue(flushDraw.getKicker().equals(Rank.ACE));
	// System.out.println(flushDraw);
	//
	// boardModel = new BoardModel("7h4s6sQhKh", DealStep.RIVER);
	// flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.HEART));
	// assertTrue(flushDraw.getNbSuitedCard() == 3);
	// assertTrue(flushDraw.getKicker().equals(Rank.KING));
	// System.out.println(flushDraw);
	//
	// boardModel = new BoardModel("7h4s6hQhKh", DealStep.RIVER);
	// flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.HEART));
	// assertTrue(flushDraw.getNbSuitedCard() == 4);
	// assertTrue(flushDraw.getKicker().equals(Rank.KING));
	// System.out.println(flushDraw);
	//
	// boardModel = new BoardModel("7h4h6hQhKh", DealStep.RIVER);
	// flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw != null);
	// assertTrue(flushDraw.getSuit().equals(Suit.HEART));
	// assertTrue(flushDraw.getNbSuitedCard() == 5);
	// assertTrue(flushDraw.getKicker().equals(Rank.KING));
	// System.out.println(flushDraw);
	// }
	//
	// @Test
	// public void testNoFindFlush() {
	// BoardModel boardModel = new BoardModel("7s4s6d", DealStep.FLOP);
	// FlushDraw flushDraw = boardModel.searchFlush();
	// assertTrue(flushDraw == null);
	// }

	@Test
	public void testFindFlushDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();
		BoardModel boardModel;
		
//		// FLOP
//		boardModel = new BoardModel("7s4s6d", DealStep.FLOP);
//		listDraw = boardModel.initBoardDrawPower();
//		System.out.println(listDraw);
//		assertTrue(listDraw.size() == 1);
//		assertTrue(listDraw.get(0).getType().equals(Type.FLUSH_DRAW));
//
//		listDraw.clear();
//		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
//		listDraw = boardModel.initBoardDrawPower();
//		System.out.println(listDraw);
//		assertTrue(listDraw.size() == 1);
//		assertTrue(listDraw.get(0).getType().equals(Type.FLUSH));
//
//		// TURN
//		listDraw.clear();
//		boardModel = new BoardModel("7s4s6dKd", DealStep.TURN);
//		listDraw = boardModel.initBoardDrawPower();
//		System.out.println(listDraw);
//		assertTrue(listDraw.size() == 2);
//		assertTrue(listDraw.get(0).getType().equals(Type.FLUSH_DRAW));
//
//		listDraw.clear();
//		boardModel = new BoardModel("7d4s6dAd", DealStep.TURN);
//		listDraw = boardModel.initBoardDrawPower();
//		System.out.println(listDraw);
//		assertTrue(listDraw.size() == 1);
//		assertTrue(listDraw.get(0).getType().equals(Type.FLUSH));

		// RIVER
		listDraw.clear();
		boardModel = new BoardModel("7s4s6dKdAd", DealStep.RIVER);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 2);
		assertTrue(listDraw.get(0).getType().equals(Type.FLUSH));

	}
	
	@Test
	public void testFindFullDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();
		BoardModel boardModel;

		boardModel = new BoardModel("7s7d6d6c7h", DealStep.RIVER);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
//		assertTrue(listDraw.size() == 2);
//		assertTrue(listDraw.get(1).getType().equals(Type.FULL_PAIR_DRAW));
		
		boardModel = new BoardModel("7s7dKc", DealStep.FLOP);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
		
		boardModel = new BoardModel("QsQdTc", DealStep.FLOP);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
		
		boardModel = new BoardModel("7s7d7hKc", DealStep.TURN);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
		
		boardModel = new BoardModel("QsQdQcTc", DealStep.TURN);
		listDraw = boardModel.initBoardDraw();
		System.out.println(listDraw);
	}
}
