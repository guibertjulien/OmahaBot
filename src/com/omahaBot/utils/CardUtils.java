package com.omahaBot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.omahaBot.model.CardModel;

public class CardUtils {

	public static String cardsToString(List<CardModel> cards) {
		
		String display = "";
		
		for (CardModel card : cards) {
			display += card.toString();
		}
		
		return display;
	}
	
	public static boolean coupleIsPair(SortedSet<CardModel> cards) {
		CardModel card1 = cards.first();
		CardModel card2 = cards.last();
		
		return card1.getRank().equals(card2.getRank()); 	
	}
	
	public static boolean coupleIsSuited(SortedSet<CardModel> cards) {
		CardModel card1 = cards.first();
		CardModel card2 = cards.last();
		
		return card1.getSuit().equals(card2.getSuit()); 	
	}
	
	public static boolean coupleIsConnected(SortedSet<CardModel> cards) {
		// TODO
		return false;
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
	
	/**
	 * ex : 2sJdKcAc --> 2JKA
	 * 
	 * @return
	 */
	public static String toRankString(ArrayList<CardModel> cards) {
		String handRank = "";

		for (CardModel cardModel : cards) {
			handRank += cardModel.getRank().getShortName();
		}

		return handRank;
	}
}
