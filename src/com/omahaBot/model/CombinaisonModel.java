package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.DrawModel.Type;
import com.omahaBot.model.handCategory.FullModel;

/**
 * 5 cards (permutations of 2 hole cards and 3 board cards)
 * 
 * @author Julien
 * 
 */
public class CombinaisonModel extends CardPackModel implements Comparable<CombinaisonModel> {

	private Rank kicker;

	private PostFlopPowerType postFlopPowerType;

	public CombinaisonModel(List<CardModel> permutationHand, List<CardModel> permutationBoard) {
		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);

		// initHandPowerType();
	}

	public SortedSet<CardModel> getCards() {
		return cards;
	}

	public void setCards(SortedSet<CardModel> cards) {
		this.cards = cards;
	}

	/**
	 * TODO : best practices ?
	 */
	public ArrayList<DrawModel> initDraw() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		if (!cards.isEmpty()) {
			listDraw.addAll(searchFlushDraw(4, 5));
			listDraw.addAll(searchFullDraw());
		}

		Collections.sort(listDraw);

		return listDraw;
	}

	public List<DrawModel> searchFullDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();

		ArrayList<List<CardModel>> listCoupleCard = new ArrayList<>();
		listCoupleCard.add(Arrays.asList(cards.first(), cards.last()));
		listCoupleCard.add(Arrays.asList(cards.last(), cards.first()));

		for (List<CardModel> listCard : listCoupleCard) {
			Pattern patternPair = Pattern.compile("(" + listCard.get(0).getRank().getShortName() + ".){2,2}");
			Pattern patternSet = Pattern.compile("(" + listCard.get(1).getRank().getShortName() + ".){3,3}");

			Matcher matcherPair = patternPair.matcher(this.toStringByRank());
			Matcher matcherSet = patternSet.matcher(this.toStringByRank());

			if (matcherPair.find() && matcherSet.find()) {
				Rank rankPair = Rank.fromShortName(String.valueOf(matcherSet.group(0).charAt(0)));
				Rank rankSet = Rank.fromShortName(String.valueOf(matcherPair.group(0).charAt(0)));

				FullModel fullModel = new FullModel(rankPair, rankSet);

				DrawModel<FullModel> drawModel = new DrawModel(Type.FULL, fullModel, this.toStringByRank(),
						kickerPack1, kickerPack2);
				listDraw.add(drawModel);
				break;
			}
		}

		return listDraw;
	}

	/**
	 * TODO : best practices ?
	 */
	private void initHandPowerType() {
		if (isStraightFlush()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT_FLUSH;
		} else if (isFourOfAKind()) {
			postFlopPowerType = PostFlopPowerType.FOUR_OF_A_KIND;
		} else if (isFull()) {
			postFlopPowerType = PostFlopPowerType.FULL_HOUSE;
		} else if (isFlush()) {
			postFlopPowerType = PostFlopPowerType.FLUSH;
		} else if (isStraight()) {
			postFlopPowerType = PostFlopPowerType.STRAIGHT;
		} else if (isThreeOfAKind()) {
			postFlopPowerType = PostFlopPowerType.THREE_OF_A_KIND;
		} else if (isTwoPair()) {
			postFlopPowerType = PostFlopPowerType.TWO_PAIR;
		} else if (isOnePair()) {
			postFlopPowerType = PostFlopPowerType.ONE_PAIR;
		} else {
			postFlopPowerType = PostFlopPowerType.HIGH_CARD;
		}
	}

	public Rank getKicker() {
		return kicker;
	}

	public void setKicker(Rank kicker) {
		this.kicker = kicker;
	}

	public PostFlopPowerType getHandPowerType() {
		return postFlopPowerType;
	}

	public void setHandPowerType(PostFlopPowerType postFlopPowerType) {
		this.postFlopPowerType = postFlopPowerType;
	}

	@Override
	public int compareTo(CombinaisonModel o) {
		if (this.getHandPowerType().ordinal() < o.getHandPowerType().ordinal()) {
			return 1;
		}
		else if (this.getHandPowerType().ordinal() > o.getHandPowerType().ordinal()) {
			return -1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "CombinaisonModel [cards=" + cards + ", postFlopPowerType=" + postFlopPowerType + "]";
	}
}
