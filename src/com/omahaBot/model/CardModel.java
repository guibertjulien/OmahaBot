package com.omahaBot.model;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;

public class CardModel implements Comparable<CardModel> {

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

	@Override
	public int compareTo(CardModel o) {
		if (this.rank.ordinal() > o.rank.ordinal())
			return 1;
		if (this.rank.ordinal() < o.rank.ordinal())
			return -1;
		else
			if (this.suit.ordinal() > o.suit.ordinal())
				return 1;
			if (this.suit.ordinal() < o.suit.ordinal())
				return -1;
			else return 0;
	}

	public boolean isConnected(CardModel o) {
		// cas particulier 2A
		if (this.getRank().equals(Rank.TWO) && this.getRank().equals(Rank.ACE)) {
			return true;
		}
		else {
			return (this.rank.ordinal() - o.rank.ordinal() == -1);
		}
	}
}
