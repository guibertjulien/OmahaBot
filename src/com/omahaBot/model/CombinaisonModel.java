package com.omahaBot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.enums.Rank;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.OnePairModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.utils.CardUtils;

/**
 * 5 cards (permutations of 2 hole cards and 3 board cards)
 * 
 * @author Julien
 * 
 */
public class CombinaisonModel extends CardPackModel implements Comparable<CombinaisonModel> {

	private Rank kicker;

	private PostFlopPowerType postFlopPowerType;

	private final SortedSet<CardModel> permutationHand;

	private final SortedSet<CardModel> permutationBoard;

	public CombinaisonModel(SortedSet<CardModel> permutationHand, SortedSet<CardModel> permutationBoard) {
		this.permutationHand = permutationHand;
		this.permutationBoard = permutationBoard;

		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);

		// initHandPowerType();
	}

	public CombinaisonModel(List<CardModel> permutationHand, List<CardModel> permutationBoard) {
		this.permutationHand = new TreeSet<CardModel>(permutationHand);
		this.permutationBoard = new TreeSet<CardModel>(permutationBoard);

		cards = new TreeSet<CardModel>();
		cards.addAll(permutationHand);
		cards.addAll(permutationBoard);
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
//			if (hasFlushDraw()) {
//				listDraw.addAll(searchFlushDraw(4, 5));
//			}

			listDraw.add(searchRankDraw());
		}

		// Collections.sort(listDraw);

		return listDraw;
	}

	
	public DrawModel searchRankDraw() {

		DrawModel drawModel = null;

		HandCategory handCategory = null;
		
		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		String group1 = "";
		String group2 = "";
		
		Rank rank1 = Rank.UNKNOWN;
		Rank rank2 = Rank.UNKNOWN;
		
		if (matcher.find()) {
			group1 = matcher.group(0);
			rank1 = Rank.fromShortName(String.valueOf(group1.charAt(0)));

			if (group1.length() == 8) {
				
				if (CardUtils.coupleIsPair(permutationHand)) {
					handCategory = HandCategory.ONE_PAIR;
				}
				else {
					handCategory = HandCategory.THREE_OF_A_KIND;
				}
				
				drawModel = new QuadsModel(rank1, handCategory, false);
			} else if (group1.length() == 6) {
				if (matcher.find()) {
					group2 = matcher.group(0);
					rank2 = Rank.fromShortName(String.valueOf(group2.charAt(0)));
					drawModel = new FullModel(rank1, rank2, handCategory, null, null, null, false);
				}
				else {
					drawModel = new SetModel(rank1, false);
				}
			} else if (group1.length() == 4) {
				if (matcher.find()) {
					group2 = matcher.group(0);
					rank2 = Rank.fromShortName(String.valueOf(group2.charAt(0)));

					if (group2.length() == 6) {
						drawModel = new FullModel(rank2, rank1, handCategory, null, null, null, false);
					}
					else {
						drawModel = new TwoPairModel(rank1, rank2, false);
					}
				}
				else {
					drawModel = new OnePairModel(rank1);
				}
			}
		}

		if (drawModel != null) {
			drawModel.initHoleCards(permutationHand);
		}
		
		return drawModel;
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
		return 1;
		// if (this.getHandPowerType().ordinal() <
		// o.getHandPowerType().ordinal()) {
		// return 1;
		// }
		// else if (this.getHandPowerType().ordinal() >
		// o.getHandPowerType().ordinal()) {
		// return -1;
		// }
		// else {
		// return 0;
		// }
	}
	
	@Override
	public String toString() {
		return "Combinaison : " + cards;
	}
}
