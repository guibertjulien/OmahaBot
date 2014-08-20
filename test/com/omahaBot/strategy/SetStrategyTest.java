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
public class SetStrategyTest {

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
	public final void testSet_10_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAcAd", dealStep);
		boardModel = new BoardModel("3hAs8d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_10_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhQcQd", dealStep);
		boardModel = new BoardModel("3hQs8d6c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_10_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhQcQs", dealStep);
		boardModel = new BoardModel("2h3sQd7c8s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_11_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKh8c8s", dealStep);
		boardModel = new BoardModel("3hAs8d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_11_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKh8c8s", dealStep);
		boardModel = new BoardModel("3hQs8d6c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_11_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKh7s7d", dealStep);
		boardModel = new BoardModel("2h3sQd7c8s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_50_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4dKh8c5s", dealStep);
		boardModel = new BoardModel("3h3s3d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_50));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_50_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("4dKh8c5s", dealStep);
		boardModel = new BoardModel("3h3s3d7c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_50));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	/**
	 * Quads Draw of 3
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testSet_50_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4dKh8c5s", dealStep);
		boardModel = new BoardModel("3h3s3d8h8d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_50));
		Assert.assertTrue(bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_30_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4dKh8c8d", dealStep);
		boardModel = new BoardModel("2s3s8h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_30_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("4dKh8c8d", dealStep);
		boardModel = new BoardModel("2s3s8h7h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_30));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	/**
	 * impossible 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testSet_30_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4dKhQcQd", dealStep);
		boardModel = new BoardModel("2s3sQh8h7c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testSet_31_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4dKh3c3d", dealStep);
		boardModel = new BoardModel("2s3s8h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_31));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_31_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("4dKh7c7d", dealStep);
		boardModel = new BoardModel("2s3s8h7h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_31));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	/**
	 * impossible 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testSet_31_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4dKh7h7d", dealStep);
		boardModel = new BoardModel("2s3sQh8h7c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_11));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_20_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4dKh8c8d", dealStep);
		boardModel = new BoardModel("2s3s8s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_20_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("4dKh8c8d", dealStep);
		boardModel = new BoardModel("2s3s8h7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	/**
	 * impossible 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testSet_20_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4dKhQcQd", dealStep);
		boardModel = new BoardModel("2s3sQh8h7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testSet_21_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4dKh3c3d", dealStep);
		boardModel = new BoardModel("2s3s8s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_21_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("4dKh7c7d", dealStep);
		boardModel = new BoardModel("2s3s8h7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	/**
	 * impossible 
	 * @throws CardPackNoValidException
	 */
	@Test
	public final void testSet_21_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("4dKh7h7d", dealStep);
		boardModel = new BoardModel("2s3sQh8h7s", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}	
	
	@Test
	public final void testSet_40_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2dKh5s5d", dealStep);
		boardModel = new BoardModel("5c3s4h", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_40));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testSet_40_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("2cKh5s5d", dealStep);
		boardModel = new BoardModel("5c3s4h2d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(SetStrategy.Set_40));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
}
