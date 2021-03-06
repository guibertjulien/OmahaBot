package com.omahaBot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import com.omahaBot.enums.BoardCategory;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.exception.StraightInitializeException;
import com.omahaBot.model.comparator.RankAceLowComparator;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.OnePairModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.StraightModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.utils.CardUtils;
import com.omahaBot.utils.PermutationsOfN;

/**
 * represente le Board au FLOP, TURN et RIVER
 * <p>
 * - recherche des tirages du board
 * 
 * @author Julien
 *
 */
public class BoardModel extends CardPackModel {

	// FLOP, TURN ou RIVER
	private final DealStep dealStep;

	/**
	 * 
	 * @param sortedCards
	 * @param dealStep
	 */
	public BoardModel(SortedSet<CardModel> sortedCards, DealStep dealStep) {
		super(sortedCards);
		this.dealStep = dealStep;
		initKickers();
	}

	/**
	 * 
	 * @param handString
	 * @param dealStep
	 */
	public BoardModel(String handString, DealStep dealStep) {
		super();

		sortedCards = new TreeSet<CardModel>();

		if (dealStep.ordinal() >= DealStep.FLOP.ordinal()) {
			CardModel card1 = new CardModel(handString.substring(0, 2));
			CardModel card2 = new CardModel(handString.substring(2, 4));
			CardModel card3 = new CardModel(handString.substring(4, 6));
			sortedCards.add(card1);
			sortedCards.add(card2);
			sortedCards.add(card3);
		}

		if (dealStep.ordinal() >= DealStep.TURN.ordinal()) {
			CardModel card4 = new CardModel(handString.substring(6, 8));
			sortedCards.add(card4);
		}
		if (dealStep.equals(DealStep.RIVER)) {
			CardModel card5 = new CardModel(handString.substring(8, 10));
			sortedCards.add(card5);
		}

		this.dealStep = dealStep;
		initKickers();
	}

	@Override
	public String toString() {
		return "board=" + sortedCards;
	}

