package com.omahaBot.service.ai;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.omahaBot.enums.Rank;
import com.omahaBot.model.CardPackModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.draw.FullModel;
import com.omahaBot.model.draw.QuadsModel;
import com.omahaBot.model.draw.SetModel;
import com.omahaBot.model.draw.TwoPairModel;

public class CombinaisonModelTest {

	@Test
	public void testFindFourOfAKind() {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		QuadsModel quadsModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("AdAh");
		permutationBoard = new CardPackModel("AcAsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof QuadsModel);
		quadsModel = (QuadsModel) drawModel;
		assertTrue(quadsModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + quadsModel);

		permutationHand = new CardPackModel("AdKh");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof QuadsModel);
		quadsModel = (QuadsModel) drawModel;
		assertTrue(quadsModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + quadsModel);
	}

	@Test
	public void testFindFull() {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		FullModel fullModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("AdAs");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.KING));
		assertTrue(fullModel.getRankPair().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdKs");
		permutationBoard = new CardPackModel("AdAsAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.ACE));
		assertTrue(fullModel.getRankPair().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdAs");
		permutationBoard = new CardPackModel("AdKcKs");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.KING));
		assertTrue(fullModel.getRankPair().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + fullModel);

		permutationHand = new CardPackModel("KdAs");
		permutationBoard = new CardPackModel("AdKcAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof FullModel);
		fullModel = (FullModel) drawModel;
		assertTrue(fullModel.getRankThree().equals(Rank.ACE));
		assertTrue(fullModel.getRankPair().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + fullModel);
	}

	@Test
	public void testFindThreeOfAKind() {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		SetModel setModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("Ad2s");
		permutationBoard = new CardPackModel("KdKsKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("Kd2s");
		permutationBoard = new CardPackModel("AdAsAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("Kd2s");
		permutationBoard = new CardPackModel("AdKcKs");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.KING));
		System.out.println(combinaisonModel + " " + setModel);

		permutationHand = new CardPackModel("2dAs");
		permutationBoard = new CardPackModel("AdKcAc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof SetModel);
		setModel = (SetModel) drawModel;
		assertTrue(setModel.getRank().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + setModel);
	}
	
	@Test
	public void testFindTwoPair() {
		CombinaisonModel combinaisonModel;
		DrawModel drawModel;
		TwoPairModel twoPairModel;
		CardPackModel permutationHand;
		CardPackModel permutationBoard;

		permutationHand = new CardPackModel("Ad2s");
		permutationBoard = new CardPackModel("As2dKc");

		combinaisonModel = new CombinaisonModel(permutationHand.getCards(), permutationBoard.getCards());
		drawModel = combinaisonModel.searchRankDraw();
		assertTrue(drawModel instanceof TwoPairModel);
		twoPairModel = (TwoPairModel) drawModel;
		assertTrue(twoPairModel.getRankPair1().equals(Rank.TWO));
		assertTrue(twoPairModel.getRankPair2().equals(Rank.ACE));
		System.out.println(combinaisonModel + " " + twoPairModel);
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
