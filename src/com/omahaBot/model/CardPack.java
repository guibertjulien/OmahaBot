package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.comparator.SuitComparator;

public abstract class CardPack {

	protected SortedSet<CardModel> cards;

	public CardPack(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	public CardPack() {
	}

	/**
	 * ex : 2sJdKcAc --> 2JKA
	 * 
	 * @return
	 */
	public String toRankString() {

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
	public String toSuitString() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();

		Collections.sort(listCards, suitComparator);

		String handSuit = "";

		for (CardModel cardModel : listCards) {
			handSuit += cardModel.getSuit().getShortName();
		}

		return handSuit;
	}

	public String toStringBySuit() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();
		Collections.sort(listCards, suitComparator);

		String result = "";

		for (CardModel cardModel : listCards) {
			result += cardModel;
		}

		return result;
	}

	public SortedSet<CardModel> getCards() {
		return cards;
	}

	public void setCards(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	public boolean isOnePair() {
		return isNbPair(1);
	}

	public boolean isTwoPair() {
		return isNbPair(2);
	}

	public boolean isThreeOfAKind() {
		return !isNbPair(2) && isThreeSameCardRank();
	}

	public boolean isFull() {
		return isNbPair(2) && isThreeSameCardRank();
	}

	public boolean isOneSuit() {
		return isNbSuit(1);
	}

	public boolean isTwoSuit() {
		return isNbSuit(2);
	}

	public boolean isFlush() {
		return isNbSameCardSuit(5);
	}

	private boolean isThreeSameCardRank() {
		return isNbSameCardRank(3);
	}

	public boolean isFourOfAKind() {
		return isNbSameCardRank(4);
	}

	public boolean isStraight() {
		return isNbConnected(5);
	}

	public boolean isStraightFlush() {
		return isStraight() && isFlush();
	}

	private boolean isNbPair(int nbPairShould) {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		int nbPair = 0;
		CardModel cardPrec = null;
		Rank rankPrec = Rank.UNKNOW;

		for (int i = 0; i < listCards.size() && nbPair < nbPairShould; i++) {
			CardModel card = listCards.get(i);
			if (i > 0) {
				if (cardPrec.getRank() == card.getRank() && !rankPrec.equals(card.getRank())) {
					rankPrec = card.getRank();
					nbPair++;
				}
			}
			cardPrec = card;
		}

		return (nbPair == nbPairShould);
	}

	private boolean isNbSuit(int nbSuitSould) {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();
		Collections.sort(listCards, suitComparator);

		int nbSuit = 0;
		CardModel cardPrec = null;
		Suit suitPrec = Suit.UNKNOW;

		for (int i = 0; i < listCards.size() && nbSuit < nbSuitSould; i++) {
			CardModel card = listCards.get(i);
			if (i > 0) {
				if (cardPrec.getSuit() == card.getSuit() && !suitPrec.equals(card.getSuit())) {
					suitPrec = card.getSuit();
					nbSuit++;
				}
			}
			cardPrec = card;
		}

		return (nbSuit == nbSuitSould);
	}

	public boolean isNbConnected(int nbConnectedShould) {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		if (listCards.size() > nbConnectedShould - 1) {

			int nbConnected = 1;
			CardModel cardPrec = null;

			for (int i = 0; i < listCards.size() && nbConnected < nbConnectedShould; i++) {
				CardModel card = listCards.get(i);
				if (i > 0) {
					if ((cardPrec.getRank().ordinal() == card.getRank().ordinal() - 1)
							|| ((cardPrec.getRank().ordinal() == nbConnectedShould - 2) && card.getRank().equals(
									Rank.ACE))) {
						nbConnected++;
					}
					else {
						nbConnected = 1;
					}
				}
				cardPrec = card;
			}

			return (nbConnected == nbConnectedShould);
		}
		else {
			return false;
		}
	}

	public boolean isNbSameCardRank(int nbSameShould) {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		if (listCards.size() > nbSameShould - 1) {

			int nbSame = 1;
			CardModel cardPrec = null;

			for (int i = 0; i < listCards.size() && nbSame < nbSameShould; i++) {
				CardModel card = listCards.get(i);
				if (i > 0) {
					if (cardPrec.getRank() == card.getRank()) {
						nbSame++;
					}
					else {
						nbSame = 1;
					}
				}
				cardPrec = card;
			}

			return (nbSame == nbSameShould);
		}
		else {
			return false;
		}
	}

	public boolean isNbSameCardSuit(int nbSameShould) {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		SuitComparator suitComparator = new SuitComparator();
		Collections.sort(listCards, suitComparator);

		if (listCards.size() > nbSameShould - 1) {

			int nbSame = 1;
			CardModel cardPrec = null;

			for (int i = 0; i < listCards.size() && nbSame < nbSameShould; i++) {
				CardModel card = listCards.get(i);
				if (i > 0) {
					if (cardPrec.getSuit() == card.getSuit()) {
						nbSame++;
					}
					else {
						nbSame = 1;
					}
				}
				cardPrec = card;
			}

			return (nbSame == nbSameShould);
		}
		else {
			return false;
		}
	}

	public FlushDraw searchFlush() {
		boolean find = false;

		FlushDraw flushDraw = null;
		Suit suitFinded = Suit.UNKNOW;
		Rank kicker = Rank.UNKNOW;
		int nbSuitedCard = 0;

		for (Suit suit : Suit.values()) {
			if (!suit.equals(Suit.UNKNOW)) {
				Pattern pattern = Pattern.compile("(." + suit.getShortName() + "){3," + cards.size() + "}");
				Matcher matcher = pattern.matcher(this.toStringBySuit());

				if (matcher.find()) {
					String group = matcher.group(0);
					suitFinded = suit;
					kicker = Rank.fromShortName(String.valueOf(group.charAt(group.length() - 2)));
					nbSuitedCard = group.length() / 2;
					find = true;
					break;
				}
			}
		}

		if (find) {
			flushDraw = new FlushDraw(kicker, suitFinded, nbSuitedCard);
		}

		return flushDraw;
	}
}
