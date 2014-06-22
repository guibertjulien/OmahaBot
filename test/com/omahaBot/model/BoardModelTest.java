package com.omahaBot.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.exception.CardPackNonValidException;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FlushModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.model.hand.HandModel;

public class BoardModelTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("==================================================================");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testPermutations() {

		BoardModel boardModel;
		List<List<CardModel>> permutations;
		
		// PERMUTATIONS 2
		boardModel = new BoardModel("Ad2s3s", DealStep.FLOP);
		permutations= boardModel.permutations(2);
		System.out.println(permutations);
		assertTrue(permutations.size() == 3);
		
		boardModel = new BoardModel("Ad2s3s4s", DealStep.TURN);
		permutations= boardModel.permutations(2);
		System.out.println(permutations);
		assertTrue(permutations.size() == 6);
		
		boardModel = new BoardModel("Ad2s3s4s5s", DealStep.RIVER);
		permutations= boardModel.permutations(2);
		System.out.println(permutations);
		assertTrue(permutations.size() == 10);
		
		// PERMUTATIONS 3
		boardModel = new BoardModel("Ad2s3s", DealStep.FLOP);
		permutations= boardModel.permutations(3);
		System.out.println(permutations);
		assertTrue(permutations.size() == 1);
		
		boardModel = new BoardModel("Ad2s3s4s", DealStep.TURN);
		permutations= boardModel.permutations(3);
		System.out.println(permutations);
		assertTrue(permutations.size() == 4);
		
		boardModel = new BoardModel("Ad2s3s4s5s", DealStep.RIVER);
		permutations= boardModel.permutations(3);
		System.out.println(permutations);
		assertTrue(permutations.size() == 10);
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
	public final void testSearchQuadsDraw() throws CardPackNonValidException {
		BoardModel boardModel;
		HandModel handModel;
		QuadsModel quadsModel;
		ArrayList<DrawModel> listDraw = new ArrayList<>();

	    // =========================================================================
	    // DRAWS POSSIBLES
	    // =========================================================================
		
		listDraw.clear();
		boardModel = new BoardModel("7sKsKd", DealStep.FLOP);
		handModel = new HandModel("2s3s4s5s");
		listDraw.addAll(boardModel.searchQuadsDraw(handModel));
		quadsModel = (QuadsModel) listDraw.get(0);		
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(quadsModel.getBoardCategory().equals(BoardCategory.ONE_PAIR));
		assertTrue(quadsModel.getRank().equals(Rank.KING));
		
		listDraw.clear();
		boardModel = new BoardModel("KhKsKd", DealStep.FLOP);
		handModel = new HandModel("2s3s4s5s");
		listDraw.addAll(boardModel.searchQuadsDraw(handModel));
		quadsModel = (QuadsModel) listDraw.get(0);		
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(quadsModel.getBoardCategory().equals(BoardCategory.THREE_OF_A_KIND));
		assertTrue(quadsModel.getRank().equals(Rank.KING));

		// handModel has 2 same rank cards
		listDraw.clear();
		boardModel = new BoardModel("KhKsQdJc", DealStep.TURN);
		handModel = new HandModel("2s3sKcKd");
		listDraw.addAll(boardModel.searchQuadsDraw(handModel));
		quadsModel = (QuadsModel) listDraw.get(0);		
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(quadsModel.getBoardCategory().equals(BoardCategory.ONE_PAIR));
		assertTrue(quadsModel.getRank().equals(Rank.KING));
		
	    // =========================================================================
	    // NO DRAWS
	    // =========================================================================
		
		listDraw.clear();
		boardModel = new BoardModel("KhKsKdKc", DealStep.TURN);
		handModel = new HandModel("2s3s4s5s");
		listDraw.addAll(boardModel.searchQuadsDraw(handModel));
		System.out.println(boardModel);
		System.out.println(listDraw);
		
		// handModel with only same rank card
		listDraw.clear();
		boardModel = new BoardModel("KhKsQdJc", DealStep.TURN);
		handModel = new HandModel("2s3s4sKc");
		listDraw.addAll(boardModel.searchQuadsDraw(handModel));
		System.out.println(boardModel);
		System.out.println(listDraw);
	}

