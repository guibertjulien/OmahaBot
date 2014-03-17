package com.omahaBot.model;

import java.util.List;

import com.omahaBot.enums.DealStep;

public class DealStepModel {
	private DealStep dealStep;
	private List<CardModel> listBoardCard;

	public DealStepModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DealStep getDealStep() {
		return dealStep;
	}

	public void setDealStep(DealStep dealStep) {
		this.dealStep = dealStep;
	}

	public List<CardModel> getListBoardCard() {
		return listBoardCard;
	}

	public void setListBoardCard(List<CardModel> listBoardCard) {
		this.listBoardCard = listBoardCard;
	}

	@Override
	public String toString() {
		return "DealStepModel [dealStep=" + dealStep + ", listBoardCard=" + listBoardCard + "]";
	}

}
