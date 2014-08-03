package com.omahaBot.service.ocr;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import javax.imageio.ImageIO;

import junit.framework.Assert;
import net.sourceforge.tess4j.TessAPI1.TessPageSegMode;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.vietocr.ImageHelper;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.Block;
import com.omahaBot.enums.BlockType;
import com.omahaBot.enums.CardBlock;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.Rank;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.service.bot.ThreadTable;

public class OcrServiceImpl implements OcrService {

	private static final Logger log = Logger.getLogger(ThreadTable.class);

	private static final String TABLE_FILENAME = "tableCaps.png";
	private static final String CAPS_DIRECTORY = "C:/_DEV/caps/";

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	private Robot robot;

	private static OcrServiceImpl INSTANCE = new OcrServiceImpl();

	private OcrServiceImpl() {
		super();

		try {
			robot = new Robot();
		} catch (AWTException e) {
			log.warn(e.getMessage());
		}
	}

	public static OcrServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public String scanDealId() {
		Rectangle block = new Rectangle(Consts.BLOCK_DEAL_ID);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, Consts.WHITELIST_DEAL_ID,
				TessPageSegMode.PSM_SINGLE_LINE, BlockType.DEAL_ID.name());

		return OcrUtils.cleanDealId(ocr);
	}

	@Override
	public BigDecimal scanPot() {
		BigDecimal res;
		
		Rectangle block = new Rectangle(Consts.BLOCK_POT);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, "",
				TessPageSegMode.PSM_SINGLE_LINE, BlockType.POT.name());

		res = OcrUtils.cleanPot(ocr);
		
		return res;
	}
	
	@Override
	public BigDecimal scanCheckOrCallButton() {
		BigDecimal res;
		
		Rectangle block = new Rectangle(Consts.BLOCK_BUTTON_CHECK_OR_CALL);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, "",
				TessPageSegMode.PSM_AUTO, BlockType.BUTTON_CHECK_OR_CALL.name());
		
		log.debug("scanCheckOrCallButton - ocr : " + ocr);
		
		if (ocr.equals(Consts.CHECK)) {
			res = new BigDecimal(0);
		}
		else {
			res = OcrUtils.cleanPot(ocr);
		}
		
		log.debug("scanCheckOrCallButton - res : " + res);
		
		return res;
	}

	// private void scanPlayerList() {
	// listPlayer.clear();
	//
	// int position = 1;
	//
	// for (PlayerBlock player : PlayerBlock.values()) {
	//
	// PlayerModel playerModel = scanPlayer(player, position);
	// listPlayer.add(playerModel);
	// //LOGGER.info(playerModel.toString());
	// position++;
	// }
	// }

	@Override
	public List<CardModel> scanBoardCards(DealStep dealStep) {
		
		listBoardCard.clear();

		switch (dealStep) {
		case FLOP:
			int nbCard = 0;
			for (CardBlock card : CardBlock.values()) {
				CardModel cardModel = scanCard(card);
				listBoardCard.add(cardModel);
				nbCard++;
				if (nbCard == 3) {
					break;
				}
			}
			break;
		case TURN:
			CardModel cardModel1 = scanCard(CardBlock.CARD4_TURN);
			listBoardCard.add(cardModel1);
			break;
		case RIVER:
			CardModel cardModel2 = scanCard(CardBlock.CARD5_RIVER);
			listBoardCard.add(cardModel2);
			break;
		default:
			break;
		}

		return listBoardCard;
	}

	@Override
	public SortedSet<CardModel> scanMyHand() {
		SortedSet<CardModel> listMyCard = new TreeSet<CardModel>();
		
		listMyCard.add(scanCard(CardBlock.MY_CARD1));
		listMyCard.add(scanCard(CardBlock.MY_CARD2));
		listMyCard.add(scanCard(CardBlock.MY_CARD3));
		listMyCard.add(scanCard(CardBlock.MY_CARD4));
		
		return listMyCard;
	}	
	
	public PlayerModel scanPlayer(PlayerBlock playerBlock) {

		String name = scanPlayerName(playerBlock);
		Double stack = scanPlayerStack(playerBlock);

		PlayerModel playerModel = new PlayerModel(playerBlock, name, stack, playerBlock.ordinal() + 1);

		return playerModel;
	}

	public String scanPlayerName(PlayerBlock playerBlock) {
		Rectangle block = new Rectangle(playerBlock.getBlock().x, playerBlock.getBlock().y,
				playerBlock.getBlock().width, Consts.BLOCK_PLAYER_NAME_HEIGHT);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, "",
				TessPageSegMode.PSM_SINGLE_LINE, playerBlock.name()
						+ BlockType.PLAYER_NAME.name());

		return ocr;
	}

	public Double scanPlayerStack(PlayerBlock playerBlock) {
		Rectangle block = new Rectangle(playerBlock.getBlock().x, playerBlock.getBlock().y
				+ Consts.BLOCK_PLAYER_STACK_Y, playerBlock.getBlock().width, Consts.BLOCK_PLAYER_STACK_HEIGHT);
		BufferedImage capture = robot.createScreenCapture(block);

		String ocr = scanBlock(null, capture, "",
				TessPageSegMode.PSM_SINGLE_LINE, playerBlock.name()
						+ BlockType.PLAYER_NAME.name());

		return OcrUtils.cleanStack(ocr);
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
			log.warn(e.getMessage());
		} catch (TesseractException e) {
			log.warn(e.getMessage());
		}

		return result.trim();
	}

	@Override
	public CardModel scanCard(CardBlock card) {
		CardModel result = null;

		BufferedImage capture = robot.createScreenCapture(card.getBlock());

		Color colorScaned = new Color(capture.getRGB(Consts.PT_SUIT.x, Consts.PT_SUIT.y));

		try {
			String cardRankScaned = OcrUtils.scanCardRank(new Tesseract1(), capture);

			// TODO à retirer
			ImageIO.write(capture, "png", new File(CAPS_DIRECTORY + card.name() + ".png"));

			Rank rankMatch = Rank.TEN;// TODO à revoir
			for (Rank rank : Rank.values()) {
				if (cardRankScaned.equals(Rank.TEN_SCANED)) {
					rankMatch = Rank.TEN;
					break;
				}
				else if (cardRankScaned.equals(rank.getShortName())) {
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
			log.warn(e.getMessage());
		}

		return result;
	}

	@Override
	public boolean checkTable() {

		try {
			BufferedImage captureTable = robot.createScreenCapture(Consts.BLOCK_TABLE);
			ImageIO.write(captureTable, "png", new File(CAPS_DIRECTORY +
					TABLE_FILENAME));
			
			Assert.assertTrue(true);

		} catch (IOException e) {
			log.warn(e.getMessage());
		}

		// TODO Auto-generated method stub
		return false;
	}

}
