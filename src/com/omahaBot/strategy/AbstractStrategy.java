package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public abstract class AbstractStrategy {

	protected boolean slowPlayStrategy = false;
	protected boolean bluffStrategy = false;
	protected boolean raise = false;

	protected final StrategyTurnContext context;

	public enum BetType {
		SMALL, BIG, POT
	}

	public AbstractStrategy(StrategyTurnContext context) {
		super();
		this.context = context;
	}

	public abstract BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType);

	public abstract BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType);

	public abstract BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType);

	/**
	 * Call all bets
	 * 
	 * @return
	 */
	public BettingDecision callAllBet() {
		return BettingDecision.CHECK_CALL;
	}

	/**
	 * Bet MIN or 50% du pot
	 * 
	 * @return
	 */
	public BettingDecision betSmall() {
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_MIN, BettingDecision.BET_RAISE_50);
	}

	/**
	 * Bet 50% ou 75% du pot
	 * 
	 * @return
	 */
	public BettingDecision betBig() {
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_50, BettingDecision.BET_RAISE_75);
	}

	/**
	 * Bet pot (ALLIN)
	 * 
	 * @return
	 */
	public BettingDecision betPot() {
		return BettingDecision.ALLIN;
	}

//	public BettingDecision bluff() {
//		bluffStrategy = true;
//		return BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE_75);
//	}

	/**
	 * 1 : betOrFold - 2 : fold
	 * 
	 * @return
	 */
	public BettingDecision betOrFold_fold(BetType betType) {
		if (context.isFirstTurnOfBet()) {// 1er tour de mise
			if (context.noBetInTurn()) {// pas de mise précédente
				return bet(betType);
			}
		}

		return BettingDecision.CHECK_FOLD;
	}

	/**
	 * 1 : betOrCall - 2 : fold
	 * 
	 * @return
	 */
	public BettingDecision betOrCall_fold(BetType betType) {
		if (context.isFirstTurnOfBet()) {// 1er tour de mise
			if (context.noBetInTurn()) {// pas de mise précédente
				return bet(betType);
			}
			else {
				return callAllBet();
			}
		}

		return BettingDecision.CHECK_FOLD;
	}

	/**
	 * MOVE Check Raise
	 * 
	 * @return
	 */
	public BettingDecision checkRaise_withNuts() {
		if (context.imFirstToAction()) {
			return callAllBet();
		}
		else {
			return betPot();
		}
	}

	// public boolean boardQuadsPossible(List<DrawModel> boardDraws) {
	// return findHandCategory(boardDraws, HandCategory.FOUR_OF_A_KIND);
	// }
	//
	// public boolean boardFlushPossible(List<DrawModel> boardDraws) {
	// return findHandCategory(boardDraws, HandCategory.FLUSH);
	// }
	//
	// public boolean boardFlushDrawPossible(List<DrawModel> boardDraws) {
	// return findHandCategory(boardDraws, HandCategory.FLUSH_DRAW);
	// }
	//
	// public boolean boardStraightPossible(List<DrawModel> boardDraws) {
	// return findHandCategory(boardDraws, HandCategory.STRAIGHT)
	// || findHandCategory(boardDraws, HandCategory.STRAIGHT_ACE_LOW);
	// }
	//
	// private boolean findHandCategory(List<DrawModel> boardDraws, HandCategory
	// handCategory) {
	// List<DrawModel> find = boardDraws.stream()
	// .filter(d -> d.getHandCategory().equals(handCategory))
	// .collect(Collectors.toList());
	//
	// return !find.isEmpty();
	// }

	public boolean isNutsForCategory(DrawModel handDraw, SortedSet<DrawModel> boardDrawsSorted) {

		for (DrawModel drawModelBoard : boardDrawsSorted) {
			if (handDraw != null && drawModelBoard != null) {
				// same handCategory
				if (handDraw.getHandCategory().equals(drawModelBoard.getHandCategory())) {
					return handDraw.isNuts(drawModelBoard);
				}
			}
		}

		return false;
	}

	private BettingDecision bet(BetType betType) {
		switch (betType) {
		case SMALL:
			return betSmall();
		case BIG:
			return betBig();
		case POT:
			return betPot();
		default:
			return BettingDecision.CHECK_FOLD;
		}
	}
}
