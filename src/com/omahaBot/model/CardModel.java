package com.omahaBot.model;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;

public class CardModel {

	private Rank rank;

	private Suit suit;

	public CardModel(Rank rank, Suit suit) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	@Override
	public String toString() {
		return rank.getShortName() + suit.getShortName();
	}

	
}
