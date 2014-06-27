package com.omahaBot.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.enums.preFlop.PreFlopStraightLevel;
import com.omahaBot.exception.CardPackNonValidException;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.OnePairModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;

public class HandModelTest {

	private static String PROPERTY_FILE_NAME = "config.properties";

	@Before
	public void setUp() throws Exception {
		System.out.println("==================================================================");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = CardPackNonValidException.class)
	public final void testHandModelifSameCards() throws CardPackNonValidException {
		HandModel handModel = new HandModel("2s2s4s5s");
		System.out.println(handModel);
	}

	@Test
	public final void testHandModel() throws CardPackNonValidException {
		HandModel handModel = new HandModel("2s3s4s5s");
		System.out.println(handModel);
	}

	@Test
	public final void testPermutations() throws CardPackNonValidException {
		HandModel handModel;
		List<List<CardModel>> permutations;

		// PERMUTATIONS 3
		handModel = new HandModel("Ad2s3s4s");
		permutations = handModel.permutations();
		System.out.println(permutations);
		assertTrue(permutations.size() == 6);
	}

    // =========================================================================
    // RANK DRAWS
    // =========================================================================
	
	@Test
	public final void testInitCombinaisonDrawsOfRank() throws CardPackNonValidException {
		HandModel handModel;
		BoardModel boardModel;
		ArrayList<DrawModel> listDraw = new ArrayList<>();
		OnePairModel onePairModel;
		TwoPairModel twoPairModel;
		SetModel setModel;
		FullModel fullModel;
		QuadsModel quadsModel;

	    // =========================================================================
	    // ONE_PAIR
	    // =========================================================================
		
		// ONE_PAIR with one hand card and one board card
		listDraw.clear();
		handModel = new HandModel("2s3sTsJs");
		boardModel = new BoardModel("4d3c6h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.ONE_PAIR));
		onePairModel = (OnePairModel) listDraw.get(0);
		assertTrue(onePairModel.getRank().equals(Rank.THREE));
		
		// ONE_PAIR in hand
		listDraw.clear();
		handModel = new HandModel("2s2cTsJs");
		boardModel = new BoardModel("4d5c6h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.ONE_PAIR));
		onePairModel = (OnePairModel) listDraw.get(0);
		assertTrue(onePairModel.getRank().equals(Rank.TWO));
		
		// ONE_PAIR in board
		listDraw.clear();
		handModel = new HandModel("2s3cTsJs");
		boardModel = new BoardModel("4d6c6h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.ONE_PAIR));
		onePairModel = (OnePairModel) listDraw.get(0);
		assertTrue(onePairModel.getRank().equals(Rank.SIX));

	    // =========================================================================
	    // TWO_PAIR
	    // =========================================================================
		
		// TWO_PAIR
		listDraw.clear();
		handModel = new HandModel("2s3sTsJs");
		boardModel = new BoardModel("2d5c6hJh", DealStep.TURN);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.TWO_PAIR));
		twoPairModel = (TwoPairModel) listDraw.get(0);
		assertTrue(twoPairModel.getRankPair1().equals(Rank.JACK));
		assertTrue(twoPairModel.getRankPair2().equals(Rank.TWO));
		
		// TWO_PAIR : one pair in hand and one pair in board
		listDraw.clear();
		handModel = new HandModel("2s3sTsTd");
		boardModel = new BoardModel("4d6c6hJhQc", DealStep.RIVER);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.TWO_PAIR));
		twoPairModel = (TwoPairModel) listDraw.get(0);
		assertTrue(twoPairModel.getRankPair1().equals(Rank.TEN));
		assertTrue(twoPairModel.getRankPair2().equals(Rank.SIX));
		
