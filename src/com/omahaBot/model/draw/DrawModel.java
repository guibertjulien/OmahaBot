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
	protected SortedSet<CardModel> nuts = new TreeSet<CardModel>();

	private final DrawType drawType;

	/**
	 * if FLUSH, display AsKs else AK
	 * 
	 * @return
	 */
	public String displayNuts() {
		String display = "";

		if (nuts != null && !nuts.isEmpty()) {
			CardModel card1 = nuts.first();
			CardModel card2 = nuts.last();

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
}
