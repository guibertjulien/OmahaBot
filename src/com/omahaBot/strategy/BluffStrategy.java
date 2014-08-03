package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

/**
 * TODO
 * 
 * @author Julien
 *
 */
public class BluffStrategy extends AbstractStrategy {

	public BluffStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> BluffStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}
}
