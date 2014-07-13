package com.omahaBot.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public abstract class AbstractStrategy {

	protected boolean slowPlayStrategy = false;
	protected boolean bluffStrategy = false;
	protected boolean raise = false;

	protected static final int STRAIGHT_OUTS_MIN = 8;

	protected final int nbTurnOfBet;
	protected final boolean imFirstToMove;

	public AbstractStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super();
		this.nbTurnOfBet = nbTurnOfBet;
		this.imFirstToMove = imFirstToMove;
	}

	public abstract BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType);

	public abstract BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType);

	public abstract BettingDecision decideAtRiver(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType);

	public BettingDecision slowPlay() {
		slowPlayStrategy = true;
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_50, BettingDecision.CHECK_CALL);
	}

	public BettingDecision callAllBet() {
		return BettingDecision.CHECK_CALL;
	}

	// TODO
	public BettingDecision jeMiseEnPremier() {
		return BettingDecision.CHECK_CALL;
	}

	// TODO en fonction du bet
	public BettingDecision callSmallBet() {
		return BettingDecision.CHECK_CALL;
	}

	public BettingDecision betSmall() {
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_MIN, BettingDecision.BET_RAISE_50);
	}

	public BettingDecision betBig() {
		return BettingDecision.randomBetween(BettingDecision.BET_RAISE_50, BettingDecision.BET_RAISE_75);
	}

	public BettingDecision betMax() {
		return BettingDecision.ALLIN;
	}

	public BettingDecision bluff() {
		bluffStrategy = true;
		return BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE_75);
	}

	public BettingDecision checkRaiseWithNuts() {
		if (imFirstToMove) {
			return BettingDecision.CHECK_CALL;
		}
		else {
			return BettingDecision.ALLIN;	
		}
	}

	public BettingDecision betFirstAndCall() {
		if (nbTurnOfBet == 1) {
			return betBig();
		}
		else {
			return BettingDecision.CHECK_CALL;
		}
	}
	
	public BettingDecision betFirstAndFold() {
		if (nbTurnOfBet == 1) {
			return betBig();
		}
		else {
			return BettingDecision.CHECK_FOLD;
		}
	}

	public boolean boardQuadsPossible(List<DrawModel> boardDraws) {
		return findHandCategory(boardDraws, HandCategory.FOUR_OF_A_KIND);
	}

	public boolean boardFlushPossible(List<DrawModel> boardDraws) {
		return findHandCategory(boardDraws, HandCategory.FLUSH);
	}

	public boolean boardFlushDrawPossible(List<DrawModel> boardDraws) {
		return findHandCategory(boardDraws, HandCategory.FLUSH_DRAW);
	}

	public boolean boardStraightPossible(List<DrawModel> boardDraws) {
		return findHandCategory(boardDraws, HandCategory.STRAIGHT)
				|| findHandCategory(boardDraws, HandCategory.STRAIGHT_ACE_LOW);
	}

	private boolean findHandCategory(List<DrawModel> boardDraws, HandCategory handCategory) {
		List<DrawModel> find = boardDraws.stream()
				.filter(d -> d.getHandCategory().equals(handCategory))
				.collect(Collectors.toList());

		return !find.isEmpty();
	}
}
