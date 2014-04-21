package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.HandModel;

public class PostFlopServiceImplTest {

	private PostFlopAnalyserServiceImpl postFlopAnalyserServiceImpl;
	
	@Before
	public void before() {
		postFlopAnalyserServiceImpl = new PostFlopAnalyserServiceImpl();
	}
	
	@Test
	public void testIfOnePair() {
		HandModel handModel = new HandModel("AsKh3c3d");
		BoardModel boardModel = new BoardModel("2s4s6s", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.ONE_PAIR));
		
		handModel = new HandModel("AsKh3c5d");
		boardModel = new BoardModel("2s6s6h", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.ONE_PAIR));

		handModel = new HandModel("AsKh3c4d");
		boardModel = new BoardModel("2s4s6s", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.ONE_PAIR));		
	}
	
	@Test
	public void testIfTwoPair() {
		HandModel handModel = new HandModel("AsKh3c3d");
		BoardModel boardModel = new BoardModel("2s6h6s", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.TWO_PAIR));
		
		handModel = new HandModel("AsKh3c5d");
		boardModel = new BoardModel("5sKs6h", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.TWO_PAIR));	
		
		handModel = new HandModel("AsKh3c5d");
		boardModel = new BoardModel("5sKsAh", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.TWO_PAIR));	
		
		/**
		 * meilleur kicker à déterminer
		 * 3 paires cachée, commune ?
		 */
	}
	
	@Test
	public void testIfThreeOfAKind() {
		// community
		HandModel handModel = new HandModel("AsKh3c6d");
		BoardModel boardModel = new BoardModel("2s6h6s", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.THREE_OF_A_KIND));

		// hole
		handModel = new HandModel("KcKh3c8d");
		boardModel = new BoardModel("5sKs6h", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.THREE_OF_A_KIND));	
		
		/**
		 * hole or not ?
		 */
	}
	
	@Test
	public void testIfFourOfAKind() {
		HandModel handModel = new HandModel("KcKh3c8d");
		BoardModel boardModel = new BoardModel("5sKsKd", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.FOUR_OF_A_KIND));	
		
		handModel = new HandModel("Kc4h3s8d");
		boardModel = new BoardModel("KhKsKd", DealStep.FLOP);
		postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.FOUR_OF_A_KIND));	
	}
	
	@Test
	public void testIfNotFourOfAKind() {
		HandModel handModel = new HandModel("KcKhKs8d");
		BoardModel boardModel = new BoardModel("5sAsKd", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(!postFlopPowerType.equals(PostFlopPowerType.FOUR_OF_A_KIND));	
	}
	
	@Test
	public void testIfStraight() {
		HandModel handModel = new HandModel("Ac2hKs8d");
		BoardModel boardModel = new BoardModel("3s4s5d", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.STRAIGHT));	
	}
	
	@Test
	public void testIfFlush() {
		HandModel handModel = new HandModel("Ac2cKs8d");
		BoardModel boardModel = new BoardModel("3c4c6c", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.FLUSH));	
	}
	
	@Test
	public void testIfStraightFlush() {
		HandModel handModel = new HandModel("Ac2cKs8d");
		BoardModel boardModel = new BoardModel("3c4c5c", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.STRAIGHT_FLUSH));	
	}
	
	@Test
	public void testIfFull() {
		HandModel handModel = new HandModel("AcAdKs8d");
		BoardModel boardModel = new BoardModel("Ah4c4h", DealStep.FLOP);
		PostFlopPowerType postFlopPowerType = postFlopAnalyserServiceImpl.analyseHandPostFlop(handModel, boardModel);
		assertTrue(postFlopPowerType.equals(PostFlopPowerType.FULL_HOUSE));	
	}
	
	
}
