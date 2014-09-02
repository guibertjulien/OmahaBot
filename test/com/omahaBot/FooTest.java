package com.omahaBot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.LastPlayerBetType;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.service.ai.PostFlopAnalyser;
import com.omahaBot.strategy.StrategyContext;

public class FooTest {

	private PostFlopAnalyser analyserService;

	HandModel handModel;
	BoardModel boardModel;
	DealStep dealStep;
	StrategyContext context;

	@Before
	public void setUp() throws Exception {
		analyserService = new PostFlopAnalyser();
	}
	
	@Test
	public void division() {
		System.out.println(10 % 3);
		System.out.println(6 % 3);
	}

	@Test
	public void testBug1() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2c8s8dQs", dealStep);
		boardModel = new BoardModel("6dJhJd", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
	}
	
	@Test
	public void testBug2() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2c3c4dQd", dealStep);
		boardModel = new BoardModel("9cTsTd", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
	}
	
	@Test
	public void testBug3() throws CardPackNoValidException {
		dealStep = DealStep.FLOP;
		handModel = new HandModel("4d6d7d5d", dealStep);
		boardModel = new BoardModel("8dKdTd", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
	}
	
	@Test
	public void testBug4() throws CardPackNoValidException {
		dealStep = DealStep.RIVER;
		handModel = new HandModel("3c8dTcAd", dealStep);
		boardModel = new BoardModel("2s2d4h5dJc", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
		
		Assert.assertTrue(analyserService.getHandLevel() == 2);
		Assert.assertTrue(analyserService.isNutsForLevel());
	}
	
	@Test
	public void testBug5() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("7sJsQcAs", dealStep);
		boardModel = new BoardModel("2s6sJhJd", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
		
		Assert.assertTrue(analyserService.getHandLevel() == 1);
		Assert.assertTrue(analyserService.isNutsForLevel());
	}
	
	/*
	 * tirage quinte par les 2 bouts
	 * pas de best draws sur le board
	 * CHECK_FOLD 
	 */
	@Test
	public void testBug6() throws CardPackNoValidException {
		dealStep = DealStep.TURN;
		handModel = new HandModel("JsTd2c5s", dealStep);
		boardModel = new BoardModel("7c4dKhQs", dealStep);
		context = new StrategyContext(1, 6, 1, LastPlayerBetType.NO_BET);
		
		analyserService.analyseHand(handModel, boardModel);
		
		analyserService.decide(dealStep, handModel, context);
	}
}
