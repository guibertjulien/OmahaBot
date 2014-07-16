package com.omahaBot.strategy;

import lombok.Data;

@Data
public class StrategyTurnContext {

	public static final int STRAIGHT_OUTS_MIN = 8;

	private final int nbTurnOfBet;
	private final int nbPlayerInAction;
	private final int nbAction;
	
	//private final PokerPosition myPokerPosition;

	private Double pot = 0.0;
	private Double lastBet = 0.0;
	
	public boolean isFirstTurnOfBet() {
		return nbTurnOfBet == 1;
	}
	
	public boolean imFirstToAction() {
		return nbAction == 1;
	}
	
	public boolean imLastToAction() {
		return nbAction % nbPlayerInAction == 0;
	}
	
	public boolean isHU() {
		return nbPlayerInAction == 2;
	}
	
	public boolean noBetInTurn() {
		return nbPlayerInAction == 2;
	}

	@Override
	public String toString() {
		return "StrategyActionContext [nbTurnOfBet=" + nbTurnOfBet + ", nbPlayerInAction=" + nbPlayerInAction
				+ ", nbAction=" + nbAction + ", pot=" + pot + ", lastBet=" + lastBet + "]";
	}
	
	
}
