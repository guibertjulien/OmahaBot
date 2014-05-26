package com.omahaBot.service.bot;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PokerPosition;

public class Context {

	DealStep dealStep;
	
	int positionButton;
	
	int myPosition;
	
	PokerPosition pokerPosition;
	
	int nbPlayerInTable;

	int nbPlayerInAction;
	
	int nbPlayerInTournament;
	
	public boolean isHU() {
		return nbPlayerInAction == 2;
	}

	public PokerPosition initPokerPosition() {
		
		int valueDiff = positionButton - myPosition;

		PokerPosition pokerPosition = PokerPosition.fromValueDiff(valueDiff);
		
		return pokerPosition;
	}
}


