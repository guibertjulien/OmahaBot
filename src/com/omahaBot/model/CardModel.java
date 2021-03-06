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

	public CardModel(Rank rank) {
		super();
		this.rank = rank;
		this.suit = Suit.UNKNOW;
	}

	public CardModel(String cardString) {
		super();
		this.rank = Rank.fromShortName(cardString.charAt(0));
		this.suit = Suit.fromShortName(cardString.charAt(1));
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

		// compare rank
		if (this.rank.equals(Rank.UNKNOWN)) {
			return -1;
		} else if (this.rank.ordinal() > o.rank.ordinal()) {
			return 1;
		}
		else if (this.rank.ordinal() < o.rank.ordinal()) {
			return -1;
		} else {
			// compare suit
			if (this.suit.ordinal() > o.suit.ordinal()) {
				return 1;
			}
			else if (this.suit.ordinal() < o.suit.ordinal()) {
				return -1;
			}
			else {
				// TODO exception
				return 0;
			}
		}
	}

	public boolean isConnected(CardModel o) {
		// cas particulier A2
		if (this.getRank().equals(Rank.ACE) && o.getRank().equals(Rank.TWO)) {
			return true;
		}
		else {
			return (this.ordinal() - o.ordinal() == -1);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardModel other = (CardModel) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	public int ordinal() {
		return rank.ordinal();

	}
}
