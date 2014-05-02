package com.omahaBot.model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.PostFlopPowerType;
import com.omahaBot.enums.Rank;
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

	private final List<CardModel> permutationHand;

	private final List<CardModel> permutationBoard;

	public CombinaisonModel(List<CardModel> permutationHand, List<CardModel> permutationBoard) {
		this.permutationHand = permutationHand;
		this.permutationBoard = permutationBoard;

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

//	/**
//	 * TODO : best practices ?
//	 */
//	public ArrayList<DrawModel> initDraw() {
//		ArrayList<DrawModel> listDraw = new ArrayList<>();
//
//		if (!cards.isEmpty()) {
//			if (hasFlushDraw()) {
//				listDraw.addAll(searchFlushDraw(4, 5));
//			}
//
//			listDraw.add(searchRankDraw());
//		}
//
//		// Collections.sort(listDraw);
//
//		return listDraw;
//	}
//
//	public <T> DrawModel<T> searchRankDraw() {
//
//		DrawModel<T> drawModel = null;
//
//		String combinaisonWhithoutSuit = this.toStringByRank().replaceAll("(s|h|d|c)", ".");
//
//		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
//		Matcher matcher = pattern.matcher(combinaisonWhithoutSuit);
//
//		if (matcher.find()) {
//			String group1 = matcher.group(0);
//			Rank rank1 = Rank.fromShortName(String.valueOf(matcher.group(0).charAt(0)));
//			Rank rank2 = Rank.UNKNOWN;
//
//			if (group1.length() == 8) {
//				QuadsModel quadsModel = new QuadsModel(rank1);
//				drawModel = new DrawModel(Type.FOUR_OF_A_KIND, quadsModel, this.toStringByRank(),
//						kickerPack1, kickerPack2);
//			} else if (group1.length() == 6) {
//				if (matcher.find()) {
//					rank2 = Rank.fromShortName(String.valueOf(matcher.group(1).charAt(0)));
//					FullModel fullModel = new FullModel(rank2, rank1);
//					drawModel = new DrawModel(Type.FULL, fullModel, this.toStringByRank(),
//							kickerPack1, kickerPack2);
//				}
//				else {
//					SetModel setModel = new SetModel(rank1);
//					drawModel = new DrawModel(Type.THREE_OF_A_KIND, setModel, this.toStringByRank(),
//							kickerPack1, kickerPack2);
//				}
//			} else if (group1.length() == 4) {
//				if (matcher.find()) {
//					String group2 = matcher.group(0);
//
//					if (group2.length() == 6) {
//						rank2 = Rank.fromShortName(String.valueOf(matcher.group(1).charAt(0)));
//						FullModel fullModel = new FullModel(rank1, rank2);
//						drawModel = new DrawModel(Type.FULL, fullModel, this.toStringByRank(),
//								kickerPack1, kickerPack2);
//					}
//					else {
//						rank2 = Rank.fromShortName(String.valueOf(matcher.group(1).charAt(0)));
//						TwoPairModel twoPairModel = new TwoPairModel(rank2, rank1);
//						drawModel = new DrawModel(Type.TWO_PAIR, twoPairModel, this.toStringByRank(),
//								kickerPack1, kickerPack2);
//					}
//				}
//				else {
//					OnePairModel onePairModel = new OnePairModel(rank1);
//					drawModel = new DrawModel(Type.ONE_PAIR, onePairModel, this.toStringByRank(),
//							kickerPack1, kickerPack2);
//				}
//			}
//		}
//
//		return drawModel;
//	}

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
		return "[" + CardUtils.cardsToString(permutationHand) + "][" + CardUtils.cardsToString(permutationBoard) + "]";
	}

	public boolean hasFlushDraw() {
		CardModel holeCard1 = permutationHand.get(0);
		CardModel holeCard2 = permutationHand.get(1);

		return holeCard1.getSuit().equals(holeCard2.getSuit());

	}
}
