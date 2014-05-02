package com.omahaBot.model.draw;

import lombok.Data;

import com.omahaBot.enums.Rank;

public @Data
class OnePairModel {
	private final Rank rank;

	@Override
	public String toString() {
		return "One pairs of " + rank;
	}
}
