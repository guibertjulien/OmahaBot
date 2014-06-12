package com.omahaBot.model.draw;

import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;

public @Data
class OnePairModel extends DrawModel {
	private final Rank rank;

	public OnePairModel(Rank rank, SortedSet<CardModel> permutationHand) {
		super(HandCategory.ONE_PAIR, permutationHand);
		this.rank = rank;

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}
	}

	@Override
	public String toString() {
		String display = "";

		display = handCategory + " of " + rank;

		return display;
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
		// TODO Auto-generated method stub
		return false;
	}
}