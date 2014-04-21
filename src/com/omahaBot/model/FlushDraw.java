package com.omahaBot.model;

import lombok.Data;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;

public @Data
class FlushDraw {
	private final Rank kicker;
	private final Suit suit;
	private final int nbSuitedCard;
	private int nbOut = 0;

	public FlushDraw(Rank kicker, Suit suit, int nbSuitedCard) {
		super();
		this.kicker = kicker;
		this.suit = suit;
		this.nbSuitedCard = nbSuitedCard;
		calculateNbOut();
	}

	private void calculateNbOut() {
		nbOut = Consts.NB_CARD_BY_SUIT - nbSuitedCard;
	}

}
