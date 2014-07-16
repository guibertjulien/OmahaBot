package com.omahaBot.service.ai;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.hand.HandModel;

public class PostFlopAnalyserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testPostFlopAnalyser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAnalysePlayers() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAnalyseTournament() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAnalyseLastAction() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAnalyseMyPosition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAnalyseHand() throws CardPackNoValidException {

		DealStep dealStep;
		HandModel handModel;
		BoardModel boardModel;
		PostFlopAnalyser postFlopAnalyser = new PostFlopAnalyser();

		//
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2d9cAd9d", dealStep);
		boardModel = new BoardModel("4d3d6s", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(postFlopAnalyser.iHaveNuts());

		// FLUSH_DRAW
		dealStep = DealStep.FLOP;
		handModel = new HandModel("6s7s8c9c", dealStep);
		boardModel = new BoardModel("2s7hTs", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		// Assert.assertTrue(!postFlopAnalyser.iHaveNuts());

		// FLUSH_DRAW
		dealStep = DealStep.TURN;
		handModel = new HandModel("TcJc2h3s", dealStep);
		boardModel = new BoardModel("Ac9cKhQd", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(!postFlopAnalyser.iHaveNuts());

		// NO FLUSH_DRAW because RIVER
		dealStep = DealStep.RIVER;
		handModel = new HandModel("TcJc2h3s", dealStep);
		boardModel = new BoardModel("Ac9cKhQd6h", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(postFlopAnalyser.iHaveNuts());

		// FLUSH NO NUTS
		dealStep = DealStep.RIVER;
		handModel = new HandModel("TcJc2h3s", dealStep);
		boardModel = new BoardModel("Ac9cKhQd6c", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(postFlopAnalyser.getHandLevel() == 0);
		Assert.assertTrue(!postFlopAnalyser.iHaveNuts());

		// FLUSH NUTS (KICKER)
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4cKc2h3s", dealStep);
		boardModel = new BoardModel("Ac9cKhQd6c", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(postFlopAnalyser.getHandLevel() == 0);
		Assert.assertTrue(postFlopAnalyser.iHaveNuts());

		//
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2h6h6dKd", dealStep);
		boardModel = new BoardModel("KsKhKc", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(postFlopAnalyser.iHaveNuts());

		//
		dealStep = DealStep.TURN;
		handModel = new HandModel("5d6h7hKh", dealStep);
		boardModel = new BoardModel("4sTdJdKd", dealStep);
		postFlopAnalyser.analyseHand(handModel, boardModel);
		Assert.assertTrue(!postFlopAnalyser.iHaveNuts());
	}

	@Test
	public final void testDecide() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDecideFlop() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDecideTurn() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDecideRiver() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetHandDrawsSorted() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetBoardDrawsSorted() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetHandLevel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsNutsForLevel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetHandDrawsSorted() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetBoardDrawsSorted() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetHandLevel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetNutsForLevel() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEquals() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCanEqual() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
