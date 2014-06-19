package com.omahaBot.service.ai;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FlushModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.StraightModel;

public class BoardModelTest {

	@BeforeClass
	public static void oneTimeSetUp() {

	}

	@Before
	public void setUp() {

	}

//	@Test
//	public void testFindFlush() {
//		BoardModel boardModel;
//		ArrayList<DrawModel> listDraw = new ArrayList<>();
//		FlushModel flushModel;
//		
//		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.SPADE));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));		
//		System.out.println(boardModel + " " + flushModel);
//
//		listDraw.clear();
//		boardModel = new BoardModel("7c4c6hAc", DealStep.TURN);
//		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));		
//		System.out.println(boardModel + " " + flushModel);
//		
//		listDraw.clear();
//		boardModel = new BoardModel("7c4c6cAc", DealStep.TURN);
//		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));		
//		System.out.println(boardModel + " " + flushModel);
//
//		listDraw.clear();
//		boardModel = new BoardModel("7h4s6sQhKh", DealStep.RIVER);
//		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.HEART));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.JACK));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));		
//		System.out.println(boardModel + " " + flushModel);
//
//		listDraw.clear();		
//		boardModel = new BoardModel("Ah4s6hQhKh", DealStep.RIVER);
//		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.HEART));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.TEN));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.JACK));		
//		System.out.println(boardModel + " " + flushModel);
//		
//		listDraw.clear();
//		boardModel = new BoardModel("AhTh6hQhKh", DealStep.RIVER);
//		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
//		flushModel = (FlushModel) listDraw.get(0);
//		assertTrue(flushModel.getSuit().equals(Suit.HEART));
//		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.NINE));
//		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.JACK));		
//		System.out.println(boardModel + " " + flushModel);
//	}
//
//	@Test
//	public void testFindStraight() {
//		BoardModel boardModel;
//		ArrayList<DrawModel> listDraw = new ArrayList<>();
//		StraightModel straightModel;
//		
//		listDraw.clear();
//		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("KsQsJs", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("KsQsTs", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("KsQs9s", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("KsKdKh", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("KsKdQs", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("2s3d5s7d", DealStep.TURN);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("2s3d5s7d8h", DealStep.RIVER);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("QsKhAc", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("2s3hAc", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(2,4,HandCategory.STRAIGHT, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("2s5h8c", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(4,6,HandCategory.STRAIGHT_DRAW, null));
//		assertTrue(!listDraw.isEmpty());
//		
//		listDraw.clear();
//		boardModel = new BoardModel("2s5h9c", DealStep.FLOP);
//		listDraw.addAll(boardModel.searchStraightDraw(4,6,HandCategory.STRAIGHT_DRAW, null));
//		assertTrue(listDraw.isEmpty());
//	}
	
	@After
	public void tearDown() {

	}
}
