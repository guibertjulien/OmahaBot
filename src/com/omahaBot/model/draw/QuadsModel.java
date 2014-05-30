package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class QuadsModel extends DrawModel {

	private final Rank rank;

	public QuadsModel(Rank rank, HandCategory boardCategory, SortedSet<CardModel> permutationHand) {
		super(HandCategory.FOUR_OF_A_KIND, permutationHand);
		this.rank = rank;

		initialize(boardCategory);

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}
	}

	@Override
	public String toString() {
		String display = "";

		display += handCategory + " " + rank + "; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize(HandCategory boardCategory) {
		CardModel card1 = null;
		CardModel card2 = null;

		switch (boardCategory) {
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

	@Override
	public boolean isNuts(Object obj) {
		QuadsModel other = (QuadsModel) obj;

		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.first().getRank().equals(other.nutsOrHoleCards.first().getRank()))
			return false;
		if (!nutsOrHoleCards.last().getRank().equals(other.nutsOrHoleCards.last().getRank()))
			return false;
		return true;
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof QuadsModel) {
			QuadsModel drawCompare = (QuadsModel) o;

			// compare rank
			if (this.rank.ordinal() > drawCompare.rank.ordinal()) {
				return -1;
			}
			else if (this.rank.ordinal() < drawCompare.rank.ordinal()) {
				return 1;
			} else {
				return 0;
			}
		}
		else {
			return super.compareTo(o);
		}
	}
}