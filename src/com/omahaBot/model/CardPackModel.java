package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.comparator.SuitComparator;
import com.omahaBot.model.draw.FlushModel;

public class CardPackModel {

	protected SortedSet<CardModel> cards;

	protected Rank kickerPack1 = Rank.UNKNOWN;
	protected Rank kickerPack2 = Rank.UNKNOWN;

	public CardPackModel(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	public CardPackModel(String cardPackString) {
		super();

		cards = new TreeSet<CardModel>();

		while (cardPackString.length() > 0) {
			cards.add(new CardModel(cardPackString.substring(0, 2)));
			cardPackString = cardPackString.substring(2);
		}
	}

	public CardPackModel() {
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

	public String toStringByRank() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		String result = "";

		for (CardModel cardModel : listCards) {
			result += cardModel;
		}

		return result;
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
		Rank rankPrec = Rank.UNKNOWN;

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

	public ArrayList<FlushModel> searchFlushDraw(int min, int max, SortedSet<CardModel> permutationHand) {

		ArrayList<FlushModel> listDraw = new ArrayList<>();

		String whithoutRank = this.toStringBySuit().replaceAll("[^shdc]", ".");

		Pattern pattern = Pattern.compile("(.\\w)\\1{" + (min - 1) + "," + (max - 1) + "}");
		Matcher matcher = pattern.matcher(whithoutRank);

		HandCategory handCategory = HandCategory.FLUSH;

		while (matcher.find()) {
			String group = matcher.group(0);
			String drawString = this.toStringBySuit().substring(matcher.start(), matcher.end());

			if ((permutationHand != null && group.length() == 8) 
					|| (permutationHand == null && group.length() == 4)) {
				handCategory = HandCategory.FLUSH_DRAW;
			}

			Suit suit = Suit.fromShortName(group.substring(1, 2));

			FlushModel flushModel = new FlushModel(handCategory, suit, drawString, permutationHand);

			listDraw.add(flushModel);
		}

		return listDraw;
	}

	protected void initKickers() {
		if (cards != null && !cards.isEmpty()) {
			ArrayList<CardModel> listCards = new ArrayList<>(cards);
			Collections.reverse(listCards);

			kickerPack1 = listCards.get(0).getRank();

			Rank rank = kickerPack1;

			int i = 0;
			while (kickerPack1.equals(rank) && i < listCards.size() - 1) {
				rank = listCards.get(++i).getRank();
			}

			kickerPack2 = listCards.get(i).getRank();
		}
	}
}
