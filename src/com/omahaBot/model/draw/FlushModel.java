package com.omahaBot.model.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CardPackModel;

public @Data
class FlushModel extends DrawModel {

	private final Suit suit;

	private Rank kicker;

	private boolean straightFlush;

	public FlushModel(HandCategory handCategory, Suit suit, String drawString, SortedSet<CardModel> permutationHand) {
		super(handCategory, permutationHand);
		this.suit = suit;
		
		initialize(drawString);

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}	
	}

	@Override
	public String toString() {
		String display = "";

		display += handCategory + " of " + suit + " with kicker " + kicker + "; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
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
				nutsOrHoleCards.add(new CardModel(rank, suit));
				if (nutsOrHoleCards.size() == 2) {
					break;
				}
			} else if (rank.equals(card.getRank())) {
				if (i == 1) {
					Rank rankNext = Rank.values()[rank.ordinal() - 1];
					nutsOrHoleCards.add(new CardModel(rankNext, suit));
				}
				else {
					card = listCard.get(++i);
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		FlushModel other = (FlushModel) obj;
		
		return suit.equals(other.suit);
	}
	
	@Override
	public boolean isNuts(Object obj) {
		FlushModel other = (FlushModel) obj;
		
		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.last().getRank().equals(other.nutsOrHoleCards.last().getRank()))
			return false;
		return true;
	}
}
