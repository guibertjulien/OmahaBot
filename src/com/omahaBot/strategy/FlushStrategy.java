package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FlushStrategy extends AbstractStrategy {

	public FlushStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> FlushStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			// PRECAUTION
			if (nbTurnOfBet == 1) {
				bettingDecision = betBig();
			}
			else {
				bettingDecision = betMax();
			}
		}
		else {
			// No nuts
			if (nbTurnOfBet == 1) {
				bettingDecision = betBig();
			}
			else {
				bettingDecision = callSmallBet();
			}
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			// PRECAUTION
			if (nbTurnOfBet == 1) {
				bettingDecision = betBig();
			}
			else {
				bettingDecision = betMax();
			}
		}
		else {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
				if (nutsForLevel) {
					if (nbTurnOfBet == 1) {
						bettingDecision = betBig();
					}
					else {
						bettingDecision = callSmallBet();
					}
				}
			default:
				// TODO exception : impossible
				break;
			}
		}

		return bettingDecision;
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
			// TODO kicker, bluff ?
		}

		return bettingDecision;
	}
}
