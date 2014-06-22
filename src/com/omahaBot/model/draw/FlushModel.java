package com.omahaBot.model.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CardPackModel;
import com.omahaBot.model.CoupleCards;

public @Data class FlushModel extends DrawModel {

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
		display += "=[" + displayNutsOrHoleCards() + "]; ";

		return display;
	}

	private void initialize(String drawString) {

		CardPackModel cardPackModel = new CardPackModel(drawString);

		List<CardModel> cardsReverse = Lists.reverse(new ArrayList<>(cardPackModel.getCards()));
		List<Rank> listRankReverse = Lists.reverse(Arrays.asList(Rank.values()));

		kicker = cardsReverse.get(0).getRank();
		
		List<CardModel> coupleCards = new ArrayList<CardModel>();
		
		CardModel card = null;
		int i = 0;
		
		for (Rank rank : listRankReverse) {
			
			if (!rank.equals(Rank.UNKNOWN)) {
				if (i < cardsReverse.size()) {
					card = cardsReverse.get(i);
					if (!rank.equals(card.getRank())) {
						coupleCards.add(new CardModel(rank, suit));
						if (coupleCards.size() == 2) {
							break;
						}
					}
				}
				else {
					coupleCards.add(new CardModel(rank, suit));
					if (coupleCards.size() == 2) {
						break;
					}
				}
				
				if (rank.equals(card.getRank())) {
					i++;	
				}
			}
		}

		if (coupleCards.size() == 2) {
			nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(coupleCards));
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

		if (!this.equals(obj)) {
			return false;
		}
		else {
			return nutsOrHoleCards.isEqualsKicker(other.nutsOrHoleCards);
		}
	}
}
