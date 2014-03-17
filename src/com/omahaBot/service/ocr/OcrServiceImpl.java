package com.omahaBot.service.ocr;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.TessAPI1.TessPageSegMode;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.vietocr.ImageHelper;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.Block;
import com.omahaBot.enums.BlockType;
import com.omahaBot.enums.BoardCards;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.model.PlayerModel;

public class OcrServiceImpl implements OcrService {

	private final static Logger LOGGER = Logger.getLogger(OcrServiceImpl.class.getName());

	protected BufferedImage captureTable;

	private static final String TABLE_FILENAME = "tableCaps.png";
	private static final String CAPS_DIRECTORY = "C:/_DEV/caps/";

	private List<PlayerModel> listPlayer = new ArrayList<PlayerModel>();
	private List<CardModel> listCard = new ArrayList<CardModel>();

	// JNA Interface Mapping
	private Tesseract instance = Tesseract.getInstance();

	private Robot robot;

	private DealModel dealModel = null;

	private static OcrServiceImpl INSTANCE = new OcrServiceImpl();

	private OcrServiceImpl() {
		super();

		try {
			robot = new Robot();
		} catch (AWTException e) {
			LOGGER.warning(e.getMessage());
		}
	}

	public static OcrServiceImpl getInstance() {
		return INSTANCE;
	}

//	@Override
//	public DealModel scanNewDeal() {
//		try {
//			dealModel = new DealModel();
//			
//			captureTable = robot.createScreenCapture(Consts.BLOCK_TABLE);
//			ImageIO.write(captureTable, "png", new File(CAPS_DIRECTORY + TABLE_FILENAME));
//			
//			// TODO
//			
//		} catch (IOException e) {
//			LOGGER.warning(e.getMessage());
//		}
//
//		return dealModel;
//	}

	@Override
	public String scanDealId() {
		Rectangle block = new Rectangle(Consts.BLOCK_DEAL_ID);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, Consts.WHITELIST_DEAL_ID,
				TessPageSegMode.PSM_SINGLE_LINE, BlockType.DEAL_ID.name());

		return OcrUtils.cleanDealId(ocr);
	}
	
	@Override
	public Double scanPot() {

		// capture de pot
		Rectangle block = new Rectangle(Consts.BLOCK_POT);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, "",
				TessPageSegMode.PSM_SINGLE_LINE, BlockType.POT.name());

		return OcrUtils.cleanPot(ocr);
	}

	private void scanPlayerList() {
		listPlayer.clear();

		int position = 1;

		for (PlayerBlock player : PlayerBlock.values()) {

			PlayerModel playerModel = scanPlayer(player, position);
			listPlayer.add(playerModel);
			//LOGGER.info(playerModel.toString());
			position++;
		}
	}

	@Override
	public List<CardModel> scanBoardCards(DealStep dealStep) {
		listCard.clear();
		
		switch (dealStep) {
		case FLOP:
			int nbCard = 0;
			for (BoardCards card : BoardCards.values()) {
				System.out.println(card.name());
				CardModel cardModel = scanCard(card);
				listCard.add(cardModel);
				nbCard++;
				if (nbCard == 3) {
					break;
				}
			}		
			break;
		case TURN:
			CardModel cardModel1 = scanCard(BoardCards.CARD4_TURN);
			listCard.add(cardModel1);
			break;
		case RIVER:
			CardModel cardModel2 = scanCard(BoardCards.CARD5_RIVER);
			listCard.add(cardModel2);
			break;
		default:
			break;
		}
		
		return listCard;
	}

	private PlayerModel scanPlayer(PlayerBlock playerBlock, int position) {

		Rectangle block_line1 = new Rectangle(playerBlock.getBlock().x, playerBlock.getBlock().y,
				playerBlock.getBlock().width, Consts.BLOCK_PLAYER_DATA_HEIGHT);
		Rectangle block_line2 = new Rectangle(playerBlock.getBlock().x, playerBlock.getBlock().y
				+ Consts.BLOCK_PLAYER_LINE2_Y, playerBlock.getBlock().width, Consts.BLOCK_PLAYER_DATA_HEIGHT);

		String name = scanBlock(block_line1, captureTable, "", TessPageSegMode.PSM_SINGLE_LINE, playerBlock.name()
				+ BlockType.PLAYER_NAME.name());
		String stack = scanBlock(block_line2, captureTable, "", TessPageSegMode.PSM_SINGLE_LINE,
				playerBlock.name() + BlockType.PLAYER_STACK);

		PlayerModel playerModel = null;

		if (!name.trim().isEmpty() && !stack.trim().isEmpty()) {
			Double stack2double = OcrUtils.cleanStack(stack);
			playerModel = new PlayerModel(playerBlock, name, stack2double, position);
		}

		return playerModel;
	}

	@Override
	public String scanBlock(Rectangle rectangle, BufferedImage capture, String whiteList, int mode, String blockName) {
		String result = "";

		if (rectangle != null) {
			capture = OcrUtils.cropImage(capture, rectangle);
		}

		try {
			capture = ImageHelper.convertImageToGrayscale(capture);
			capture = OcrUtils.rezizeImage(capture, Block.ZOOM_RESIZE);

			// TODO à retirer
			ImageIO.write(capture, "png", new File(CAPS_DIRECTORY + blockName + ".png"));

			Tesseract1 instance = new Tesseract1();
			instance.setTessVariable(OcrUtils.TESS_VAR_WHITELIST, whiteList);
			instance.setPageSegMode(mode);
			result = instance.doOCR(capture);

		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		} catch (TesseractException e) {
			LOGGER.warning(e.getMessage());
		}

		return result.trim();
	}

	@Override
	public CardModel scanCard(BoardCards card) {
		CardModel result = null;
		
		BufferedImage capture = robot.createScreenCapture(card.getBlock());

		Color colorScaned = new Color(capture.getRGB(Consts.PT_SUIT.x, Consts.PT_SUIT.y));

		try {
			String cardRankScaned = OcrUtils.scanCardRank(new Tesseract1(), capture);

			// TODO à retirer
			ImageIO.write(capture, "png", new File(CAPS_DIRECTORY + card.name() + ".png"));

			Rank rankMatch = Rank.UNKNOW;
			for (Rank rank : Rank.values()) {
				if (cardRankScaned.equals(rank.getShortName())) {
					rankMatch = rank;
					break;
				}
			}

			Suit suitMatch = Suit.UNKNOW;
			for (Suit suit : Suit.values()) {
				if (colorScaned.equals(suit.getPixelColor())) {
					suitMatch = suit;
					break;
				}
			}

			result = new CardModel(rankMatch, suitMatch);
		} catch (TesseractException | IOException e) {
			LOGGER.warning(e.getMessage());
		}

		System.out.println("--> scanCard : " + result);
		return result;
	}

}
