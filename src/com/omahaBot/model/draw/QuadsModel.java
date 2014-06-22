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

public @Data class QuadsModel extends DrawModel {

	private final Rank rank;

	public QuadsModel(Rank rank, BoardCategory boardCategory, SortedSet<CardModel> permutationHand) {
		super(HandCategory.FOUR_OF_A_KIND, permutationHand);
		this.rank = rank;

		this.boardCategory = boardCategory;
		
		initialize(boardCategory);

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}
	}

	@Override
	public String toString() {
		String display = "";

		display += handCategory + " " + rank + "; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]; ";
		display += "boardCategory : " + boardCategory;

		return display;
	}

	private void initialize(BoardCategory boardCategory) {
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
			nutsOrHoleCards =  new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
		}
	}

	@Override
	public boolean isNuts(Object obj) {
		QuadsModel other = (QuadsModel) obj;

		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.getSortedCards().first().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().first().getRank()))
			return false;
		if (!nutsOrHoleCards.getSortedCards().last().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().last().getRank()))
			return false;
		return true;
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
}