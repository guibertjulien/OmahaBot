package com.omahaBot.model.draw;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;
import com.omahaBot.utils.CardUtils;

/**
 * 
 * @author Julien
 * 
 */
public @Data class FullModel extends DrawModel {

	private Rank rankThree;
	private Rank rankPair;

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param rankThree
	 * @param rankPair
	 * @param permutationHand
	 */
	public FullModel(Rank rankThree, Rank rankPair, SortedSet<CardModel> permutationHand) {
		super(HandCategory.FULL_HOUSE);
		this.rankThree = rankThree;
		this.rankPair = rankPair;

		if (CardUtils.coupleIsPair(permutationHand)) {
			boardCategory = BoardCategory.THREE_OF_A_KIND;
		}
		else {
			boardCategory = BoardCategory.ONE_PAIR;
		}

		initHoleCards(permutationHand);
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param rankThree
	 * @param rankPair
	 * @param boardCategory
	 * @param rankGroup
	 * @param kickerPack1
	 * @param kickerPack2
	 */
	public FullModel(Rank rankThree, Rank rankPair, BoardCategory boardCategory, Rank rankGroup, Rank kickerPack1,
			Rank kickerPack2) {
		super(HandCategory.FULL_HOUSE);
		this.rankThree = rankThree;
		this.rankPair = rankPair;
		this.boardCategory = boardCategory;

		initRanksAndNuts(boardCategory, rankGroup, kickerPack1, kickerPack2);
	}

	@Override
	public String toString() {
		return this.display(handCategory + " " + rankThree + " full of " + rankPair + "; ");
	}

	/**
	 * init rankThree, rankPair and nuts
	 * 
	 * @param boardCategory
	 * @param rankGroup
	 * @param kickerPack1
	 * @param kickerPack2
	 */
	private void initRanksAndNuts(BoardCategory boardCategory, Rank rankGroup, Rank kickerPack1, Rank kickerPack2) {
		CardModel card1 = null, card2 = null;

		switch (boardCategory) {

		case ONE_PAIR:
		case TWO_PAIR:
			card1 = new CardModel(rankThree, Suit.SPADE);

			if (rankGroup.equals(kickerPack1)) {
				if (rankPair.equals(kickerPack2)) {
					card2 = new CardModel(rankPair, Suit.HEART);
				}
				else {
					card2 = new CardModel(kickerPack2, Suit.HEART);
				}
			}
			else {
				card2 = new CardModel(rankThree, Suit.HEART);
			}

			break;
		case THREE_OF_A_KIND:
			if (rankGroup.equals(kickerPack1)) {
				if (kickerPack1.equals(Rank.ACE)) {
					// ex : AAA5 --> AAA_KK
					card1 = new CardModel("Ks");
					card2 = new CardModel("Kh");
					rankPair = Rank.KING;
				}
				else {
					// ex : QQQ5 --> QQQ_AA
					card1 = new CardModel("As");
					card2 = new CardModel("Ah");
					rankPair = Rank.ACE;
				}
			}
			else {
				// ex : 222K --> 22K_KK
				card1 = new CardModel(kickerPack1, Suit.SPADE);
				card2 = new CardModel(kickerPack1, Suit.HEART);
				rankPair = rankGroup;
			}
			break;
		case FOUR_OF_A_KIND:
			if (rankGroup.equals(Rank.ACE)) {
				// ex : AAAA2 --> AAA_KK
				card1 = new CardModel("Ks");
				card2 = new CardModel("Kh");
			}
			else {
				if (rankGroup.equals(kickerPack1)) {
					// ex : QQQQ3 --> QQQ_AA
					card1 = new CardModel("As");
					card2 = new CardModel("Ah");
				}
				else {
					// ex : 2222K --> 22K_KK
					card1 = new CardModel(kickerPack1, Suit.SPADE);
					card2 = new CardModel(kickerPack1, Suit.HEART);
				}
			}

			if (rankGroup.equals(kickerPack1)) {
				rankPair = card1.getRank();
			}
			else {
				rankPair = rankGroup;
			}

			break;
		case FULL_HOUSE:
			if (kickerPack1.ordinal() == kickerPack2.ordinal() + 1) {
				// AAAKK --> AAA_QQ
				rankPair = Rank.values()[kickerPack2.ordinal() - 1];
			}
			else {
				if (rankPair.equals(Rank.ACE)) {
					// TTTAA --> TTT_KK
					rankPair = Rank.values()[kickerPack1.ordinal() - 1];
				} else {
					// TTT22 --> TTT_AA
					rankPair = Rank.ACE;
				}
			}

			card1 = new CardModel(rankPair, Suit.SPADE);
			card2 = new CardModel(rankPair, Suit.HEART);
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
		if (obj instanceof FullModel) {
			FullModel other = (FullModel) obj;

			if (rankPair != other.rankPair)
				return false;
			if (rankThree != other.rankThree)
				return false;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof FullModel) {

			FullModel drawCompare = (FullModel) o;

			// compare rankThree next rankPair
			if (this.rankThree.ordinal() > drawCompare.rankThree.ordinal()) {
				return -1;
			}
			else if (this.rankThree.ordinal() < drawCompare.rankThree.ordinal()) {
				return 1;
			} else {
				if (this.rankPair.ordinal() > drawCompare.rankPair.ordinal()) {
					return -1;
				}
				else if (this.rankPair.ordinal() < drawCompare.rankPair.ordinal()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
		else {
			return super.compareTo(o);
		}
	}
	
	@Override
	public boolean isNuts(Object obj) {
		FullModel other = (FullModel) obj;

		if (!nutsOrHoleCards.getSortedCards().first().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().first().getRank()))
			return false;
		if (!nutsOrHoleCards.getSortedCards().last().getRank()
				.equals(other.nutsOrHoleCards.getSortedCards().last().getRank()))
			return false;
		return true;
	}
}
