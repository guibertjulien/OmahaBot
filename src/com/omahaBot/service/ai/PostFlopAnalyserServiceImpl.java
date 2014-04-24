package com.omahaBot.service.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.BoardDrawPower;
import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.HandModel;

public class PostFlopAnalyserServiceImpl {

	public PostFlopPowerType analyseHandPostFlop(HandModel handModel, BoardModel boardModel) {

		System.out.println("-----------------------");
		System.out.println("- analyseHandPostFlop -");
		System.out.println("-----------------------");

		ArrayList<CombinaisonModel> combinaisons = new ArrayList<CombinaisonModel>();

		for (List<CardModel> permutationHand : handModel.permutations()) {
			for (List<CardModel> permutationBoard : boardModel.permutations()) {
				CombinaisonModel combinaison = new CombinaisonModel(permutationHand, permutationBoard);
				combinaisons.add(combinaison);
				System.out.println(combinaison);
			}
		}

		Collections.sort(combinaisons);

		CombinaisonModel combinaisonBest = combinaisons.get(0);
		PostFlopPowerType postFlopPowerType = combinaisonBest.getHandPowerType();

		System.out.println(postFlopPowerType.toString());

		return postFlopPowerType;
	}

	private BoardDrawPower analyseBoard(BoardModel boardModel) {

		System.out.println("------------------------");
		System.out.println("- analyseBoardPostFlop -");
		System.out.println("------------------------");
		
//		DealStep dealStep;
//
//		// analyse des tirages
		BoardDrawPower boardDrawPower = null;

		return boardDrawPower;
	}

	/**
	 * TODO :
	 * 
	 * SLOW_PLAY
	 * 
	 * @param handModel
	 * @param boardModel
	 * @return
	 */
	public BettingDecision decide(HandModel handModel, BoardModel boardModel, boolean firstTurnBet) {
		BettingDecision bettingDecision;

		if (canBluff()) {
			bettingDecision = BettingDecision.BET_RAISE;
		}
		else {
			PostFlopPowerType postFlopPowerTypeHand = analyseHandPostFlop(handModel, boardModel);
			BoardDrawPower boardDrawPower = analyseBoard(boardModel);

			// tests si tirage > hand : wheel

			// TODO kicker / bluff / dealStep
			switch (postFlopPowerTypeHand) {
			case STRAIGHT_FLUSH:
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE);
				}
				else {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE,
							BettingDecision.CHECK_CALL);
				}
				break;
			case STRAIGHT:
			case THREE_OF_A_KIND:
			case TWO_PAIR:
				if (firstTurnBet) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE,
							BettingDecision.CHECK_CALL);
				}
				else {
					bettingDecision = BettingDecision.CHECK_CALL;
				}
				break;
			default:
				bettingDecision = BettingDecision.CHECK_FOLD;
				break;
			}
		}

		System.out.println(bettingDecision);

		return bettingDecision;
	}

	/**
	 * TODO à déterminer
	 * 
	 * @return
	 */
	public boolean canBluff() {

		return false;

	}
}
