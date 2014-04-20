package com.omahaBot.service.ai;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandType;
import com.omahaBot.enums.PreFlopPower;
import com.omahaBot.enums.PreFlopRank;
import com.omahaBot.enums.PreFlopSuit;
import com.omahaBot.model.HandModel;

public class PreFlopAnalyserServiceImpl {

	public PreFlopPower analyseHand(HandModel handModel) {

		System.out.println("----------------------");
		System.out.println("- analyseHandPreFlop -");
		System.out.println("----------------------");

		PreFlopRank preFlopRank = PreFlopRank.NO_POWER;
		PreFlopSuit preFlopSuit = PreFlopSuit.NO_POWER;

		if (handModel.isFourOfKind()) {
			//
			preFlopRank = PreFlopRank.NO_POWER;
		} else if (handModel.isThreeOfKind()) {
			//
			preFlopRank = PreFlopRank.NO_POWER;
		} else if (handModel.isTwoPair()) {
			//
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.TWO_PAIR, handModel.handRank());
		} else if (handModel.isOnePair()) {
			//
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.ONE_PAIR, handModel.handRank());
		} else {
			//
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.CONNECTOR, handModel.handRank());
		}

		preFlopSuit = PreFlopSuit.fromHand(handModel.handSuit());

		System.out.println(" HAND : " + handModel.toString());
		System.out.println(" RANK (" + handModel.handRank() + ") : " + preFlopRank);
		System.out.println(" SUIT (" + handModel.handSuit() + ") : " + preFlopSuit);

		PreFlopPower preFlopPower = PreFlopPower.fromRankAndSuit(preFlopRank, preFlopSuit);

		System.out.println(preFlopPower.toString());

		return preFlopPower;
	}

	public BettingDecision decide(HandModel handModel) {

		BettingDecision bettingDecision;

		PreFlopPower preFlopPower = analyseHand(handModel);

		switch (preFlopPower.getLevel()) {
		case MAX:
		case HIGHT:
			bettingDecision = BettingDecision.BET_RAISE;
			break;
		case MEDIUM:
			bettingDecision = BettingDecision.CHECK_CALL;
			break;
		case LOW:
			bettingDecision = BettingDecision.CHECK;
			break;
		case ZERO:
		default:
			bettingDecision = BettingDecision.FOLD;
			break;
		}

		System.out.println(bettingDecision);

		return bettingDecision;
	}
}
