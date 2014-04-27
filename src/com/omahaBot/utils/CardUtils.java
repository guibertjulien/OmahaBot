package com.omahaBot.utils;

import java.util.List;

import com.omahaBot.model.CardModel;

public class CardUtils {

	public static String cardsToString(List<CardModel> cards) {
		
		String display = "";
		
		for (CardModel card : cards) {
			display += card.toString();
		}
		
		return display;
	}
}
