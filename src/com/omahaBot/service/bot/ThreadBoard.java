package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.BoardCards;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadBoard extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadBoard.class.getName());

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	private DealStep dealStepOld = DealStep.UNKNOW;

	private DealModel dealModel;

	private Robot robot;

	private ThreadPot threadPot;
	
	private ThreadPlayer threadPlayer;

	public ThreadBoard(MainForm mainForm, DealModel dealModel) {
		super();
		this.mainForm = mainForm;
		this.dealModel = dealModel;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			LOGGER.warning(e.getMessage());
		}
	}

	/**
	 * TODO gestion du changement de table
	 */
	@Override
	public void run() {

		System.out.println("#### START ThreadBoard");

		while (running) {
			// scan du dealStep toutes les 1s
			final DealStep dealStep = initDealStep();// critère de rupture

			if (!dealStepOld.equals(dealStep)) {
				System.out.println("--> NEW STEP : " + dealStep);
				dealStepOld = dealStep;

				dealModel.setStep(dealStep);

				initBoard(dealStep);

				// --> deal position

				arretThreadChild();
								
				// demarrage d'un nouveau thread
				threadPot = new ThreadPot(mainForm, dealModel);
				threadPot.start();
				
				// demarrage d'un nouveau thread
				threadPlayer = new ThreadPlayer(mainForm, dealModel);
				threadPlayer.start();

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initDealWidget(dealModel);
						mainForm.initBoardWidget(listBoardCard, dealStep);
					}
				});
			}
			try {
				// pause
				sleep(REFRESH);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}

		}

		System.out.println("#### STOP ThreadBoard");
	}

	@Override
	public void arretThreadChild() {
		if (threadPot != null && threadPot.isAlive()) {
			threadPot.arret();
		}
		if (threadPlayer != null && threadPlayer.isAlive()) {
			threadPlayer.arret();
		}
	}
	
	public DealStep initDealStep() {

		ArrayList<BoardCards> listCard = new ArrayList<>();
		listCard.add(BoardCards.CARD5_RIVER);
		listCard.add(BoardCards.CARD4_TURN);
		listCard.add(BoardCards.CARD3_FLOP);

		for (BoardCards card : listCard) {
			Color colorScaned = robot.getPixelColor(card.getBlock().x + Consts.PT_SUIT.x, card.getBlock().y
					+ Consts.PT_SUIT.y);

			for (Suit suit : Suit.values()) {
				if (colorScaned.equals(suit.getPixelColor())) {
					switch (card) {
					case CARD5_RIVER:
						return DealStep.RIVER;
					case CARD4_TURN:
						return DealStep.TURN;
					case CARD3_FLOP:
						return DealStep.FLOP;
					default:
						break;
					}
				}
			}
		}

		return DealStep.PRE_FLOP;
	}

	public void initBoard(DealStep dealStep) {

		switch (dealStep) {
		case FLOP:
			listBoardCard.clear();
			listBoardCard.addAll(ocrService.scanBoardCards(DealStep.FLOP));
			break;
		case TURN:
			if (listBoardCard.size() == 3) {
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.TURN));
			}
			else {
				listBoardCard.clear();
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.FLOP));
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.TURN));
			}
			break;
		case RIVER:
			if (listBoardCard.size() == 4) {
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.RIVER));
			}
			else {
				listBoardCard.clear();
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.FLOP));
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.TURN));
				listBoardCard.addAll(ocrService.scanBoardCards(DealStep.RIVER));
			}
			break;
		default:
			break;
		}

		System.out.println(dealStep + " : " + listBoardCard);
	}

}