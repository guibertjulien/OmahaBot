package com.omahaBot.model.draw;

import static com.omahaBot.enums.Rank.ACE;
import static com.omahaBot.enums.Rank.UNKNOWN;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

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

	private Rank rank;// kicker

	private final String drawString;

	// TODO
	private boolean straightFlush;

	/**
	 * Constructor for HandModel / CombinaisonModel
	 * 
	 * @param handCategory
	 * @param drawString
	 * @param permutationHand
	 */
	public StraightModel(HandCategory handCategory, String drawString, SortedSet<CardModel> permutationHand) {
		super(handCategory);

		this.drawString = drawString;

		initHoleCards(permutationHand);

		if (handCategory.equals(HandCategory.STRAIGHT)) {
			char cardrank = drawString.charAt(drawString.length() - 1);
			rank = Rank.fromShortName(cardrank);
		}
		else if (handCategory.equals(HandCategory.STRAIGHT_ACE_LOW)) {
			rank = Rank.FIVE;
		}
	}

	/**
	 * Constructor for BoardModel
	 * 
	 * @param handCategory
	 * @param drawString
	 */
	public StraightModel(HandCategory handCategory, String drawString) {
		super(handCategory);

		this.drawString = drawString;

		initRankAndNuts(drawString);
	}

	// TODO à revoir
	@Override
	public String toString() {
		return this.display(rank + "-high " + handCategory + " [" + drawString + "]; ");
	}

	private void initRankAndNuts(String drawString) {
		assertTrue("drawString != 3", drawString.length() == 3);

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
					rank = rankC;
					rankHole1 = Rank.values()[rank.ordinal() - 4];
					rankHole2 = Rank.values()[rank.ordinal() - 3];
				}
				else {
					rank = rankB;
					rankHole1 = ACE;// ACE LOW
					rankHole2 = Rank.TWO;
				}
				break;
			case KING:// _ABC_
				rank = ACE;
				rankHole1 = Rank.values()[rank.ordinal() - 4];
				rankHole2 = rank;
				break;
			default:// ABC__
				rank = Rank.values()[rankC.ordinal() + 2];
				rankHole1 = Rank.values()[rank.ordinal() - 1];
				rankHole2 = rank;
				break;
			}
		}
		// AB
		else if (rankA.isConnected(rankB)) {
			switch (rankC) {
			case FIVE:// _AB_C
				rank = rankC;
				rankHole1 = ACE;// ACE LOW
				rankHole2 = Rank.values()[rank.ordinal() - 1];
				break;
			case ACE:// _AB_C
				rank = rankC;
				rankHole1 = Rank.values()[rank.ordinal() - 4];
				rankHole2 = Rank.values()[rank.ordinal() - 1];
				break;
			default:
				if (rankB.diff(rankC) == 2) {// AB_C_
					rank = Rank.values()[rankC.ordinal() + 1];
					rankHole1 = Rank.values()[rank.ordinal() - 3];
					rankHole2 = rank;
				}
				else {// AB__C
					rank = rankC;
					rankHole1 = Rank.values()[rank.ordinal() - 2];
					rankHole2 = Rank.values()[rank.ordinal() - 1];
				}
				break;
			}
		}
		// BC
		else if (rankB.isConnected(rankC)) {
			switch (rankC) {
			case FIVE: // ACE LOW
				if (rankA.equals(Rank.ACE)) {// A__BC
					rank = rankC;
					rankHole1 = Rank.values()[rank.ordinal() - 3];
					rankHole2 = Rank.values()[rank.ordinal() - 2];
				}
				else {// _A_BC
					rank = rankC;
					rankHole1 = ACE;
					rankHole2 = Rank.values()[rank.ordinal() - 2];
				}
				break;
			case ACE:// _A_BC
				rank = rankC;
				rankHole1 = Rank.values()[rank.ordinal() - 4];
				rankHole2 = Rank.values()[rank.ordinal() - 2];
				break;
			default:
				if (rankA.diff(rankB) == 2) {// A_BC_
					rank = Rank.values()[rankC.ordinal() + 1];
					rankHole1 = Rank.values()[rank.ordinal() - 3];
					rankHole2 = rank;
				}
				else {// A__BC
					rank = rankC;
					rankHole1 = Rank.values()[rank.ordinal() - 3];
					rankHole2 = Rank.values()[rank.ordinal() - 2];
				}
				break;
			}
		}
		else if (rankA.diff(rankB) == 2 && rankB.diff(rankC) == 2) {// A_B_C
			if (rankA.equals(Rank.ACE)) {// ACE_LOW
				rank = rankC;
				rankHole1 = Rank.values()[rank.ordinal() - 3];
				rankHole2 = Rank.values()[rank.ordinal() - 1];
			} else {
				rank = rankC;
				rankHole1 = Rank.values()[rank.ordinal() - 3];
				rankHole2 = Rank.values()[rank.ordinal() - 1];
			}
		}
		else {
			System.err.println("???");
		}

		CardModel card1 = new CardModel(rankHole1);
		CardModel card2 = new CardModel(rankHole2);

		nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(Arrays.asList(card1, card2)));
	}

	@Override
	public boolean equals(Object obj) {
		StraightModel other = (StraightModel) obj;

		return rank.equals(other.rank);
	}

	@Override
	public boolean isNuts(Object obj) {
		StraightModel other = (StraightModel) obj;

		if (!this.equals(obj)) {
			return false;
		}
		else {// on rank of straight
			return this.rank.equals(other.getRank());
		}
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof StraightModel) {

			StraightModel drawCompare = (StraightModel) o;

			if (this.getRank().ordinal() > drawCompare.getRank().ordinal()) {
				return -1;
			} else if (this.getRank().ordinal() < drawCompare.getRank().ordinal()) {
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
