package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class BluffStrategy extends AbstractStrategy {

	public BluffStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> BluffStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtRiver(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;
		return bettingDecision;
	}
}
