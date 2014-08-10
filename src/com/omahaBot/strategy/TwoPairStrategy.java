package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

/**
 * TODO même comportement que OnePairStrategy
 * 
 * @author Julien
 *
 */
public class TwoPairStrategy extends AbstractStrategy {

	//
	public static String TwoPair_10 = "TwoPair_10 : TOP 2 PAIR HIDE";
	public static String TwoPair_11 = "TwoPair_11 : 2 PAIR HIDE";
	//
	public static String TwoPair_20 = "TwoPair_20 : TOP 2 PAIR but PAIR on board";
	public static String TwoPair_21 = "TwoPair_21 : 2 PAIR but PAIR on board";
	//
	public static String TwoPair_30 = "TwoPair_30 : TOP 2 PAIR but FLUSH on board";
	public static String TwoPair_31 = "TwoPair_31 : 2 PAIR but FLUSH on board";
	//
	public static String TwoPair_40 = "TwoPair_40 : TOP 2 PAIR but FLUSH_DRAW on board";
	public static String TwoPair_41 = "TwoPair_41 : 2 PAIR but FLUSH_DRAW on board";
	//
	public static String TwoPair_50 = "TwoPair_50 : TOP 2 PAIR and good STRAIGHT outs";
	public static String TwoPair_51 = "TwoPair_51 : 2 PAIR and bad STRAIGHT outs";
	public static String TwoPair_52 = "TwoPair_52 : 2 PAIR but STRAIGHT outs";
	// RIVER
	public static String TwoPair_60 = "TwoPair_60 : 2 PAIR but best draws on board";
	
	public TwoPairStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> TwoPairStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		// test boardDraw first
		DrawModel boardDrawFirst = boardDrawsSorted.first();

		switch (boardDrawsSorted.first().getHandCategory()) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
			if (nutsForLevel) {
				System.out.println(TwoPair_20);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(TwoPair_21);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;
		case FLUSH:
			if (nutsForLevel) {
				System.out.println(TwoPair_30);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(TwoPair_31);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;
		case FLUSH_DRAW:
			if (nutsForLevel) {
				System.out.println(TwoPair_40);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(TwoPair_41);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;
		case STRAIGHT:
		case STRAIGHT_ACE_LOW:
			// no STRAIGHT outs
			if (straightDrawType.getOuts() == 0) {
				System.out.println(TwoPair_52);
				bettingDecision = betIfnoBetOrFold_fold(BetType.SMALL);				
			}
			// good STRAIGHT outs
			else if (straightDrawType.getOuts() >= StrategyContext.STRAIGHT_OUTS_MIN) {
				System.out.println(TwoPair_50);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			// bad STRAIGHT outs
			else {
				System.out.println(TwoPair_51);
				bettingDecision =  betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;
		case THREE_OF_A_KIND:
			if (nutsForLevel) {
				// tirage FULL MAX
				System.out.println(TwoPair_10);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				// tirage FULL
				System.out.println(TwoPair_11);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;

		default:
			// TODO exception : impossible
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
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		// test boardDraw first
		DrawModel boardDrawFirst = boardDrawsSorted.first();

		if (boardDrawsSorted.first().getHandCategory().equals(HandCategory.THREE_OF_A_KIND)) {
			if (nutsForLevel) {
				System.out.println(TwoPair_10);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(TwoPair_11);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
		}
		else  {
			System.out.println(TwoPair_60);
			bettingDecision = BettingDecision.CHECK_FOLD;			
		}

		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName() + ", boardDrawFirst.getHandCategory() : "
					+ boardDrawFirst.getHandCategory());
		}

		return bettingDecision;
	}
}
