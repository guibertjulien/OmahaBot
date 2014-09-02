package com.omahaBot.service.ocr;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;

import com.omahaBot.enums.CardBlock;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.PlayerModel;

public interface OcrService {

	String scanDealId();

	String scanBlock(Rectangle rectangle, BufferedImage capture, String whiteList, int mode, String blockName);

	List<CardModel> scanBoardCards(DealStep dealStep);

	BigDecimal scanPot();
	
	PlayerModel scanPlayer(PlayerBlock playerBlock);
	
	boolean checkTable();

	SortedSet<CardModel> scanMyHand();

	CardModel scanCard(CardBlock card);

	BigDecimal scanCheckOrCallButton();

	void screenCaps();

	BigDecimal scanPlayerStack(PlayerBlock playerBlock);
}
