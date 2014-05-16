package com.omahaBot.model.draw;

import java.util.Arrays;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

/**
 * 
 * @author Julien
 * 
 */
public @Data
class FullModel extends DrawModel {

	private Rank rankThree;
	private Rank rankPair;

	public FullModel(Rank rankThree, Rank rankPair, HandCategory handCategory, Rank rankGroup, Rank kickerPack1,
			Rank kickerPack2, boolean isDraw) {
		super(DrawType.BEST_FULL_DRAW, isDraw);
		this.rankThree = rankThree;
		this.rankPair = rankPair;

		if (isDraw) {
			initialize(handCategory, rankGroup, kickerPack1, kickerPack2);
		}
	}

	// public FullModel(Builder builder) {
	// super(DrawType.BEST_FULL_DRAW, builder.isDraw);
	// this.rankThree = builder.rankThree;
	// this.rankPair = builder.rankPair;
	// this.nuts = builder.nuts;
	// this.holeCards = builder.holeCards;
	// }

	@Override
	public String toString() {
		String display = "";

		display = "Full house, " + rankThree + " full of " + rankPair + "; ";
		display += isDraw ? "nuts" : "holeCards";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize(HandCategory handCategory, Rank rankGroup, Rank kickerPack1, Rank kickerPack2) {
		CardModel card1 = null;
		CardModel card2 = null;

		switch (handCategory) {
		case ONE_PAIR:
		case TWO_PAIR:
			card1 = new CardModel(rankThree, Suit.SPADE);

			if (rankGroup.equals(kickerPack1)) {
				card2 = new CardModel(rankPair, Suit.HEART);
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
			nutsOrHoleCards.addAll(Arrays.asList(card1, card2));
		}
	}

	@Override
	public boolean equals(Object obj) {
		// if (this == obj)
		// return true;
		// if (!super.equals(obj))
		// return false;
		// if (getClass() != obj.getClass())
		// return false;
		FullModel other = (FullModel) obj;
		if (rankPair != other.rankPair)
			return false;
		if (rankThree != other.rankThree)
			return false;
		return true;
	}

	@Override
	public boolean isNuts(Object obj) {
		FullModel other = (FullModel) obj;

		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.first().getRank().equals(other.nutsOrHoleCards.first().getRank()))
			return false;
		if (!nutsOrHoleCards.last().getRank().equals(other.nutsOrHoleCards.last().getRank()))
			return false;
		return true;
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

	// /**
	// *
	// * @author Julien
	// *
	// */
	// public static class Builder {
	//
	// private final Rank rankThree;
	// private final Rank rankPair;
	// private final boolean isDraw;
	// private SortedSet<CardModel> nuts = new TreeSet<CardModel>();
	// private SortedSet<CardModel> holeCards = new TreeSet<CardModel>();
	//
	// public Builder(Rank rankThree, Rank rankPair, boolean isDraw) {
	// this.rankThree = rankThree;
	// this.rankPair = rankPair;
	// this.isDraw = isDraw;
	// }
	//
	// // builder methods for setting property
	// public Builder nuts(SortedSet<CardModel> nuts) {
	// this.nuts = nuts;
	// return this;
	// }
	//
	// public Builder holeCards(SortedSet<CardModel> holeCards) {
	// this.holeCards = holeCards;
	// return this;
	// }
	//
	// // return fully build object
	// public FullModel build() {
	// return new FullModel(this);
	// }
	// }

}
