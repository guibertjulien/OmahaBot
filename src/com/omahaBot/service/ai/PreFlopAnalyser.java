package com.omahaBot.service.ai;

import lombok.Data;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;

@Data
public class PreFlopAnalyser {

	private HandPreFlopPower handPreFlopPower;

	public void analyseHand(HandModel handModel) {
		handPreFlopPower = new HandPreFlopPower(handModel);
		System.out.println(handPreFlopPower.toString());
	}

	/**
	 * TODO :
	 * 
	 * isHU nbPayer short stack finalTable
	 * 
	 * 
	 * @param handModel
	 * @param firstTurnBet
	 * @return
	 */
	public BettingDecision decide(HandModel handModel, boolean firstTurnBet) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (!firstTurnBet) {
			bettingDecision = BettingDecision.CHECK_CALL;
		}

		if (canBluff()) {
			bettingDecision = BettingDecision.ALLIN;
		}

		if (handPreFlopPower.isTrashHand()) {
			// TODO vérifier si AK suited
			bettingDecision = BettingDecision.CHECK_FOLD;
		} else {

			if (handPreFlopPower.isBestHand()) {
				switch (handPreFlopPower.getSuitedType()) {
				case DOUBLE_SUITED:
					bettingDecision = BettingDecision.ALLIN;
					break;
				case ONE_SUIT:
					bettingDecision = BettingDecision.BET_RAISE;
					break;
				case UNSUITED:
					bettingDecision = BettingDecision.CHECK_CALL;
					break;
				default:
					break;
				}
			}
			// TODO à paramètrer
			else {
				if (handPreFlopPower.getPower() >= 20) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, 
							BettingDecision.BET_RAISE);
				}
				else if (handPreFlopPower.getPower() >= 10) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE,
							BettingDecision.CHECK_CALL);
				}
				else if (handPreFlopPower.getPower() >= 5) {
					bettingDecision = BettingDecision.CHECK_CALL;
				}
				else if (handPreFlopPower.getPower() < 5) {
					bettingDecision = BettingDecision.CHECK_FOLD;
				}
			}
		}

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
