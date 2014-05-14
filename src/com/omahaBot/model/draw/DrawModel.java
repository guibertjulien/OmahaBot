package com.omahaBot.model.draw;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.DrawType;
import com.omahaBot.model.CardModel;

public @Data
class DrawModel {

	private int nbOut = 0;
	private double percent = 0.0;
	protected SortedSet<CardModel> nutsOrHoleCards = new TreeSet<CardModel>();
	private final DrawType drawType;
	protected final boolean isDraw;

	public DrawModel(DrawType drawType, boolean isDraw) {
		super();
		this.drawType = drawType;
		this.isDraw = isDraw;
	}

	public String displayNutsOrHoleCards() {
		if (isDraw) {
			return displayNuts();
		}
		else {
			return displayHoleCards();
		}
	}

	/**
	 * if FLUSH, display AsKs else AK
	 * 
	 * @return
	 */
	private String displayNuts() {
		String display = "";

		if (nutsOrHoleCards != null && !nutsOrHoleCards.isEmpty()) {
			CardModel card1 = nutsOrHoleCards.first();
			CardModel card2 = nutsOrHoleCards.last();

			switch (drawType) {
			case FLUSH:
			case FLUSH_DRAW:
				display = card1.toString().concat(card2.toString());
				break;
			case BEST_TWO_PAIR_DRAW:
			case BEST_SET_DRAW:
			case BEST_FULL_DRAW:
			case QUADS_DRAW:
				display = card1.getRank().getShortName().concat(card2.getRank().getShortName());
				break;

			default:
				break;
			}
		}

		return display;
	}

	/**
	 * 
	 * @return
	 */
	private String displayHoleCards() {
		String display = "";

		if (nutsOrHoleCards != null && !nutsOrHoleCards.isEmpty()) {
			CardModel card1 = nutsOrHoleCards.first();
			CardModel card2 = nutsOrHoleCards.last();
			display = card1.toString().concat(card2.toString());
		}
		
		return display;
	}

	public void initHoleCards(SortedSet<CardModel> permutationHand) {
		nutsOrHoleCards.addAll(permutationHand);
	}

	public boolean isNuts(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
