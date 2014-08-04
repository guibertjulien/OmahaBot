package com.omahaBot.strategy;

import java.util.SortedSet;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StrategyUnknownException;
import com.omahaBot.model.draw.DrawModel;

public class FullStrategy extends AbstractStrategy {

	public static String Full_10 = "Full_10 : I have NUTS !!!";
	public static String Full_20 = "Full_20 : FULL MAX";
	public static String Full_21 = "Full_21 : FULL no max";

	public FullStrategy(StrategyContext context) {
		super(context);
		System.out.println("--> FullStrategy");
	}

	@Override
	public BettingDecision decideAtFlop(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;
		
		if (iHaveNuts) {
			System.out.println(Full_10);
			bettingDecision = checkRaise_withNuts();
		}
		else {// FOUR_OF_A_KIND possible sur le board
			if (nutsForLevel) {
				System.out.println(Full_20);
				// TODO caution
				bettingDecision = betIfnoBetOrCall_call(BetType.BIG);
			}
			else {
				System.out.println(Full_21);
				// TODO caution
				bettingDecision = betIfnoBetOrCall_call(BetType.SMALL);
			}
		}
				
		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}
		
		return bettingDecision;
	}

	@Override
	public BettingDecision decideAtTurn(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {
		return decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
	}

	@Override
	public BettingDecision decideAtRiver(SortedSet<DrawModel> handDrawsSorted, SortedSet<DrawModel> boardDrawsSorted,
			boolean iHaveNuts, boolean nutsForLevel, StraightDrawType straightDrawType) throws StrategyUnknownException {

		BettingDecision bettingDecision = null;

		if (iHaveNuts) {
			System.out.println(Full_10);
			bettingDecision = betPot();
		}
		else {// FOUR_OF_A_KIND possible sur le board
			bettingDecision = decideAtFlop(handDrawsSorted, boardDrawsSorted, iHaveNuts, nutsForLevel, straightDrawType);
		}
		
		if (bettingDecision == null) {
			throw new StrategyUnknownException(this.getClass().getName());
		}
		
		return bettingDecision;
	}
}
