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

	public QuadsModel(Rank rank, HandCategory handCategory) {
		super(DrawType.QUADS_DRAW);
		this.rank = rank;
		
		initialize(handCategory);
	}

	@Override
	public String toString() {
		return "Quads of " + rank + "; nuts=[" + displayNuts() + "]";
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
			nuts.addAll(Arrays.asList(card1, card2));
		}
	}

}