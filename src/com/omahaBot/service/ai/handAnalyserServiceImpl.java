package com.omahaBot.service.ai;

import com.omahaBot.enums.HandType;
import com.omahaBot.enums.PowerHand;
import com.omahaBot.enums.PowerHandRank;
import com.omahaBot.enums.PowerHandSuit;
import com.omahaBot.model.HandModel;

public class handAnalyserServiceImpl {

	public PowerHand analyseHandPreFlop(HandModel handModel) {

		PowerHandRank powerHandRank = PowerHandRank.NO_POWER;
		PowerHandSuit powerHandSuit = PowerHandSuit.NO_POWER;

		if (handModel.isTwoPair()) {
			powerHandRank = PowerHandRank.fromTypeAndHand(HandType.TWO_PAIR, handModel.handRank());
		} else if (handModel.isOnePair()) {
			powerHandRank = PowerHandRank.fromTypeAndHand(HandType.ONE_PAIR, handModel.handRank());	
		} else {
			
		}

		if (handModel.isTwoSuit()) {

		}

		PowerHand powerHand = PowerHand.fromRankAndSuit(powerHandRank, powerHandSuit);

		return powerHand;
	}
}
     