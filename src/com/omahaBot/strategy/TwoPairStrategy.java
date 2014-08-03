package com.omahaBot.strategy;

import java.util.SortedSet;

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

	// private static String TwoPair_10 = "TwoPair_10 : NO FIRST TURN OF BET";
	private static String TwoPair_20 = "TwoPair_20 : TOP 2 PAIR but PAIR or FLUSH/DRAW in board";
	private static String TwoPair_30 = "TwoPair_30 : TOP 2 PAIR and good STRAIGHT outs";
	private static String TwoPair_31 = "TwoPair_30 : TOP 2 PAIR and bad STRAIGHT outs";
	private static String TwoPair_50 = "TwoPair_50 : NO TOP 2 PAIR";
	private static String TwoPair_60 = "TwoPair_60 : FOLD at RIVER";
	private static String TwoPair_70 = "TwoPair_70 : STAIGHT_DRAW ???";

	public TwoPairStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> TwoPairStrategy");
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
				System.out.println(TwoPair_20);
				break;
			case FLUSH_DRAW:
			case STRAIGHT:
			case STRAIGHT_ACE_LOW:
				if (straightDrawType.getOuts() > StrategyContext.STRAIGHT_OUTS_MIN) {
					System.out.println(TwoPair_30);
					bettingDecision = betOrCall_fold(BetType.BIG);
				}
				else {
					System.out.println(TwoPair_31);
					bettingDecision = betOrFold_fold(BetType.BIG);
				}
				break;
			default:
				System.out.println(TwoPair_70);
				// TODO tester straightDrawType
				break;
			}
		}
		else {
			System.out.println(TwoPair_50);
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

		System.out.println(TwoPair_60);
		return BettingDecision.CHECK_FOLD;
	}
}
