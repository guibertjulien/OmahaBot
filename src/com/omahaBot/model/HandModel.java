package com.omahaBot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.utils.PermutationsOfN;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * TODO http://www.leveluplunch.com/java/examples/sort-a-collection/
 * @author Julien
 * 
 */
public class HandModel extends CardPackModel {

	public HandModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HandModel(SortedSet<CardModel> cards) {
		super(cards);
		// TODO Auto-generated constructor stub
	}

	public HandModel(String cardPackString) {
		super(cardPackString);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Hand : " + cards;
	}

	public boolean isTwoPairSuited()
	{
		return isTwoPair() && isTwoSuit();
	}

	public boolean isTwoPairConnected()
	{
		// TODO
		return false;
	}

	public boolean isTwoPairSuitedConnector()
	{
		// TODO
		return false;
	}

	public List<List<CardModel>> permutations() {
		ArrayList<CardModel> listCards = new ArrayList<>(cards);
		PermutationsOfN<CardModel> permutationsOrdered = new PermutationsOfN<CardModel>();

		return permutationsOrdered.processSubsets(listCards, 2);
	}

	public boolean hasFlushDraw() {
		String whithoutRank = this.toStringBySuit().replaceAll("[^shdc]", "");
		return !Suit.ALL_SUIT.equals(whithoutRank);
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
			for (List<CardModel> permutationBoard : boardModel.permutations()) {
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

	private void cleanDraws(SortedSet<DrawModel> handDrawsSorted) {
		cleanRankDraws(handDrawsSorted);
		cleanConnectorDraws(handDrawsSorted);
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
		handDrawsSorted.add(bestRankDraw.get());
	}

	/**
	 *
	 * @param handDrawsSorted
	 */
	private void cleanConnectorDraws(SortedSet<DrawModel> handDrawsSorted) {
		// TODO
	}
}
