package com.omahaBot.model;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;

@Data
public class CoupleCards {

	private final CardModel card1;

	private final CardModel card2;

	private SortedSet<CardModel> sortedCards;

	public CoupleCards(CardModel card1, CardModel card2) {
		super();
		this.card1 = card1;
		this.card2 = card2;

		this.sortedCards = new TreeSet<CardModel>();
		this.sortedCards.add(card1);
		this.sortedCards.add(card2);
	}
	
	public CoupleCards(SortedSet<CardModel> sortedCards) {
		super();
		this.card1 = sortedCards.first();
		this.card2 = sortedCards.last();

		this.sortedCards = new TreeSet<CardModel>();
		this.sortedCards.addAll(sortedCards);
	}

	@Override
	public String toString() {
		return String.join(", ", card1.toString(), card2.toString());
	}

	public boolean isEqualsKicker(CoupleCards other) {
		return (this.sortedCards.last().getRank().equals(other.sortedCards.last().getRank()));
	}
	
//	public String displayNuts(HandCategory handCategory) {
//		
//		String display = "";
//		
//		switch (handCategory) {
//		case STRAIGHT_FLUSH:
//		case FLUSH:
//		case FLUSH_DRAW:
//			display = this.toString();
//			break;
//		case FOUR_OF_A_KIND:
//		case FULL_HOUSE:
//		case STRAIGHT:
//		case STRAIGHT_ACE_LOW:
//		case THREE_OF_A_KIND:
//		case TWO_PAIR:
//			display = card1.getRank().getShortName().concat(card2.getRank().getShortName());
//			break;
//
//		default:
//			break;
//		}
//		
//		return display;
//	}
//	
//	public String displayHoleCards() {
//		return this.toString();
//	}
}
