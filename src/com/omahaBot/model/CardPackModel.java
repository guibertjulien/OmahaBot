package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.DrawModel.Type;
import com.omahaBot.model.comparator.SuitComparator;
import com.omahaBot.model.handCategory.FlushModel;
import com.omahaBot.model.handCategory.FullModel;
import com.omahaBot.model.handCategory.TopSetModel;
import com.omahaBot.model.handCategory.TopTwoPairModel;

public abstract class CardPackModel {

	protected SortedSet<CardModel> cards;

	protected Rank kickerPack1 = Rank.UNKNOWN;
	protected Rank kickerPack2 = Rank.UNKNOWN;

	public CardPackModel(SortedSet<CardModel> cards) {
		this.cards = cards;
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

	public List<DrawModel> searchFlushDraw(int min, int max) {

		ArrayList<DrawModel> listDraw = new ArrayList<>();

		Type type;

		for (Suit suit : Suit.values()) {
			if (!suit.equals(Suit.UNKNOW)) {

				Pattern pattern = Pattern.compile("(." + suit.getShortName() + "){" + min + "," + max + "}");
				Matcher matcher = pattern.matcher(this.toStringBySuit());

				if (matcher.find()) {
					String group = matcher.group(0);

					if (group.length() == max * 2) {
						type = Type.FLUSH;
					}
					else {
						type = Type.FLUSH_DRAW;
					}

					CardModel cardSuitKicker = new CardModel(group.substring(group.length() - 2));
					FlushModel flushModel = new FlushModel(cardSuitKicker.getRank(), suit);

					DrawModel<FullModel> drawModel = new DrawModel(type, flushModel, group, kickerPack1, kickerPack2);
					listDraw.add(drawModel);
				}
			}
		}

		return listDraw;
	}

	public DrawModel<TopTwoPairModel> searchTopTwoPairDraw() {

		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		Collections.reverse(listCards);

		CardModel topPair1 = listCards.get(0);
		CardModel topPair2 = listCards.get(1);
		String drawString = topPair1.toString().concat(topPair2.toString());

		TopTwoPairModel topTwoPairModel = new TopTwoPairModel(topPair1.getRank(), topPair2.getRank());

		DrawModel<TopTwoPairModel> drawModel = new DrawModel<TopTwoPairModel>(Type.TOP_TWO_PAIR_DRAW, topTwoPairModel,
				drawString, kickerPack1, kickerPack2);

		return drawModel;
	}

	/**
	 * ThreeOfAKind
	 * 
	 * @return
	 */
	public DrawModel<TopSetModel> searchTopSetDraw() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		Collections.reverse(listCards);

		CardModel topSet = listCards.get(0);
		String drawString = topSet.toString();

		TopSetModel topSetModel = new TopSetModel(topSet.getRank());

		DrawModel<TopSetModel> drawModel = new DrawModel<TopSetModel>(Type.TOP_SET_DRAW, topSetModel, drawString,
				kickerPack1, kickerPack2);

		return drawModel;
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
