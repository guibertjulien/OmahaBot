package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.Suit;
import com.omahaBot.utils.PermutationsOfN;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * 
 * @author Julien
 * 
 */
public class HandModel extends CardPackModel {

	public HandModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HandModel(SortedSet<CardModel> cards) {
		super(cards);
		// TODO Auto-generated constructor stub
	}

	public HandModel(String cardPackString) {
		super(cardPackString);
		// TODO Auto-generated constructor stub
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

	public boolean hasFlushDraw() {
		String whithoutRank = this.toStringBySuit().replaceAll("[^shdc]", "");
		return !Suit.ALL_SUIT.equals(whithoutRank);
	}
}
