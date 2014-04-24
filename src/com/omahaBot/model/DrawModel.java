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
class DrawModel implements Comparable<DrawModel> {

	public enum Type {
		TWO_PAIR_DRAW, 
		BRELAN_DRAW, 
		FLUSH_DRAW, 
		FLUSH, 
		FULL_PAIR_DRAW, 
		FULL_THREE_DRAW, 
		FULL_FOUR_DRAW, 
		CARRE_DRAW, 
		STRAIGHT_FLUSH_DRAW;
	}

	private static String PREFIX_FLUSH = "FLUSH";

	private final Type type;
	private final String drawString;
	private final Rank kickerPack1;
	private final Rank kickerPack2;
	private SortedSet<CardModel> cards;
	private Suit suit = Suit.UNKNOW;
	private int nbOut = 0;
	private double percent = 0.0;
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

		if (type.name().startsWith(PREFIX_FLUSH)) {
			suit = cards.first().getSuit();
		}

		initBestHand();

		if (!bestHand.isEmpty()) {
			bestHandkicker = bestHand.last().getRank();
		}
	}

	public void initBestHand() {
		bestHand = new TreeSet<>();

		switch (type) {
		case FLUSH:
		case FLUSH_DRAW:
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
			break;

		case FULL_PAIR_DRAW:
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
			break;
		case FULL_THREE_DRAW:
			// TODO super best hand : same card (carre)
			if (cards.first().getRank().equals(kickerPack1)) {
				if (kickerPack1.equals(Rank.ACE)) {
					// AAA5 --> KK
					bestHand.add(new CardModel("Ks"));
					bestHand.add(new CardModel("Kh"));
				}
				else {
					// QQQ5 --> AA
					bestHand.add(new CardModel("As"));
					bestHand.add(new CardModel("Ah"));
				}
			}
			else {
				// 222K --> KK
				bestHand.add(new CardModel(kickerPack1, Suit.SPADE));
				bestHand.add(new CardModel(kickerPack1, Suit.HEART));
			}
			break;
		case FULL_FOUR_DRAW:
			if (cards.first().getRank().equals(Rank.ACE)) {
				bestHand.add(new CardModel("Ks"));
				bestHand.add(new CardModel("Kh"));
			}
			else {
				bestHand.add(new CardModel("As"));
				bestHand.add(new CardModel("Ah"));
			}
			break;
		case BRELAN_DRAW:
			bestHand.add(new CardModel(kickerPack1, Suit.SPADE));
			bestHand.add(new CardModel(kickerPack1, Suit.HEART));
			break;
		case TWO_PAIR_DRAW:
			bestHand.add(new CardModel(kickerPack1, Suit.UNKNOW));
			bestHand.add(new CardModel(kickerPack2, Suit.UNKNOW));
			break;
		case CARRE_DRAW:
			Rank rank = cards.first().getRank();
			bestHand.add(new CardModel(rank, Suit.SPADE));
			bestHand.add(new CardModel(rank, Suit.HEART));
			break;
		default:
			break;
		}
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

	/**
	 * if FLUSH, display AsKs else A?K?
	 * 
	 * @return
	 */
	public String displayBestHand() {
		CardModel card1 = bestHand.first();
		CardModel card2 = bestHand.last();

		if (!type.name().startsWith(PREFIX_FLUSH)) {
			card1.setSuit(suit.UNKNOW);
			card2.setSuit(suit.UNKNOW);
		}

		return card1.toString().concat(card2.toString());
	}
}
