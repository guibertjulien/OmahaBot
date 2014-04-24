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
		if (handModel.isNbSameCardRank(4) || handModel.isNbSameCardRank(3)) {// mains poubelles
			preFlopRank = PreFlopRank.NO_POWER;
		} else if (handModel.isTwoPair()) {
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.TWO_PAIR, handModel.toRankString());
		} else if (handModel.isOnePair()) {
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.ONE_PAIR, handModel.toRankString());
		} else {
			preFlopRank = PreFlopRank.fromTypeAndHand(HandType.CONNECTOR, handModel.toRankString());
		}

		preFlopSuit = PreFlopSuit.fromHand(handModel.toSuitString());

		System.out.println(" HAND : " + handModel.toString());
		System.out.println(" RANK (" + handModel.toRankString() + ") : " + preFlopRank);
		System.out.println(" SUIT (" + handModel.toSuitString() + ") : " + preFlopSuit);

		PreFlopPower preFlopPower = PreFlopPower.fromRankAndSuit(preFlopRank, preFlopSuit);

		System.out.println(preFlopPower.toString());

		return preFlopPower;
	}

	/**
	 * TODO :
	 * 
	 * isHU
	 * nbPayer
	 * short stack
	 * finalTable
	 * 
	 * 
	 * @param handModel
	 * @param firstTurnBet
	 * @return
	 */
	public BettingDecision decide(HandModel handModel, boolean firstTurnBet) {

		BettingDecision bettingDecision;

		if (canBluff()) {
			bettingDecision = BettingDecision.BET_RAISE;
		}
		else {
			PreFlopPower preFlopPower = analyseHand(handModel);

			switch (preFlopPower.getLevel()) {
			case MAX:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE);
				}
				else {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
				}
				break;
			case HIGHT:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.BET_RAISE;	
				}
				else {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
				}
				break;
			case MEDIUM:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.CHECK_CALL;	
				}
				else {
					bettingDecision = BettingDecision.CHECK_CALL;
				}
				break;
			case LOW:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.CHECK_FOLD;	
				}
				else {
					bettingDecision = BettingDecision.CHECK_FOLD;
				}
				break;
			case ZERO:
			default:
				bettingDecision = BettingDecision.CHECK_FOLD;
				break;
			}
		}

		System.out.println(bettingDecision);

		return bettingDecision;
	}

	/**
	 * TODO à déterminer - position - nb joueurs
	 * 
	 * @return
	 */
	public boolean canBluff() {

		return false;
	}

}
