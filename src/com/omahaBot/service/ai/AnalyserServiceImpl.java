package com.omahaBot.service.ai;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.model.TournamentModel;

public class AnalyserServiceImpl {

	private TournamentModel tournamentModel;
	
	private DealStep dealStep;
	
	private int nbBetTurn = 1;
	
	private double pot = 0.0;
	
	public AnalyserServiceImpl() {
	}

	public void analyseHand() {

		switch (dealStep) {
		case PRE_FLOP:
			analyseHandPreFlop();
			break;
		case FLOP:
		case TURN:
		case RIVER:
			analyseHandPostFlop();
			break;
		default:
			break;
		}
	}

	public void analysePlayers() {

	}

	public void analyseTournament() {

	}

	public BettingDecision decide() {
		BettingDecision bettingDecision = BettingDecision.FOLD_ALWAYS;

		return bettingDecision;
	}

	private void analyseHandPreFlop() {

		
		
		
	}

	private void analyseHandPostFlop() {

		
		
		
	}
}
