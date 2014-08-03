package com.omahaBot.model;

import lombok.Data;

import com.omahaBot.enums.BettingDecision;

@Data
public class StrategyModel {

	private final BettingDecision bettingDecision;
	private final String context;
}
