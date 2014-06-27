package com.omahaBot.model.hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.enums.Suit;
import com.omahaBot.exception.CardPackNonValidException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CardPackModel;
import com.omahaBot.model.comparator.RankAceLowComparator;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.service.draw.StraightDrawService;
import com.omahaBot.utils.PermutationsOfN;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * <p>
 * http://www.leveluplunch.com/java/examples/sort-a-collection/
 * 
 * @author Julien
 * 
 */
public class HandModel extends CardPackModel {

	// PREFLOP, FLOP, TURN ou RIVER
	private final DealStep dealStep;

	public HandModel() {
		super();
		this.dealStep = DealStep.PRE_FLOP;
	}

	public HandModel(SortedSet<CardModel> sortedCards) {
		super(sortedCards);
		this.dealStep = DealStep.PRE_FLOP;
	}

	public HandModel(String cardPackString) throws CardPackNonValidException {
		super(cardPackString);
		this.dealStep = DealStep.PRE_FLOP;
	}

	public HandModel(DealStep dealStep) {
		super();
		this.dealStep = dealStep;
	}

	public HandModel(SortedSet<CardModel> sortedCards, DealStep dealStep) {
		super(sortedCards);
		this.dealStep = dealStep;
	}

	public HandModel(String cardPackString, DealStep dealStep) throws CardPackNonValidException {
		super(cardPackString);
		this.dealStep = dealStep;
	}

	@Override
	public String toString() {
		return "hand=" + sortedCards;
	}

	public void displayOut() {
		System.out.println("Ma main: " + sortedCards);
	}

	/**
	 * 
	 * @return
	 */
	public List<List<CardModel>> permutations() {
		ArrayList<CardModel> cards = new ArrayList<>(sortedCards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();
		return permutationsOrdered.processSubsets(cards, 2);
	}

	/**
	 * 
	 * @return
	 */
	public TreeSet<DrawModel> initCombinaisonDraws(BoardModel boardModel) {

		ArrayList<DrawModel> handDraws = new ArrayList<DrawModel>();
		Set<DrawModel> handDrawsSet;
		SortedSet<DrawModel> handDrawsSorted;

		SortedSet<CombinaisonModel> combinaisons = new TreeSet<CombinaisonModel>();

		for (List<CardModel> permutationHand : this.permutations()) {
			for (List<CardModel> permutationBoard : boardModel.permutations(3)) {
				CombinaisonModel combinaison = new CombinaisonModel(permutationHand, permutationBoard,
						this.hasFlushDraw());
				combinaisons.add(combinaison);
			}
		}

		// initialisation de handDraws
		for (CombinaisonModel combinaisonModel : combinaisons) {
			handDraws.addAll(combinaisonModel.initDraw());
		}

		// tri
		handDrawsSorted = new TreeSet<DrawModel>(handDraws);

		// suppression des draws inutiles
		cleanDraws(handDrawsSorted);

		// Suppression des doublons
		handDrawsSet = new HashSet<DrawModel>(handDrawsSorted);

		return new TreeSet<DrawModel>(handDrawsSet);
	}

	/**
	 * Prerequis : call before initCombinaisonDraws()
	 * 
	 * @param handDrawsSorted
	 * @return
	 */
	public boolean isStraight(SortedSet<DrawModel> handDrawsSorted) {

		Predicate<? super DrawModel> filter_rankDraws = (d -> d.getHandCategory().equals(HandCategory.STRAIGHT_ACE_LOW)
				|| d.getHandCategory().equals(HandCategory.STRAIGHT));

		Optional<DrawModel> bestRankDraw = handDrawsSorted
				.stream()
				.filter(filter_rankDraws)
				.findFirst();

		return bestRankDraw.isPresent();
	}

	// =========================================================================
	// DRAWS METHODS
	// =========================================================================

	/**
	 * 
	 * @return
	 */
	public boolean hasFlushDraw() {
		String whithoutRank = this.toStringBySuit().replaceAll("[^shdc]", "");
		return !Suit.ALL_SUIT.equals(whithoutRank);
	}

	private void cleanDraws(SortedSet<DrawModel> handDrawsSorted) {
		cleanRankDraws(handDrawsSorted);
		cleanStraightDraws(handDrawsSorted);
		cleanFlushDraws(handDrawsSorted);
	}

	/**
	 *
	 * @param handDrawsSorted
	 */
	private void cleanRankDraws(SortedSet<DrawModel> handDrawsSorted) {

		Predicate<? super DrawModel> filter_rankDraws = (d -> d.getHandCategory().equals(HandCategory.ONE_PAIR)
				|| d.getHandCategory().equals(HandCategory.TWO_PAIR)
				|| d.getHandCategory().equals(HandCategory.THREE_OF_A_KIND)
				|| d.getHandCategory().equals(HandCategory.FULL_HOUSE)
				|| d.getHandCategory().equals(HandCategory.FOUR_OF_A_KIND));

		Optional<DrawModel> bestRankDraw = handDrawsSorted
				.stream()
				.filter(filter_rankDraws)
				.findFirst();

		handDrawsSorted.removeIf(filter_rankDraws);

		if (bestRankDraw.isPresent()) {
			handDrawsSorted.add(bestRankDraw.get());
		}
	}

	/**
	 * for dealStep = FLOP or TURN
	 * 
	 * @param boardModel
	 * @return
	 */
	public StraightDrawType searchStraightDrawType(BoardModel boardModel) {

		StraightDrawType straightDrawType = StraightDrawType.NO_DRAW;
		StraightDrawType straightDrawTypeMax = StraightDrawType.NO_DRAW;

		// permutations de 2 cartes du board + hand => 6 cartes
		for (List<CardModel> permutationBoard : boardModel.permutations(2)) {

			SortedSet<CardModel> combinaisonCards = new TreeSet<CardModel>(permutationBoard);
			combinaisonCards.addAll(sortedCards);

			StraightDrawService straightDrawService = new StraightDrawService(combinaisonCards, boardModel);

			straightDrawType = straightDrawService.straightDrawType();

			// ACE LOW
			CardPackModel cardPackModel = new CardPackModel(combinaisonCards);

			if (cardPackModel.hasRankCard(Rank.ACE)) {
				ArrayList<CardModel> combinaisonCardsByAceLow = new ArrayList<>(cardPackModel.getCards());
				RankAceLowComparator rankAceLowComparator = new RankAceLowComparator();
				Collections.sort(combinaisonCardsByAceLow, rankAceLowComparator);

				StraightDrawService straightDrawServiceLow = new StraightDrawService(combinaisonCardsByAceLow,
						boardModel);
				StraightDrawType straightDrawTypeLow = straightDrawServiceLow.straightDrawType();

				if (straightDrawTypeLow.ordinal() > straightDrawType.ordinal()) {
					straightDrawType = straightDrawTypeLow;
				}
			}

			if (straightDrawType.ordinal() > straightDrawTypeMax.ordinal()) {
				straightDrawTypeMax = straightDrawType;
			}
		}

		return straightDrawTypeMax;
	}

	private void cleanFlushDraws(SortedSet<DrawModel> handDrawsSorted) {

		// suppression des FLUSH_DRAW si RIVER
		if (dealStep.equals(DealStep.RIVER)) {
			Predicate<? super DrawModel> filter_flushDraws = (d -> d.getHandCategory().equals(HandCategory.FLUSH_DRAW));
			handDrawsSorted.removeIf(filter_flushDraws);
		}
	}
}
