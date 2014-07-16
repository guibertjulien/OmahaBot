package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FullStrategy extends AbstractStrategy {

	private static String Full_10 = "Full_10 : I have NUTS";
	private static String Full_20 = "Full_20 : FULL but QUADS_DRAW in board";
	private static String Full_30 = "Full_30 : FOLD at RIVER";

	public FullStrategy(StrategyTurnContext context) {
		super(context);
		System.out.println("--> FullStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Full_10);
			bettingDecision = checkRaise_withNuts();
		}
		else {
			// test bordDraw level 0
			switch (boardDrawsSorted.first().getHandCategory()) {
			case FOUR_OF_A_KIND:
				System.out.println(Full_20);
				bettingDecision = betOrFold_fold(BetType.SMALL);
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
			System.out.println(Full_10);
			bettingDecision = betPot();
		}
		else {
			System.out.println(Full_30);
		}

		return bettingDecision;
	}
}
