package com.omahaBot.strategy;

import java.util.ArrayList;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.draw.DrawModel;

public class FlushDrawStrategy extends AbstractStrategy {

	public FlushDrawStrategy(int nbTurnOfBet, boolean imFirstToMove) {
		super(nbTurnOfBet, imFirstToMove);
		System.out.println("--> FlushDrawStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			bettingDecision = betMax();
		}
		else {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
				bettingDecision = BettingDecision.CHECK_FOLD;
				break;
			default:
				// TODO test straightDrawType
				// TODO test THREE_OF_A_KIND, TWO_PAIR
				break;
			}
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (iHaveNuts) {
			bettingDecision = betMax();
		}
		else {
			// test bordDraw level 0
			switch (boardDraws.get(0).getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
				bettingDecision = BettingDecision.CHECK_FOLD;
				break;
			default:
				// TODO test straightDrawType, DOUBLE_FLUSH_DRAW
				break;
			}
		}

		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtRiver(DrawModel drawModel, boolean iHaveNuts,
			ArrayList<DrawModel> boardDraws, boolean nutsForLevel, StraightDrawType straightDrawType) {
		// no river possible for FLUSH_DRAW
		return null;
	}
}
