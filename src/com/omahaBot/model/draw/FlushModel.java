package com.omahaBot.model.draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.google.common.collect.Lists;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CardPackModel;
import com.omahaBot.model.CoupleCards;

public @Data class FlushModel extends DrawModel {

	private final Suit suit;

	private Rank rank;// kicker

	private boolean straightFlush;

	/**
	 * Just one constructor for DrawModel
	 * 
	 * @param handCategory
	 * @param suit
	 * @param drawString
	 * @param permutationHand
	 */
	public FlushModel(HandCategory handCategory, Suit suit, String drawString, SortedSet<CardModel> permutationHand) {
		super(handCategory);
		this.suit = suit;

		initRankAndNuts(drawString);	
		
		if (permutationHand != null) {
			initHoleCards(permutationHand);
			rank = permutationHand.last().getRank();
		}
	}

	@Override
	public String toString() {
		return this.display(handCategory + " of " + suit + " with kicker " + rank + "; ");
	}

	private void initRankAndNuts(String drawString) {

		try {
			CardPackModel cardPackModel = new CardPackModel(drawString);

			List<CardModel> cardsReverse = Lists.reverse(new ArrayList<>(cardPackModel.getCards()));
			List<Rank> listRankReverse = Lists.reverse(Arrays.asList(Rank.values()));

			rank = cardsReverse.get(0).getRank();

			List<CardModel> coupleCards = new ArrayList<CardModel>();

			CardModel card = null;
			int i = 0;

			for (Rank rank : listRankReverse) {

				if (!rank.equals(Rank.UNKNOWN)) {
					if (i < cardsReverse.size()) {
						card = cardsReverse.get(i);
						if (!rank.equals(card.getRank())) {
							coupleCards.add(new CardModel(rank, suit));
							if (coupleCards.size() == 2) {
								break;
							}
						}
					}
					else {
						coupleCards.add(new CardModel(rank, suit));
						if (coupleCards.size() == 2) {
							break;
						}
					}

					if (rank.equals(card.getRank())) {
						i++;
					}
				}
			}

			if (coupleCards.size() == 2) {
				nutsOrHoleCards = new CoupleCards(new TreeSet<CardModel>(coupleCards));
			}
		} catch (CardPackNoValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		FlushModel other = (FlushModel) obj;

		return suit.equals(other.suit);
	}

	@Override
	public int compareTo(DrawModel o) {

		if (o instanceof FlushModel) {
			FlushModel drawCompare = (FlushModel) o;

			// compare HandCategory
			if (this.handCategory.ordinal() > drawCompare.handCategory.ordinal()) {
				return -1;
			}
			// compare nutsOrHoleCards
			else {
				if (this.nutsOrHoleCards.getCard2().ordinal() > drawCompare.nutsOrHoleCards.getCard2().ordinal()) {
					return -1;
				}
				else if (this.nutsOrHoleCards.getCard2().ordinal() < drawCompare.nutsOrHoleCards.getCard2().ordinal()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
		else {
			return super.compareTo(o);
		}
	}

	@Override
	public boolean isNuts(Object obj) {
		FlushModel other = (FlushModel) obj;

		if (!this.equals(obj)) {
			return false;
		}
		else {
			return nutsOrHoleCards.isEqualsKicker(other.nutsOrHoleCards);
		}
	}
}
