package com.omahaBot.model.draw;

import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

/**
 * ABSTRACT CLASS
 * @author Julien
 *
 */
public @Data abstract class DrawModel implements Comparable<DrawModel>, DrawAnalyserService {

//	private int nbOut = 0;
//	private double percent = 0.0;
	protected CoupleCards nutsOrHoleCards;
	/** REQUIRED */
	protected final HandCategory handCategory;
	/** OPTIONAL */
	protected BoardCategory boardCategory = BoardCategory.UNDEFINED;
	protected SortedSet<CardModel> permutationHand;

	/**
	 * 
	 * @param handCategory
	 * @param boardCategory
	 * @param permutationHand
	 */
	public DrawModel(HandCategory handCategory) {
		super();
		this.handCategory = handCategory;
	}

	public void initHoleCards(SortedSet<CardModel> permutationHand) {
		this.permutationHand = permutationHand;
		nutsOrHoleCards = new CoupleCards(permutationHand);
	}

	/**
	 * do not modify
	 */
	@Override
	public int compareTo(DrawModel o) {
		// compare drawType
		if (this.handCategory.ordinal() > o.handCategory.ordinal()) {
			return -1;
		}
		else {
			return 1;
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

	protected String display(String msgCategory) {
		StringBuilder display = new StringBuilder();

		display.append(msgCategory);

		if (nutsOrHoleCards != null) {
			display.append(permutationHand != null ? "holeCards=" : "nuts=").append(nutsOrHoleCards.toString()).append("; ");
		}
		if (!(boardCategory == null || boardCategory.equals(BoardCategory.UNDEFINED))) {
			display.append("boardCategory=" + boardCategory);
		}

		return display.toString();
	}
	
//	// =========================================================================
//    // BUILDER
//    // =========================================================================
//		
//	public static class DrawBuilder {
//		private final HandCategory handCategory;
//
//		private CoupleCards nutsOrHoleCards;
//		private SortedSet<CardModel> permutationHand;
// 
//        public DrawBuilder(HandCategory handCategory) {
//            this.handCategory = handCategory;
//        }
// 
//        public DrawBuilder nutsOrHoleCards(CoupleCards nutsOrHoleCards) {
//            this.nutsOrHoleCards = nutsOrHoleCards;
//            return this;
//        }
// 
//        public DrawBuilder permutationHand(SortedSet<CardModel> permutationHand) {
//            this.permutationHand = permutationHand;
//            return this;
//        }
// 
//        public DrawModel build() {
//            return new DrawModel(this);
//        }
//    }
	
}
