package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FlushDrawStrategy extends AbstractStrategy {

	private static String FlushDraw_10 = "Flush_10 : I have NUTS and FLUSH_DRAW MAX";
	private static String FlushDraw_11 = "Flush_11 : I have NUTS and FLUSH_DRAW";
	private static String FlushDraw_20 = "Flush_20 : FLUSH_DRAW MAX";
	private static String FlushDraw_21 = "Flush_21 : FLUSH_DRAW";
	private static String FlushDraw_BUG = "FlushDraw_BUG : NO RIVER possible with FLUSH_DRAW";

	public FlushDrawStrategy(StrategyTurnContext context) {
		super(context);
		System.out.println("--> FlushDrawStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		DrawModel handDrawLevel1 = handDrawsSorted.iterator().next();

		// test handDrawLevel1
		switch (handDrawLevel1.getHandCategory()) {
		case STRAIGHT:
		case STRAIGHT_ACE_LOW:
		case THREE_OF_A_KIND:
		case TWO_PAIR:
			if (isNutsForCategory(handDrawLevel1, boardDrawsSorted)) {
				if (iHaveNuts) {
					System.out.println(FlushDraw_10);
					bettingDecision = checkRaise_withNuts();
				}
				else {
					System.out.println(FlushDraw_11);
					bettingDecision = betPot();
				}
			}
			else {
				if (iHaveNuts) {
					System.out.println(FlushDraw_20);
					bettingDecision = betOrCall_fold(BetType.POT);
				}
				else {
					System.out.println(FlushDraw_21);
					bettingDecision = betOrFold_fold(BetType.SMALL);
				}
			}
			break;
		default:
			break;
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
		// no river possible for FLUSH_DRAW
		System.out.println(FlushDraw_BUG);
		return null;
	}

}
