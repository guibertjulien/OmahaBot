package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

public @Data class SetModel extends DrawModel {

	private final Rank rank;

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param rank
	 * @param permutationHand
	 */
	public SetModel(Rank rank, SortedSet<CardModel> permutationHand) {
		super(HandCategory.THREE_OF_A_KIND);

		this.rank = rank;

		initHoleCards(permutationHand);
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param rank
	 */
	public SetModel(Rank rank) {
		super(HandCategory.THREE_OF_A_KIND);

		this.rank = rank;

		initNuts();
	}

	@Override
	public String toString() {
		return this.display(handCategory + " " + rank + "; ");
	}

	private void initNuts() {
		CardModel card1 = new CardModel(rank);
		CardModel card2 = new CardModel(rank);

		nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
	}

	@Override
	public boolean isNuts(Object obj) {
		SetModel other = (SetModel) obj;

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