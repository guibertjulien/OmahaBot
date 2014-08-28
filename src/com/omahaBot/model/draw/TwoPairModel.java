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

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param rankPair1
	 * @param rankPair2
	 * @param permutationHand
	 */
	public TwoPairModel(Rank rankPair1, Rank rankPair2, SortedSet<CardModel> permutationHand) {
		super(HandCategory.TWO_PAIR);
		this.rankPair1 = rankPair1;
		this.rankPair2 = rankPair2;

		initHoleCards(permutationHand);
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param rankPair1
	 * @param rankPair2
	 */
	public TwoPairModel(Rank rankPair1, Rank rankPair2) {
		super(HandCategory.TWO_PAIR);
		this.rankPair1 = rankPair1;
		this.rankPair2 = rankPair2;

		initNuts();
	}

	@Override
	public String toString() {
		return this.display(handCategory + " of " + rankPair1 + " and " + rankPair2 + "; ");
	}

	private void initNuts() {
		CardModel card1 = null, card2 = null;

		card1 = new CardModel(rankPair1);
		card2 = new CardModel(rankPair2);

		nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TwoPairModel) {
			TwoPairModel other = (TwoPairModel) obj;

			if (rankPair1 != other.rankPair1)
				return false;
			if (rankPair2 != other.rankPair2)
				return false;
			return true;
		}
		else {
			return false;
		}
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
		return this.equals(other);
	}
}
