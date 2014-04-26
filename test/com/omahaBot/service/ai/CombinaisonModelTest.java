package com.omahaBot.service.ai;

import java.util.ArrayList;

import org.junit.Test;

import com.omahaBot.model.CardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.DrawModel;

public class CombinaisonModelTest {

	@Test
	public void testFindFlushDraw() {

		ArrayList<DrawModel> listDraw = new ArrayList<>();
		CombinaisonModel combinaisonModel;

		ArrayList<CardModel> listHoleCard = new ArrayList<>();
		ArrayList<CardModel> listBoardCard = new ArrayList<>();

		listHoleCard.add(new CardModel("As"));
		listHoleCard.add(new CardModel("Ks"));
		listHoleCard.add(new CardModel("2s"));
		listHoleCard.add(new CardModel("3s"));
		listHoleCard.add(new CardModel("4s"));
		
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

	}
}
