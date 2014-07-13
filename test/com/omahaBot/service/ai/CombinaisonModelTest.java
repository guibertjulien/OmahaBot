package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Rank;
import com.omahaBot.exception.CardPackNoValidException;
import com.omahaBot.model.CardPackModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.StraightModel;
import com.omahaBot.model.draw.TwoPairModel;
import com.omahaBot.model.hand.CombinaisonModel;

public class CombinaisonModelTest {

	@Test
	public void testFindFourOfAKind() throws CardPackNoValidException {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		QuadsModel quadsModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("AdAh");
		permutationBoard = new CardPackModel("AcAsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof QuadsModel);
		quadsModel = (QuadsModel) drawModel;
		assertTrue(quadsModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + quadsModel);

		permutationHand = new CardPackModel("AdKh");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof QuadsModel);
		quadsModel = (QuadsModel) drawModel;
		assertTrue(quadsModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + quadsModel);
	}

	@Test
	public void testFindFull() throws CardPackNoValidException {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		FullModel fullModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("AdAs");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.KING));
		assertTrue(fullModel.getRankPair().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdKs");
		permutationBoard = new CardPackModel("AdAsAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.ACE));
		assertTrue(fullModel.getRankPair().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdAs");
		permutationBoard = new CardPackModel("AdKcKs");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.KING));
		assertTrue(fullModel.getRankPair().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdAs");
		permutationBoard = new CardPackModel("AdKcAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.ACE));
		assertTrue(fullModel.getRankPair().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + fullModel);
	}

	@Test
	public void testFindThreeOfAKind() throws CardPackNoValidException {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		SetModel setModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("Ad2s");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("Kd2s");
		permutationBoard = new CardPackModel("AdAsAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("Kd2s");
		permutationBoard = new CardPackModel("AdKcKs");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("2dAs");
		permutationBoard = new CardPackModel("AdKcAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + setModel);
	}
	
	@Test
	public void testFindTwoPair() throws CardPackNoValidException {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		TwoPairModel twoPairModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("Ad2s");
		permutationBoard = new CardPackModel("As2dKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchBestRankDraw();
		assertTrue(drawModel instanceof TwoPairModel);
		twoPairModel = (TwoPairModel) drawModel;
		assertTrue(twoPairModel.getRankPair1().equals(Rank.ACE));
		assertTrue(twoPairModel.getRankPair2().equals(Rank.TWO));
		System.out.println(combinaisonModel + " " + twoPairModel);
	}
	
	@Test
	public void testFindStraight() throws CardPackNoValidException {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		StraightModel straightModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("3d5s");
		permutationBoard = new CardPackModel("4s6d7c");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchStraight();
		assertTrue(drawModel instanceof StraightModel);
		straightModel = (StraightModel) drawModel;
		assertTrue(straightModel.getRank().equals(Rank.SEVEN));
		System.out.println(combinaisonModel + " " + straightModel);
		
		permutationHand = new CardPackModel("3d5s");
		permutationBoard = new CardPackModel("As4d2c");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchStraight();
		assertTrue(drawModel instanceof StraightModel);
		straightModel = (StraightModel) drawModel;
		assertTrue(straightModel.getRank().equals(Rank.FIVE));
		System.out.println(combinaisonModel + " " + straightModel);
		
		permutationHand = new CardPackModel("3d8s");
		permutationBoard = new CardPackModel("As4d2c");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards(), DealStep.PRE_FLOP);
		drawModel = combinaisonModel.searchStraight();
		assertTrue(drawModel == null);
	}
	
	// @Test
	// public void testFindFlushDraw() {
	//
	// ArrayList<DrawModel> listDraw = new ArrayList<>();
	// CombinaisonModel combinaisonModel;
	//
	// ArrayList<CardModel> listHoleCard = new ArrayList<>();
	// ArrayList<CardModel> listBoardCard = new ArrayList<>();
	//
	// listHoleCard.add(new CardModel("As"));
	// listHoleCard.add(new CardModel("Ks"));
	// listBoardCard.add(new CardModel("2s"));
	// listBoardCard.add(new CardModel("3s"));
	// listBoardCard.add(new CardModel("4d"));
	//
	// combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
	// listDraw = combinaisonModel.initDraw();
	// System.out.println(listDraw);
	//
	// }
}
