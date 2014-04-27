package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.CardBlock;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.model.HandModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDealStep extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private final static String START_LOG = "    ";

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	private DealStep oldDealStep = DealStep.UNKNOW, currentDealStep;

	private DealStepModel dealStepModel;

	private HandModel myHand;

	private BoardModel board;

	private ArrayList<CombinaisonModel> combinaisonModels;
	
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
			LOGGER.warning(e.getMessage());
		}
	}

	/**
	 * TODO gestion du changement de table
	 */
	@Override
	public void run() {

		System.out.println(START_LOG + ">> START ThreadDealStep : " + this.getId());

		while (running) {
			// scan du dealStep toutes les 1s
			currentDealStep = initDealStep();// critère de rupture

			if (!oldDealStep.equals(currentDealStep)) {
				System.out.println(START_LOG + "---------------------------------------------");
				System.out.println(START_LOG + "NEW DEAL STEP : " + currentDealStep);

				oldDealStep = currentDealStep;

				initBoardCard();

				dealStepModel = new DealStepModel();
				dealStepModel.setDealStep(currentDealStep);
				dealStepModel.setListBoardCard(listBoardCard);

				if (Consts.register) {
					if (myHand == null) {
						initMyHand();
					}
				}
				else {
					myHand = new HandModel("AsKsKdAd");
				}
				
				// analyse des permutations hand / board
				combinaisonModels = postFlopAnalyserServiceImpl.initCombinaisons(myHand, board);

				arretThreadChild();

				if (running) {
					// demarrage d'un nouveau thread
					threadAction = new ThreadAction(mainForm, currentDealStep, myHand, board);
					threadAction.start();
				}

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initBoardWidget(dealStepModel);

						if (dealStepModel.getDealStep().ordinal() > DealStep.PRE_FLOP.ordinal()) {
							mainForm.initAnalyseWidget(board);
							mainForm.initAnalyseWidget(myHand, board, combinaisonModels);
						}
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

		System.out.println(START_LOG + "<< STOP ThreadDealStep : " + this.getId());
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

	private void initMyHand() {
		SortedSet<CardModel> listCard = ocrService.scanMyHand();
		myHand = new HandModel(listCard);
	}
}