	/**
	 * return permutation of nbCard in board
	 * 
	 * @param nbCard
	 * @return
	 */
	public List<List<CardModel>> permutations(int nbCard) {
		ArrayList<CardModel> listCards = new ArrayList<>(sortedCards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();

		return permutationsOrdered.processSubsets(listCards, nbCard);
	}

	// =========================================================================
	// SEARCH DRAWS METHODS
	// =========================================================================
	/**
	 * Recherche du meilleurs tirages FULL
	 * <p>
	 * le board doit contenir au moins une paire.
	 * 
	 * @return
	 */
	public FullModel searchBestFullDraw() {

		BoardCategory boardCategory = BoardCategory.UNDEFINED;
		FullModel fullModel = null;

		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");
		Matcher matcher = pattern.matcher(whithoutSuit);

		Rank rankGroup = Rank.UNKNOWN;
		Rank rankThree = Rank.UNKNOWN;
		Rank rankPair = Rank.UNKNOWN;

		String group1 = "";
		String group2 = "";

		if (matcher.find()) {
			group1 = matcher.group(0);
		}
		if (matcher.find()) {
			group2 = matcher.group(0);
		}

		if (group1.length() > 0 && group2.length() > 0) {
			Rank rankGroup1 = Rank.fromShortName(group1.charAt(0));
			Rank rankGroup2 = Rank.fromShortName(group2.charAt(0));

			// FULL_HOUSE
			if (group1.length() == 6 || group2.length() == 6) {
				boardCategory = BoardCategory.FULL_HOUSE;

				if (group1.length() == 6) {
					rankThree = rankGroup1;
				}
				else {
					rankThree = rankGroup2;
				}
			}
			// TWO_PAIR
			else {
				boardCategory = BoardCategory.TWO_PAIR;
				rankGroup = Rank.fromShortName(group2.charAt(0));

				rankThree = (kickerPack1.ordinal() > rankGroup2.ordinal()) ? kickerPack1 : rankGroup2;
				rankPair = (rankGroup.ordinal() == kickerPack1.ordinal()) ? rankGroup1 : rankGroup;
			}
		}
		else if (group1.length() > 0) {

			if (group1.length() == 8) {
				// FOUR_OF_A_KIND
				boardCategory = BoardCategory.FOUR_OF_A_KIND;
				rankGroup = Rank.fromShortName(group1.charAt(0));
				rankThree = (kickerPack1.ordinal() > rankGroup.ordinal()) ? kickerPack1 : rankGroup;
				// THREE_OF_A_KIND
			} else if (group1.length() == 6) {
				boardCategory = BoardCategory.THREE_OF_A_KIND;
				rankGroup = Rank.fromShortName(group1.charAt(0));
				rankThree = (kickerPack1.ordinal() > rankGroup.ordinal()) ? kickerPack1 : rankGroup;
				// ONE_PAIR
			} else if (group1.length() == 4) {
				boardCategory = BoardCategory.ONE_PAIR;
				rankGroup = Rank.fromShortName(group1.charAt(0));

				rankThree = kickerPack1;
				rankPair = (rankGroup.ordinal() == kickerPack1.ordinal()) ? kickerPack2 : rankGroup;
			}
		}

		if (!boardCategory.equals(BoardCategory.UNDEFINED)) {
			fullModel = new FullModel(rankThree, rankPair, boardCategory, rankGroup, kickerPack1, kickerPack2);
		}

		return fullModel;
	}

	/**
	 * @param handModel
	 *            : pour vérifer si la main ne contient pas une carte du même
	 *            rang que le QUADS
	 * @return
	 */
	public ArrayList<DrawModel> searchQuadsDraw(HandModel handModel) {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		BoardCategory boardCategory = BoardCategory.UNDEFINED;

		String whithoutSuit = this.toStringByRank().replaceAll("[shdc]", ".");

		Pattern pattern = Pattern.compile("(\\w.)\\1{1,}");

		Matcher matcher = pattern.matcher(whithoutSuit);

		Rank rankGroup = Rank.UNKNOWN;

		while (matcher.find()) {
			String group = matcher.group(0);

			if (group.length() == 8) {
				// NO DRAW POSSIBLE : 4 same rank card on board
				break;
			} else if (group.length() == 6) {
				// THREE_OF_A_KIND
				boardCategory = BoardCategory.THREE_OF_A_KIND;
			} else if (group.length() == 4) {
				// ONE_PAIR
				boardCategory = BoardCategory.ONE_PAIR;
			}

			rankGroup = Rank.fromShortName(group.charAt(0));

			if (!boardCategory.equals(BoardCategory.UNDEFINED)) {
				// test if handModel has a same rank card
				if (handModel != null && !handModel.hasOnlyOneRankCard(rankGroup)) {
					QuadsModel quadsModel = new QuadsModel(rankGroup, boardCategory);
					listDraw.add(quadsModel);
				}
			}
		}

		return listDraw;
	}

	/**
	 * 
	 * @return
	 */
	public SetModel searchBestSetDraw() {
		ArrayList<CardModel> listCards = new ArrayList<>(sortedCards);
		Collections.reverse(listCards);

		CardModel topSet = listCards.get(0);

		SetModel setModel = new SetModel(topSet.getRank());

		return setModel;
	}

	/**
	 * 
	 * @return
	 */
	public DrawModel searchBestTwoPairDraw() {
		ArrayList<CardModel> listCards = new ArrayList<>(sortedCards);
		Collections.reverse(listCards);

		CardModel topPair1 = listCards.get(0);
		CardModel topPair2 = listCards.get(1);

		TwoPairModel twoPairModel = new TwoPairModel(topPair1.getRank(), topPair2.getRank());

		return twoPairModel;
	}

	/**
	 * 
	 * @return
	 */
	public DrawModel searchBestOnePairDraw() {

		ArrayList<CardModel> listCards = new ArrayList<>(sortedCards);
		Collections.reverse(listCards);

		CardModel topPair = listCards.get(0);

		OnePairModel onePairModel = new OnePairModel(topPair.getRank());

		return onePairModel;
	}

	/**
	 * 
	 * @return
	 * @throws StraightInitializeException
	 */
	public ArrayList<StraightModel> searchStraightDraw() throws StraightInitializeException {

		ArrayList<StraightModel> listDraw = new ArrayList<>();

		// draws HIGH
		listDraw.addAll(searchStraightDrawByHandCategory(HandCategory.STRAIGHT));

		if (listDraw.isEmpty()) {
			// draws ACE LOW
			listDraw.addAll(searchStraightDrawByHandCategory(HandCategory.STRAIGHT_ACE_LOW));
		}

		return listDraw;
	}

	/**
	 * 
	 * @param handCategory
	 *            : STRAIGHT ou STRAIGHT_ACE_LOW
	 * @return
	 * @throws StraightInitializeException
	 */
	private ArrayList<StraightModel> searchStraightDrawByHandCategory(HandCategory handCategory)
			throws StraightInitializeException {

		Assert.assertTrue(handCategory.equals(HandCategory.STRAIGHT)
				|| handCategory.equals(HandCategory.STRAIGHT_ACE_LOW));

		ArrayList<CardModel> cards = new ArrayList<>(sortedCards);

		if (handCategory.equals(HandCategory.STRAIGHT_ACE_LOW)) {
			RankAceLowComparator rankAsLowComparator = new RankAceLowComparator();
			Collections.sort(cards, rankAsLowComparator);
		}

		ArrayList<StraightModel> listDraw = new ArrayList<>();

		String rankString = CardUtils.toRankString(cards);

		int diffRank = 0;

		int nbDrawMax = rankString.length() - 2;

		int i = 0;

		int ordinalRank1 = 0;

		while (i < nbDrawMax) {
			Rank rank1 = Rank.fromShortName(rankString.charAt(i + 0));
			Rank rank2 = Rank.fromShortName(rankString.charAt(i + 1));
			Rank rank3 = Rank.fromShortName(rankString.charAt(i + 2));

			if (rank1.equals(rank2) || rank2.equals(rank3)) {
				i++;
				continue;
			}

			if (rank1.equals(Rank.ACE)) {
				ordinalRank1 = -1;
			} else {
				ordinalRank1 = rank1.ordinal();
			}

			diffRank = (rank3.ordinal() - rank2.ordinal()) + (rank2.ordinal() - ordinalRank1);

			String drawString = rankString.substring(0 + i, 3 + i);

			if (diffRank >= 2 && diffRank <= 4) {
				StraightModel straightModel = new StraightModel(handCategory, drawString);
				listDraw.add(straightModel);
			}

			i++;
		}

		return listDraw;
	}

	// =========================================================================
	// INIT ALL DRAWS
	// =========================================================================

	/**
	 * 
	 * @param handModel
	 * @return
	 * @throws StraightInitializeException
	 */
	public TreeSet<DrawModel> initDraws(HandModel handModel) throws StraightInitializeException {

		ArrayList<DrawModel> draws = new ArrayList<DrawModel>();
		Set<DrawModel> drawsSet;
		SortedSet<DrawModel> drawsSorted;

		DrawModel drawModel = null;

		if (!sortedCards.isEmpty()) {

			if (dealStep.equals(DealStep.FLOP) || dealStep.equals(DealStep.TURN)) {
				draws.addAll(searchFlushDraw(2, 4, null));
			}
			else if (dealStep.equals(DealStep.RIVER)) {
				draws.addAll(searchFlushDraw(3, 5, null));
			}

			draws.addAll(searchStraightDraw());

			draws.addAll(searchQuadsDraw(handModel));

			drawModel = searchBestFullDraw();
			if (drawModel != null) {
				draws.add(drawModel);
			}
			else {// no PAIR in board
				drawModel = searchBestSetDraw();
				if (drawModel != null) {
					draws.add(drawModel);
				}

				drawModel = searchBestTwoPairDraw();
				if (drawModel != null) {
					draws.add(drawModel);
				}

				drawModel = searchBestOnePairDraw();
				if (drawModel != null) {
					draws.add(drawModel);
				}
			}
		}

		// tri
		drawsSorted = new TreeSet<DrawModel>(draws);

		// suppression des draws inutiles
		cleanDraws(drawsSorted);

		// Suppression des doublons
		drawsSet = new HashSet<DrawModel>(drawsSorted);

		return new TreeSet<DrawModel>(drawsSet);
	}

	/**
	 * 
	 * @param boardDrawsSorted
	 */
	private void cleanDraws(SortedSet<DrawModel> boardDrawsSorted) {

		// return best STRAIGHT DRAW
		cleanStraightDraws(boardDrawsSorted);
	}
}
