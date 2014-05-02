package com.omahaBot.utils;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;

public class CardUtils {

	public static String cardsToString(List<CardModel> cards) {
		
		String display = "";
		
		for (CardModel card : cards) {
			display += card.toString();
		}
		
		return display;
	}
	
//	public static SortedSet<CardModel> initNuts (List<Rank> listNoRank) {
//		
//		SortedSet<CardModel> nuts = new TreeSet<>();
//
//		List<Rank> listNoRankR = Lists.reverse(new TreeSet(arg0));
//		List<Rank> listAllRankR = Lists.reverse(Arrays.asList(Rank.values()));
//
//		Rank noRank = listNoRankR.get(0);
//		int i = 0;
//
//		for (Rank rank : listAllRankR) {
//			if (!rank.equals(Rank.UNKNOWN) && !rank.equals(noRank)) {
//				CardModel card1 = new CardModel(rank, Suit.SPADE);
//				CardModel card2 = new CardModel(rank, Suit.HEART);
//				
//				nuts.addAll(Arrays.asList(card1, card2));
//				
//				break;
//			} else if (rank.equals(noRank)) {
//				rank = listNoRankR.get(++i);
//			}
//		}
//		
//		return nuts;
//	}
}
