package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class SetStrategy extends AbstractStrategy {

	private static String Set_10 = "Set_10 : I have NUTS";
	private static String Set_20 = "Set_20 : TOP SET but FLUSH in board";
	private static String Set_21 = "Set_21 : SET but FLUSH in board";
	private static String Set_30 = "Set_30 : TOP SET but FLUSH_DRAW in board";
	private static String Set_31 = "Set_31 : SET but FLUSH_DRAW in board";
	private static String Set_40 = "Set_40 : TOP SET and good STRAIGHT outs";
	private static String Set_41 = "Set_41 : SET and good STRAIGHT outs";

	public SetStrategy(StrategyTurnContext actionContext) {
		super(actionContext);
		System.out.println("--> SetStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Set_10);
			bettingDecision = betPot();
		}
		else {
			// Tirage FULL possible
			
			// test bordDraw level 0
			switch (boardDrawsSorted.first().getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				if (nutsForLevel) {
					System.out.println(Set_20);
					bettingDecision = betOrCall_fold(BetType.SMALL);
				}
				else {
					System.out.println(Set_21);
					bettingDecision = betOrFold_fold(BetType.SMALL);
				}
				break;
			case FLUSH_DRAW:
				if (nutsForLevel) {
					System.out.println(Set_30);
					bettingDecision = betOrCall_fold(BetType.SMALL);
				}
				else {
					System.out.println(Set_31);
					bettingDecision = betOrFold_fold(BetType.SMALL);
				}
				break;
			case STRAIGHT:
			case STRAIGHT_ACE_LOW:
				if (straightDrawType.getOuts() > StrategyTurnContext.STRAIGHT_OUTS_MIN) {
					System.out.println(Set_40);
					bettingDecision = betOrCall_fold(BetType.SMALL);
				}
				else {
					System.out.println(Set_41);
					bettingDecision = betOrFold_fold(BetType.SMALL);
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
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Set_10);
			bettingDecision = checkRaise_withNuts();
		}
		else {
			return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
		}

		return bettingDecision;
	}
}
