package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.DrawModel.Type;
import com.omahaBot.model.handCategory.FullModel;
import com.omahaBot.model.handCategory.QuadsModel;
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
		return "HandModel [Cards=" + cards + "]";
	}

	public List<List<CardModel>> permutations() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();

		return permutationsOrdered.processSubsets(listCards, 3);
	}

	/**
	 * TODO : best practices ?
	 */
	public ArrayList<DrawModel> initDraw() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		if (!cards.isEmpty()) {

			if (dealStep.equals(DealStep.FLOP) || dealStep.equals(DealStep.TURN)) {
				listDraw.addAll(searchFlushDraw(2, 3));
			}
			else if (dealStep.equals(DealStep.RIVER)) {
				listDraw.addAll(searchFlushDraw(3, 3));
			}

			// Search FULL, BRELAN or DOUBLE PAIR draw
			// 1 (FLOP) ou 2 tirages (TURN ou RIVER)
			List<DrawModel> listFullDraw = searchFullDraw();

			if (listFullDraw.isEmpty()) {
				listDraw.add(searchTopSetDraw());
				listDraw.add(searchTopTwoPairDraw());
			} else {
				listDraw.addAll(listFullDraw);
			}
		}

		Collections.sort(listDraw);

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

	public List<DrawModel> searchFullDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();

		Type type = null;

		for (Rank rank : Rank.values()) {
			if (!rank.equals(Rank.UNKNOWN)) {
				Pattern pattern = Pattern.compile("(" + rank.getShortName() + ".){2,4}");
				Matcher matcher = pattern.matcher(this.toStringByRank());

				if (matcher.find()) {
					String drawString = matcher.group(0);

					Rank rankPair = Rank.UNKNOWN;
					Rank rankSet = Rank.UNKNOWN;

					if (drawString.length() == 4) {
						type = Type.FULL_PAIR_DRAW;
						rankPair = Rank.fromShortName(String.valueOf(drawString.charAt(0)));
					} else if (drawString.length() == 6) {
						type = Type.FULL_SET_DRAW;
						rankSet = Rank.fromShortName(String.valueOf(drawString.charAt(0)));
					} else if (drawString.length() == 8) {
						type = Type.FULL_FOUR_DRAW;
						rankSet = Rank.fromShortName(String.valueOf(drawString.charAt(0)));
					}

					FullModel fullModel = new FullModel(rankPair, rankSet);
					DrawModel<FullModel> drawModel1 = new DrawModel<FullModel>(type, fullModel, drawString,
							kickerPack1, kickerPack2);
					listDraw.add(drawModel1);

					Rank rankQuads = Rank.UNKNOWN;

					if (type.equals(Type.FULL_PAIR_DRAW)) {
						type = Type.QUADS_PAIR_DRAW;
						rankQuads = rankPair;
					} else if (type.equals(Type.FULL_PAIR_DRAW)) {
						type = Type.QUADS_SET_DRAW;
						rankQuads = rankSet;
					}

					if (!rankQuads.equals(Rank.UNKNOWN)) {
						QuadsModel quadsModel = new QuadsModel(rankQuads);
						DrawModel<QuadsModel> drawModel2 = new DrawModel<QuadsModel>(type, quadsModel, drawString,
								kickerPack1, kickerPack2);
						listDraw.add(drawModel2);
					}
				}
			}
		}

		return listDraw;
	}
}
