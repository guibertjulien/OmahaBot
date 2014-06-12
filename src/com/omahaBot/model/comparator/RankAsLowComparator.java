package com.omahaBot.model.comparator;

import java.util.Comparator;

import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;

public class RankAsLowComparator implements Comparator<CardModel> {

	@Override
	public int compare(CardModel o1, CardModel o2) {
		if (o1.getRank().equals(Rank.ACE)) {
			return -1;
		}
		if (o1.getRank().ordinal() > o2.getRank().ordinal())
			return 1;
		if (o1.getRank().ordinal() < o2.getRank().ordinal())
			return -1;
		else
			return 0;
	}

}
