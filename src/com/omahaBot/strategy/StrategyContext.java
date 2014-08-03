package com.omahaBot.strategy;

import lombok.Data;

import com.omahaBot.enums.LastPlayerBetType;

@Data
public class StrategyContext {

	public static final int STRAIGHT_OUTS_MIN = 8;

	private final int nbTurnOfBet;
	private final int nbPlayerInAction;
	private final int nbAction;
	private final LastPlayerBetType lastPlayerBetType;
	
	//private final PokerPosition myPokerPosition;
	
	public boolean isFirstTurnOfBet() {
		return nbTurnOfBet == 1;
	}
	
	public boolean imFirstToAction() {
		return nbAction == 1;
	}
//	
//	public boolean imLastToAction() {
//		return nbAction % nbPlayerInAction == 0;
//	}
	
	public boolean isHU() {
		return nbPlayerInAction == 2;
	}
	
	public boolean noBetInTurn() {
		return lastPlayerBetType.equals(LastPlayerBetType.NO_BET);
	}

	@Override
	public String toString() {
		return "StrategyActionContext [nbTurnOfBet=" + nbTurnOfBet + ", nbPlayerInAction=" + nbPlayerInAction
				+ ", nbAction=" + nbAction + ", lastPlayerBetType=" + lastPlayerBetType + "]";
	}
	
	
}
