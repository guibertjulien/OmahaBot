package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.utils.PermutationsOfN;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * 
 * @author Julien
 * 
 */
public class HandModel extends CardPack {

	public HandModel(SortedSet<CardModel> cards) {
		super(cards);
	}

	public HandModel(String handString) {
		super();

		cards = new TreeSet<CardModel>();

		CardModel card1 = new CardModel(handString.substring(0, 2));
		CardModel card2 = new CardModel(handString.substring(2, 4));
		CardModel card3 = new CardModel(handString.substring(4, 6));
		CardModel card4 = new CardModel(handString.substring(6, 8));

		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
	}

	@Override
	public String toString() {
		return "HandModel [Cards=" + cards + "]";
	}

	public boolean isTwoPairSuited()
	{
		return isTwoPair() && isTwoSuit();
	}

	public boolean isTwoPairConnected()
	{
		// TODO
		return false;
	}

	public boolean isTwoPairSuitedConnector()
	{
		// TODO
		return false;
	}

	public List<List<CardModel>> permutations() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();

		return permutationsOrdered.processSubsets(listCards, 2);
	}
}
