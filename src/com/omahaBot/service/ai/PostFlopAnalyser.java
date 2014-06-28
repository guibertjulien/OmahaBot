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
	//
	// private int nbBetTurn = 1;
	//
	// private double pot = 0.0;

	// draws triés
	private SortedSet<DrawModel> handDrawsSorted;
	private SortedSet<DrawModel> boardDrawsSorted;

	private int handLevel;
	private boolean nutsForLevel;

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

	public void analyseMyPosition() {
		// TODO
	}

	public void analyseHand(HandModel handModel, BoardModel boardModel) {
		
		if (log.isDebugEnabled()) {
			log.debug(">> START analyseHand");
		}

		System.out.println("############################################");
		System.out.println(">> START analyseHand ");
		System.out.println(handModel.toString());
		System.out.println(boardModel.toString());
		System.out.println("############################################");		
		
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
			
		}
		
		System.out.println("--------------------------------------------");
		System.out.println("- HAND DRAWS");
		System.out.println("--------------------------------------------");
		
		// analyse de STRAIGHT DRAWS TYPE si pas de STRAIGHT
		if (!handModel.isStraight(handDrawsSorted)) {
			StraightDrawType straightDrawType = handModel.searchStraightDrawType(boardModel);
			System.out.println("straightDrawType=" + straightDrawType);			
		}

		for (DrawModel drawModel : handDrawsSorted) {
			System.out.println(drawModel);
		}

		System.out.println("--------------------------------------------");
		System.out.println("- BOARD DRAWS");
		System.out.println("--------------------------------------------");
		
		int level = 0;
		for (DrawModel drawModel : boardDrawsSorted) {
 			System.out.println("#" + level + ": " + drawModel);
			level++;
		}

		System.out.println("============================================");
		if (isNuts()) {
			System.out.println("==> ANALYSE: NUTS !!!");	
		}
		else {
			System.out.println("==> ANALYSE: LEVEL=" + handLevel + " / NUTS for level=" + isNutsForLevel());
		}
		System.out.println("============================================");
	}

	public BettingDecision decide(DealStep dealStep, HandModel myHand) {

		if (log.isDebugEnabled()) {
			log.debug(">> START decide " + dealStep);
		}

		System.out.println("############################################");
		System.out.println(">> START decide " + dealStep);
		System.out.println("############################################");		
		
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		switch (dealStep) {
		case FLOP:
			bettingDecision = decideFlop();
			break;
		case TURN:
			bettingDecision = decideRiver();
			break;
		case RIVER:
			bettingDecision = decideTurn();
			break;
		default:
			break;
		}

		System.out.println("============================================");
		System.out.println("==> Moi: Je " + bettingDecision);
		System.out.println("============================================");
		
		return bettingDecision;
	}

	public BettingDecision decideFlop() {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

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

			switch (bestPermutation.getHandCategory()) {
			case FOUR_OF_A_KIND:
			case FULL_HOUSE:
				if (handLevel == 0) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
				}
				else {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.CHECK_CALL, BettingDecision.CHECK_FOLD);
				}
				break;
			case FLUSH:
			case FLUSH_DRAW:
				if (handLevel == 0) {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
				}
				else {
					bettingDecision = BettingDecision.randomBetween(BettingDecision.CHECK_CALL, BettingDecision.CHECK_FOLD);
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
		}

		return bettingDecision;
	}

	public BettingDecision decideTurn() {
		return decideFlop();
	}
	

	public BettingDecision decideRiver() {
		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		if (handLevel == 0) {
			bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE);
		} else if (handLevel == 1) {
			bettingDecision = BettingDecision.randomBetween(BettingDecision.ALLIN, BettingDecision.BET_RAISE, BettingDecision.CHECK_CALL);
		} else if (handLevel == 2) {
			bettingDecision = BettingDecision.randomBetween(BettingDecision.CHECK_CALL, BettingDecision.CHECK_FOLD);
		} else {
			bettingDecision = BettingDecision.CHECK_FOLD;
		}

		return bettingDecision;
	}
	
	public boolean isNuts () {
		return handLevel == 0 && nutsForLevel;
	}
}
