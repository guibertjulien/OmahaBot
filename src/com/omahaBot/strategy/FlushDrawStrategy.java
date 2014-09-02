package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public class FlushDrawStrategy extends AbstractStrategy {

	// ONE FLUSH_DRAW
	public static String FlushDraw_10 = "FlushDraw_10 : I have NUTS and FLUSH_DRAW MAX !!!";
	public static String FlushDraw_11 = "FlushDraw_11 : I have NUTS and FLUSH_DRAW !!!";
	public static String FlushDraw_20 = "FlushDraw_20 : FLUSH_DRAW MAX (no best draws on board)";
	public static String FlushDraw_21 = "FlushDraw_21 : FLUSH_DRAW MAX but FULL draw on board";
	public static String FlushDraw_22 = "FlushDraw_22 : FLUSH_DRAW MAX but STRAIGHT draw on board";
	public static String FlushDraw_30 = "FlushDraw_30 : FLUSH_DRAW (no best draws on board)";
	public static String FlushDraw_31 = "FlushDraw_31 : FLUSH_DRAW but BEST draws on board";

	// TWO FLUSH_DRAW on hand (only at TURN)
	public static String FlushDraw_40 = "FlushDraw_40 : I have NUTS and TWO FLUSH_DRAW !!!";
	public static String FlushDraw_50 = "FlushDraw_50 : TWO FLUSH_DRAW MAX (no best draws on board)";
	public static String FlushDraw_51 = "FlushDraw_51 : TWO FLUSH_DRAW MAX but FULL draw on board";
	public static String FlushDraw_52 = "FlushDraw_52 : TWO FLUSH_DRAW MAX but STRAIGHT draw on board";
	public static String FlushDraw_60 = "FlushDraw_60 : TWO FLUSH_DRAW (no best draws on board)";
	public static String FlushDraw_61 = "FlushDraw_61 : TWO FLUSH_DRAW but BEST draws on board";

	// TODO
	// FLUSH_DRAW and SET
	// KICKER FLUSH BOARD KO
	
	public static String FlushDraw_BUG = "FlushDraw_BUG : NO RIVER possible with FLUSH_DRAW";

	public FlushDrawStrategy(StrategyContext context) {
		super(context);
		System.out.println("----------------------------------------");
		System.out.println("FlushDrawStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		if (!boardHasFullorStraightDraw(boardDrawsSorted)) {
			// NUTS (NO FLUSH_DRAW)
			if (isNutsForSecondDrawCategory(handDrawsSorted, boardDrawsSorted)) {
				if (iHaveNuts) {
					System.out.println(FlushDraw_10);
					bettingDecision = betPot();
				}
				else {
					System.out.println(FlushDraw_11);
					bettingDecision = betPot();// TODO caution
				}
			}
			// NO NUTS
			else {
				if (iHaveNuts) {
					System.out.println(FlushDraw_20);
					bettingDecision = betIfnoBetOrCall_call(BetType.POT);
				}
				else {
					System.out.println(FlushDraw_30);// TODO BETTER METHOD
					bettingDecision = betIfnoBetOrFold_fold(BetType.SMALL);
				}
			}
		}
		// PAIR or STRAIGHT_DRAW in board; iHaveNuts = false, use nutsForLevel  
		else {
			if (isNutsForSecondDrawCategory(handDrawsSorted, boardDrawsSorted)) {
				if (iHaveNuts) {
					System.out.println(FlushDraw_10);
					bettingDecision = betPot();
				}
				else {
					System.out.println(FlushDraw_11);
					bettingDecision = betPot();// TODO caution
				}
			}
			else {
				if (nutsForLevel) {
					if (boardHasFullDraw(boardDrawsSorted)) {
						System.out.println(FlushDraw_21);
						bettingDecision = betIfnoBetOrFold_fold(BetType.SMALL);	
					}
					else {
						System.out.println(FlushDraw_22);
						bettingDecision = betIfnoBetOrCall_call(BetType.POT);
					}
				}
				else {
					System.out.println(FlushDraw_31);
					bettingDecision = BettingDecision.CHECK_FOLD;
				}
			}
		}

		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}

		return bettingDecision;
	}

	/**
	 * two FLUSH draw possible on hand
	 */
	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		DrawModel handDrawSecond = drawSecond(handDrawsSorted);

		// TWO FLUSH DRAW possible on hand
		if (handDrawSecond != null && handDrawSecond.getHandCategory().equals(HandCategory.FLUSH_DRAW)) {

			if (!boardHasFullorStraightDraw(boardDrawsSorted)) {
				// NUTS (NO FLUSH_DRAW)
				if (isNutsForSecondDrawCategory(handDrawsSorted, boardDrawsSorted)) {
					System.out.println(FlushDraw_40);
					bettingDecision = betPot();
				}
				else {
					if (iHaveNuts && isNutsForCategory(handDrawSecond, boardDrawsSorted)) {
						System.out.println(FlushDraw_50);
						bettingDecision = betPot();
					}
					else {
						System.out.println(FlushDraw_60);
						bettingDecision = betPot();
					}
				}
			}
			// PAIR or STRAIGHT_DRAW in board; iHaveNuts = false, use nutsForLevel  
			else {
				if (isNutsForSecondDrawCategory(handDrawsSorted, boardDrawsSorted)) {
					System.out.println(FlushDraw_40);
					bettingDecision = betPot();
				}
				else {
					if (nutsForLevel && isNutsForCategory(handDrawSecond, boardDrawsSorted)) {
						if (boardHasFullDraw(boardDrawsSorted)) {
							System.out.println(FlushDraw_51);
							bettingDecision = betIfnoBetOrFold_fold(BetType.SMALL);	
						}
						else {
							System.out.println(FlushDraw_52);
							bettingDecision = betPot();
						}
					}
					else {
						System.out.println(FlushDraw_61);
						bettingDecision = BettingDecision.CHECK_FOLD;
					}
				}
			}
		}
		// ONE FLUSH_DRAW
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
		// no river possible for FLUSH_DRAW
		System.out.println(FlushDraw_BUG);
		return null;
	}

}
