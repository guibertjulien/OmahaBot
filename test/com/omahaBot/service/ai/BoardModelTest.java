package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FlushModel;
import com.omahaBot.model.draw.FullModel;

public class BoardModelTest {

	@BeforeClass
	public static void oneTimeSetUp() {

	}

	@Before
	public void setUp() {

	}

	@Test
	public void testFullDrawRiver() {

		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;

		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKsQsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKsQsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// TWO_PAIR HIGH
		boardModel = new BoardModel("AdAsKdKsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// TWO_PAIR LOW
		boardModel = new BoardModel("2d2sJdJsQs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.QUEEN));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAhKs2s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKhQs2s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2hKs3s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// FULL ACE
		boardModel = new BoardModel("KdKsAhAsAd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.QUEEN));
		System.out.println(boardModel + " " + fullModel);

		// FULL HIGH
		boardModel = new BoardModel("KdKsKhAsAd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.QUEEN));
		System.out.println(boardModel + " " + fullModel);

		// FULL LOW
		boardModel = new BoardModel("2d2s2hKsKd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// CARRE ACE
		boardModel = new BoardModel("AdAsAhAc2d", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// CARRE HIGH
		boardModel = new BoardModel("QdQsQhQcTd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// CARRE LOW
		boardModel = new BoardModel("2d2s2h2c3d", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.THREE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.THREE));
		System.out.println(boardModel + " " + fullModel);
	}

	@Test
	public void testFullDrawTurn() {
		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;

		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKsQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKsQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// TWO_PAIR HIGH
		boardModel = new BoardModel("AdAsKdKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// TWO_PAIR LOW
		boardModel = new BoardModel("2d2sJdJs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.TWO));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.JACK));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAhKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKhQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2hKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// CARRE ACE
		boardModel = new BoardModel("AdAsAhAc", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// CARRE HIGH
		boardModel = new BoardModel("QdQsQhQc", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// CARRE LOW
		boardModel = new BoardModel("2d2s2h2c", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);
	}

	@Test
	public void testFullDrawFlop() {
		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;
		
		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKs", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKs", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAh", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKh", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2h", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getNutsOrHoleCards().first().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));
		System.out.println(boardModel + " " + fullModel);
	}

	@Test
	public void testFindFlush() {
		BoardModel boardModel;
		ArrayList<DrawModel> listDraw = new ArrayList<>();
		FlushModel flushModel;
		
		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.SPADE));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));		
		System.out.println(boardModel + " " + flushModel);

		listDraw.clear();
		boardModel = new BoardModel("7c4c6hAc", DealStep.TURN);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));		
		System.out.println(boardModel + " " + flushModel);
		
		listDraw.clear();
		boardModel = new BoardModel("7c4c6cAc", DealStep.TURN);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.KING));		
		System.out.println(boardModel + " " + flushModel);

		listDraw.clear();
		boardModel = new BoardModel("7h4s6sQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.JACK));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.ACE));		
		System.out.println(boardModel + " " + flushModel);

		listDraw.clear();		
		boardModel = new BoardModel("Ah4s6hQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.TEN));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.JACK));		
		System.out.println(boardModel + " " + flushModel);
		
		listDraw.clear();
		boardModel = new BoardModel("AhTh6hQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5));
		flushModel = (FlushModel) listDraw.get(0);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().first().getRank().equals(Rank.NINE));
		assertTrue(flushModel.getNutsOrHoleCards().last().getRank().equals(Rank.JACK));		
		System.out.println(boardModel + " " + flushModel);
	}

	@After
	public void tearDown() {

	}
}
