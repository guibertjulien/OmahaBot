package com.omahaBot.model;

import java.util.ArrayList;

import com.omahaBot.enums.PlayerAction;

public class ActionModel {

	private int nbPlayer;
	private int activePlayer;
	private PlayerAction playerAction;
	private ArrayList<PlayerModel> listPlayer;

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

	public int getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
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

}
