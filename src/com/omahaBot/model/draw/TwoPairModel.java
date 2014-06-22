package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

public @Data class TwoPairModel extends DrawModel {

	private final Rank rankPair1;

	private final Rank rankPair2;

	public TwoPairModel(Rank rankPair1, Rank rankPair2, SortedSet<CardModel> permutationHand) {
		super(HandCategory.TWO_PAIR, permutationHand);
		this.rankPair1 = rankPair1;
		this.rankPair2 = rankPair2;

		initialize();

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}
	}

	@Override
	public String toString() {
		String display = "";

		display += handCategory + " of " + rankPair1 + " and " + rankPair2 + "; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize() {
		CardModel card1 = new CardModel(rankPair1);
		CardModel card2 = new CardModel(rankPair2);

		nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof TwoPairModel) {
			TwoPairModel drawCompare = (TwoPairModel) o;

			// compare rankPair1 next rankPair2
			if (this.rankPair1.ordinal() > drawCompare.rankPair1.ordinal()) {
				return -1;
			}
			else if (this.rankPair1.ordinal() < drawCompare.rankPair1.ordinal()) {
				return 1;
			} else {
				if (this.rankPair2.ordinal() > drawCompare.rankPair2.ordinal()) {
					return -1;
				}
				else if (this.rankPair2.ordinal() < drawCompare.rankPair2.ordinal()) {
					return 1;
				} else {
					return compareHoleCards(o);
				}
			}
		}
		else {
			return super.compareTo(o);
		}
	}

	@Override
	public boolean isNuts(Object obj) {
		TwoPairModel other = (TwoPairModel) obj;

		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.getSortedCards().first().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().first().getRank()))
			return false;
		if (!nutsOrHoleCards.getSortedCards().last().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().last().getRank()))
			return false;
		return true;
	}
}
