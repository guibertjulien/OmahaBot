package com.omahaBot.service.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.Data;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.HandModel;
import com.omahaBot.model.TournamentModel;
import com.omahaBot.model.draw.DrawModel;

/**
 * TODO
 *  continuation BET
 *  SLOW_PLAY
 *  DEAD_CARD
 *  POSITION
 *  BLUFF CAPACITY
 *  OUT
 *  STACK
 *  2 DRAW CARRES
 * @author Julien
 *
 */
@Data
public class AnalyserServiceImpl {

	private TournamentModel tournamentModel;

	private int nbBetTurn = 1;

	private double pot = 0.0;

	// draws triés
	private SortedSet<DrawModel> handDrawsSorted;
	private SortedSet<DrawModel> boardDrawsSorted;

	private int handLevel;
	private boolean nutsForLevel;

	public AnalyserServiceImpl() {
	}

	public void analyseHand(HandModel myHand, BoardModel board, DealStep dealStep) {

		switch (dealStep) {
		case PRE_FLOP:
			analyseHandPreFlop();
			break;
		case FLOP:
		case TURN:
		case RIVER:
			analyseHandPostFlop(myHand, board);
			break;
		default:
			break;
		}
	}

	public void analysePlayers() {

	}

	public void analyseTournament() {

	}
	
	public void analyseLastAction() {

	}
	
	public void analyseMyPosition() {

	}

	public BettingDecision decide() {
		BettingDecision bettingDecision = BettingDecision.FOLD_ALWAYS;
		return bettingDecision;
	}

	private void analyseHandPreFlop() {

	}

	public void analyseHandPostFlop(HandModel handModel, BoardModel boardModel) {
		handDrawsSorted = handModel.initCombinaisonDraws(boardModel);
		boardDrawsSorted = new TreeSet<DrawModel>(boardModel.initDraws(handModel));

		// comparaison de handDraws & boardDraws
		DrawModel bestPermutation = handDrawsSorted.first();
		
		handLevel = 0;
		nutsForLevel = false;
		
		for (DrawModel drawModelBoard : boardDrawsSorted) {

			if (bestPermutation != null && drawModelBoard != null) {
				// same handCategory
				if (bestPermutation.getHandCategory().equals(drawModelBoard.getHandCategory())) {
					nutsForLevel = bestPermutation.isNuts(drawModelBoard);
					break;
				}
			}

			handLevel++;
		}
		
		System.out.println("\n=> HAND DRAWS : ");
		for (DrawModel drawModel : handDrawsSorted) {
			System.out.println(drawModel);
		}
		
		System.out.println("\n=> BOARD DRAWS : ");
		int level=0;
		for (DrawModel drawModel : boardDrawsSorted) {
			System.out.println("Level " + level + " : " + drawModel);
			level++;
		}		
		
		System.out.println("\n=> ANALYSE : ");
		System.out.println("Level " + handLevel + " / Nuts : " + isNutsForLevel());
	}

	public BettingDecision decideFlop() {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		DrawModel bestPermutation = handDrawsSorted.first();
		ArrayList<DrawModel> boardDraws = new ArrayList<DrawModel>(boardDrawsSorted);

		List<DrawModel> boardflushDraws = boardDraws.stream().filter(d -> d.getHandCategory().equals(HandCategory.FLUSH))
				.collect(Collectors.toList());
		
		if (boardflushDraws.isEmpty()) {
			boardflushDraws = boardDraws.stream().filter(d -> d.getHandCategory().equals(HandCategory.FLUSH_DRAW))
					.collect(Collectors.toList());
		}

		switch (bestPermutation.getHandCategory()) {
		case FOUR_OF_A_KIND:
		case FULL_HOUSE:
			if (handLevel == 0) {
				BettingDecision.randomBetween(BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
			}
			else {
				BettingDecision.randomBetween(BettingDecision.CHECK_CALL, BettingDecision.CHECK_FOLD);
			}
			break;
		case FLUSH:
			if (handLevel == 0) {
				BettingDecision.randomBetween(BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
			}
			else {
				BettingDecision.randomBetween(BettingDecision.CHECK_CALL, BettingDecision.CHECK_FOLD);
			}
			break;
		case THREE_OF_A_KIND:
			if (boardflushDraws.isEmpty()) {
				bettingDecision = BettingDecision.ALLIN;
			}
			else {
				if (boardflushDraws.get(0).getHandCategory().equals(HandCategory.FLUSH)) {
					bettingDecision = BettingDecision.CHECK_FOLD;
				}
				else {
					bettingDecision = BettingDecision.CHECK_CALL;
				}
			}
			break;
		case TWO_PAIR:
		case ONE_PAIR:
			bettingDecision = BettingDecision.CHECK_FOLD;
			break;
		default:
			bettingDecision = BettingDecision.CHECK_FOLD;
			break;
		}
		
		System.out.println("\n=> DECISION : ");
		System.out.println(bettingDecision);
		
		return bettingDecision;
	}
}
