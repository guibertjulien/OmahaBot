package com.omahaBot.model.draw;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.model.CardModel;

public @Data abstract class DrawModel implements Comparable<DrawModel>, DrawAnalyserService {

	private int nbOut = 0;
	private double percent = 0.0;
	protected SortedSet<CardModel> nutsOrHoleCards = new TreeSet<CardModel>();
	protected final HandCategory handCategory;
	protected final SortedSet<CardModel> permutationHand;

	// TODO à revoir
	public HandCategory boardCategory;

	public DrawModel(HandCategory handCategory, SortedSet<CardModel> permutationHand) {
		super();
		this.handCategory = handCategory;
		this.permutationHand = permutationHand;
	}

	public String displayNutsOrHoleCards() {
		if (permutationHand != null) {
			return displayHoleCards();
		}
		else {
			return displayNuts();
		}
	}

	/**
	 * if FLUSH, display AsKs else AK
	 * 
	 * @return
	 */
	private String displayNuts() {
		String display = "";

		if (nutsOrHoleCards != null && !nutsOrHoleCards.isEmpty()) {
			CardModel card1 = nutsOrHoleCards.first();
			CardModel card2 = nutsOrHoleCards.last();

			switch (handCategory) {
			case FLUSH:
			case FLUSH_DRAW:
				display = card1.toString().concat(card2.toString());
				break;
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
			case THREE_OF_A_KIND:
			case TWO_PAIR:
				display = card1.getRank().getShortName().concat(card2.getRank().getShortName());
				break;

			default:
				break;
			}
		}

		return display;
	}

	/**
	 * 
	 * @return
	 */
	private String displayHoleCards() {
		String display = "";

		if (nutsOrHoleCards != null && !nutsOrHoleCards.isEmpty()) {
			CardModel card1 = nutsOrHoleCards.first();
			CardModel card2 = nutsOrHoleCards.last();
			display = card1.toString().concat(card2.toString());
		}

		return display;
	}

	public void initHoleCards(SortedSet<CardModel> permutationHand) {
		nutsOrHoleCards.clear();
		nutsOrHoleCards.addAll(permutationHand);
	}

	@Override
	public int compareTo(DrawModel o) {
		// compare drawType
		if (this.handCategory.ordinal() < o.handCategory.ordinal()) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public int compareHoleCards(DrawModel o) {
		if (this.nutsOrHoleCards.last().getRank().ordinal() > o.nutsOrHoleCards.last().getRank().ordinal()) {
			return -1;
		}
		else if (this.nutsOrHoleCards.last().getRank().ordinal() < o.nutsOrHoleCards.last().getRank().ordinal()) {
			return 1;
		}
		else {
			if (this.nutsOrHoleCards.first().getRank().ordinal() > o.nutsOrHoleCards.first().getRank().ordinal()) {
				return -1;
			}
			else if (this.nutsOrHoleCards.first().getRank().ordinal() < o.nutsOrHoleCards.first().getRank().ordinal()) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}
