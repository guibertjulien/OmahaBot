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
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDealStep extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	private DealStep oldDealStep = DealStep.UNKNOW, currentDealStep;

	private DealStepModel dealStepModel;

	private Robot robot;
//
//	private ThreadPot threadPot;
	
	private ThreadAction threadAction;

	public ThreadDealStep(MainForm mainForm) {
		super();
		this.mainForm = mainForm;

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

		System.out.println("#### START ThreadDealStep");

		while (running) {
			// scan du dealStep toutes les 1s
			currentDealStep = initDealStep();// critère de rupture

			if (!oldDealStep.equals(currentDealStep)) {
				System.out.println("--> NEW DEAL STEP : " + currentDealStep);
				oldDealStep = currentDealStep;

				initBoardCard();

				dealStepModel = new DealStepModel();
				dealStepModel.setDealStep(currentDealStep);
				dealStepModel.setListBoardCard(listBoardCard);
				
				arretThreadChild();
				
				// demarrage d'un nouveau thread
				threadAction = new ThreadAction(mainForm);
				threadAction.start();

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initBoardWidget(dealStepModel);
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

		System.out.println("#### STOP ThreadDealStep");
	}

	@Override
	public void arretThreadChild() {
//		if (threadPot != null && threadPot.isAlive()) {
//			threadPot.arret();
//		}
		if (threadAction != null && threadAction.isAlive()) {
			threadAction.arret();
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

	public void initBoardCard() {

		switch (currentDealStep) {
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
	}

	@Override
	public void initialize() {
	}

}