package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.PreFlopPower;
import com.omahaBot.enums.PreFlopRank;
import com.omahaBot.enums.PreFlopSuit;
import com.omahaBot.model.HandModel;

public class PreFlopServiceImplTest {

	private PreFlopAnalyserServiceImpl preFlopAnalyserServiceImpl;
	
	@Before
	public void before() {
		preFlopAnalyserServiceImpl = new PreFlopAnalyserServiceImpl();
	}
	
	@Test
	public void testIfOnePair() {
		HandModel handModel = new HandModel("AsKs3c3d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.ONE_PAIR_LOW));
		
		handModel = new HandModel("AsKsKc3d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.ONE_PAIR_MEDIUM));
		
		handModel = new HandModel("AsAcKc3d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.ONE_PAIR_HIGHT));
	}
	
	@Test
	public void testIfNotOnePair() {
		HandModel handModel = new HandModel("As3s3c3d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopRank.ONE_PAIR_LOW));
		
		handModel = new HandModel("3h3s3c3d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopRank.ONE_PAIR_LOW));
	}

	@Test
	public void testIfTwoPair() {
		HandModel handModel = new HandModel("AsAh3c3d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.TWO_PAIR_HIGHT));
		
		handModel = new HandModel("JsJhTcTd");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.TWO_PAIR_HIGHT));
		
		handModel = new HandModel("TsTh3c3d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.TWO_PAIR_MEDIUM));
		
		handModel = new HandModel("2s2c3c3d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.TWO_PAIR_LOW));
	}
	
	@Test
	public void testIfNotTwoPair() {
		HandModel handModel = new HandModel("As2c3c3d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(!preFlopPower.getPreFlopRank().equals(PreFlopRank.TWO_PAIR_LOW));
	}

	@Test
	public void testIfConnectorsFour() {
		HandModel handModel = new HandModel("5s2c3c4d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.CONNECTOR4_HIGHT));
		
		handModel = new HandModel("As2c3c4d");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.CONNECTOR4_MEDIUM));
	}
	
	@Test
	public void testIfConnectorsThree() {
		HandModel handModel = new HandModel("Ks2c3c4d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.CONNECTOR3_HIGHT));
		
		handModel = new HandModel("As2c3cKd");
		preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopRank().equals(PreFlopRank.CONNECTOR3_MEDIUM));
	}
	
	@Test
	public void testIfTwoColor() {
		HandModel handModel = new HandModel("Ks2s3d4d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.TWO_COLORS));
	}
	
	@Test
	public void testIfOneColor() {
		HandModel handModel = new HandModel("Ks2c3c4d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.ONE_COLOR));
	}
	
	@Test
	public void testIfNoColor() {
		HandModel handModel = new HandModel("Ks2h3c4d");
		PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(handModel);
		assertTrue(preFlopPower.getPreFlopSuit().equals(PreFlopSuit.NO_COLOR));
	}
}
