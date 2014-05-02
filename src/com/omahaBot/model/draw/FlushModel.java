package com.omahaBot.model.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CardPackModel;

public @Data
class FlushModel extends DrawModel {

	private final Suit suit;

	private Rank kicker;

	private boolean straightFlush;

	public FlushModel(DrawType drawType, Suit suit, String drawString) {
		super(drawType);
		this.suit = suit;
		
		initialize(drawString);
	}

	@Override
	public String toString() {
		return "Flush " + suit + " with kicker " + kicker + "; nuts=[" + displayNuts() + "]";
	}

	private void initialize(String drawString) {

		CardPackModel cardPackModel = new CardPackModel(drawString);
		
		List<CardModel> listCard = Lists.reverse(new ArrayList<>(cardPackModel.getCards()));
		List<Rank> listRank = Lists.reverse(Arrays.asList(Rank.values()));

		CardModel card = listCard.get(0);
		kicker = card.getRank();
		
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
	}
}
