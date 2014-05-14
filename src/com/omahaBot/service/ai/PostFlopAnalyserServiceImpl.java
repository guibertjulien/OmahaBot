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
import com.omahaBot.model.draw.DrawModel;

public class PostFlopAnalyserServiceImpl {

	private ArrayList<DrawModel> handDraws = new ArrayList<DrawModel>();

	private ArrayList<DrawModel> boardDraws = new ArrayList<DrawModel>();

	public ArrayList<CombinaisonModel> initCombinaisons(HandModel handModel, BoardModel boardModel) {

		ArrayList<CombinaisonModel> combinaisons = new ArrayList<CombinaisonModel>();

		for (List<CardModel> permutationHand : handModel.permutations()) {
			for (List<CardModel> permutationBoard : boardModel.permutations()) {
				CombinaisonModel combinaison = new CombinaisonModel(permutationHand, permutationBoard);
				combinaisons.add(combinaison);
			}
		}

		// Collections.sort(combinaisons);

		return combinaisons;
	}

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

		// DealStep dealStep;
		//
		// // analyse des tirages
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

	public void compareHand(HandModel handModel, BoardModel boardModel) {

		handDraws.clear();
		boardDraws.clear();

		// initialisation des combinaisons
		ArrayList<CombinaisonModel> combinaisons = new ArrayList<CombinaisonModel>();

		for (List<CardModel> permutationHand : handModel.permutations()) {
			for (List<CardModel> permutationBoard : boardModel.permutations()) {
				CombinaisonModel combinaison = new CombinaisonModel(permutationHand, permutationBoard);
				combinaisons.add(combinaison);
			}
		}

		// initialisation de handDraws
		for (CombinaisonModel combinaisonModel : combinaisons) {
			handDraws.addAll(combinaisonModel.initDraw());
		}

		// initialisation de boardDraws
		boardDraws.addAll(boardModel.initDraw());

		System.out.println(boardModel.toString());
		System.out.println(handModel.toString());

		System.out.println("****************************************");
		System.out.println("handDraws : ");
		System.out.println("----------------------------------------");

		// affchage des handDraws
		for (DrawModel drawModel : handDraws) {
			System.out.println(drawModel);
		}

		System.out.println("****************************************");
		System.out.println("boardDraws : ");
		System.out.println("----------------------------------------");

		// affchage des boardDraws
		for (DrawModel drawModel : boardDraws) {
			System.out.println(drawModel);
		}

		System.out.println("****************************************");
		System.out.println("compare : ");
		System.out.println("----------------------------------------");

		// comparaison de handDraws & boardDraws
		for (DrawModel drawModelHand : handDraws) {
			for (DrawModel drawModelBoard : boardDraws) {

				if (drawModelHand != null && drawModelBoard != null) {
					if (drawModelHand.getClass().equals(drawModelBoard.getClass())) {
						System.out.println(drawModelHand);

						if (drawModelHand.isNuts(drawModelBoard)) {
							System.out.println("NUTS !");
						}
						else {
							System.out.println("CAUTION NO NUTS !");
						}
					}
				}
			}
		}
	}

	// public <T> void foo() {
	//
	// ArrayList<DrawModel<T>> listDraw1 = new ArrayList<>();
	//
	// ArrayList<DrawModel<T>> listDraw2 = new ArrayList<>();
	//
	// // trier les 2 listes
	//
	// for (DrawModel<T> drawModel : listDraw2) {
	//
	// // search type in boardDraw
	// DrawModel<T> drawModel2 = null;
	//
	// drawModel.getHandCategory().equals(drawModel2.getHandCategory());
	//
	//
	//
	//
	//
	// }
	//
	//
	//
	// }
}
