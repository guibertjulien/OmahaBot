package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class StraightDrawStrategy extends AbstractStrategy {

	public static String StraightDraw_10 = "StraightDraw_10 : STRAIGHT_DRAW but BEST draws on board";
	public static String StraightDraw_20 = "StraightDraw_20 : STRAIGHT_DRAW good and no BEST draws on board";
	public static String StraightDraw_21 = "StraightDraw_21 : STRAIGHT_DRAW bad and no BEST draws on board";

	public StraightDrawStrategy(StrategyContext actionContext) {
		super(actionContext);
		System.out.println("----------------------------------------");
		System.out.println("StraightDrawStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return decideAtFlopOrTurn(boardDrawsSorted, straightDrawType, DealStep.FLOP);
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return decideAtFlopOrTurn(boardDrawsSorted, straightDrawType, DealStep.TURN);
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return BettingDecision.CHECK_FOLD;
	}

	private BettingDecision decideAtFlopOrTurn(SortedSet<DrawModel> boardDrawsSorted,
			StraightDrawType straightDrawType, DealStep dealStep) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		// test bordDraw level 0
		switch (boardDrawsSorted.first().getHandCategory()) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
		case FLUSH:
			System.out.println(StraightDraw_10);
			bettingDecision = BettingDecision.CHECK_FOLD;
			break;
		default:
			if (straightDrawType.isGoodStraightOut()) {
				System.out.println(StraightDraw_20);
				if (dealStep.equals(DealStep.FLOP)) {
					bettingDecision = betPot();
				}
				else {
					bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
				}
			}
			else {
				System.out.println(StraightDraw_21);
				bettingDecision = BettingDecision.CHECK_FOLD;
			}
			break;
		}

		return bettingDecision;
	}
}
