package com.omahaBot.service.ai;

import lombok.Data;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.exception.HandNoValidException;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;

@Data
public class PreFlopAnalyser {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	private HandPreFlopPower handPreFlopPower;

	private int nbTurnToBet = 1;

	public void analyseHand(HandModel handModel) throws HandNoValidException {

		if (log.isDebugEnabled()) {
			log.debug(">> START analyseHand");
		}

		if (handModel.getCards().size() != 4) {
			throw new HandNoValidException("no valid hand");
		}

		System.out.println("----------------------------------------------------------------");
		System.out.println(" ANALYSE PREFLOP : " + handModel);

		handPreFlopPower = new HandPreFlopPower(handModel);

		if (handPreFlopPower.isTrashHand()) {
			System.out.println(">>>> My hand is TRASH !");
		}
		else if (handPreFlopPower.isBestHand()) {
			System.out.println(">>>> My hand is TOP 30 ! " + handPreFlopPower.getBestHandLevel());
		}

		System.out.println(" - PAIR=" + handPreFlopPower.getPreFlopPairLevel());
		System.out.println(" - CONNECTED=" + handPreFlopPower.getPreFlopStraightLevel());
		System.out.println(" - SUITED=" + handPreFlopPower.getPreFlopSuitLevel());

		System.out.println(">>>> My POWER hand : " + handPreFlopPower.getPower());
		System.out.println("----------------------------------------------------------------");
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
	public BettingDecision decide(HandModel handModel, int nbTurnToBet) {

		if (log.isDebugEnabled()) {
			log.debug(">> START decide PREFLOP");
		}

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (nbTurnToBet > 1) {
			if (handPreFlopPower.getPower() < 10) {
				bettingDecision = BettingDecision.CHECK_FOLD;		
			}
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
					bettingDecision = BettingDecision.BET_RAISE_MIN;
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
							BettingDecision.BET_RAISE_75);
				}
				else if (handPreFlopPower.getPower() >= 10) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE_50,
							BettingDecision.BET_RAISE_75,
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

		System.out.println(">>>> I " + bettingDecision + " at PREFLOP (" + nbTurnToBet + ")");
		System.out.println("----------------------------------------------------------------");

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
