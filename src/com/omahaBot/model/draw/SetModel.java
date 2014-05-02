package com.omahaBot.model.draw;

import java.util.Arrays;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class SetModel extends DrawModel {

	private final Rank rank;

	public SetModel(Rank rank) {
		super(DrawType.BEST_SET_DRAW);
		this.rank = rank;

		initialize();
	}

	@Override
	public String toString() {
		return "Set of " + rank;
	}

	private void initialize() {
		CardModel card1 = new CardModel(rank, Suit.SPADE);
		CardModel card2 = new CardModel(rank, Suit.HEART);

		nuts.addAll(Arrays.asList(card1, card2));
	}

}