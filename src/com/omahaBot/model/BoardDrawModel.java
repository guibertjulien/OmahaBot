package com.omahaBot.model;

import lombok.Data;

import com.omahaBot.enums.BoardDrawPower;

public @Data class BoardDrawModel {

	private final BoardDrawPower boardDrawPower;

	private final int nbOut;

	private final double percent;
}
