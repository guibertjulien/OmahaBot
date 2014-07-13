package com.omahaBot.strategy;

import com.omahaBot.enums.HandCategory;

public class StrategyFactory {

	public static AbstractStrategy getStrategy(HandCategory handCategory, int nbTurnOfBet, boolean imFirstToMove) {
		switch (handCategory) {
		case FOUR_OF_A_KIND:
			return new QuadsStategy(nbTurnOfBet, imFirstToMove);
		case FULL_HOUSE:
			return new FullStrategy(nbTurnOfBet, imFirstToMove);
		case FLUSH:
			return new FlushStrategy(nbTurnOfBet, imFirstToMove);
		case FLUSH_DRAW:
			return new FlushDrawStrategy(nbTurnOfBet, imFirstToMove);
		case STRAIGHT:
			return new StraightStrategy(nbTurnOfBet, imFirstToMove);
		case THREE_OF_A_KIND:
			return new SetStrategy(nbTurnOfBet, imFirstToMove);
		case TWO_PAIR:
			return new TwoPairStrategy(nbTurnOfBet, imFirstToMove);
		case ONE_PAIR:
			return new OnePairStrategy(nbTurnOfBet, imFirstToMove);
		default:
			return new BluffStrategy(nbTurnOfBet, imFirstToMove);
		}
	}
}
