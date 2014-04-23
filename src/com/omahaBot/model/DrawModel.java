package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;

public @Data
class DrawModel {

	public enum Type {
		FLUSH, FLUSH_DRAW, FULL_PAIR_DRAW, FULL_THREE_DRAW, FULL_FOUR_DRAW;
	}

	private static String PREFIX_FLUSH = "FLUSH";

	private final Type type;
	private final String drawString;
	private final Rank kickerPack1;
	private final Rank kickerPack2;
	private SortedSet<CardModel> cards;
	private Suit suit = Suit.UNKNOW;
	private int nbOut = 0;
	private Rank bestHandkicker;
	private SortedSet<CardModel> bestHand;

	public DrawModel(Type type, String drawString, Rank kickerPack1, Rank kickerPack2) {
		super();
		this.type = type;
		this.drawString = drawString;
		this.kickerPack1 = kickerPack1;
		this.kickerPack2 = kickerPack2;
		cards = new TreeSet<CardModel>();

		while (drawString.length() > 1) {
			CardModel card = new CardModel(drawString.substring(0, 2));
			cards.add(card);
			drawString = drawString.substring(2, drawString.length());
		}

		if (type.equals(type.name().startsWith(PREFIX_FLUSH))) {
			suit = cards.first().getSuit();
		}

		initBestHand();

		if (!bestHand.isEmpty()) {
			bestHandkicker = bestHand.last().getRank();
		}
	}

	public void initBestHand() {
		bestHand = new TreeSet<>();

		if (type.equals(Type.FLUSH) || type.equals(Type.FLUSH_DRAW)) {
			List<CardModel> listCard = Lists.reverse(new ArrayList<>(cards));
			List<Rank> listRank = Lists.reverse(Arrays.asList(Rank.values()));

			CardModel card = listCard.get(0);
			int i = 0;

			for (Rank rank : listRank) {
				if (!rank.equals(Rank.UNKNOW) && !rank.equals(card.getRank())) {

					bestHand.add(new CardModel(rank, suit));
					if (bestHand.size() == 2) {
						break;
					}
				} else if (rank.equals(card.getRank())) {
					card = listCard.get(++i);
				}

			}
		}

		else if (type.equals(Type.FULL_PAIR_DRAW)) {
			// TODO super best hand : same pair (carre)
			if (cards.first().getRank().equals(kickerPack1)) {
				// AA5 --> A5
				bestHand.add(new CardModel(kickerPack1, Suit.UNKNOW));
				bestHand.add(new CardModel(kickerPack2, Suit.UNKNOW));
			}
			else {
				// 22K --> KK
				bestHand.add(new CardModel(kickerPack1, Suit.SPADE));
				bestHand.add(new CardModel(kickerPack1, Suit.HEART));
			}
		} else if (type.equals(Type.FULL_THREE_DRAW)) {
			// TODO super best hand : same card (carre)
			Rank rankBestHand;
			if (cards.first().getRank().equals(kickerPack1)) {
				// AAA5 --> 55
				rankBestHand = Rank.values()[kickerPack1.ordinal()-1];
			}
			else {
				// 222K --> KK
				rankBestHand = kickerPack1;
			}

			bestHand.add(new CardModel(rankBestHand, Suit.SPADE));
			bestHand.add(new CardModel(rankBestHand, Suit.HEART));
		} else if (type.equals(Type.FULL_FOUR_DRAW)) {
			if (cards.first().getRank().equals(Rank.ACE)) {
				bestHand.add(new CardModel("Ks"));
				bestHand.add(new CardModel("Kh"));
			}
			else {
				bestHand.add(new CardModel("As"));
				bestHand.add(new CardModel("Ah"));
			}
		}
	}
}
