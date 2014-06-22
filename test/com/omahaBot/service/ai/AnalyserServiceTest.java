package com.omahaBot.service.ai;

import org.junit.Before;
import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.exception.CardPackNonValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.hand.HandModel;

public class AnalyserServiceTest {

	private PostFlopAnalyser analyserService;

	@Before
	public void before() {
		analyserService = new PostFlopAnalyser();
	}

	@Test
	public void analyseHandPostFlop() {

		HandModel handModel;
		BoardModel boardModel;
		DealStep dealStep;

//		dealStep = DealStep.FLOP;
//		handModel = new HandModel("Ac2dKh8h");
//		boardModel = new BoardModel("Ah4c4h", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//
//		dealStep = DealStep.TURN;
//		handModel = new HandModel("AcAdQcQd");
//		boardModel = new BoardModel("Ah4c4hQs", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//
//		dealStep = DealStep.TURN;
//		handModel = new HandModel("AcAd4c4d");
//		boardModel = new BoardModel("Ah4s4hQs", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//
//		dealStep = DealStep.TURN;
//		handModel = new HandModel("AcAd4c4d");
//		boardModel = new BoardModel("Ah6sKhQs", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//		
//		dealStep = DealStep.TURN;
//		handModel = new HandModel("AcAdQc4d");
//		boardModel = new BoardModel("Ah4c4hQs", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//		
//		dealStep = DealStep.TURN;
//		handModel = new HandModel("AcKdQc4d");
//		boardModel = new BoardModel("Ah4c3hQs", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//		
//		dealStep = DealStep.FLOP;
//		handModel = new HandModel("Ac2d3c4d");
//		boardModel = new BoardModel("4c5h6s", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//		
//		dealStep = DealStep.FLOP;
//		handModel = new HandModel("Ac2d3c4d");
//		boardModel = new BoardModel("4c5h6s", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
	}
	
	@Test
	public void analyseStraightPostFlop() throws CardPackNonValidException {

		HandModel handModel;
		BoardModel boardModel;
		DealStep dealStep;
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc3d4c5d");
		boardModel = new BoardModel("6h5h2s", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);

		analyserService.analyseHand(handModel, boardModel);
		analyserService.decideFlop();

		System.out.println("#######################################################");
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc9d6c5d");
		boardModel = new BoardModel("6h3h2s", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);

		analyserService.analyseHand(handModel, boardModel);
		analyserService.decideFlop();

		System.out.println("#######################################################");
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc9d6c5d");
		boardModel = new BoardModel("Ah3h2s", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);
		
		analyserService.analyseHand(handModel, boardModel);
		analyserService.decideFlop();
		
		System.out.println("#######################################################");
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc9d6c5d");
		boardModel = new BoardModel("Ah4h5s", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);
		
		analyserService.analyseHand(handModel, boardModel);
		analyserService.decideFlop();
		
		System.out.println("#######################################################");
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc9d6c5d");
		boardModel = new BoardModel("Ah3h5s", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);
		
		analyserService.analyseHand(handModel, boardModel);
		analyserService.decideFlop();

		System.out.println("#######################################################");
		
		dealStep = DealStep.FLOP;
		handModel = new HandModel("Tc9d6c5d");
		boardModel = new BoardModel("AhKhQs", dealStep);

		System.out.println(handModel);
		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
//		
//		dealStep = DealStep.FLOP;
//		handModel = new HandModel("Tc9d6c5d");
//		boardModel = new BoardModel("2h4h6s", dealStep);
//
//		System.out.println(handModel);
//		System.out.println(boardModel);
//
//		analyserService.analyseHand(handModel, boardModel);
//		analyserService.decideFlop();
//
//		System.out.println("#######################################################");
		
	}
		
		
}