		// TWO_PAIR : one pair in hand and one pair in board, pair of TEN is cleaned by pair of ACE
		listDraw.clear();
		handModel = new HandModel("AdAsTsTd");
		boardModel = new BoardModel("4d6c6hJhQc", DealStep.RIVER);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.TWO_PAIR));
		twoPairModel = (TwoPairModel) listDraw.get(0);
		assertTrue(twoPairModel.getRankPair1().equals(Rank.ACE));
		assertTrue(twoPairModel.getRankPair2().equals(Rank.SIX));
		
	    // =========================================================================
	    // THREE_OF_A_KIND
	    // =========================================================================

		// pair on board
		listDraw.clear();
		handModel = new HandModel("2s3sTsJs");
		boardModel = new BoardModel("4d3c3h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		setModel = (SetModel) listDraw.get(0);
		assertTrue(setModel.getRank().equals(Rank.THREE));
		
		// pair on hand
		listDraw.clear();
		handModel = new HandModel("2s3sTs3d");
		boardModel = new BoardModel("4d3c7h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.THREE_OF_A_KIND));
		setModel = (SetModel) listDraw.get(0);
		assertTrue(setModel.getRank().equals(Rank.THREE));
		
	    // =========================================================================
	    // FULL_HOUSE
	    // =========================================================================

		listDraw.clear();
		handModel = new HandModel("2s2cTsTd");
		boardModel = new BoardModel("3d3c3h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FULL_HOUSE));
		fullModel = (FullModel) listDraw.get(0);
		assertTrue(fullModel.getRankPair().equals(Rank.TEN));
		assertTrue(fullModel.getRankThree().equals(Rank.THREE));
		assertTrue(fullModel.getBoardCategory().equals(BoardCategory.THREE_OF_A_KIND));
		
		listDraw.clear();
		handModel = new HandModel("2s2cTs3c");
		boardModel = new BoardModel("3dTc3h", DealStep.FLOP);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FULL_HOUSE));
		fullModel = (FullModel) listDraw.get(0);
		assertTrue(fullModel.getRankPair().equals(Rank.TEN));
		assertTrue(fullModel.getRankThree().equals(Rank.THREE));
		assertTrue(fullModel.getBoardCategory().equals(BoardCategory.ONE_PAIR));
		
	    // =========================================================================
	    // FOUR_OF_A_KIND
	    // =========================================================================

		listDraw.clear();
		handModel = new HandModel("2s3sTs3c");
		boardModel = new BoardModel("3dTc3hTd", DealStep.TURN);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		quadsModel = (QuadsModel) listDraw.get(0);
		assertTrue(quadsModel.getRank().equals(Rank.THREE));
		assertTrue(quadsModel.getBoardCategory().equals(BoardCategory.ONE_PAIR));

		listDraw.clear();
		handModel = new HandModel("2s2cTs3c");
		boardModel = new BoardModel("3dTc3hTs3s", DealStep.RIVER);
		listDraw.addAll(handModel.initCombinaisonDraws(boardModel));
		System.out.println(handModel);
		System.out.println(boardModel);
		System.out.println(listDraw);
		assertTrue(listDraw.size() == 1);
		assertTrue(listDraw.get(0).getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));
		quadsModel = (QuadsModel) listDraw.get(0);
		assertTrue(quadsModel.getRank().equals(Rank.THREE));
		assertTrue(quadsModel.getBoardCategory().equals(BoardCategory.THREE_OF_A_KIND));
	}
	
    // =========================================================================
    // RANK DRAWS
    // =========================================================================
	
    // =========================================================================
    // RANK DRAWS
    // =========================================================================
	
	@Test
	public final void testIsStraight() throws CardPackNonValidException {
		HandModel handModel;
		BoardModel boardModel;
		TreeSet<DrawModel> draws;
		
		// STRAIGHT
		handModel = new HandModel("2s3sTsJs");
		boardModel = new BoardModel("4d5c6h", DealStep.FLOP);
		draws = handModel.initCombinaisonDraws(boardModel);
		assertTrue(handModel.isStraight(draws));
		
		// STRAIGHT_ACE_LOW
		handModel = new HandModel("As2sTsJs");
		boardModel = new BoardModel("4d5cJh3c", DealStep.TURN);
		draws = handModel.initCombinaisonDraws(boardModel);
		assertTrue(handModel.isStraight(draws));
		
		// no STRAIGHT
		handModel = new HandModel("2s5sTsJs");
		boardModel = new BoardModel("4d5c6h", DealStep.FLOP);
		draws = handModel.initCombinaisonDraws(boardModel);
		assertFalse(handModel.isStraight(draws));
		
		handModel = new HandModel("2s3s4sJs");
		boardModel = new BoardModel("Qd5c6h", DealStep.FLOP);
		draws = handModel.initCombinaisonDraws(boardModel);
		assertFalse(handModel.isStraight(draws));
	}

	// =========================================================================
	// FLUSH DRAWS
	// =========================================================================

	@Test
	public final void testHasFlushDraw() throws CardPackNonValidException {
		HandModel handModel;

		handModel = new HandModel("As2d3h4c");
		assertTrue(!handModel.hasFlushDraw());

		handModel = new HandModel("As2s3d4h");
		assertTrue(handModel.hasFlushDraw());

		handModel = new HandModel("As2s3s4h");
		assertTrue(handModel.hasFlushDraw());

		handModel = new HandModel("As2s3s4s");
		assertTrue(handModel.hasFlushDraw());

		handModel = new HandModel("As2s3d4d");
		assertTrue(handModel.hasFlushDraw());
	}

	@Test
	public final void testSearchStraightDrawType() throws CardPackNonValidException {
		HandModel handModel;
		BoardModel boardModel;
		StraightDrawType straightDrawType;

		// test if Gut-Shot
		handModel = new HandModel("AsJs9s2s");
		boardModel = new BoardModel("4s5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));

		// test if Gut-Shot
		handModel = new HandModel("3sJs9s2s");
		boardModel = new BoardModel("As5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));

		// test if Gut-Shot
		handModel = new HandModel("AsJs6s2s");
		boardModel = new BoardModel("4s5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));

		// test if Open-Ended [45][67]
		handModel = new HandModel("QsQh5d4c");
		boardModel = new BoardModel("Ks6s7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.OPEN_ENDED));

		// test if Open-Ended : [57][46]
		handModel = new HandModel("5s7s5h7d");
		boardModel = new BoardModel("4sJs6d", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.OPEN_ENDED));
		
		// test if Inside Broadway
		handModel = new HandModel("KsJsTs5s");
		boardModel = new BoardModel("AsQs7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.INSIDE_BROADWAY));
		
		// test if 12 outs Straight Draw
		handModel = new HandModel("QsJs9s7s");
		boardModel = new BoardModel("Ts8s2s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD12_DRAW));

		// test if 13 Card Wrap
		handModel = new HandModel("9s8s6s5s");
		boardModel = new BoardModel("Ks7s4s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD13_WRAP));

		// test if 17 Card Wrap
		handModel = new HandModel("Ts9s6s2s");
		boardModel = new BoardModel("As8s7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD17_WRAP));

		// test if 20 Card Wrap
		handModel = new HandModel("Ts9s6s5s");
		boardModel = new BoardModel("8s7s2s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD20_WRAP));
	}

	@Test
	public void testHandPreFlopPowerTest() throws IOException, CardPackNonValidException {

		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));

		assertNotNull(properties.get("bestHands"));

		HandModel handModel;
		HandPreFlopPower handPreFlopPower;

		handModel = new HandModel("AsAdJdJs");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("AsAdKdKc");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("2s3d4h5c");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("AsAd2h3c");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("KsKd2h3c");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("9sTcJhJc");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("9sKcJhJc");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("9s7dQhJc");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
	}
	
	@Test
	public void testBug1() throws CardPackNonValidException {
		// - Ma main est une poubelle !	[hand=[Td, Qh, Ks, Ac] : 
		// => CONNECTOR3_INSIDE(PreFlopPowerPoint.LOW, "AKQ", "23.A"),
		
		HandModel handModel = new HandModel("TsKcAcQd");
		PreFlopStraightLevel preFlopStraightLevel = PreFlopStraightLevel.fromTypeAndHand(handModel.toRankString());
		assertTrue(preFlopStraightLevel.equals(PreFlopStraightLevel.CONNECTOR3_INSIDE));
	}
}
