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

	public SetModel(Rank rank, boolean isDraw) {
		super(DrawType.BEST_SET_DRAW, isDraw);
		this.rank = rank;

		if (isDraw) {
			initialize();			
		}
	}

	@Override
	public String toString() {
		String display = "";

		display = "Set of " + rank + "; ";
		display += isDraw ? "nuts" : "holeCards";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize() {
		CardModel card1 = new CardModel(rank, Suit.SPADE);
		CardModel card2 = new CardModel(rank, Suit.HEART);

		nutsOrHoleCards.addAll(Arrays.asList(card1, card2));
	}

}