	@Test
	public final void testSearchBestSetDraw() {
		BoardModel boardModel;
		SetModel setModel;

		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
		setModel = (SetModel) boardModel.searchBestSetDraw();
		System.out.println(boardModel);
		System.out.println(setModel);
		assertTrue(setModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(setModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));
		
		boardModel = new BoardModel("7s4sKsAh", DealStep.TURN);
		setModel = (SetModel) boardModel.searchBestSetDraw();
		System.out.println(boardModel);
		System.out.println(setModel);
		assertTrue(setModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.ACE));
		assertTrue(setModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));
		
		boardModel = new BoardModel("7s4s5s6h2d", DealStep.RIVER);
		setModel = (SetModel) boardModel.searchBestSetDraw();
		System.out.println(boardModel);
		System.out.println(setModel);
		assertTrue(setModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.SEVEN));
		assertTrue(setModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.SEVEN));	
	}

	@Test
	public final void testSearchBestTwoPairDraw() {
		BoardModel boardModel;
		TwoPairModel twoPairModel;

		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
		twoPairModel = (TwoPairModel) boardModel.searchBestTwoPairDraw();
		System.out.println(boardModel);
		System.out.println(twoPairModel);
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.SEVEN));
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));
		
		boardModel = new BoardModel("7s4sKsAh", DealStep.TURN);
		twoPairModel = (TwoPairModel) boardModel.searchBestTwoPairDraw();
		System.out.println(boardModel);
		System.out.println(twoPairModel);
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.KING));
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));
		
		boardModel = new BoardModel("7s4s5sAh2d", DealStep.RIVER);
		twoPairModel = (TwoPairModel) boardModel.searchBestTwoPairDraw();
		System.out.println(boardModel);
		System.out.println(twoPairModel);
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.SEVEN));
		assertTrue(twoPairModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));
	}

	@Test
	public final void testSearchStraightDraw() {
		BoardModel boardModel;
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		listDraw.clear();
		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("KsQsJs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("KsQsTs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("KsQs9s", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("KsKdKh", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("KsKdQs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("2s3d5s7d", DealStep.TURN);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("2s3d5s7d8h", DealStep.RIVER);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("QsKhAc", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());

		listDraw.clear();
		boardModel = new BoardModel("2s3hAc", DealStep.FLOP);
		listDraw.addAll(boardModel.searchStraightDraw());
		assertTrue(!listDraw.isEmpty());
	}

	@Test
	public final void testSearchFlushDraw() {
		BoardModel boardModel;
		ArrayList<DrawModel> listDraw = new ArrayList<>();
		FlushModel flushModel;

		boardModel = new BoardModel("7s4sKs", DealStep.FLOP);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.SPADE));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		listDraw.clear();
		boardModel = new BoardModel("7c4c6hAc", DealStep.TURN);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		listDraw.clear();
		boardModel = new BoardModel("7c4c6cAc", DealStep.TURN);
		listDraw.addAll(boardModel.searchFlushDraw(2, 4, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.CLUB));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.QUEEN));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.KING));

		listDraw.clear();
		boardModel = new BoardModel("7h4s6sQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.JACK));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.ACE));

		listDraw.clear();
		boardModel = new BoardModel("Ah4s6hQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.TEN));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.JACK));

		listDraw.clear();
		boardModel = new BoardModel("AhTh6hQhKh", DealStep.RIVER);
		listDraw.addAll(boardModel.searchFlushDraw(3, 5, null));
		flushModel = (FlushModel) listDraw.get(0);
		System.out.println(boardModel);
		System.out.println(flushModel);
		assertTrue(flushModel.getSuit().equals(Suit.HEART));
		assertTrue(flushModel.getNutsOrHoleCards().getCard1().getRank().equals(Rank.NINE));
		assertTrue(flushModel.getNutsOrHoleCards().getCard2().getRank().equals(Rank.JACK));
	}

	@Test
	public final void testInitDraws() throws CardPackNonValidException {
		BoardModel boardModel;
		HandModel handModel;// just for dead cards
		ArrayList<DrawModel> listDraw = new ArrayList<>();
		
	    // =========================================================================
	    // RANK DRAWS
	    // =========================================================================
		
		listDraw.clear();
		boardModel = new BoardModel("2s4d7c", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 3);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.ONE_PAIR));

		listDraw.clear();
		boardModel = new BoardModel("2s7d7c", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 5);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.FULL_HOUSE));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		// no FOUR_OF_A_KIND draw
		listDraw.clear();
		boardModel = new BoardModel("2s7d7c", DealStep.FLOP);
		handModel = new HandModel("KsKhKd7c");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FULL_HOUSE));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("2s7d7c", DealStep.FLOP);
		handModel = new HandModel("KsKh7d7c");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 5);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.FULL_HOUSE));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.ONE_PAIR));
		
	    // =========================================================================
	    // FLUSH DRAWS
	    // =========================================================================
		
		listDraw.clear();
		boardModel = new BoardModel("2s3s9c", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FLUSH_DRAW));	
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("2s3s9s", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FLUSH));	
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("2s3s9dTd", DealStep.TURN);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 5);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FLUSH_DRAW));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.FLUSH_DRAW));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("2s3s9dTdAd", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 5);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FLUSH));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.ONE_PAIR));
		
	    // =========================================================================
	    // STRAIGHT DRAWS
	    // =========================================================================
		
		listDraw.clear();
		boardModel = new BoardModel("2s3s9dTdAc", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));	
		
		listDraw.clear();
		boardModel = new BoardModel("As2h3d", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));	
		
		listDraw.clear();
		boardModel = new BoardModel("As2h4d", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));	

		listDraw.clear();
		boardModel = new BoardModel("As2h5d", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));	
		
		// no straight draw
		listDraw.clear();
		boardModel = new BoardModel("As2h6d", DealStep.FLOP);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 3);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.ONE_PAIR));	
		
		listDraw.clear();
		boardModel = new BoardModel("AsThJdQc", DealStep.TURN);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));	
		
		listDraw.clear();
		boardModel = new BoardModel("8sThJdQc", DealStep.TURN);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("8sThJd2c", DealStep.TURN);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));

		// STRAIGH ACE LOW is not return => best STRAIGHT is return
		listDraw.clear();
		boardModel = new BoardModel("AsThJd2c5s", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 4);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		// with flush draw
		listDraw.clear();
		boardModel = new BoardModel("AsTsJd2c5s", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 5);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FLUSH));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.ONE_PAIR));
		
	    // =========================================================================
	    // ALL DRAWS
	    // =========================================================================		
		
		listDraw.clear();
		boardModel = new BoardModel("AsTsJd2c2s", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 7);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.FULL_HOUSE));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.FLUSH));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.STRAIGHT));// TJQKA
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(5).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(6).getHandCategory().equals(HandCategory.ONE_PAIR));
		
		listDraw.clear();
		boardModel = new BoardModel("As9s3d2s9c", DealStep.RIVER);
		handModel = new HandModel("KsKhKdKc");
		listDraw.addAll(boardModel.initDraws(handModel));
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 7);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		assertTrue(listDraw.get(1).getHandCategory().equals(HandCategory.FULL_HOUSE));
		assertTrue(listDraw.get(2).getHandCategory().equals(HandCategory.FLUSH));
		assertTrue(listDraw.get(3).getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));// A2345
		assertTrue(listDraw.get(4).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		assertTrue(listDraw.get(5).getHandCategory().equals(HandCategory.TWO_PAIR));
		assertTrue(listDraw.get(6).getHandCategory().equals(HandCategory.ONE_PAIR));
	}
}
