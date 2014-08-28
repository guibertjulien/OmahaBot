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
public class FlushDrawStrategyTest {

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
	public final void testFlushDraw_10_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2dKhAhAc", dealStep);
		boardModel = new BoardModel("3hAsQh", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw2_10_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4s7hTsJh", dealStep);
		boardModel = new BoardModel("9sQsKc", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFlushDraw_10_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("2dKhAhAc", dealStep);
		boardModel = new BoardModel("3hAsQh7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_11_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("AdKh8hAc", dealStep);
		boardModel = new BoardModel("3hAsQh", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_11_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("AdKh8hAc", dealStep);
		boardModel = new BoardModel("3hAsQh7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_20_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("7dKhAhKc", dealStep);
		boardModel = new BoardModel("3hAsQh", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_20_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7dKhAhKc", dealStep);
		boardModel = new BoardModel("3hAsQh6s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_21_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("7dKhAhKc", dealStep);
		boardModel = new BoardModel("3h2h3d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_21_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7dKhAhKc", dealStep);
		boardModel = new BoardModel("3hAsQh5s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_30_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("7dKh9hKc", dealStep);
		boardModel = new BoardModel("3hAsQh", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_30_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7dKh9hKc", dealStep);
		boardModel = new BoardModel("3hAsQh6s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_31_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("7dKh9hKc", dealStep);
		boardModel = new BoardModel("3hAs2h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_31));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_31_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7dKh9hKc", dealStep);
		boardModel = new BoardModel("3hAsQhQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_31));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	/**
	 * NUTS = STRAIGHT ACE
	 * 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testFlushDraw_40_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sTh9hKs", dealStep);
		boardModel = new BoardModel("3hAsJhQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_40));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFlushDraw_50_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sAh9hKs", dealStep);
		boardModel = new BoardModel("3hAs8hQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_50));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	/**
	 * NUTS = STRAIGHT ACE
	 * 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testFlushDraw_51_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sAh9hKs", dealStep);
		boardModel = new BoardModel("3hAsThQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_51));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFlushDraw_60_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sAh9hJs", dealStep);
		boardModel = new BoardModel("3hAs8hQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_60));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	/**
	 * NUTS = STRAIGHT ACE
	 * 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testFlushDraw_61_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sAh9hJs", dealStep);
		boardModel = new BoardModel("QhAs8hQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FlushDrawStrategy.FlushDraw_61));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

}
