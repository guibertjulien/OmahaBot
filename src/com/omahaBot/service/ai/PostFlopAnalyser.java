package com.omahaBot.service.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import lombok.Data;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.HandCategory;
import com.omahaBot.enums.StraightDrawType;
import com.omahaBot.exception.StraightInitializeException;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.strategy.AbstractStrategy;
import com.omahaBot.strategy.StrategyFactory;

/**
 * TODO continuation BET SLOW_PLAY DEAD_CARD POSITION BLUFF CAPACITY OUT STACK 2
 * DRAW CARRES
 * 
 * @author Julien
 *
 */
@Data
public class PostFlopAnalyser {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	// private TournamentModel tournamentModel;
	// private double pot = 0.0;

	// draws triés
	private SortedSet<DrawModel> handDrawsSorted;
	private SortedSet<DrawModel> boardDrawsSorted;

	private int handLevel;
	private boolean nutsForLevel;
	private StraightDrawType straightDrawType = StraightDrawType.NO_DRAW;
	private boolean imFirstToMove;

	public PostFlopAnalyser() {
	}

	public void analysePlayers() {
		// TODO
	}

	public void analyseTournament() {
		// TODO
	}

	public void analyseLastAction() {
		// TODO
	}

	public void analyseMyPosition(int nbTurnToBet, int nbAction) {
		if (nbTurnToBet == 1 && nbAction == 1) {
			System.out.println(">>>> I'm first to move");
			imFirstToMove = true;
		}
		else {
			imFirstToMove = false;
		}
	}

	public void analyseHand(HandModel handModel, BoardModel boardModel) {

		if (log.isDebugEnabled()) {
			log.debug(">> START analyseHand");
		}

		System.out.println("----------------------------------------------------------------");
		System.out.println(" ANALYSE POSTFLOP : " + handModel + " / " + boardModel);

		handLevel = 99;
		handDrawsSorted = handModel.initCombinaisonDraws(boardModel);

		try {
			boardDrawsSorted = boardModel.initDraws(handModel);
		} catch (StraightInitializeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!handDrawsSorted.isEmpty()) {

			// comparaison de handDraws & boardDraws
			DrawModel bestPermutation = handDrawsSorted.first();

			handLevel = 0;
			nutsForLevel = false;
			boolean find = false;
			boolean isFlushDraw = false;

			for (DrawModel drawModelBoard : boardDrawsSorted) {

				if (bestPermutation != null && drawModelBoard != null) {
					// same handCategory
					if (bestPermutation.getHandCategory().equals(drawModelBoard.getHandCategory())) {
						nutsForLevel = bestPermutation.isNuts(drawModelBoard);
						find = true;
						break;
					}
				}

				// don't update level if 2 flushDraw
				if (!(isFlushDraw && drawModelBoard.getHandCategory().equals(HandCategory.FLUSH_DRAW))) {
					handLevel++;
				}

				isFlushDraw = drawModelBoard.getHandCategory().equals(HandCategory.FLUSH_DRAW);
			}

			if (!find) {
				handLevel = 99;
			}

			if (bestPermutation.compareTo(boardDrawsSorted.first()) < 0) {
				handLevel = 0;
				nutsForLevel = true;
				System.out.println("==> BUG !!!");
				log.error("==> BUG !!!");
			}

		}

		System.out.println(" - HAND DRAWS :");

		// analyse de STRAIGHT DRAWS TYPE si pas de STRAIGHT
		if (!handModel.isStraight(handDrawsSorted)) {
			straightDrawType = handModel.searchStraightDrawType(boardModel);
			System.out.println(" straightDrawType=" + straightDrawType);
		}

		int level = 0;
		for (DrawModel drawModel : handDrawsSorted) {
			System.out.println("  #" + level + ": " + drawModel);
			level++;
		}

		System.out.println(" - BOARD DRAWS :");

		level = 0;
		for (DrawModel drawModel : boardDrawsSorted) {
			System.out.println("  #" + level + ": " + drawModel);
			level++;
		}

		if (ihaveNuts()) {
			System.out.println(">>>> I have NUTS !");
		}
		else {
			System.out.println(">>>> LEVEL=" + handLevel + " / NUTS=" + isNutsForLevel());
		}

		System.out.println("----------------------------------------------------------------");
	}

	public BettingDecision decide(DealStep dealStep, HandModel myHand, int nbTurnToBet) {

		if (log.isDebugEnabled()) {
			log.debug(">> START decide " + dealStep);
		}

		System.out.println("----------------------------------------------------------------");
		System.out.println(" DECIDE : " + dealStep);

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		// TODO better : caution straightDrawType
		if (!handDrawsSorted.isEmpty()) {

			DrawModel bestPermutation = handDrawsSorted.first();
			ArrayList<DrawModel> boardDraws = new ArrayList<DrawModel>(boardDrawsSorted);

			List<DrawModel> boardflushDraws = boardDraws.stream()
					.filter(d -> d.getHandCategory().equals(HandCategory.FLUSH))
					.collect(Collectors.toList());

			if (boardflushDraws.isEmpty()) {
				boardflushDraws = boardDraws.stream().filter(d -> d.getHandCategory().equals(HandCategory.FLUSH_DRAW))
						.collect(Collectors.toList());
			}

			AbstractStrategy strategyFactory = StrategyFactory.getStrategy(bestPermutation.getHandCategory(),
					nbTurnToBet, imFirstToMove);

			switch (dealStep) {
			case FLOP:
				bettingDecision = strategyFactory.decideAtFlop(bestPermutation, ihaveNuts(),
						boardDraws,	nutsForLevel, straightDrawType);
				break;
			case TURN:
				bettingDecision = strategyFactory.decideAtTurn(bestPermutation, ihaveNuts(),
						boardDraws, nutsForLevel, straightDrawType);
				break;
			case RIVER:
				bettingDecision = strategyFactory.decideAtRiver(bestPermutation, ihaveNuts(),
						boardDraws, nutsForLevel, straightDrawType);
				break;
			default:
				break;
			}
		}

		System.out.println(">>>> I " + bettingDecision + " at " + dealStep + " (" + nbTurnToBet + ")");
		System.out.println("----------------------------------------------------------------");

		return bettingDecision;
	}

	public boolean ihaveNuts() {
		return handLevel == 0 && nutsForLevel;
	}
}
