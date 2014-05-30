package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class SetModel extends DrawModel {

	private final Rank rank;

	public SetModel(Rank rank, SortedSet<CardModel> permutationHand) {
		super(HandCategory.THREE_OF_A_KIND, permutationHand);
		this.rank = rank;

		initialize();

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

	private void initialize() {
		CardModel card1 = new CardModel(rank, Suit.SPADE);
		CardModel card2 = new CardModel(rank, Suit.HEART);

		nutsOrHoleCards.addAll(Arrays.asList(card1, card2));
	}

	@Override
	public boolean isNuts(Object obj) {
		SetModel other = (SetModel) obj;

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

		if (o instanceof SetModel) {
			SetModel drawCompare = (SetModel) o;

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