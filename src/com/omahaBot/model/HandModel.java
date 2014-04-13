package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;

import com.omahaBot.enums.PowerHand;
import com.omahaBot.model.comparator.SuitComparator;

public class HandModel {

	private SortedSet<CardModel> cards;

	public HandModel(SortedSet<CardModel> cards) {
		super();
		this.cards = cards;
	}

	public SortedSet<CardModel> getCards() {
		return cards;
	}

	public void setCards(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	/**
	 * ex : 2sJdKcAc --> 2JKA
	 * 
	 * @return
	 */
	public String handRank() {

		String handRank = "";

		for (CardModel cardModel : this.cards) {
			handRank += cardModel.getRank().getShortName();
		}

		return handRank;
	}

	@Override
	public String toString() {
		return "HandModel [Cards=" + cards + "]";
	}

	public boolean isTwoPair()
	{
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
		&& listCards.get(2).getRank() == listCards.get(3).getRank());
	}

	public boolean isOnePair()
	{
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				|| listCards.get(1).getRank() == listCards.get(2).getRank()
				|| listCards.get(2).getRank() == listCards.get(3).getRank());
	}

	public boolean isTwoSuit()
	{
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();

		Collections.sort(listCards, suitComparator);

		return (listCards.get(0).getSuit() == listCards.get(1).getSuit()
		&& listCards.get(2).getSuit() == listCards.get(3).getSuit());
	}

	public boolean isTwoPairSuited()
	{
		return isTwoPair() && isTwoSuit();
	}

	public boolean isTwoPairConnected()
	{
		return false;
	}

	public boolean isTwoPairSuitedConnector()
	{
		return false;
	}
}
