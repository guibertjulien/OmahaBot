package com.omahaBot.service.ai;

import java.util.ArrayList;

import org.junit.Test;

import com.omahaBot.enums.DealStep;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.DrawModel;
import com.omahaBot.model.HandModel;

public class CombinaisonModelTest {

	@Test
	public void testFindFlushDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();
		CombinaisonModel combinaisonModel;

		ArrayList<CardModel> listHoleCard = new ArrayList<>();
		ArrayList<CardModel> listBoardCard = new ArrayList<>();

		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ks"));
		listBoardCard.add(new CardModel("2s"));
		listBoardCard.add(new CardModel("3s"));
		listBoardCard.add(new CardModel("4d"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);

	}
	
	@Test
	public void testFindFull() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();
		CombinaisonModel combinaisonModel;

		ArrayList<CardModel> listHoleCard = new ArrayList<>();
		ArrayList<CardModel> listBoardCard = new ArrayList<>();

		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ah"));
		listBoardCard.add(new CardModel("Ad"));
		listBoardCard.add(new CardModel("3s"));
		listBoardCard.add(new CardModel("3h"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);
		
		listHoleCard.clear();
		listBoardCard.clear();
		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ah"));
		listBoardCard.add(new CardModel("3d"));
		listBoardCard.add(new CardModel("3s"));
		listBoardCard.add(new CardModel("3h"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);
		
		listHoleCard.clear();
		listBoardCard.clear();
		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ah"));
		listBoardCard.add(new CardModel("Ad"));
		listBoardCard.add(new CardModel("3s"));
		listBoardCard.add(new CardModel("Qh"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);
		
		listHoleCard.clear();
		listBoardCard.clear();
		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ah"));
		listBoardCard.add(new CardModel("3d"));
		listBoardCard.add(new CardModel("3s"));
		listBoardCard.add(new CardModel("Qh"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);
		
		listHoleCard.clear();
		listBoardCard.clear();
		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ah"));
		listBoardCard.add(new CardModel("3d"));
		listBoardCard.add(new CardModel("5s"));
		listBoardCard.add(new CardModel("Qh"));
		
		combinaisonModel = new CombinaisonModel(listHoleCard, listBoardCard);
		listDraw = combinaisonModel.initDraw();
		System.out.println(listDraw);

	}
	
	@Test
	public void test1() {
		ArrayList<DrawModel> listDraw = new ArrayList<>();

		HandModel handModel = new HandModel("AsAdKsKd");
		BoardModel boardModel = new BoardModel("8s6sAh8c7s", DealStep.RIVER);   
		
		PostFlopAnalyserServiceImpl analyserServiceImpl = new PostFlopAnalyserServiceImpl();
		
		ArrayList<CombinaisonModel> combinaisonModels = analyserServiceImpl.initCombinaisons(handModel, boardModel);
		
		System.out.println(combinaisonModels);
		
		for (CombinaisonModel combinaisonModel : combinaisonModels) {
			listDraw.addAll(combinaisonModel.initDraw());
		}
		
		System.out.println("-----------");
		System.out.println(listDraw);
	}
}
