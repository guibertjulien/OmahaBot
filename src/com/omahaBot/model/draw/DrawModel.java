package com.omahaBot.model.draw;

import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

public @Data abstract class DrawModel implements Comparable<DrawModel>, DrawAnalyserService {

	private int nbOut = 0;
	private double percent = 0.0;
	protected CoupleCards nutsOrHoleCards;
	protected final HandCategory handCategory;
	protected final SortedSet<CardModel> permutationHand;

	// TODO à revoir
	public BoardCategory boardCategory;

	public DrawModel(HandCategory handCategory, SortedSet<CardModel> permutationHand) {
		super();
		this.handCategory = handCategory;
		this.permutationHand = permutationHand;
	}

	public String displayNutsOrHoleCards() {
		if (nutsOrHoleCards == null) {
			return "";
		}
		return nutsOrHoleCards.toString();
	}

	public void initHoleCards(SortedSet<CardModel> permutationHand) {
		nutsOrHoleCards = new CoupleCards(permutationHand);
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
		if (this.nutsOrHoleCards.getSortedCards().last().getRank().ordinal() > o.nutsOrHoleCards.getSortedCards()
				.last().getRank().ordinal()) {
			return -1;
		}
		else if (this.nutsOrHoleCards.getSortedCards().last().getRank().ordinal() < o.nutsOrHoleCards.getSortedCards()
				.last().getRank().ordinal()) {
			return 1;
		}
		else {
			if (this.nutsOrHoleCards.getSortedCards().first().getRank().ordinal() > o.nutsOrHoleCards.getSortedCards()
					.first().getRank().ordinal()) {
				return -1;
			}
			else if (this.nutsOrHoleCards.getSortedCards().first().getRank().ordinal() < o.nutsOrHoleCards
					.getSortedCards().first().getRank().ordinal()) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}
