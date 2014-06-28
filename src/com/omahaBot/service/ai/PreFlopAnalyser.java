package com.omahaBot.service.ai;

import lombok.Data;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;

@Data
public class PreFlopAnalyser {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	private HandPreFlopPower handPreFlopPower;

	public void analyseHand(HandModel handModel) {

		if (log.isDebugEnabled()) {
			log.debug(">> START analyseHand");
		}

		System.out.println("############################################");
		System.out.println(">> START analyseHand PREFLOP");
		System.out.println(handModel.toString());
		System.out.println("############################################");

		handPreFlopPower = new HandPreFlopPower(handModel);

		System.out.println("============================================");
		if (handPreFlopPower.isTrashHand()) {
			System.out.println("==> POUBELLE !");
		}
		else if (handPreFlopPower.isBestHand()) {
			System.out.println("==> TOP 30: " + handPreFlopPower.getBestHandLevel());
		}
		System.out.println("- PAIR=" + handPreFlopPower.getPreFlopPairLevel());
		System.out.println("- CONNECTED=" + handPreFlopPower.getPreFlopStraightLevel());
		System.out.println("- SUITED=" + handPreFlopPower.getPreFlopSuitLevel());
		System.out.println("==> POWER: " + handPreFlopPower.getPower());

		System.out.println("============================================");
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

		if (log.isDebugEnabled()) {
			log.debug(">> START decide PREFLOP");
		}

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
