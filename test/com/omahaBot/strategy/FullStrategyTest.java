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
public class FullStrategyTest {

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
	public final void testFull_10_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAcQc", dealStep);
		boardModel = new BoardModel("3hAhAd", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_10_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAcQc", dealStep);
		boardModel = new BoardModel("3hAhAd2d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_10_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAcQc", dealStep);
		boardModel = new BoardModel("3hAhAd2d3c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_10));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}

	@Test
	public final void testFull_20_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2dKhAcAd", dealStep);
		boardModel = new BoardModel("3hAh3c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_20_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("2dKhAcAd", dealStep);
		boardModel = new BoardModel("3hAh3c5d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_20_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("2dKhAcAd", dealStep);
		boardModel = new BoardModel("3hAh3c5dQc", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_20));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_21_FLOP() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("3dKhAc5d", dealStep);
		boardModel = new BoardModel("3hAh3c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_21_TURN() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("3dKhAcQc", dealStep);
		boardModel = new BoardModel("3hAhAd5d", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
	
	@Test
	public final void testFull_21_RIVER() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3dKhAcQc", dealStep);
		boardModel = new BoardModel("3hAhAd5d3c", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);

		analyserService.analyseHand(handModel, boardModel);
		BettingDecision bettingDecision = analyserService.decide(dealStep, handModel, context);

		// Put things back
		System.out.flush();
		System.setOut(old);

		Assert.assertTrue(baos.toString().contains(FullStrategy.Full_21));
		Assert.assertTrue(!bettingDecision.equals(BettingDecision.CHECK_FOLD));
	}
}
