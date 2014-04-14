package com.omahaBot.service.ai;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandType;
import com.omahaBot.enums.PowerHand;
import com.omahaBot.enums.PowerHandRank;
import com.omahaBot.enums.PowerHandSuit;
import com.omahaBot.model.HandModel;
import com.omahaBot.model.PlayerModel;

public class handAnalyserServiceImpl {

	public PowerHand analyseHandPreFlop(HandModel handModel) {

		PowerHandRank powerHandRank = PowerHandRank.NO_POWER;
		PowerHandSuit powerHandSuit = PowerHandSuit.NO_POWER;

		if (handModel.isFourOfKind()) {
			//
			powerHandRank = PowerHandRank.NO_POWER;
		} else if (handModel.isThreeOfKind()) {
			//
			powerHandRank = PowerHandRank.NO_POWER;
		} else if (handModel.isTwoPair()) {
			//
			powerHandRank = PowerHandRank.fromTypeAndHand(HandType.TWO_PAIR, handModel.handRank());
		} else if (handModel.isOnePair()) {
			//
			powerHandRank = PowerHandRank.fromTypeAndHand(HandType.ONE_PAIR, handModel.handRank());
		} else {
			//
			powerHandRank = PowerHandRank.NO_POWER;
		}
 		
		powerHandSuit = PowerHandSuit.fromHand(handModel.handSuit());

		System.out.println("HAND : " + handModel.toString());
		System.out.println("RANK (" + handModel.handRank() + ") : " + powerHandRank);
		System.out.println("SUIT (" + handModel.handSuit() + ") : " + powerHandSuit);
		
		PowerHand powerHand = PowerHand.fromRankAndSuit(powerHandRank, powerHandSuit);

		System.out.println("POWERHAND : " + powerHand + "|" + powerHand.getPowerHandRank() + "|"
				+ powerHand.getPowerHandSuit());
		System.out.println("LEVEL : " + powerHand.getLevel());
		
		System.out.println("------------------------------------------------------");
		
		return powerHand;
	}

	public BettingDecision decidePreFlop(HandModel handModel) {

		BettingDecision bettingDecision = BettingDecision.FOLD;

		PowerHand powerHand = analyseHandPreFlop(handModel);

		switch (powerHand) {
		case TWO_PAIRS_SUITED_MAX:
			bettingDecision = BettingDecision.RAISE;
			break;
		case TWO_PAIRS_SUITED:
		case TWO_PAIRS:
			bettingDecision = BettingDecision.CALL;
			break;
		default:
			bettingDecision = BettingDecision.FOLD;
			break;
		}

		return bettingDecision;
	}

	public void decideAfterFlop(PlayerModel playerModel) {
		// TODO
	}
}
