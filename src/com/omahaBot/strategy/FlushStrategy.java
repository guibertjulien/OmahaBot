package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FlushStrategy extends AbstractStrategy {

	private static String Flush_10 = "Flush_10 : I have NUTS";
	private static String Flush_11 = "Flush_11 : No NUTS for FLUSH";
	private static String Flush_20 = "Flush_20 : FLUSH MAX but FULL_DRAW (PAIR in Board)";
	private static String Flush_21 = "Flush_21 : FLUSH but FULL_DRAW (PAIR in Board)";
	private static String Flush_30 = "Flush_30 : FOLD at RIVER";
	
	public FlushStrategy(StrategyTurnContext context) {
		super(context);
		System.out.println("--> FlushStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Flush_10);
			bettingDecision = betPot();
		}
		else {
			System.out.println(Flush_11);
			bettingDecision = betOrFold_fold(BetType.SMALL);
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		// test bordDraw level 0
		switch (boardDrawsSorted.first().getHandCategory()) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
			if (nutsForLevel) {
				if (context.isFirstTurnOfBet()) {
					System.out.println(Flush_20);
					bettingDecision = betBig();
				}
				else {
					System.out.println(Flush_21);
					bettingDecision = betOrFold_fold(BetType.SMALL);
				}
			}
		default:
			// TODO exception : impossible
			break;
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Flush_10);
			bettingDecision = betPot();
		}
		else {
			System.out.println(Flush_30);
		}

		return bettingDecision;
	}
}
