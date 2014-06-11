package com.omahaBot.model.draw;

import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

/**
 * http://www.moreev.com/learn-poker/articles/88-omaha-for-beginners-the-straight-draw.html
 * http://uk.888poker.com/poker-strategy/omaha/straight-draws-and-wraps
 * @author Julien
 *
 */
public @Data class StraightModel extends DrawModel {

	private Rank kicker;

	private String drawString;

	private boolean straightFlush;

	public StraightModel(HandCategory handCategory, String drawString, SortedSet<CardModel> permutationHand) {
		super(handCategory, permutationHand);

		this.drawString = drawString;

		initialize(drawString);

		if (permutationHand != null) {
			initHoleCards(permutationHand);
		}
	}

	@Override
	public String toString() {
		String display = "";

		display += kicker + "-high " + handCategory + " [" + drawString + "]; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize(String drawString) {
		Rank rankHole1 = null;
		Rank rankHole2 = null;

		// cas aux limites
		if (drawString.equals("JQK")) {
			rankHole1 = Rank.TEN;
			rankHole2 = Rank.ACE;
			kicker = Rank.ACE;
		}
		// cas aux limites
		else if (drawString.equals("QKA")) {
			rankHole1 = Rank.TEN;
			rankHole2 = Rank.JACK;
			kicker = Rank.ACE;
		// autres cas
		} else {
			Rank rank1 = Rank.fromShortName(drawString.charAt(0));
			Rank rank2 = Rank.fromShortName(drawString.charAt(1));
			Rank rank3 = Rank.fromShortName(drawString.charAt(2));

			if (rank2.ordinal() - rank1.ordinal() == 3) {
				rankHole1 = Rank.values()[rank1.ordinal() + 1];
				rankHole2 = Rank.values()[rank1.ordinal() + 2];
				kicker = rank3;
			} else if (rank2.ordinal() - rank1.ordinal() == 2) {
				rankHole1 = Rank.values()[rank1.ordinal() + 1];

				if (rank3.ordinal() - rank2.ordinal() == 2) {
					rankHole2 = Rank.values()[rank2.ordinal() + 1];
					kicker = rank3;
				}
				else {// rank3.ordinal() - rank2.ordinal() == 1
					rankHole2 = Rank.values()[rank3.ordinal() + 1];
					kicker = rankHole2;
				}
			} else {// rank2.ordinal() - rank1.ordinal() == 1

				if (rank3.ordinal() - rank2.ordinal() == 3) {
					rankHole1 = Rank.values()[rank2.ordinal() + 1];
					rankHole2 = Rank.values()[rank2.ordinal() + 2];
					kicker = rank3;
				} else if (rank3.ordinal() - rank2.ordinal() == 2) {
					rankHole1 = Rank.values()[rank2.ordinal() + 1];
					rankHole2 = Rank.values()[rank3.ordinal() + 1];
					kicker = rankHole2;
				}
				else {// rank3.ordinal() - rank2.ordinal() == 1
					rankHole1 = Rank.values()[rank3.ordinal() + 1];
					rankHole2 = Rank.values()[rank3.ordinal() + 2];
					kicker = rankHole2;
				}
			}
		}

		nutsOrHoleCards.add(new CardModel(rankHole1, Suit.UNKNOW));
		nutsOrHoleCards.add(new CardModel(rankHole2, Suit.UNKNOW));
	}

	@Override
	public boolean equals(Object obj) {
		StraightModel other = (StraightModel) obj;

		return kicker.equals(other.kicker);
	}

	@Override
	public boolean isNuts(Object obj) {
		StraightModel other = (StraightModel) obj;

		if (!this.equals(obj))
			return false;
		if (!nutsOrHoleCards.last().getRank().equals(other.nutsOrHoleCards.last().getRank()))
			return false;
		return true;
	}
}
