package com.omahaBot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.utils.PermutationsOfN;

/**
 * http://fr.pokerlistings.com/potlimit-omaha-mains-de-depart
 * 
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
		return "HandModel [Cards=" + cards + "]";
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

		// suppression des draws intutiles
		cleanDraws(handDraws);
		
		// tri
		handDrawsSorted = new TreeSet<DrawModel>(handDraws);
		
		// Suppression des doublons
		handDrawsSet = new HashSet<DrawModel>(handDrawsSorted);

		return new TreeSet<DrawModel>(handDrawsSet);
	}
	
	private void cleanDraws(ArrayList<DrawModel> handDraws) {
		
		// si TWO_PAIR draw
		if (handDraws.stream().filter(d -> d.getHandCategory().equals(HandCategory.TWO_PAIR)).findFirst().isPresent()) {
			// suppression des ONE_PAIR draw
			handDraws.removeIf(d -> d.getHandCategory().equals(HandCategory.ONE_PAIR));
		}
		
	}
}
