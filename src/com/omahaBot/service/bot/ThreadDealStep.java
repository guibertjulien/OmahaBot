package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.CardBlock;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDealStep extends MyThread {

	private static final Logger log = Logger.getLogger(ThreadDealStep.class);

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	private DealStep oldDealStep = DealStep.UNKNOWN, currentDealStep;

	private DealStepModel dealStepModel;

	private BoardModel board;

	private Robot robot;
	//
	// private ThreadPot threadPot;

	private ThreadAction threadAction;

	public ThreadDealStep(MainForm mainForm) {
		super();
		this.mainForm = mainForm;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * TODO gestion du changement de table
	 */
	@Override
	public void run() {

		log.debug(">> START ThreadDealStep : " + this.getId());

		while (running) {
			// scan du dealStep toutes les 1s
			currentDealStep = initDealStep();// critère de rupture

			boolean isChangeDealStep = !oldDealStep.equals(currentDealStep);
			
			if (isChangeDealStep) {
				log.debug("NEW DEAL STEP : " + currentDealStep);
				
				oldDealStep = currentDealStep;

				initBoardCard();

				dealStepModel = new DealStepModel();
				dealStepModel.setDealStep(currentDealStep);
				dealStepModel.setListBoardCard(listBoardCard);

				arretThreadChild();

				if (running) {
					// demarrage d'un nouveau thread
					threadAction = new ThreadAction(mainForm, currentDealStep, board);
					threadAction.start();
				}

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

		log.debug("<< STOP ThreadDealStep : " + this.getId());
	}

	@Override
	public void arretThreadChild() {
		// if (threadPot != null && threadPot.isAlive()) {
		// threadPot.arret();
		// }
		if (threadAction != null && threadAction.isAlive()) {
			threadAction.arret();
		}
	}

	public DealStep initDealStep() {

		ArrayList<CardBlock> listCard = new ArrayList<>();
		listCard.add(CardBlock.CARD5_RIVER);
		listCard.add(CardBlock.CARD4_TURN);
		listCard.add(CardBlock.CARD3_FLOP);

		int nbCardScaned = 0;

		for (CardBlock card : listCard) {
			if (nbCardScaned > 5) {
				return DealStep.PRE_FLOP;
			}

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

			nbCardScaned++;
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

		SortedSet<CardModel> listCard = new TreeSet<CardModel>(listBoardCard);
		board = new BoardModel(listCard, currentDealStep);
	}

	@Override
	public void initialize() {
	}
}