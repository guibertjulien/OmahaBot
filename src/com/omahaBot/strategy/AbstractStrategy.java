package com.omahaBot.strategy;

import java.util.Iterator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.Predicate;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public abstract class AbstractStrategy {

	protected boolean slowPlayStrategy = false;
	protected boolean bluffStrategy = false;
	protected boolean raise = false;

	protected final StrategyContext context;

	public enum BetType {
		SMALL, BIG, POT
	}

	public AbstractStrategy(StrategyContext context) {
		super();
		this.context = context;
	}

	public abstract BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException;

	public abstract BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException;

	public abstract BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted,
			SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException;

	/**
	 * Call all bets
	 * 
	 * @return
	 */
	public BettingDecision callAllBet() {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		
		if (go()) {
			bettingDecision = BettingDecision.CHECK_CALL;
		}
		
		return bettingDecision;
	}

	/**
	 * Bet MIN or 50% du pot
	 * 
	 * @return
	 */
	public BettingDecision betSmall() {
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_25, BettingDecision.BET_RAISE_50);
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

	// public BettingDecision bluff() {
	// bluffStrategy = true;
	// return BettingDecision.randomBetween(BettingDecision.ALLIN,
	// BettingDecision.BET_RAISE_75);
	// }

	/**
	 * 
	 * @param betType
	 * @return
	 */
	public BettingDecision betIfnoBetOrCall_call(BetType betType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (context.isFirstTurnOfBet()) {// 1er tour de mise
			if (context.noBetInTurn()) {// pas de mise précédente
				bettingDecision = bet(betType);
			}
			else {
				bettingDecision = callAllBet();
			}
		}
		else {
			bettingDecision = callAllBet();
		}

		return bettingDecision;
	}

	public BettingDecision betIfnoBetOrCall_fold(BetType betType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (context.isFirstTurnOfBet()) {// 1er tour de mise
			if (context.noBetInTurn()) {// pas de mise précédente
				bettingDecision = bet(betType);
			}
			else {
				bettingDecision = callAllBet();
			}
		}
		else {
			bettingDecision = BettingDecision.CHECK_FOLD;
		}

		return bettingDecision;
	}
	
	public BettingDecision betIfnoBetOrFold_fold(BetType betType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (context.isFirstTurnOfBet()) {// 1er tour de mise
			if (context.noBetInTurn()) {// pas de mise précédente
				bettingDecision = bet(betType);
			}
			else {
				bettingDecision = BettingDecision.CHECK_FOLD;
			}
		}
		else {
			bettingDecision = BettingDecision.CHECK_FOLD;
		}

		return bettingDecision;
	}

	/**
	 * MOVE Check Raise
	 * 
	 * @return
	 */
	public BettingDecision checkRaise_withNuts() {

		System.out.println("checkRaise_withNuts");

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
				if (handDraw.equals(drawModelBoard)) {
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

	private DrawModel drawSecondCategory(SortedSet<DrawModel> drawsSorted) {
		if (drawsSorted.size() > 0) {
			HandCategory level0 = drawsSorted.first().getHandCategory();

			for (DrawModel drawModel : drawsSorted) {
				if (!drawModel.getHandCategory().equals(level0)) {
					return drawModel;
				}
			}
		}

		return null;
	}
	
	protected DrawModel drawSecond(SortedSet<DrawModel> drawsSorted) {
		if (drawsSorted.size() > 1) {
			Iterator<DrawModel> it = drawsSorted.iterator();
			DrawModel drawModel1 = it.next();
			DrawModel drawModel2 = it.next();
			
			return drawModel2; 
		}

		return null;
	}

	public boolean isNutsForSecondDrawCategory(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted) {

		// compare handDraw & boarDraw second Categoy
		DrawModel handDrawSecondCategory = drawSecondCategory(handDrawsSorted);
		DrawModel boardDrawSecondCategory = drawSecondCategory(boardDrawsSorted);

		if (handDrawSecondCategory != null && boardDrawSecondCategory != null) {
			if (handDrawSecondCategory.getHandCategory().equals(boardDrawSecondCategory.getHandCategory())) {
				return handDrawSecondCategory.isNuts(boardDrawSecondCategory);
			}	
		}
		
		return false;
	}

	/**
	 * MIN ONE PAIR on board
	 * @param drawsSorted
	 * @return
	 */
	public boolean boardHasFullDraw(SortedSet<DrawModel> drawsSorted) {

		Predicate<? super DrawModel> filter_rankDraws = (d -> d.getHandCategory().equals(HandCategory.FOUR_OF_A_KIND)
				|| d.getHandCategory().equals(HandCategory.FULL_HOUSE));

		Optional<DrawModel> drawExist = drawsSorted
				.stream()
				.filter(filter_rankDraws)
				.findAny();

		return drawExist.isPresent();
	}
	
	public boolean boardHasStraightDraw(SortedSet<DrawModel> drawsSorted) {

		Predicate<? super DrawModel> filter_rankDraws = (d -> d.getHandCategory().equals(HandCategory.STRAIGHT)
				|| d.getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW));

		Optional<DrawModel> drawExist = drawsSorted
				.stream()
				.filter(filter_rankDraws)
				.findAny();

		return drawExist.isPresent();
	}
	
	public boolean boardHasFullorStraightDraw(SortedSet<DrawModel> drawsSorted) {
		return boardHasFullDraw(drawsSorted) || boardHasStraightDraw(drawsSorted);
	}
	
	public boolean go() {
		
		boolean go = true;
		
		// test si HU
		if (context.isHU()) {
			System.out.println("--> HU");
		}
		else {	
			System.out.println("--> PERCENT TO GO: " + context.percentOfBet());
		}
		
		return go;
	}
}
