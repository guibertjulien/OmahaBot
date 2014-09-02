package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class StraightStrategy extends AbstractStrategy {

	private static String Straight_10 = "Straight_10 : I have NUTS";
	private static String Straight_20 = "Straight_20 : STRAIGHT but BEST draws on board";
	private static String Straight_30 = "Straight_30 : TOP STRAIGHT but FLUSH_DRAW in board";
	private static String Straight_31 = "Straight_31 : STRAIGHT but FLUSH_DRAW in board";

	public StraightStrategy(StrategyContext actionContext) {
		super(actionContext);
		System.out.println("----------------------------------------");
		System.out.println("StraightStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			System.out.println(Straight_10);
			bettingDecision = betPot();
		}
		else {
			// test bordDraw level 0
			switch (boardDrawsSorted.first().getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case FLUSH:
				System.out.println(Straight_20);
				bettingDecision = BettingDecision.CHECK_FOLD;
				break;
			case FLUSH_DRAW:
				if (nutsForLevel) {
					System.out.println(Straight_30);
					bettingDecision = betPot();
				}
				else {
					System.out.println(Straight_31);
					bettingDecision = betIfnoBetOrCall_fold(BetType.BIG);
				}
				break;
			default:
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
			System.out.println(Straight_10);
			//bettingDecision = checkRaise_withNuts();
			bettingDecision = betPot();
		}
		else {
			return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
		}

		return bettingDecision;
	}
}
