package com.omahaBot.model.handCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public @Data
class FlushModel {

	private final Rank kicker;

	private final Suit suit;

	private boolean straightFlush;

	@Override
	public String toString() {
		return "Flush " + suit + " with kicker " + kicker;
	}

	public SortedSet<CardModel> initNuts(SortedSet<CardModel> cards) {
		SortedSet<CardModel> nuts = new TreeSet<>();

		List<CardModel> listCard = Lists.reverse(new ArrayList<>(cards));
		List<Rank> listRank = Lists.reverse(Arrays.asList(Rank.values()));

		CardModel card = listCard.get(0);
		int i = 0;

		for (Rank rank : listRank) {
			if (!rank.equals(Rank.UNKNOWN) && !rank.equals(card.getRank())) {

				nuts.add(new CardModel(rank, suit));
				if (nuts.size() == 2) {
					break;
				}
			} else if (rank.equals(card.getRank())) {
				card = listCard.get(++i);
			}
		}
		
		return nuts;
	}
}
