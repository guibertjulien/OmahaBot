package com.omahaBot.model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.enums.Rank;

/**
 * @author Julien
 * 
 */
public class CombinaisonModel extends CardPack implements Comparable<CombinaisonModel> {

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

	/**
	 * TODO : best practices ?
	 */
	private void initHandPowerType() {
		if (isStraightFlush()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT_FLUSH;
		} else if (isFourOfAKind()) {
			postFlopPowerType = PostFlopPowerType.FOUR_OF_A_KIND;
		} else if (isFull()) {
			postFlopPowerType = PostFlopPowerType.FULL_HOUSE;
		} else if (isFlush()) {
			postFlopPowerType = PostFlopPowerType.FLUSH;
		} else if (isStraight()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT;
		} else if (isThreeOfAKind()) {
			postFlopPowerType = PostFlopPowerType.THREE_OF_A_KIND;
		} else if (isTwoPair()) {
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
}
