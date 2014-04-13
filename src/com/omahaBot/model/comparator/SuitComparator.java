package com.omahaBot.model.comparator;

import java.util.Comparator;

import com.omahaBot.model.CardModel;

public class SuitComparator implements Comparator<CardModel> {

	@Override
	public int compare(CardModel o1, CardModel o2) {
		if (o1.getSuit().ordinal() > o2.getSuit().ordinal())
			return 1;
		if (o1.getSuit().ordinal() < o2.getSuit().ordinal())
			return -1;
		else
			return 0;
	}

}
