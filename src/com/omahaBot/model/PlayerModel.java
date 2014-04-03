package com.omahaBot.model;

import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;

public class PlayerModel {

	private PlayerBlock playerBlock;
	private String name;
	private Double stack;
	private Integer position;
	private PlayerAction action;
	private boolean isActiv;

	public PlayerModel() {
		super();
		this.action = PlayerAction.INACTIV;
	}

	public PlayerModel(PlayerBlock playerBlock, String name, Double stack, int position) {
		super();
		this.playerBlock = playerBlock;
		this.name = name;
		this.stack = stack;
		this.position = position;
		this.action = PlayerAction.INACTIV;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getStack() {
		return stack;
	}

	public void setStack(Double stack) {
		this.stack = stack;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public PlayerAction getAction() {
		return action;
	}

	public void setAction(PlayerAction action) {
		this.action = action;
	}

	public PlayerBlock getPlayerBlock() {
		return playerBlock;
	}

	public void setPlayerBlock(PlayerBlock playerBlock) {
		this.playerBlock = playerBlock;
	}

	@Override
	public String toString() {
		return "PlayerModel [playerBlock=" + playerBlock + ", name=" + name + ", stack=" + stack + ", position="
				+ position + ", action=" + action + "]";
	}

	public boolean isActiv() {
		return isActiv;
	}

	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
	}

}
