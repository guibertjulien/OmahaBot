package com.omahaBot.service.ai;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.HandModel;
import com.omahaBot.model.HandPreFlopPower;

public class HandModelTest {
	
	private static String PROPERTY_FILE_NAME = "config.properties";
	
	@Test
	public void read_properties_file() throws IOException {
	    
		Properties properties = new Properties();
	    properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
	    
	    assertNotNull(properties.get("bestHands"));
	    
	    HandModel handModel;
		HandPreFlopPower handPreFlopPower;
	    
		handModel = new HandModel("AsAdJdJs");
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
	    
	    handModel = new HandModel("AsAdKdKc");	    	    
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());

		handModel = new HandModel("2s3d4h5c");	    	    
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
		
		handModel = new HandModel("AsAd2h3c");	    	    
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
		
		handModel = new HandModel("KsKd2h3c");	    	    
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
		
		handModel = new HandModel("9sTcJhJc");	    	
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
		
		handModel = new HandModel("9sKcJhJc");	    	
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
		
		handModel = new HandModel("9s7dQhJc");	    	
		handPreFlopPower = new HandPreFlopPower(handModel);
		assertTrue(!handPreFlopPower.isBestHand());
		assertTrue(!handPreFlopPower.isDoubledSuited());
		System.out.println(handPreFlopPower.toString());
	}
	
	@Test
	public void testIfHasFlushDraw() {
		
		HandModel handModel;
		
		handModel = new HandModel("As2d3h4c");
		assertTrue(!handModel.hasFlushDraw());
		
		handModel = new HandModel("As2s3d4h");
		assertTrue(handModel.hasFlushDraw());
		
		handModel = new HandModel("As2s3s4h");
		assertTrue(handModel.hasFlushDraw());
		
		handModel = new HandModel("As2s3s4s");
		assertTrue(handModel.hasFlushDraw());

		handModel = new HandModel("As2s3d4d");
		assertTrue(handModel.hasFlushDraw());
	}
	
	@Test
	public void searchStraightDrawType() {
		
		HandModel handModel;
		BoardModel boardModel;
		StraightDrawType straightDrawType;
						
		// test if Gut-Shot
		handModel = new HandModel("AsJs9s2s");
		boardModel = new BoardModel("4s5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));
		
		// test if Gut-Shot
		handModel = new HandModel("3sJs9s2s");
		boardModel = new BoardModel("As5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));

		// test if Gut-Shot
		handModel = new HandModel("AsJs6s2s");
		boardModel = new BoardModel("4s5s8s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.GUT_SHOT));
		
		// test if Open-Ended
		handModel = new HandModel("QsQh5d4c");
		boardModel = new BoardModel("Ks6s7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.OPEN_ENDED));
		
		// test if Open-Ended
		handModel = new HandModel("KsJsTs5s");
		boardModel = new BoardModel("AsQs7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.INSIDE_BROADWAY));
		
		// test if 12 outs Straight Draw
		handModel = new HandModel("QsJs9s7s");
		boardModel = new BoardModel("Ts8s2s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD12_DRAW));
		
		// test if 13 Card Wrap
		handModel = new HandModel("9s8s6s5s");
		boardModel = new BoardModel("Ks7s4s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD13_WRAP));
		
		// test if 17 Card Wrap
		handModel = new HandModel("Ts9s6s2s");
		boardModel = new BoardModel("As8s7s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD17_WRAP));
		
		// test if 20 Card Wrap
		handModel = new HandModel("Ts9s6s5s");
		boardModel = new BoardModel("8s7s2s", DealStep.FLOP);
		straightDrawType = handModel.searchStraightDrawType(boardModel);
		System.out.println(straightDrawType);
		assertTrue(straightDrawType.equals(StraightDrawType.CARD20_WRAP));
	}

}
