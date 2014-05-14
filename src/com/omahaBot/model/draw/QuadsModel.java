package com.omahaBot.model.draw;

import java.util.Arrays;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class QuadsModel extends DrawModel {

	private final Rank rank;

	public QuadsModel(Rank rank, HandCategory handCategory, boolean isDraw) {
		super(DrawType.QUADS_DRAW, isDraw);
		this.rank = rank;
		
		if (isDraw) {
			initialize(handCategory);			
		}
	}

	@Override
	public String toString() {
		String display = "";

		display = "Quads of " + rank + "; ";
		display += isDraw ? "nuts" : "holeCards";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize(HandCategory handCategory) {
		CardModel card1 = null;
		CardModel card2 = null;

		switch (handCategory) {
		case ONE_PAIR:
			card1 = new CardModel(rank, Suit.SPADE);
			card2 = new CardModel(rank, Suit.HEART);
			break;
		case THREE_OF_A_KIND:
			card1 = new CardModel(rank, Suit.SPADE);
			card2 = new CardModel(Rank.UNKNOWN, Suit.HEART);
			break;
		default:
			// TODO exception
			break;
		}

		if (card1 != null && card2 != null) {
			nutsOrHoleCards.addAll(Arrays.asList(card1, card2));
		}
	}

}