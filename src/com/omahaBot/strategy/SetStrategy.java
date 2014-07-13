package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class SetStrategy extends AbstractStrategy {

	private static String CASE_1 = "CASE 1 : I have NUTS";
	private static String CASE_2 = "CASE 2 : CAUTION FLUSH !!! NUTS for LEVEL";
	private static String CASE_21 = "CASE 21 : CAUTION FLUSH !!! NO NUTS for LEVEL";
	private static String CASE_3 = "CASE 3 : CAUTION FLUSH_DRAW !!! NUTS for LEVEL";
	private static String CASE_31 = "CASE 31 : CAUTION FLUSH_DRAW !!! NO NUTS for LEVEL";
	private static String CASE_4 = "CASE 4 : STRAIGHT out are GOOD, NUTS for LEVEL";
	private static String CASE_41 = "CASE 41 : STRAIGHT out are GOOD, NO NUTS for LEVEL";

	public SetStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> SetStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(CASE_1);
			bettingDecision = betMax();
		}
		else {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				// TODO better : TIRAGE FULL POSSIBLE
				if (nutsForLevel) {
					System.out.println(CASE_2);
					bettingDecision = callAllBet();
				}
				else {
					System.out.println(CASE_21);
					
				}
			case FLUSH_DRAW:
				// TODO better : TIRAGE FULL POSSIBLE
				if (nutsForLevel) {
					System.out.println(CASE_3);
					bettingDecision = betFirstAndCall();
				}
				else {
					System.out.println(CASE_31);
					bettingDecision = betFirstAndFold();
				}
				break;
			case STRAIGHT:
			case STRAIGHT_ACE_LOW:
				if (straightDrawType.getOuts() > STRAIGHT_OUTS_MIN) {
					System.out.println(CASE_4);
					bettingDecision = betFirstAndCall();
				}
				else {
					System.out.println(CASE_41);
					bettingDecision = betFirstAndFold();
				}
				break;
			default:
				// TODO exception : impossible
				break;
			}
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return decideAtFlop(drawModel, iHaveNuts, boardDraws, nutsForLevel, straightDrawType);
	}

	@Override
	public BettingDecision decideAtRiver(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(CASE_1);
			checkRaiseWithNuts();
		}
		else {
			return decideAtFlop(drawModel, iHaveNuts, boardDraws, nutsForLevel, straightDrawType);			
		}

		return bettingDecision;
	}
}
