package com.omahaBot.model;

import java.util.List;

public class DealModel {
	private String dealId;
	private List<CardModel> listMyCard;
	private Double myStack;
	private Double smallBlind;
	private Double bigBlind;
	private int level;
	private int buttonPosition;
	private int nbPlayerTable;

	public DealModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public List<CardModel> getListMyCard() {
		return listMyCard;
	}

	public void setListMyCard(List<CardModel> listMyCard) {
		this.listMyCard = listMyCard;
	}

	public Double getMyStack() {
		return myStack;
	}

	public void setMyStack(Double myStack) {
		this.myStack = myStack;
	}

	public Double getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(Double smallBlind) {
		this.smallBlind = smallBlind;
	}

	public Double getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(Double bigBlind) {
		this.bigBlind = bigBlind;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getButtonPosition() {
		return buttonPosition;
	}

	public void setButtonPosition(int buttonPosition) {
		this.buttonPosition = buttonPosition;
	}

	public int getNbPlayerTable() {
		return nbPlayerTable;
	}

	public void setNbPlayerTable(int nbPlayerTable) {
		this.nbPlayerTable = nbPlayerTable;
	}

}
