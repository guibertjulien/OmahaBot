package com.omahaBot.model.draw;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.Rank;

public @Data
class OnePairModel extends DrawModel {
	private final Rank rank;

	public OnePairModel(Rank rank) {
		super(DrawType.BEST_TWO_PAIR_DRAW, false);
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "One pairs of " + rank;
	}
}
