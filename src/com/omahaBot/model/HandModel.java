package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.model.comparator.SuitComparator;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * 
 * @author Julien
 * 
 */
public class HandModel {

	private SortedSet<CardModel> cards;

	public HandModel(SortedSet<CardModel> cards) {
		super();
		this.cards = cards;
	}

	public HandModel(String handString) {
		super();
		CardModel card1 = new CardModel(handString.substring(0, 2));
		CardModel card2 = new CardModel(handString.substring(2, 4));
		CardModel card3 = new CardModel(handString.substring(4, 6));
		CardModel card4 = new CardModel(handString.substring(6, 8));

		SortedSet<CardModel> cards = new TreeSet<CardModel>();

		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);

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

	/**
	 * ex : 2sJdKcAc --> sdcc
	 * 
	 * @return
	 */
	public String handSuit() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();

		Collections.sort(listCards, suitComparator);
		
		String handSuit = "";

		for (CardModel cardModel : listCards) {
			handSuit += cardModel.getSuit().getShortName();
		}

		return handSuit;
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

	public boolean isThreeOfKind() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(1).getRank() == listCards.get(2).getRank())
				|| (listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(2).getRank() == listCards.get(3).getRank());
	}

	public boolean isFourOfKind() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(2).getRank() == listCards.get(3).getRank());
	}

	public boolean isSameColor() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getSuit() == listCards.get(1).getSuit()
				&& listCards.get(1).getSuit() == listCards.get(2).getSuit()
				&& listCards.get(2).getSuit() == listCards.get(3).getSuit());
	}
}
