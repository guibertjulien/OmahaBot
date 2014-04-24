package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.DealStep;
import com.omahaBot.model.DrawModel.Type;
import com.omahaBot.utils.PermutationsOfN;

public class BoardModel extends CardPack {

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
	public ArrayList<DrawModel> initBoardDraw() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		if (dealStep.ordinal() > DealStep.PRE_FLOP.ordinal()) {

			List<DrawModel> listFlushDraw = null;
			List<DrawModel> listFullDraw = null;

			// Search FLUSH draw
			listFlushDraw = searchFlushDraw(Type.FLUSH);

			if (listFlushDraw.isEmpty() && (dealStep.equals(DealStep.FLOP) || dealStep.equals(DealStep.TURN))) {
				if (listFlushDraw.isEmpty()) {
					// 1 (FLOP) ou 2 tirages (TURN)
					listFlushDraw.addAll(searchFlushDraw(Type.FLUSH_DRAW));
				}
			}

			// Search FULL, BRELAN or DOUBLE PAIR draw
			// 1 (FLOP) ou 2 tirages (TURN ou RIVER)
			listFullDraw = searchFullDraw();

			if (listFullDraw.isEmpty()) {
				listFullDraw.add(searchBrelanDraw());
				listFullDraw.add(searchTwoPairDraw());
			}

			//
			listDraw.addAll(listFlushDraw);
			listDraw.addAll(listFullDraw);

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
}
