package com.omahaBot.model;

import java.util.List;

import com.omahaBot.enums.DealStep;

public class DealModel {
	private String dealId;
	private Double currentPot;
	private String title;
	private int nbPlayer;

	private List<PlayerModel> players;
	private List<CardModel> listCard;
	private DealStep step;

	public DealModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DealModel(String dealId, Double currentPot, String title) {
		super();
		this.dealId = dealId;
		this.currentPot = currentPot;
		this.title = title;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Double getCurrentPot() {
		return currentPot;
	}

	public void setCurrentPot(Double currentPot) {
		this.currentPot = currentPot;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

	public List<CardModel> getListCard() {
		return listCard;
	}

	public void setListCard(List<CardModel> listCard) {
		this.listCard = listCard;
	}

	public DealStep getStep() {
		return step;
	}

	public void setStep(DealStep step) {
		this.step = step;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}
	
	
}
