package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public class FlushStrategy extends AbstractStrategy {

	public static String Flush_10 = "Flush_10 : I have NUTS !!!";
	public static String Flush_11 = "Flush_11 : FLUSH no max";
	public static String Flush_20 = "Flush_20 : FLUSH and FULL_DRAW MAX";
	public static String Flush_21 = "Flush_21 : FLUSH and FULL_DRAW";
	public static String Flush_30 = "Flush_30 : FLUSH MAX but PAIR on Board";
	public static String Flush_31 = "Flush_31 : FLUSH but PAIR on Board";
	public static String Flush_40 = "Flush_40 : FLUSH and STRAIGHT MAX";
	public static String Flush_41 = "Flush_41 : FLUSH and STRAIGHT";

	public FlushStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> FlushStrategy");
	}

	/**
	 * Seul le tirage FLUSH est possible au flop
	 * 
	 * @throws StrategyUnknownException
	 */
	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		if (iHaveNuts) {
			System.out.println(Flush_10);
			bettingDecision = betPot();
		}
		else {
			// test FULL_DRAW on Hand
			DrawModel handDrawSecond = drawSecond(handDrawsSorted);

			if (handDrawSecond.getHandCategory().equals(HandCategory.THREE_OF_A_KIND)
					|| handDrawSecond.getHandCategory().equals(HandCategory.TWO_PAIR)) {
				if (isNutsForCategory(handDrawSecond, boardDrawsSorted)) {
					System.out.println(Flush_20);
					bettingDecision = betPot();
				}
				else {
					System.out.println(Flush_21);
					bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
				}
			}
			else {
				System.out.println(Flush_11);
				bettingDecision = betIfnoBetOrFold_fold(BetType.SMALL);
			}
		}
		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		// PAIR in board
		if (boardHasFullDraw(boardDrawsSorted)) {
			if (nutsForLevel) {
				System.out.println(Flush_30);
				// TODO à revoir
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(Flush_31);
				// TODO à revoir
				bettingDecision = BettingDecision.CHECK_FOLD;
			}
		}
		// NO PAIR on Board
		else {
			bettingDecision = decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
		}

		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		return decideAtTurn(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
	}
}
