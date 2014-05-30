package com.omahaBot.service.ai;

import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.HandModel;

public class AnalyserServiceTest {

	private AnalyserServiceImpl analyserService;
	
	@Before
	public void before() {
		analyserService = new AnalyserServiceImpl();
	}
	
	@Test
	public void analyseHandPostFlop() {

		HandModel handModel;
		BoardModel boardModel;
		DealStep dealStep = DealStep.FLOP;
		
		handModel = new HandModel("Ac2dKh8h");
		boardModel = new BoardModel("Ah4c4h", dealStep);
		
		analyserService.analyseHand(handModel, boardModel, dealStep);;
				
		System.out.println(handModel);
		System.out.println(boardModel);

		analyserService.display();
		
//		handModel = new HandModel("AcAdQcQd");
//		boardModel = new BoardModel("Ah4c4hQs", DealStep.TURN);
//		
//		postFlopAnalyserServiceImpl.compareHand(handModel, boardModel);
//		
//		handModel = new HandModel("AcAd4c4d");
//		boardModel = new BoardModel("Ah4s4hQs", DealStep.TURN);
//		
//		postFlopAnalyserServiceImpl.compareHand(handModel, boardModel);
//		
//		handModel = new HandModel("AcAd4c4d");
//		boardModel = new BoardModel("Ah6sKhQs", DealStep.TURN);
//		
//		postFlopAnalyserServiceImpl.compareHand(handModel, boardModel);
	}
}
