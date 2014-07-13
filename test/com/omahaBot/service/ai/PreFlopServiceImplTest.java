package com.omahaBot.service.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.preFlop.PreFlopSuitLevel;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.exception.HandNoValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower.SuitedType;

public class PreFlopServiceImplTest {

	private PreFlopAnalyser preFlopAnalyser;
	
	@Before
	public void before() {
		preFlopAnalyser = new PreFlopAnalyser();
	}
	
//	@Test
//	public void testIfOnePair() {
//		HandModel handModel = new HandModel("AsKs3c3d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.ONE_PAIR_LOW));
//		
//		handModel = new HandModel("AsKsKc3d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.ONE_PAIR_MEDIUM));
//		
//		handModel = new HandModel("AsAcKc3d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.ONE_PAIR_AS));
//	}
//	
//	@Test
//	public void testIfNotOnePair() {
//		HandModel handModel = new HandModel("As3s3c3d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.ONE_PAIR_LOW));
//		
//		handModel = new HandModel("3h3s3c3d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.ONE_PAIR_LOW));
//	}
//
//	@Test
//	public void testIfTwoPair() {
//		HandModel handModel = new HandModel("AsAh3c3d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.TWO_PAIR_HIGHT));
//		
//		handModel = new HandModel("JsJhTcTd");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.TWO_PAIR_HIGHT));
//		
//		handModel = new HandModel("TsTh3c3d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.TWO_PAIR_MEDIUM));
//		
//		handModel = new HandModel("2s2c3c3d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.TWO_PAIR_LOW));
//	}
//	
//	@Test
//	public void testIfNotTwoPair() {
//		HandModel handModel = new HandModel("As2c3c3d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.TWO_PAIR_LOW));
//	}
//
//	@Test
//	public void testIfConnectorsFour() {
//		HandModel handModel = new HandModel("5s2c3c4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.CONNECTOR4_HIGHT));
//		
//		handModel = new HandModel("As2c3c4d");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.CONNECTOR4_MEDIUM));
//	}
//	
//	@Test
//	public void testIfConnectorsThree() {
//		HandModel handModel = new HandModel("Ks2c3c4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.CONNECTOR3_HIGHT));
//		
//		handModel = new HandModel("As2c3cKd");
//		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopPairLevel.CONNECTOR3_MEDIUM));
//	}
//	
//	@Test
//	public void testIfTwoColor() {
//		HandModel handModel = new HandModel("Ks2s3d4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.TWO_COLORS));
//	}
//	
//	@Test
//	public void testIfNotTwoColor() {
//		HandModel handModel = new HandModel("Kd2d8d4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(!preFlopPower.getPreFlopSuit().equals(PreFlopSuit.TWO_COLORS));
//	}
//	
//	@Test
//	public void testIfOneColor() {
//		HandModel handModel = new HandModel("Ks2c3c4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.ONE_COLOR));
//	}
//	
//	@Test
//	public void testIfNoColor() {
//		HandModel handModel = new HandModel("Ks2h3c4d");
//		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
//		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.NO_COLOR));
//	}

	@Test
	public void testOneSuit() throws HandNoValidException, CardPackNoValidException {
		DealStep dealStep;
		HandModel handModel;

		// ONE_SUIT_A
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2d9cAd9s", dealStep);
		preFlopAnalyser.analyseHand(handModel);
		
		Assert.assertTrue(preFlopAnalyser.getHandPreFlopPower().getPreFlopSuitLevel().equals(PreFlopSuitLevel.ONE_SUIT_A));
		
		// ONE_SUIT_A (3 cards suited)
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2d9cAd9d", dealStep);
		preFlopAnalyser.analyseHand(handModel);
		
		Assert.assertTrue(preFlopAnalyser.getHandPreFlopPower().getPreFlopSuitLevel().equals(PreFlopSuitLevel.ONE_SUIT_A));
		
		// ONE_SUIT_A (4 cards suited)
		dealStep = DealStep.FLOP;
		handModel = new HandModel("2d8dAd9d", dealStep);
		preFlopAnalyser.analyseHand(handModel);
		
		Assert.assertTrue(preFlopAnalyser.getHandPreFlopPower().getPreFlopSuitLevel().equals(PreFlopSuitLevel.ONE_SUIT_A));
	}
}
