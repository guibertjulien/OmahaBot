package com.omahaBot.model;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

@Data
public class CoupleCards {

	private final CardModel card1;

	private final CardModel card2;

	private SortedSet<CardModel> sortedCards;

	public CoupleCards(SortedSet<CardModel> sortedCards) {
		super();
		this.card1 = sortedCards.first();
		this.card2 = sortedCards.last();

		this.sortedCards = new TreeSet<CardModel>();
		this.sortedCards.addAll(sortedCards);
	}

	@Override
	public String toString() {
		return sortedCards.toString();
		// return String.join(", ", card1.toString(), card2.toString());
	}

	public boolean isEqualsKicker(CoupleCards other) {
		return (this.sortedCards.last().getRank().equals(other.sortedCards.last().getRank()));
	}
}
