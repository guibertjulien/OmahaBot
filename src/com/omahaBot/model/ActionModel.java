package com.omahaBot.model;

import java.util.ArrayList;

import com.omahaBot.enums.PlayerAction;

public class ActionModel {

	private int nbPlayer;
	private int positionPlayerTurnPlay;
	private PlayerAction playerAction;
	private ArrayList<PlayerModel> listPlayer;
	private Double lastBet;
	private boolean firstTurnBet = true;

	public ActionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public int getPositionPlayerTurnPlay() {
		return positionPlayerTurnPlay;
	}

	public void setPositionPlayerTurnPlay(int positionPlayerTurnPlay) {
		this.positionPlayerTurnPlay = positionPlayerTurnPlay;
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	public void setPlayerAction(PlayerAction playerAction) {
		this.playerAction = playerAction;
	}

	public ArrayList<PlayerModel> getListPlayer() {
		return listPlayer;
	}

	public void setListPlayer(ArrayList<PlayerModel> listPlayer) {
		this.listPlayer = listPlayer;
	}

	public Double getLastBet() {
		return lastBet;
	}

	public void setLastBet(Double lastBet) {
		this.lastBet = lastBet;
	}

	public boolean isFirstTurnBet() {
		return firstTurnBet;
	}

	public void setFirstTurnBet(boolean firstTurnBet) {
		this.firstTurnBet = firstTurnBet;
	}

	
}
