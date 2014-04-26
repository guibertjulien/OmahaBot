package com.omahaBot.model.handCategory;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DrawModel.Type;

/**
 * pair and brelan rank
 * 
 * @author Julien
 * 
 */
public @Data
class FullModel {

	private final Rank rankPair;

	private final Rank rankSet;

	@Override
	public String toString() {
		return "Full house, " + rankSet + " full of " + rankPair;
	}

	public SortedSet<CardModel> initNuts(Type type, Rank kickerPack1, Rank kickerPack2) {
		SortedSet<CardModel> nuts = new TreeSet<>();

		CardModel card1 = null;
		CardModel card2 = null;

		switch (type) {
		case FULL_PAIR_DRAW:
			if (rankPair.equals(kickerPack1)) {
				// ex : AA5 --> A5
				card1 = new CardModel(kickerPack1, Suit.UNKNOW);
				card2 = new CardModel(kickerPack2, Suit.UNKNOW);
			}
			else {
				// ex : 22K --> KK
				card1 = new CardModel(kickerPack1, Suit.SPADE);
				card2 = new CardModel(kickerPack1, Suit.HEART);

			}
			break;
		case FULL_SET_DRAW:
			if (rankSet.equals(kickerPack1)) {
				if (kickerPack1.equals(Rank.ACE)) {
					// ex : AAA5 --> KK
					card1 = new CardModel("Ks");
					card2 = new CardModel("Kh");
				}
				else {
					// ex : QQQ5 --> AA
					card1 = new CardModel("As");
					card2 = new CardModel("Ah");
				}
			}
			else {
				// ex : 222K --> KK
				card1 = new CardModel(kickerPack1, Suit.SPADE);
				card2 = new CardModel(kickerPack1, Suit.HEART);
			}
			break;
		case FULL_FOUR_DRAW:
			if (rankSet.equals(Rank.ACE)) {
				// ex : AAAA --> KK
				card1 = new CardModel("Ks");
				card2 = new CardModel("Kh");
			}
			else {
				// ex : 2222 --> AA
				card1 = new CardModel("As");
				card2 = new CardModel("Ah");
			}
			break;
		default:
			// TODO exception
			break;
		}

		if (card1 != null && card2 != null) {
			nuts.addAll(Arrays.asList(card1, card2));			
		}
		
		return nuts;
	}
}
