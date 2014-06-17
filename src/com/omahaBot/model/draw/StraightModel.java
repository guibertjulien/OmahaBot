package com.omahaBot.model.draw;

import static com.omahaBot.enums.Rank.ACE;
import static com.omahaBot.enums.Rank.UNKNOWN;
import static junit.framework.Assert.assertTrue;

import java.util.SortedSet;

import lombok.Data;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CoupleCards;

/**
 * http://www.moreev.com/learn-poker/articles/88-omaha-for-beginners-the-
 * straight-draw.html
 * http://uk.888poker.com/poker-strategy/omaha/straight-draws-and-wraps
 * 
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

		if (permutationHand != null) {
			initHoleCards(permutationHand);

			if (handCategory.equals(HandCategory.STRAIGHT)) {
				char cardKicker = drawString.charAt(drawString.length() - 1);
				kicker = Rank.fromShortName(cardKicker);
			}
			else if (handCategory.equals(HandCategory.STRAIGHT_ACE_LOW)) {
				kicker = Rank.FIVE;
			}
		}
		else {
			initialize(drawString);
		}
	}

	// TODO à revoir
	@Override
	public String toString() {
		String display = "";

		display += kicker + "-high " + handCategory + " [" + drawString + "]; ";
		display += (permutationHand != null) ? "holeCards" : "nuts";
		display += "=[" + displayNutsOrHoleCards() + "]";

		return display;
	}

	private void initialize(String drawString) {
		assertTrue("drawString != 3", drawString.length() == 3);

		System.out.println("drawString : " + drawString);

		Rank rankA = Rank.fromShortName(drawString.charAt(0));
		Rank rankB = Rank.fromShortName(drawString.charAt(1));
		Rank rankC = Rank.fromShortName(drawString.charAt(2));

		Rank rankHole1 = UNKNOWN;
		Rank rankHole2 = UNKNOWN;

		// ABC
		if (rankA.isConnected(rankB) && rankB.isConnected(rankC)) {
			switch (rankC) {
			case ACE:// __ABC
				if (rankB.equals(Rank.KING)) {// ACE HIGH
					kicker = rankC;
					rankHole1 = Rank.values()[kicker.ordinal() - 4];
					rankHole2 = Rank.values()[kicker.ordinal() - 3];
				}
				else {
					kicker = rankB;
					rankHole1 = ACE;// ACE LOW
					rankHole2 = Rank.TWO;
				}
				break;
			case KING:// _ABC_
				kicker = ACE;
				rankHole1 = Rank.values()[kicker.ordinal() - 4];
				rankHole2 = kicker;
				break;
			default:// ABC__
				kicker = Rank.values()[rankC.ordinal() + 2];
				rankHole1 = Rank.values()[kicker.ordinal() - 1];
				rankHole2 = kicker;
				break;
			}
		}
		// AB
		else if (rankA.isConnected(rankB)) {
			switch (rankC) {
			case FIVE:// _AB_C
				kicker = rankC;
				rankHole1 = ACE;// ACE LOW
				rankHole2 = Rank.values()[kicker.ordinal() - 1];
				break;
			case ACE:// _AB_C
				kicker = rankC;
				rankHole1 = Rank.values()[kicker.ordinal() - 4];
				rankHole2 = Rank.values()[kicker.ordinal() - 1];
				break;
			default :
				if (rankB.diff(rankC) == 2) {// AB_C_
					kicker = Rank.values()[rankC.ordinal() + 1];
					rankHole1 = Rank.values()[kicker.ordinal() - 3];
					rankHole2 = kicker;
				}
				else {// AB__C
					kicker = rankC;
					rankHole1 = Rank.values()[kicker.ordinal() - 2];
					rankHole2 = Rank.values()[kicker.ordinal() - 1];
				}
				break;
			}
		}
		// BC
		else if (rankB.isConnected(rankC)) {
			switch (rankC) {
			case FIVE: // ACE LOW
				if (rankA.equals(Rank.ACE)) {// A__BC
					kicker = rankC;
					rankHole1 = Rank.values()[kicker.ordinal() - 3];
					rankHole2 = Rank.values()[kicker.ordinal() - 2];					
				}
				else {// _A_BC
					kicker = rankC;
					rankHole1 = ACE;
					rankHole2 = Rank.values()[kicker.ordinal() - 2];					
				}
				break;
			case ACE:// _A_BC
				kicker = rankC;
				rankHole1 = Rank.values()[kicker.ordinal() - 4];
				rankHole2 = Rank.values()[kicker.ordinal() - 2];
				break;
			default:
				if (rankA.diff(rankB) == 2) {// A_BC_
					kicker = Rank.values()[rankC.ordinal() + 1];
					rankHole1 = Rank.values()[kicker.ordinal() - 3];
					rankHole2 = kicker;
				}
				else {// A__BC
					kicker = rankC;
					rankHole1 = Rank.values()[kicker.ordinal() - 3];
					rankHole2 = Rank.values()[kicker.ordinal() - 2];
				}
				break;
			}
		}
		else if (rankA.diff(rankB) == 2 && rankB.diff(rankC) == 2) {//A_B_C
			if (rankA.equals(Rank.ACE)) {// ACE_LOW
				kicker = rankC;
				rankHole1 = Rank.values()[kicker.ordinal() - 3];
				rankHole2 = Rank.values()[kicker.ordinal() - 1];					
			} else {
				kicker = rankC;
				rankHole1 = Rank.values()[kicker.ordinal() - 3];
				rankHole2 = Rank.values()[kicker.ordinal() - 1];				
			}
		}
		else {
			System.err.println("???");
		}

		nutsOrHoleCards = new CoupleCards(new CardModel(rankHole1), new CardModel(rankHole2));
	}

	@Override
	public boolean equals(Object obj) {
		StraightModel other = (StraightModel) obj;

		return kicker.equals(other.kicker);
	}

	@Override
	public boolean isNuts(Object obj) {
		StraightModel other = (StraightModel) obj;

		if (!this.equals(obj)) {
			return false;
		}
		else {
			return nutsOrHoleCards.isEqualsKicker(other.nutsOrHoleCards);
		}
	}
}
