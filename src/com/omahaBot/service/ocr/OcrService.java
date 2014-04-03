package com.omahaBot.service.ocr;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.omahaBot.enums.BoardCards;
import com.omahaBot.enums.DealStep;
import com.omahaBot.model.CardModel;

public interface OcrService {

	String scanDealId();

	CardModel scanCard(BoardCards card);

	String scanBlock(Rectangle rectangle, BufferedImage capture, String whiteList, int mode, String blockName);

	List<CardModel> scanBoardCards(DealStep dealStep);

	Double scanPot();
	
	boolean checkTable();

}
