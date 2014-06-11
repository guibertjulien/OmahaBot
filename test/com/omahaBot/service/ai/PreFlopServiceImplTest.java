package com.omahaBot.service.ai;

import org.junit.Before;

public class PreFlopServiceImplTest {

	private PreFlopAnalyser preFlopAnalyserServiceImpl;
	
	@Before
	public void before() {
		preFlopAnalyserServiceImpl = new PreFlopAnalyser();
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
}
