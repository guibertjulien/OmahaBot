package com.omahaBot.model.draw;

import java.util.Arrays;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class TwoPairModel extends DrawModel {

	private final Rank rankPair1;

	private final Rank rankPair2;

	public TwoPairModel(Rank rankPair1, Rank rankPair2, boolean isDraw) {
		super(DrawType.BEST_TWO_PAIR_DRAW, isDraw);
		this.rankPair1 = rankPair1;
		this.rankPair2 = rankPair2;

		if (isDraw) {
			initialize();			
		}
	}

	@Override
	public String toString() {
		String display = "";

		display = "Two pairs of " + rankPair1 + " and " + rankPair2 + "; ";
		display += isDraw ? "nuts" : "holeCards";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize() {
		CardModel card1 = new CardModel(rankPair1, Suit.SPADE);
		CardModel card2 = new CardModel(rankPair2, Suit.HEART);

		nutsOrHoleCards.addAll(Arrays.asList(card1, card2));
	}
}
