package com.omahaBot.service.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.HandModel;

public class PostFlopAnalyserServiceImpl{

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

	public BettingDecision decide(HandModel handModel, BoardModel boardModel) {
		BettingDecision bettingDecision;

		PostFlopPowerType postFlopPowerType = analyseHandPostFlop(handModel, boardModel);

		// TODO kicker / bluff / dealStep
		switch (postFlopPowerType) {
		case STRAIGHT_FLUSH:
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
		case FLUSH:
			bettingDecision = BettingDecision.BET_RAISE;
			break;
		case STRAIGHT:
		case THREE_OF_A_KIND:
		case TWO_PAIR:
			bettingDecision = BettingDecision.CHECK_CALL;
			break;
		default:
			bettingDecision = BettingDecision.FOLD;
			break;
		}

		System.out.println(bettingDecision);

		return bettingDecision;
	}
}
