package com.omahaBot.model.handCategory;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class TopSetModel {

	private final Rank rank;

	@Override
	public String toString() {
		return "Set of " + rank;
	}

	public SortedSet<CardModel> initNuts() {

		SortedSet<CardModel> nuts = new TreeSet<>();

		CardModel card1 = new CardModel(rank, Suit.SPADE);
		CardModel card2 = new CardModel(rank, Suit.HEART);

		nuts.addAll(Arrays.asList(card1, card2));

		return nuts;
	}
}