package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public class OnePairStrategy extends AbstractStrategy {

	// private static String OnePair_10 = "OnePair_10 : NO FIRST TURN OF BET";
	private static String OnePair_10 = "OnePair_10 : PAIR but FULL_DRAW or FLUSH/DRAW in board";
	private static String OnePair_20 = "OnePair_20 : PAIR and good STRAIGHT outs";
	private static String OnePair_30 = "OnePair_30 : TOP PAIR ACE and bad STRAIGHT outs";
	private static String OnePair_31 = "OnePair_31 : PAIR and bad STRAIGHT outs";
	private static String OnePair_99 = "OnePair_99 : PAIR, CHECK_FOLD at RIVER";

	public OnePairStrategy(StrategyContext context) {
		super(context);
		System.out.println("----------------------------------------");
		System.out.println("OnePairStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		// test boardDraw first
		DrawModel boardDrawFirst = boardDrawsSorted.first();
		
		switch (boardDrawFirst.getHandCategory()) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
		case FLUSH:
			System.out.println(OnePair_10);
			bettingDecision = BettingDecision.CHECK_FOLD;
			break;
		case THREE_OF_A_KIND:
		case FLUSH_DRAW:
		case STRAIGHT:
		case STRAIGHT_ACE_LOW:
			if (straightDrawType.isGoodStraightOut()) {
				System.out.println(OnePair_20);
				bettingDecision = betIfnoBetOrCall_fold(BetType.SMALL);
			}
			else {
				if (nutsForLevel) {
					System.out.println(OnePair_30);
					bettingDecision = betIfnoBetOrCall_fold(BetType.SMALL);
				}
				else {
					System.out.println(OnePair_31);
					//bettingDecision = betOrFold_fold(BetType.SMALL);
					bettingDecision = BettingDecision.CHECK_FOLD;
				}
			}
			break;
		default:
			break;
		}

		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName() + ", boardDrawFirst.getHandCategory() : "
					+ boardDrawFirst.getHandCategory());
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = null;

		System.out.println(OnePair_99);
		bettingDecision = BettingDecision.CHECK_FOLD;

		return bettingDecision;
	}
}
