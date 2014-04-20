package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.enums.Rank;

/**
 * @author Julien
 * 
 */
public class CombinaisonModel implements Comparable<CombinaisonModel> {

	private SortedSet<CardModel> cards;

	private Rank kicker;

	private PostFlopPowerType postFlopPowerType;

	public CombinaisonModel(List<CardModel> permutationHand, List<CardModel> permutationBoard) {
		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);

		initHandPowerType();
	}

	public SortedSet<CardModel> getCards() {
		return cards;
	}

	public void setCards(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	public boolean isOnePair() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				|| listCards.get(1).getRank() == listCards.get(2).getRank()
				|| listCards.get(2).getRank() == listCards.get(3).getRank()
				|| listCards.get(3).getRank() == listCards.get(4).getRank());
	}

	public boolean isTwoPairs() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(2).getRank() == listCards.get(3).getRank())
				|| (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(3).getRank() == listCards.get(4).getRank())
				|| (listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(3).getRank() == listCards.get(4).getRank());
	}

	public boolean isThreeOfAKind() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(1).getRank() == listCards.get(2).getRank())
				|| (listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(2).getRank() == listCards.get(3).getRank())
				|| (listCards.get(2).getRank() == listCards.get(3).getRank()
				&& listCards.get(3).getRank() == listCards.get(4).getRank());
	}

	public boolean isStraight() {
		CardModel cardPrev = null;

		for (CardModel card : cards) {
			if (cardPrev == null) {
				cardPrev = card;
			} else if (cardPrev.getRank().ordinal() != card.getRank().ordinal() + 1) {
				return false;
			}
		}

		return true;
	}

	public boolean isFlush() {
		CardModel cardPrev = null;

		for (CardModel card : cards) {
			if (cardPrev == null) {
				cardPrev = card;
			} else if (!cardPrev.getSuit().equals(card.getSuit())) {
				return false;
			}
		}

		return true;
	}

	public boolean isFullHouse() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(3).getRank() == listCards.get(4).getRank())
				|| (listCards.get(0).getRank() == listCards.get(1).getRank()
						&& listCards.get(2).getRank() == listCards.get(3).getRank()
						&& listCards.get(3).getRank() == listCards.get(4).getRank());
	}

	public boolean isFourOfAKind() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);

		return (listCards.get(0).getRank() == listCards.get(1).getRank()
				&& listCards.get(1).getRank() == listCards.get(2).getRank()
				&& listCards.get(2).getRank() == listCards.get(3).getRank())
				|| (listCards.get(1).getRank() == listCards.get(2).getRank()
						&& listCards.get(2).getRank() == listCards.get(3).getRank()
						&& listCards.get(3).getRank() == listCards.get(4).getRank());
	}

	public boolean isStraightFlush() {
		return isStraight() && isFlush();
	}

	/**
	 * TODO : best practices ?
	 */
	private void initHandPowerType() {
		if (isStraightFlush()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT_FLUSH;
		} else if (isFourOfAKind()) {
			postFlopPowerType = PostFlopPowerType.FOUR_OF_A_KIND;
		} else if (isFullHouse()) {
			postFlopPowerType = PostFlopPowerType.FULL_HOUSE;
		} else if (isFlush()) {
			postFlopPowerType = PostFlopPowerType.FLUSH;
		} else if (isStraight()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT;
		} else if (isThreeOfAKind()) {
			postFlopPowerType = PostFlopPowerType.THREE_OF_A_KIND;
		} else if (isTwoPairs()) {
			postFlopPowerType = PostFlopPowerType.TWO_PAIR;
		} else if (isOnePair()) {
			postFlopPowerType = PostFlopPowerType.ONE_PAIR;
		} else {
			postFlopPowerType = PostFlopPowerType.HIGH_CARD;
		}
	}

	public Rank getKicker() {
		return kicker;
	}

	public void setKicker(Rank kicker) {
		this.kicker = kicker;
	}

	public PostFlopPowerType getHandPowerType() {
		return postFlopPowerType;
	}

	public void setHandPowerType(PostFlopPowerType postFlopPowerType) {
		this.postFlopPowerType = postFlopPowerType;
	}

	@Override
	public int compareTo(CombinaisonModel o) {
		if (this.getHandPowerType().ordinal() < o.getHandPowerType().ordinal()) {
			return 1;
		}
		else if (this.getHandPowerType().ordinal() > o.getHandPowerType().ordinal()) {
			return -1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "CombinaisonModel [cards=" + cards + ", postFlopPowerType=" + postFlopPowerType + "]";
	}

	// @Override
	// public int compareTo(CardModel o) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public int compare(CombinaisonModel o1, CombinaisonModel o2) {
	// if (o1.getHandPowerType().ordinal() > o2.getHandPowerType().ordinal())
	// return 1;
	// if (o1.getHandPowerType().ordinal() < o2.getHandPowerType().ordinal())
	// return -1;
	// else
	// return 0;
	// }

}
