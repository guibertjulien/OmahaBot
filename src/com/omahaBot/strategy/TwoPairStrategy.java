package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

/**
 * TODO même comportement que OnePairStrategy
 * 
 * @author Julien
 *
 */
public class TwoPairStrategy extends AbstractStrategy {

	private static String CASE_1 = "CASE 1 : NO first turn of bet";
	private static String CASE_2 = "CASE 2 : CAUTION PAIR or FLUSH in board !!!";
	private static String CASE_3 = "CASE 3 : STRAIGHT out are GOOD";
	private static String CASE_4 = "CASE 4 : CHECK_CALL if first turn of bet";
	private static String CASE_5 = "CASE 5 : CHECK_FOLD";

	public TwoPairStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> TwoPairStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (nbTurnOfBet > 1) {
			System.out.println(CASE_1);
		}
		// i have top PAIR ?
		else if (nutsForLevel) {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				System.out.println(CASE_2);
				break;
			case FLUSH_DRAW:
			case STRAIGHT:
			case STRAIGHT_ACE_LOW:
				if (straightDrawType.getOuts() > STRAIGHT_OUTS_MIN) {
					System.out.println(CASE_3);
					bettingDecision = betBig();
				}
				else {
					System.out.println(CASE_4);
					bettingDecision = betSmall();
				}
				break;
			default:
				break;
			}
		}
		else {
			System.out.println(CASE_5);
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
		return BettingDecision.CHECK_FOLD;
	}
}
