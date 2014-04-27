package com.omahaBot.model.handCategory;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class TwoPairModel {

	private final Rank rankPair1;

	private final Rank rankPair2;

	@Override
	public String toString() {
		return "Two pairs of " + rankPair1 + " and " + rankPair2;
	}

	public SortedSet<CardModel> initNuts() {

		SortedSet<CardModel> nuts = new TreeSet<>();

		CardModel card1 = new CardModel(rankPair1, Suit.SPADE);
		CardModel card2 = new CardModel(rankPair2, Suit.HEART);

		nuts.addAll(Arrays.asList(card1, card2));

		return nuts;
	}
}
