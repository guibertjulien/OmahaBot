package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class OnePairStrategy extends AbstractStrategy {

	// private static String OnePair_10 = "OnePair_10 : NO FIRST TURN OF BET";
	private static String OnePair_20 = "OnePair_20 : TOP PAIR but PAIR or FLUSH/DRAW in board";
	private static String OnePair_30 = "OnePair_30 : TOP PAIR and good STRAIGHT outs";
	private static String OnePair_31 = "OnePair_30 : TOP PAIR and bad STRAIGHT outs";
	private static String OnePair_50 = "OnePair_50 : NO TOP PAIR";
	private static String OnePair_60 = "OnePair_60 : FOLD at RIVER";

	public OnePairStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> OnePairStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		// TOP PAIR ?
		if (nutsForLevel) {
			// test bordDraw level 0
			switch (boardDrawsSorted.first().getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				System.out.println(OnePair_20);
				break;
			case FLUSH_DRAW:
			case STRAIGHT:
			case STRAIGHT_ACE_LOW:
				if (straightDrawType.getOuts() > StrategyContext.STRAIGHT_OUTS_MIN) {
					System.out.println(OnePair_30);
					bettingDecision = betOrCall_fold(BetType.SMALL);
				}
				else {
					System.out.println(OnePair_31);
					bettingDecision = betOrFold_fold(BetType.SMALL);
				}
				break;
			default:
				break;
			}
		}
		else {
			System.out.println(OnePair_50);
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

		System.out.println(OnePair_60);
		return BettingDecision.CHECK_FOLD;
	}
}
