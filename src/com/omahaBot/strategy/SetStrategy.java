package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public class SetStrategy extends AbstractStrategy {

	public static String Set_10 = "Set_10 : I have NUTS !!!";
	public static String Set_11 = "Set_11 : SET no max";
	//
	public static String Set_20 = "Set_20 : SET MAX but FLUSH on board";
	public static String Set_21 = "Set_21 : SET but FLUSH on board";
	//
	public static String Set_30 = "Set_30 : SET MAX but FLUSH_DRAW on board";
	public static String Set_31 = "Set_31 : SET but FLUSH_DRAW on board";
	//
	public static String Set_40 = "Set_40 : SET MAX and good STRAIGHT outs";
	public static String Set_41 = "Set_41 : SET and good STRAIGHT outs";
	public static String Set_42 = "Set_42 : SET MAX and bad STRAIGHT outs";
	public static String Set_43 = "Set_43 : SET and bad STRAIGHT outs";
	public static String Set_44 = "Set_44 : SET MAX but STRAIGHT outs";
	public static String Set_45 = "Set_45 : SET but STRAIGHT outs";
	//
	public static String Set_50 = "Set_50 : COMMUNITY SET";
	public static String Set_51 = "Set_51 : SET but PAIR at board";

	public SetStrategy(StrategyContext actionContext) {
		super(actionContext);
		System.out.println("--> SetStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		// test boardDraw first
		DrawModel boardDrawFirst = boardDrawsSorted.first();

		switch (boardDrawFirst.getHandCategory()) {
		case FOUR_OF_A_KIND:
			// COMMUNITY SET, 3 same cards at board
			if (boardDrawFirst.getBoardCategory().equals(BoardCategory.THREE_OF_A_KIND)) {
				System.out.println(Set_50);
				bettingDecision = BettingDecision.CHECK_FOLD;
			}
			break;
		case FULL_HOUSE:
			// THREE_OF_A_KIND, PAIR at board
			System.out.println(Set_51);
			bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			break;
		case FLUSH:
			if (nutsForLevel) {
				System.out.println(Set_20);
				bettingDecision = callAllBet();
			}
			else {
				System.out.println(Set_21);
				bettingDecision = callAllBet();
			}
			break;
		case FLUSH_DRAW:
			if (nutsForLevel) {
				System.out.println(Set_30);
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(Set_31);
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
			break;
		case STRAIGHT:
		case STRAIGHT_ACE_LOW:
			// no STRAIGHT outs
			if (straightDrawType.getOuts() == 0) {
				if (nutsForLevel) {
					System.out.println(Set_44);
					bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
				}
				else {
					System.out.println(Set_45);
					bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
				}
			}
			// good STRAIGHT outs
			else if (straightDrawType.isGoodStraightOut()) {
				if (nutsForLevel) {
					System.out.println(Set_40);
					bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
				}
				else {
					System.out.println(Set_41);
					bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
				}
			}
			// bad STRAIGHT outs
			else {
				if (nutsForLevel) {
					System.out.println(Set_42);
					bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
				}
				else {
					System.out.println(Set_43);
					bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
				}
			}
			break;
		case THREE_OF_A_KIND:
			// THREE_OF_A_KIND HOLE, no PAIR at board
			if (iHaveNuts) {
				System.out.println(Set_10);
				bettingDecision = checkRaise_withNuts();
			}
			else {
				System.out.println(Set_11);
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

		if (iHaveNuts) {
			System.out.println(Set_10);
			bettingDecision = betPot();
		}
		else {
			return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
		}

		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}

		return bettingDecision;
	}
}
