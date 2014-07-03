package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.CardBlock;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.Suit;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadTable extends MyThread {

	private static final Logger log = Logger.getLogger(ThreadTable.class);

	private DealModel dealModel;

	// critères de ruptures :

	private String dealIdCurrent, dealIdOld = "0";

	private DealStep dealStepCurrent, dealStepOld = DealStep.UNKNOW;

	private int positionTurnToPlayCurrent, positionTurnToPlayOld = 0;

	// dealStep :

	private DealStepModel dealStepModel;

	private BoardModel boardModel;

	private List<CardModel> listBoardCard = new ArrayList<CardModel>();

	// actions :

	private ActionModel actionModel;

	private ArrayList<PlayerBlock> listCurrentPlayerBlock;

	private ArrayList<PlayerModel> listCurrentPlayer;

	private HandModel handModel;

	private boolean firstTurnBet = true;

	private boolean firstAction = true;

	private ArrayList<DrawModel> handDraws = new ArrayList<DrawModel>();

	private ArrayList<DrawModel> boardDraws = new ArrayList<DrawModel>();

	private Double potOld = 0.0, potCurrent;

	private int nbPlayerOld = 0, nbPlayerCurrent = 0;

	private boolean firstLoop = true;

	private PlayerAction lastActionOld = PlayerAction.UNKNOW, lastActionCurrent = PlayerAction.UNKNOW;

	private Double lastBet = 0.0;

	// TODO Contexte

	public ThreadTable(MainForm mainForm) {
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
		log.debug(">> START ThreadTable : " + this.getId());

		initialize();

		while (running) {
			
			if (isDealIdChange()) {
				
				listCurrentPlayerBlock = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));
				listCurrentPlayer = new ArrayList<PlayerModel>();
				
				goDealId();
			}
			else if (isDealStepChange()) {
				goDealStep();
			}
			else if (isPositionTurnToPlayChange()) {
				goAction();
			}

			try {
				// pause
				sleep(100);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		log.debug("<< STOP ThreadTable : " + this.getId());
	}

	private void goAction() {
		log.debug("NEW PLAYER ACTION : " + positionTurnToPlayCurrent);
		
		potCurrent = ocrService.scanPot();

		nbPlayerCurrent = nbPlayerActive();

		initListCurrentPlayer();
		// initLastActionPlayer(positionPlayerTurnPlayOld);

		actionModel = new ActionModel();
		actionModel.setPositionPlayerTurnPlay(positionTurnToPlayCurrent);

		actionModel.setNbPlayer(nbPlayerCurrent);
		actionModel.setListPlayer(listCurrentPlayer);
		actionModel.setPlayerAction(lastActionCurrent);
		actionModel.setLastBet(lastBet);

		nbPlayerOld = nbPlayerCurrent;

		potOld = potCurrent;

		if (Consts.register) {
			handDraws.clear();
			boardDraws.clear();

			if (positionTurnToPlayCurrent == Consts.MY_TABLEPOSITION) {
				
				if (handModel == null) {
					initHandCard();
				}
				
				if (dealStepCurrent.equals(DealStep.PRE_FLOP)) {
					preFlopAnalyser.analyseHand(handModel);
				} else if (dealStepCurrent.ordinal() > DealStep.PRE_FLOP.ordinal()) {
					preFlopAnalyser.analyseHand(handModel);					
				}
			}
			else if (firstAction && dealStepCurrent.ordinal() > DealStep.PRE_FLOP.ordinal()) {
				if (handModel == null) {
					initHandCard();
				}

				System.out.println("-->test4 : postFlopAnalyser.analyseHand(myHand);");

				postFlopAnalyser.analyseHand(handModel, boardModel);
				handDraws.addAll(postFlopAnalyser.getHandDrawsSorted());
				boardDraws.addAll(postFlopAnalyser.getBoardDrawsSorted());
			}

			if (positionTurnToPlayCurrent == Consts.MY_TABLEPOSITION) {
				play();
			}
		}

		positionTurnToPlayOld = positionTurnToPlayCurrent;

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mainForm.initPotWidget(potCurrent);
				mainForm.initActionWidget(actionModel);
				mainForm.initPlayerWidget(listCurrentPlayer);

				if (handModel != null) {
					
					if (dealStepCurrent.equals(DealStep.PRE_FLOP)) {
						mainForm.initAnalyseWidget(handModel, preFlopAnalyser.getHandPreFlopPower());
					} else if (dealStepCurrent.ordinal() > DealStep.PRE_FLOP.ordinal()) {
						mainForm.initAnalyseWidget(boardModel, boardDraws);
						mainForm.initAnalyseWidget(handModel, boardModel, handDraws, postFlopAnalyser);			
					}
				}
			}
		});
	}

	private void goDealStep() {
		log.debug("NEW DEALSTEP : " + dealStepCurrent);

		// START - intialize
		firstTurnBet = true;
		firstAction = true;
		handDraws.clear();
		boardDraws.clear();
		potOld = 0.0;
		nbPlayerOld = 0;
		nbPlayerCurrent = 0;
		firstLoop = true;
		lastActionOld = PlayerAction.UNKNOW;
		lastActionCurrent = PlayerAction.UNKNOW;
		lastBet = 0.0;
		// END - intialize
		
		dealStepOld = dealStepCurrent;

		initBoardCard();

		dealStepModel = new DealStepModel();
		dealStepModel.setDealStep(dealStepCurrent);
		dealStepModel.setListBoardCard(listBoardCard);

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mainForm.initBoardWidget(dealStepModel);
			}
		});
	}

	private void goDealId() {
		// initDealContexte()
		log.debug("__________________________________________________________________");
		log.debug("NEW DEAL : " + dealIdCurrent);

		// START - intialize
		handModel = null;
		boardModel = null;
		dealStepOld = DealStep.UNKNOW;
		listBoardCard.clear();
		positionTurnToPlayOld = 0;
		// END - intialize
		
		dealIdOld = dealIdCurrent;

		dealModel = new DealModel();
		dealModel.setDealId(dealIdCurrent);

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mainForm.initDealWidget(dealModel);
				mainForm.initPotWidget(0.0);

				ActionModel actionModel = new ActionModel();
				actionModel.setPositionPlayerTurnPlay(0);
				actionModel.setPlayerAction(PlayerAction.UNKNOW);
				mainForm.initActionWidget(actionModel);
			}
		});
	}

	@Override
	public void initialize() {

	}

	public boolean isDealIdChange() {
		dealIdCurrent = ocrService.scanDealId();
		return (!dealIdCurrent.isEmpty() && !dealIdOld.equals(dealIdCurrent));
	}

	public boolean isDealStepChange() {
		dealStepCurrent = dealStep();
		return (!dealStepOld.equals(dealStepCurrent));
	}

	public boolean isPositionTurnToPlayChange() {
		positionTurnToPlayCurrent = positionTurnToPlay();
		return (positionTurnToPlayOld != positionTurnToPlayCurrent);
	}

	public DealStep dealStep() {

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

	public int positionTurnToPlay() {

		int position = positionTurnToPlayOld;

		for (PlayerBlock playerBlock : listCurrentPlayerBlock) {
			// TODO optimize
			Color colorScanedActive = robot.getPixelColor(playerBlock.getActive().x, playerBlock.getActive().y);
			Color colorScanedTurnToPlay = robot.getPixelColor(playerBlock.getTurnPlay().x, playerBlock.getTurnPlay().y);

			if (playerBlock.isActivePlayer(colorScanedActive) && playerBlock.isPlayerTurnPlay(colorScanedTurnToPlay)) {
				position = playerBlock.ordinal() + 1;
				break;
			}
		}

		return position;
	}

	public void initBoardCard() {

		switch (dealStepCurrent) {
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
		boardModel = new BoardModel(listCard, dealStepCurrent);
	}

	private void initHandCard() {
		SortedSet<CardModel> listCard = ocrService.scanMyHand();
		handModel = new HandModel(listCard, dealStepCurrent);
	}

	private void play() {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		switch (dealStepCurrent) {
		case PRE_FLOP:
			System.out.println("play PRE");
			bettingDecision = preFlopAnalyser.decide(handModel, firstTurnBet);
			break;
		case FLOP:
		case TURN:
		case RIVER:
			System.out.println("play POST");
			bettingDecision = postFlopAnalyser.decide(dealStepCurrent, handModel);
			break;
		default:
			break;
		}

		firstTurnBet = false;

		try {
			MyRobot robot = new MyRobot();
			robot.clickAction(bettingDecision, this.getId());
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int nbPlayerActive() {
		ArrayList<PlayerBlock> listPlayerBlock = new ArrayList<>(listCurrentPlayerBlock);

		for (PlayerBlock playerBlock : listPlayerBlock) {
			Color colorScaned = robot.getPixelColor(playerBlock.getActive().x, playerBlock.getActive().y);

			if (!playerBlock.isActivePlayer(colorScaned)) {
				log.debug(playerBlock.name() + " OUT");
				listCurrentPlayerBlock.remove(playerBlock);
			}
		}

		return listCurrentPlayerBlock.size();
	}

	public void initListCurrentPlayer() {

		for (PlayerBlock playerBlock : PlayerBlock.values()) {
			PlayerModel playerModel;

			if (firstLoop) {
				playerModel = ocrService.scanPlayer(playerBlock);
				listCurrentPlayer.add(playerModel);
			}
			else {
				playerModel = listCurrentPlayer.get(playerBlock.ordinal());
			}

			Color colorScaned = robot.getPixelColor(playerBlock.getActive().x, playerBlock.getActive().y);

			// TODO optimize
			if (playerBlock.isActivePlayer(colorScaned)) {
				playerModel.setActiv(true);
				playerModel.setStack(ocrService.scanPlayerStack(playerBlock));
			}
			else {
				playerModel.setActiv(false);
				playerModel.setStack(ocrService.scanPlayerStack(playerBlock));
			}
		}

		firstLoop = false;
	}

	@Override
	public void arretThreadChild() {
		// TODO Auto-generated method stub

	}

}