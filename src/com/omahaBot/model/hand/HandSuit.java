package com.omahaBot.model.hand;

import lombok.Data;

import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;

@Data
public class HandSuit {

	final Suit suit;
	
	final Rank kicker;
}
