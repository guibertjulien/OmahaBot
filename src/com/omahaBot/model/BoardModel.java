package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.BoardDrawPower;
import com.omahaBot.enums.DealStep;
import com.omahaBot.utils.PermutationsOfN;

public class BoardModel extends CardPack {

	private final DealStep dealStep;

	private BoardDrawPower boardDrawPower;

	public BoardModel(SortedSet<CardModel> cards, DealStep dealStep) {
		super(cards);
		this.dealStep = dealStep;
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

		//initBoardDrawPower();
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
	private void initBoardDrawPower() {
		if (isStraightFlush()) {
			boardDrawPower = BoardDrawPower.STRAIGHT_FLUSH;
		} else if (isFourOfAKindDraw()) {
			boardDrawPower = BoardDrawPower.FOUR_OF_A_KIND;
		} else if (isFullDraw()) {
			boardDrawPower = BoardDrawPower.FULL;
		} else if (isFlushDraw()) {
			boardDrawPower = BoardDrawPower.FLUSH;
		} else {
			boardDrawPower = BoardDrawPower.TWO_PAIR;
		}
	}

	public boolean isFourOfAKindDraw() {
		return isThreeOfAKind();
	}

	/**
	 * TODO : LEVEL
	 * 
	 * @return
	 */
	public boolean isFullDraw() {
		return isFourOfAKind()
				|| isTwoPair()
				|| isOnePair();

	}

	/**
	 * TODO : LEVEL 1 or 2 flush; Kicker
	 * 
	 * @return
	 */
	public boolean isFlushDraw() {

		boolean result = false;

		switch (dealStep) {
		case FLOP:
		case TURN:
			result = isOneSuit();
			break;
		case RIVER:
			result = isNbSameCardSuit(3);
			break;
		default:
			break;
		}

		return result;
	}

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

	public BoardDrawPower getBoardDrawPower() {
		return boardDrawPower;
	}

	public void setBoardDrawPower(BoardDrawPower boardDrawPower) {
		this.boardDrawPower = boardDrawPower;
	}

}
