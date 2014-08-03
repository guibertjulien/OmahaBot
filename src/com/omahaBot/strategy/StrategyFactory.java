package com.omahaBot.strategy;

import com.omahaBot.enums.HandCategory;

public class StrategyFactory {

	public static AbstractStrategy getStrategy(HandCategory handCategory, StrategyContext actionContext) {
		switch (handCategory) {
		case FOUR_OF_A_KIND:
			return new QuadsStategy(actionContext);
		case FULL_HOUSE:
			return new FullStrategy(actionContext);
		case FLUSH:
			return new FlushStrategy(actionContext);
		case FLUSH_DRAW:
			return new FlushDrawStrategy(actionContext);
		case STRAIGHT:
			return new StraightStrategy(actionContext);
		case THREE_OF_A_KIND:
			return new SetStrategy(actionContext);
		case TWO_PAIR:
			return new TwoPairStrategy(actionContext);
		case ONE_PAIR:
			return new OnePairStrategy(actionContext);
		default:
			return new BluffStrategy(actionContext);
		}
	}
}
