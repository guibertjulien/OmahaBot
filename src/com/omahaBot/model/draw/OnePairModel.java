package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

public @Data class OnePairModel extends DrawModel {
	private final Rank rank;

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param rank
	 * @param permutationHand
	 */
	public OnePairModel(Rank rank, SortedSet<CardModel> permutationHand) {
		super(HandCategory.ONE_PAIR);
		this.rank = rank;

		initHoleCards(permutationHand);
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param rank
	 */
	public OnePairModel(Rank rank) {
		super(HandCategory.ONE_PAIR);
		this.rank = rank;

		initNuts();
	}

	@Override
	public String toString() {
		return this.display(handCategory + " of " + rank + "; ");
	}

	private void initNuts() {
		CardModel card1 = new CardModel(rank);
		CardModel card2 = new CardModel(Rank.UNKNOWN);

		nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof OnePairModel) {
			OnePairModel drawCompare = (OnePairModel) o;

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

	@Override
	public boolean isNuts(Object obj) {
		OnePairModel other = (OnePairModel) obj;

		if (!this.equals(obj))
			return false;
		if (other.nutsOrHoleCards.getCard1().getRank().equals(rank))
			return true;
		if (other.nutsOrHoleCards.getCard2().getRank().equals(rank))
			return true;
		return false;
	}
}
