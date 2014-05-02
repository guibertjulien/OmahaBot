package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.utils.PermutationsOfN;

public class BoardModel extends CardPackModel {

	private final DealStep dealStep;

	public BoardModel(SortedSet<CardModel> cards, DealStep dealStep) {
		super(cards);
		this.dealStep = dealStep;
		initKickers();
	}

	public BoardModel(String handString, DealStep dealStep) {
		super();

		cards = new TreeSet<CardModel>();

		if (dealStep.ordinal() >= DealStep.FLOP.ordinal()) {
			CardModel card1 = new CardModel(handString.substring(0, 2));
			CardModel card2 = new CardModel(handString.substring(2, 4));
			CardModel card3 = new CardModel(handString.substring(4, 6));
			cards.add(card1);
			cards.add(card2);
			cards.add(card3);
		}

		if (dealStep.ordinal() >= DealStep.TURN.ordinal()) {
			CardModel card4 = new CardModel(handString.substring(6, 8));
			cards.add(card4);
		}
		if (dealStep.equals(DealStep.RIVER)) {
			CardModel card5 = new CardModel(handString.substring(8, 10));
			cards.add(card5);
		}

		this.dealStep = dealStep;
		initKickers();
	}

	@Override
	public String toString() {
		return "Board : " + cards;
	}

	public List<List<CardModel>> permutations() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();

		return permutationsOrdered.processSubsets(listCards, 3);
	}

	/**
	 * 
	 * @return
	 */
	public FullModel searchBestFullDraw() {

		HandCategory handCategory = null;

		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		Rank rankGroup = Rank.UNKNOWN;
		Rank rankThree = Rank.UNKNOWN;
		Rank rankPair = Rank.UNKNOWN;

		String group1 = "";
		String group2 = "";

		if (matcher.find()) {
			group1 = matcher.group(0);
		}
		if (matcher.find()) {
			group2 = matcher.group(0);
		}

		if (group1.length() > 0 && group2.length() > 0) {
			Rank rankGroup1 = Rank.fromShortName(String.valueOf(group1.charAt(0)));
			Rank rankGroup2 = Rank.fromShortName(String.valueOf(group2.charAt(0)));

			// FULL_HOUSE
			if (group1.length() == 6 || group2.length() == 6) {
				handCategory = HandCategory.FULL_HOUSE;

				if (group1.length() == 6) {
					rankThree = rankGroup1;
				}
				else {
					rankThree = rankGroup2;
				}
			}
			// TWO_PAIR
			else {
				handCategory = HandCategory.TWO_PAIR;
				rankGroup = Rank.fromShortName(String.valueOf(group2.charAt(0)));

				rankThree = (kickerPack1.ordinal() > rankGroup2.ordinal()) ? kickerPack1 : rankGroup2;
				rankPair = (rankGroup.ordinal() == kickerPack1.ordinal()) ? rankGroup1 : rankGroup;
			}
		}
		else if (group1.length() > 0) {

			if (group1.length() == 8) {
				// FOUR_OF_A_KIND
				handCategory = HandCategory.FOUR_OF_A_KIND;
				rankGroup = Rank.fromShortName(String.valueOf(group1.charAt(0)));
				rankThree = (kickerPack1.ordinal() > rankGroup.ordinal()) ? kickerPack1 : rankGroup;
				// THREE_OF_A_KIND
			} else if (group1.length() == 6) {
				handCategory = HandCategory.THREE_OF_A_KIND;
				rankGroup = Rank.fromShortName(String.valueOf(group1.charAt(0)));
				rankThree = (kickerPack1.ordinal() > rankGroup.ordinal()) ? kickerPack1 : rankGroup;
				// ONE_PAIR
			} else if (group1.length() == 4) {
				handCategory = HandCategory.ONE_PAIR;
				rankGroup = Rank.fromShortName(String.valueOf(group1.charAt(0)));

				rankThree = kickerPack1;
				rankPair = (rankGroup.ordinal() == kickerPack1.ordinal()) ? kickerPack2 : rankGroup;
			}
		}

		FullModel fullModel = new FullModel(rankThree, rankPair, handCategory, rankGroup, kickerPack1, kickerPack2);

		return fullModel;
	}


	/**
	 * 
	 * @return
	 */
	public ArrayList<QuadsModel> searchQuadsDraw() {

		ArrayList<QuadsModel> listDraw = new ArrayList<>();

		HandCategory handCategory = null;

		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		Rank rankGroup = Rank.UNKNOWN;

		while (matcher.find()) {
			String group = matcher.group(0);

			if (group.length() == 4) {
				// ONE_PAIR
				handCategory = HandCategory.ONE_PAIR;
			} else if (group.length() == 6) {
				// THREE_OF_A_KIND
				handCategory = HandCategory.THREE_OF_A_KIND;
			}

			rankGroup = Rank.fromShortName(String.valueOf(group.charAt(0)));
			QuadsModel quadsModel = new QuadsModel(rankGroup, handCategory);

			listDraw.add(quadsModel);
		}

		return listDraw;
	}

	/**
	 * 
	 * @return
	 */
	public DrawModel searchBestTwoPairDraw() {

		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		Collections.reverse(listCards);

		CardModel topPair1 = listCards.get(0);
		CardModel topPair2 = listCards.get(1);

		TwoPairModel twoPairModel = new TwoPairModel(topPair1.getRank(), topPair2.getRank());

		return twoPairModel;
	}

	/**
	 * 
	 * @return
	 */
	public SetModel searchBestSetDraw() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		Collections.reverse(listCards);

		CardModel topSet = listCards.get(0);

		SetModel setModel = new SetModel(topSet.getRank());

		return setModel;
	}

	/**
	 * TODO : best practices ?
	 */
	public ArrayList<DrawModel> initDraw() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		if (!cards.isEmpty()) {

			if (dealStep.equals(DealStep.FLOP) || dealStep.equals(DealStep.TURN)) {
				listDraw.addAll(searchFlushDraw(2, 4));
			}
			else if (dealStep.equals(DealStep.RIVER)) {
				listDraw.addAll(searchFlushDraw(3, 5));
			}

			FullModel fullModel = searchBestFullDraw();

			if (fullModel != null) {
				listDraw.add(searchBestSetDraw());
				listDraw.add(searchBestTwoPairDraw());
			} else {
				listDraw.add(fullModel);
			}
			
			listDraw.addAll(searchQuadsDraw());
		}

		//Collections.sort(listDraw);

		return listDraw;
	}

	// public boolean isFourOfAKindDraw() {
	// return isThreeOfAKind();
	// }
	//
	// /**
	// * TODO : LEVEL
	// *
	// * @return
	// */
	// public boolean isFullDraw() {
	// return isFourOfAKind()
	// || isTwoPair()
	// || isOnePair();
	//
	// }

	/**
	 * TODO : LEVEL; Kicker; openEnded
	 * 
	 * @return
	 */
	public boolean isStraightDraw() {
		boolean result = false;

		switch (dealStep) {
		case FLOP:
		case TURN:
			result = isNbConnected(2);
			break;
		case RIVER:
			result = isNbConnected(3);
			break;
		default:
			break;
		}

		return result;
	}
}
