package com.omahaBot.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;

public class BoardModelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("---------------------------------------------------------");
		System.out.println("NEW TEST");
		System.out.println("---------------------------------------------------------");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testPermutations() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearchBestFullDrawFlop() {

		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;

		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKs", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKs", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAh", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKh", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2h", DealStep.FLOP);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));
	}

	@Test
	public final void testSearchBestFullDrawTurn() {

		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;

		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKsQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKsQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// TWO_PAIR HIGH
		boardModel = new BoardModel("AdAsKdKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// TWO_PAIR LOW
		boardModel = new BoardModel("2d2sJdJs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.TWO));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.JACK));

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAhKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKhQs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2hKs", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// CARRE ACE
		boardModel = new BoardModel("AdAsAhAc", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// CARRE HIGH
		boardModel = new BoardModel("QdQsQhQc", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// CARRE LOW
		boardModel = new BoardModel("2d2s2h2c", DealStep.TURN);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));
	}

	@Test
	public final void testSearchBestFullDrawRiver() {

		BoardModel boardModel;
		DrawModel drawModel;
		FullModel fullModel;

		// ONE_PAIR HIGH
		boardModel = new BoardModel("AdAsKsQsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// ONE_PAIR LOW
		boardModel = new BoardModel("2d2sKsQsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// TWO_PAIR HIGH
		boardModel = new BoardModel("AdAsKdKsTs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// TWO_PAIR LOW
		boardModel = new BoardModel("2d2sJdJsQs", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.QUEEN));

		// BRELAN ACE
		boardModel = new BoardModel("AdAsAhKs2s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// BRELAN HIGH
		boardModel = new BoardModel("KdKsKhQs2s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// BRELAN LOW
		boardModel = new BoardModel("2d2s2hKs3s", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// FULL ACE
		boardModel = new BoardModel("KdKsAhAsAd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.QUEEN));

		// FULL HIGH
		boardModel = new BoardModel("KdKsKhAsAd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.QUEEN));

		// FULL LOW
		boardModel = new BoardModel("2d2s2hKsKd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// CARRE ACE
		boardModel = new BoardModel("AdAsAhAc2d", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		// CARRE HIGH
		boardModel = new BoardModel("QdQsQhQcTd", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		// CARRE LOW
		boardModel = new BoardModel("2d2s2h2c3d", DealStep.RIVER);
		drawModel = boardModel.searchBestFullDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		System.out.println(boardModel);
		System.out.println(fullModel);
		assertTrue(fullModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.THREE));
		assertTrue(fullModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.THREE));
	}

	@Test
	public final void testSearchQuadsDraw() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearchBestSetDraw() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearchBestTwoPairDraw() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSearchStraightDraw() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInitDraws() {
		fail("Not yet implemented"); // TODO
	}

}
