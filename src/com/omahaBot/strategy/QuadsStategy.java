package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class QuadsStategy extends AbstractStrategy {

	public QuadsStategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> QuadsStategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			if (nbTurnOfBet == 1) {
				bettingDecision = slowPlay();
			}
			else {
				bettingDecision = betBig();
			}
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		return decideAtFlop(drawModel, iHaveNuts, boardDraws, nutsForLevel, straightDrawType);
	}

	@Override
	public BettingDecision decideAtRiver(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			if (nbTurnOfBet == 1) {
				bettingDecision = betSmall();
			}
			else {
				bettingDecision = betMax();
			}
		}
		else {
			// TODO Bluff ?
		}

		return bettingDecision;
	}
}
