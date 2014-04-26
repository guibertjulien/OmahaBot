package com.omahaBot.model;

import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.model.handCategory.FlushModel;
import com.omahaBot.model.handCategory.FullModel;
import com.omahaBot.model.handCategory.QuadsModel;
import com.omahaBot.model.handCategory.TopSetModel;
import com.omahaBot.model.handCategory.TopTwoPairModel;

public @Data
class DrawModel<T> implements Comparable<DrawModel> {

	public enum Type {
		UNKNOW,
		TOP_TWO_PAIR_DRAW,
		TOP_SET_DRAW,
		FLUSH_DRAW,
		FLUSH,
		FULL,
		FULL_PAIR_DRAW,
		QUADS_PAIR_DRAW,
		FULL_SET_DRAW,
		QUADS_SET_DRAW,
		FULL_FOUR_DRAW,
		STRAIGHT_FLUSH_DRAW,
	}

	private static String PREFIX_FLUSH = "FLUSH";

	private final T handCategory;

	private final Type type;
	private final String drawString;
	private SortedSet<CardModel> cards;
	private int nbOut = 0;
	private double percent = 0.0;
	private SortedSet<CardModel> nuts;

	public DrawModel(Type type, T handCategory, String drawString, Rank kickerPack1, Rank kickerPack2) {
		super();

		this.type = type;
		this.handCategory = handCategory;
		this.drawString = drawString;
		cards = new TreeSet<CardModel>();

		while (drawString.length() > 1) {
			CardModel card = new CardModel(drawString.substring(0, 2));
			cards.add(card);
			drawString = drawString.substring(2, drawString.length());
		}

		initNuts(kickerPack1, kickerPack2);
	}

	@Override
	public int compareTo(DrawModel o) {
		// compare Type
		if (this.type.ordinal() < o.type.ordinal()) {
			return 1;
		}
		else
			return -1;
	}

	public void initNuts(Rank kickerPack1, Rank kickerPack2) {
		if (handCategory instanceof FlushModel) {
			nuts = ((FlushModel) handCategory).initNuts(cards);
		} else if (handCategory instanceof FullModel) {
			nuts = ((FullModel) handCategory).initNuts(type, kickerPack1, kickerPack2);
		} else if (handCategory instanceof QuadsModel) {
			nuts = ((QuadsModel) handCategory).initNuts();
		} else if (handCategory instanceof TopSetModel) {
			nuts = ((TopSetModel) handCategory).initNuts();
		} else if (handCategory instanceof TopTwoPairModel) {
			nuts = ((TopTwoPairModel) handCategory).initNuts();
		}
	}

	/**
	 * if FLUSH, display AsKs else A?K?
	 * 
	 * @return
	 */
	public String displayNuts() {
		String display = "";

		if (!nuts.isEmpty()) {
			CardModel card1 = nuts.first();
			CardModel card2 = nuts.last();

			if (!type.name().startsWith(PREFIX_FLUSH)) {
				display = card1.getRank().getShortName().concat(card2.getRank().getShortName());
			}
			else {
				display = card1.toString().concat(card2.toString());
			}
		}

		return display;
	}
}
