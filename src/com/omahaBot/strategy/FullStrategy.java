package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FullStrategy extends AbstractStrategy {

	public FullStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> FullStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			bettingDecision = slowPlay();
		}
		else {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
				if (nbTurnOfBet == 1) {
					bettingDecision = callSmallBet();
				}
			default:
				// TODO exception : impossible
				break;
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
			bettingDecision = callAllBet();
		}

		return bettingDecision;

	}
}
