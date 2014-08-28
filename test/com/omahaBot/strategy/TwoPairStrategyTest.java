package com.omahaBot.strategy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.LastPlayerBetType;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.service.ai.PostFlopAnalyser;

/**
 * CAUTION : test sysout
 * 
 * @author Julien
 *
 */
public class TwoPairStrategyTest {

	private PostFlopAnalyser analyserService;

	HandModel handModel;
	BoardModel boardModel;
	DealStep dealStep;
	StrategyContext context;

	ByteArrayOutputStream baos;
	PrintStream old;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		analyserService = new PostFlopAnalyser();

		// Create a stream to hold the output
		baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testTwoPair_10_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("3hAs8d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_10_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("3hAs8d6c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_10_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAcJc", dealStep);
		boardModel = new BoardModel("3hAs8d6cJs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testTwoPair_11_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("3hAs9d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_11_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("3hAs9d6c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_11_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAcJc", dealStep);
		boardModel = new BoardModel("3hAs8d6c9s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_20_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc5c", dealStep);
		boardModel = new BoardModel("8hAs8d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_20_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc5c", dealStep);
		boardModel = new BoardModel("8hAs8d3c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_20_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAc5c", dealStep);
		boardModel = new BoardModel("JhAs8d3cJs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_21_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhKc5c", dealStep);
		boardModel = new BoardModel("JhAs8d3cJs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_30_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_30_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs3s7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_30_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs3s7d5c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_31_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_31));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_31_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs3s7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_31));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_31_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs3s7d5c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_40_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_40));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_40_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs3c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_40));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_40_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAc8c", dealStep);
		boardModel = new BoardModel("8sAs3c7d5c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testTwoPair_41_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_41));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_41_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs3c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_41));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_41_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAc7c", dealStep);
		boardModel = new BoardModel("8sAs3c7d5c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testTwoPair_50_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("TdKh9d7c", dealStep);
		boardModel = new BoardModel("8s9c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_52));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_50_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("TdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_50));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_50_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("TdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h2s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_51_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("JdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_51));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_51_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("JdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_51));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_51_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("JdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h2s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_52_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("QdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_52));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_52_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("QdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_52));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_52_60_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("QdKh8c7c", dealStep);
		boardModel = new BoardModel("8s9c7d3h2s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_60));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testTwoPair_whithPairInHandAndNuts_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("AdAh8c7c", dealStep);
		boardModel = new BoardModel("5s5c7d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(TwoPairStrategy.TwoPair_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
}
