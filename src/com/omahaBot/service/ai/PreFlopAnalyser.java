package com.omahaBot.service.ai;

import lombok.Data;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.exception.HandNoValidException;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;
import com.omahaBot.strategy.StrategyContext;

@Data
public class PreFlopAnalyser {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	private HandPreFlopPower handPreFlopPower;

	public void analyseHand(HandModel handModel) throws HandNoValidException {

		if (log.isDebugEnabled()) {
			log.debug(">> START analyseHand");
		}

		if (handModel.getCards().size() != 4) {
			throw new HandNoValidException("no valid hand");
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(" ANALYSE PREFLOP : " + handModel);
		System.out.println("----------------------------------------");
		
		handPreFlopPower = new HandPreFlopPower(handModel);

		if (handPreFlopPower.isTrashHand()) {
			System.out.println("Me: My hand is TRASH !");
		}
		else if (handPreFlopPower.isBestHand()) {
			System.out.println("Me: My hand is TOP 30 ! " + handPreFlopPower.getBestHandLevel());
		}

		System.out.println("+ " + handPreFlopPower.getPreFlopPairLevel());
		System.out.println("+ " +  handPreFlopPower.getPreFlopStraightLevel());
		System.out.println("+ " +  handPreFlopPower.getPreFlopSuitLevel());

		System.out.println("=POWER hand : " + handPreFlopPower.getPower());
		System.out.println("----------------------------------------");
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
	public BettingDecision decide(StrategyContext context) {

		if (log.isDebugEnabled()) {
			log.debug(">> START decide PREFLOP");
		}

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (handPreFlopPower.isBestHand()) {
			if (context.isFirstTurnOfBet()) {
				bettingDecision = BettingDecision.ALLIN;
			}
			else {
				bettingDecision = BettingDecision.CHECK_CALL;
			}
		}
		else if (handPreFlopPower.getPower() >= 10) {
			if (context.isFirstTurnOfBet() && context.noBetInTurn()) {
				bettingDecision = BettingDecision.BET_RAISE_50;
			}
			else {
				bettingDecision = BettingDecision.CHECK_CALL;
			}	
		}
		else if (handPreFlopPower.getPower() >= 5) {
			if (context.isFirstTurnOfBet()) {
				//if (context.noBetInTurn()) {
					bettingDecision = BettingDecision.CHECK_CALL;	
				//}				
			}		
		}
		
		System.out.println("Me: I " + bettingDecision + " at PREFLOP (" + context.getNbTurnOfBet() + ")");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
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
