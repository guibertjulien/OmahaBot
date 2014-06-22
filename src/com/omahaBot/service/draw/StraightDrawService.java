package com.omahaBot.service.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.utils.PermutationsOfN;

public class StraightDrawService {

	private final List<CardModel> combinaisonCards;

	private final BoardModel boardModel;

	private CardModel card1, card2, card3, card4, card5, card6;
	
	private List<CardModel> connectors;

	public StraightDrawService(SortedSet<CardModel> combinaisonCardsSortedSet, BoardModel boardModel) {
		
		this.combinaisonCards = new ArrayList<CardModel>(combinaisonCardsSortedSet);
		this.boardModel = boardModel;
		
		this.card1 = this.combinaisonCards.get(0);
		this.card2 = this.combinaisonCards.get(1);
		this.card3 = this.combinaisonCards.get(2);
		this.card4 = this.combinaisonCards.get(3);
		this.card5 = this.combinaisonCards.get(4);
		this.card6 = this.combinaisonCards.get(5);
		
		initConnectors();
	}
	
	public StraightDrawService(ArrayList<CardModel> combinaisonCardsSortedByAceLow, BoardModel boardModel) {
		
		this.combinaisonCards = new ArrayList<CardModel>(combinaisonCardsSortedByAceLow);
		this.boardModel = boardModel;

		this.card1 = this.combinaisonCards.get(0);
		this.card2 = this.combinaisonCards.get(1);
		this.card3 = this.combinaisonCards.get(2);
		this.card4 = this.combinaisonCards.get(3);
		this.card5 = this.combinaisonCards.get(4);
		this.card6 = this.combinaisonCards.get(5);
		
		initConnectors();
	}

	public StraightDrawType straightDrawType() {
		
		StraightDrawType straightDrawType = StraightDrawType.NO_DRAW;
		
		if (isValidConnectors()) {
			if (is20CardWrap()) {
				return StraightDrawType.CARD20_WRAP;
			}
			else if (is17CardWrap()) {
				return StraightDrawType.CARD17_WRAP;
			}
			else if (is13CardWrap()) {
				return StraightDrawType.CARD13_WRAP;
			}
			else if (is12OutStraight()) {
				return StraightDrawType.CARD12_DRAW;
			}
			else if (isInsideBroadway()) {
				return StraightDrawType.INSIDE_BROADWAY;
			}
			else if (isOpenEnded()) {
				return StraightDrawType.OPEN_ENDED;
			}
		}
		else if (isGutshot()) {
			return StraightDrawType.GUT_SHOT;
		}
		
		return straightDrawType;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isGutshot() {
		
		int ordinalFirst = (card1.getRank().equals(Rank.ACE)) ? -1 : card1.ordinal(); 		
		int diffDraw1 = (card4.ordinal() - card3.ordinal()) + (card3.ordinal() - card2.ordinal()) + (card2.ordinal() - ordinalFirst);

		ordinalFirst = (card2.getRank().equals(Rank.ACE)) ? -1 : card2.ordinal(); 
		int diffDraw2 = (card5.ordinal() - card4.ordinal()) + (card4.ordinal() - card3.ordinal()) + (card3.ordinal() - ordinalFirst);
		
		ordinalFirst = (card3.getRank().equals(Rank.ACE)) ? -1 : card3.ordinal(); 
		int diffDraw3 = (card6.ordinal() - card5.ordinal()) + (card5.ordinal() - card4.ordinal()) + (card4.ordinal() - ordinalFirst);
		
		return diffDraw1 == 4 || diffDraw2 == 4 || diffDraw3 == 4;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isOpenEnded() {

		if (connectors.size() != 4) {
			return false;
		}

		ArrayList<CardModel> cards = new ArrayList<>(connectors);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();
		List<List<CardModel>> permutations = permutationsOrdered.processSubsets(cards, 2);
		
		for (List<CardModel> list : permutations) {
			if (boardModel.getCards().containsAll(list)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isInsideBroadway() {

		if (connectors.size() != 5) {
			return false;
		}

		if (!boardModel.hasRankCard(Rank.ACE)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean is12OutStraight() {

		if (connectors.size() != 6) {
			return false;
		}

		if (is20CardWrap()) {
			return false;
		}
		
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean is13CardWrap() {

		if (connectors.size() != 6) {
			return false;
		}

		CardModel cardFirst = connectors.get(0);
		CardModel cardLast = connectors.get(5);

		if (boardModel.getCards().containsAll(Arrays.asList(cardFirst, cardLast))) {
			return false;
		}

		if (boardModel.getCards().contains(cardFirst) || boardModel.getCards().contains(cardLast)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	private boolean is17CardWrap() {

		if (connectors.size() != 5) {
			return false;
		}

		CardModel cardFirst = connectors.get(0);
		CardModel cardLast = connectors.get(connectors.size()-1);

		if (boardModel.getCards().contains(cardFirst) || boardModel.getCards().contains(cardLast)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean is20CardWrap() {

		if (connectors.size() != 6) {
			return false;
		}

		CardModel cardMiddle1 = connectors.get(2);
		CardModel cardMiddle2 = connectors.get(3);

		if (boardModel.getCards().containsAll(Arrays.asList(cardMiddle1, cardMiddle2))) {
			return true;
		}

		return false;
	}

	private void initConnectors() {

		List<CardModel> connectors = new ArrayList<CardModel>();

		CardModel cardModelNext;

		int i = 0;
		
		for (CardModel cardModel : combinaisonCards) {
			if (i < combinaisonCards.size() - 1) {
				cardModelNext = combinaisonCards.get(i + 1);

				if (cardModel.ordinal() == cardModelNext.ordinal()) {
					i++;
					continue;// itération suivante
				} else if (cardModel.isConnected(cardModelNext)) {
					if (connectors.isEmpty()) {
						connectors.add(cardModel);
					}
					connectors.add(cardModelNext);
				} else {
					if (connectors.size() < 4) {
						connectors.clear();
					}
				}
			}
			
			i++;
		}

		// TODO bonnes pratiques ?
		this.connectors = connectors;
	}
	
	/**
	 * test if 2 board cards in connectors
	 * @return
	 */
	private boolean isValidConnectors() {
		
		int countBoardCardInConnectors = 0;
		
		for (CardModel cardModel : boardModel.getCards()) {
			if (connectors.contains(cardModel)) {
				countBoardCardInConnectors++;
			}
		}
		
		return (countBoardCardInConnectors == 2);
	}
}
