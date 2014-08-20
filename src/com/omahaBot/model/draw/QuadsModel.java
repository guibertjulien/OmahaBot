package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;
import com.omahaBot.utils.CardUtils;

@Data 
public class QuadsModel extends DrawModel {

	private final Rank rank;

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param rank
	 * @param permutationHand
	 */
	public QuadsModel(Rank rank, SortedSet<CardModel> permutationHand) {
		super(HandCategory.FOUR_OF_A_KIND);

		this.rank = rank;

		if (CardUtils.coupleIsPair(permutationHand)) {
			boardCategory = BoardCategory.ONE_PAIR;
		}
		else {
			boardCategory = BoardCategory.THREE_OF_A_KIND;
		}

		initHoleCards(permutationHand);
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param rank
	 * @param boardCategory
	 */
	public QuadsModel(Rank rank, BoardCategory boardCategory) {
		super(HandCategory.FOUR_OF_A_KIND);

		this.rank = rank;
		this.boardCategory = boardCategory;

		initNuts(boardCategory);
	}

	@Override
	public String toString() {
		return this.display(handCategory + " " + rank + "; ");
	}

	private void initNuts(BoardCategory boardCategory) {
		CardModel card1 = null;
		CardModel card2 = null;

		switch (boardCategory) {
		case ONE_PAIR:
			card1 = new CardModel(rank);
			card2 = new CardModel(rank);
			break;
		case THREE_OF_A_KIND:
			card1 = new CardModel(rank);
			card2 = new CardModel(Rank.UNKNOWN);
			break;
		default:
			// TODO exception
			break;
		}

		if (card1 != null && card2 != null) {
			nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuadsModel) {
			QuadsModel other = (QuadsModel) obj;

			if (rank != other.rank)
				return false;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof QuadsModel) {
			QuadsModel drawCompare = (QuadsModel) o;
			
			// compare rank
			if (this.rank.ordinal() > drawCompare.rank.ordinal()) {
				return -1;
			}
			else if (this.rank.ordinal() < drawCompare.rank.ordinal()) {
				return 1;
			} else {
				return 0;
			}
		}
		else {
			return super.compareTo(o);
		}
	}

	@Override
	public boolean isNuts(Object obj) {
		QuadsModel other = (QuadsModel) obj;
		
		if (!(nutsOrHoleCards.getSortedCards().first().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().first().getRank())
				|| other.nutsOrHoleCards.getSortedCards().first().getRank().equals(Rank.UNKNOWN)))
			return false;
		if (!(nutsOrHoleCards.getSortedCards().last().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().last().getRank())
				|| other.nutsOrHoleCards.getSortedCards().last().getRank().equals(Rank.UNKNOWN)))
			return false;
		return true;
	}
}