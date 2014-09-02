package com.omahaBot.strategy;

import java.math.BigDecimal;

import lombok.Data;

import com.omahaBot.enums.LastPlayerBetType;

@Data
public class StrategyContext {

	private final int nbTurnOfBet;
	private final int nbPlayerInAction;
	private final int nbAction;
	private final LastPlayerBetType lastPlayerBetType;
	private BigDecimal pot;
	private BigDecimal toCall;
	
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
	
	public double percentOfBet() {
		return toCall.doubleValue() / pot.doubleValue();
	}

	@Override
	public String toString() {
		return "StrategyActionContext [nbTurnOfBet=" + nbTurnOfBet + ", nbPlayerInAction=" + nbPlayerInAction
				+ ", nbAction=" + nbAction + ", lastPlayerBetType=" + lastPlayerBetType + "]";
	}

	public StrategyContext(int nbTurnOfBet, int nbPlayerInAction, int nbAction, LastPlayerBetType lastPlayerBetType) {
		super();
		this.nbTurnOfBet = nbTurnOfBet;
		this.nbPlayerInAction = nbPlayerInAction;
		this.nbAction = nbAction;
		this.lastPlayerBetType = lastPlayerBetType;
	}

	public StrategyContext(int nbTurnOfBet, int nbPlayerInAction, int nbAction, LastPlayerBetType lastPlayerBetType,
			BigDecimal pot, BigDecimal toCall) {
		super();
		this.nbTurnOfBet = nbTurnOfBet;
		this.nbPlayerInAction = nbPlayerInAction;
		this.nbAction = nbAction;
		this.lastPlayerBetType = lastPlayerBetType;
		this.pot = pot;
		this.toCall = toCall;
	}
	
	
}
