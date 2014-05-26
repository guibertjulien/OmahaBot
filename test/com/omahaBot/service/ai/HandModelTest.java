package com.omahaBot.service.ai;

import static org.junit.Assert.*;

import org.junit.Test;

import com.omahaBot.model.HandModel;

public class HandModelTest {

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

}
