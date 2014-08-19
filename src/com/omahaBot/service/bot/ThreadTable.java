package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.math.BigDecimal;
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
import com.omahaBot.enums.LastPlayerBetType;
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.Suit;
import com.omahaBot.exception.HandNoValidException;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.strategy.StrategyContext;
import com.omahaBot.ui.form.MainForm;

public class ThreadTable extends MyThread {

	private static final Logger log = Logger.getLogger(ThreadTable.class);

	private DealModel dealModel;

	// critères de ruptures :

	private String dealIdCurrent, dealIdOld = "0";

	private DealStep dealStepCurrent, dealStepOld = DealStep.UNKNOWN;

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

	private ArrayList<DrawModel> handDraws = new ArrayList<DrawModel>();

	private ArrayList<DrawModel> boardDraws = new ArrayList<DrawModel>();

	private int nbPlayerOld = 0, nbPlayerCurrent = 0;

	private boolean firstLoop = true;

	private boolean initAnalyseWidgetPreFlopDone = false;

	private PlayerAction lastActionOld = PlayerAction.UNKNOW, lastActionCurrent = PlayerAction.UNKNOW;

	private BigDecimal potOld = BigDecimal.ZERO;
	private BigDecimal potCurrent = BigDecimal.ZERO;

	private int nbTurnOfBet = 1;

	private int nbAction = 1;

	private ArrayList<StrategyContext> strategyContexts = new ArrayList<StrategyContext>();

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
				sleep(500);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		log.debug("<< STOP ThreadTable : " + this.getId());
	}

	private void goDealId() {
		// initDealContexte()
		log.debug("NEW DEAL : " + dealIdCurrent);

		System.out.println("NEW DEAL : " + dealIdCurrent + ", dealer : " + positionDealer());
		System.out.println("========================================");


		// START - intialize
		handModel = null;
		boardModel = null;
		dealStepOld = DealStep.UNKNOWN;
		listBoardCard.clear();
		positionTurnToPlayOld = 0;
		initAnalyseWidgetPreFlopDone = false;

		potOld = BigDecimal.ZERO;
		potCurrent = BigDecimal.ZERO;
		// END - intialize

		dealIdOld = dealIdCurrent;

		dealModel = new DealModel();
		dealModel.setDealId(dealIdCurrent);

		strategyContexts.clear();

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

	private void goDealStep() {
		log.debug("NEW STEP : " + dealStepCurrent);

		System.out.println(" STEP : " + dealStepCurrent);

		// START - intialize
		handDraws.clear();
		boardDraws.clear();
		nbPlayerOld = 0;
		nbPlayerCurrent = 0;
		firstLoop = true;
		lastActionOld = PlayerAction.UNKNOW;
		lastActionCurrent = PlayerAction.UNKNOW;
		positionTurnToPlayOld = 0;
		nbTurnOfBet = 1;
		nbAction = 0;

		potOld = ocrService.scanPot();
		// END - intialize

		dealStepOld = dealStepCurrent;

		initBoardCard();

		dealStepModel = new DealStepModel();
		dealStepModel.setDealStep(dealStepCurrent);
		dealStepModel.setListBoardCard(listBoardCard);

		if (iAmInDeal() && dealStepCurrent.ordinal() > DealStep.PRE_FLOP.ordinal()) {
			// TODO à suppr ?
			initHandCard();

			postFlopAnalyser.analyseHand(handModel, boardModel);
			handDraws.addAll(postFlopAnalyser.getHandDrawsSorted());
			boardDraws.addAll(postFlopAnalyser.getBoardDrawsSorted());
		}

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mainForm.initBoardWidget(dealStepModel);

				if (iAmInDeal() && dealStepCurrent.ordinal() > DealStep.PRE_FLOP.ordinal()) {
					mainForm.initAnalyseWidget(boardModel, boardDraws);
					mainForm.initAnalyseWidget(handModel, boardModel, handDraws, postFlopAnalyser);
				}
			}
		});
	}

	private void goAction() {
		log.debug("TURN TO PLAY : " + positionTurnToPlayCurrent);

		if (positionTurnToPlayCurrent == Consts.MY_TABLEPOSITION) {
			System.out.println("Me : it's my TURN TO PLAY");
		}
//		else {
//			System.out.println("----> TURN TO PLAY : " + positionTurnToPlayCurrent);
//		}

		nbAction++;

		try {
			potCurrent = ocrService.scanPot();

			nbPlayerCurrent = nbPlayerActive();

			initListCurrentPlayer();
			// initLastActionPlayer(positionPlayerTurnPlayOld);

			actionModel = new ActionModel();
			actionModel.setPositionPlayerTurnPlay(positionTurnToPlayCurrent);

			actionModel.setNbPlayer(nbPlayerCurrent);
			actionModel.setListPlayer(listCurrentPlayer);
			actionModel.setPlayerAction(lastActionCurrent);
			nbPlayerOld = nbPlayerCurrent;

			potOld = potCurrent;

			if (Consts.register) {
				handDraws.clear();
				boardDraws.clear();

				if (!initAnalyseWidgetPreFlopDone && positionTurnToPlayCurrent == Consts.MY_TABLEPOSITION
						&& dealStepCurrent.equals(DealStep.PRE_FLOP)) {
					initHandCard();
					preFlopAnalyser.analyseHand(handModel);
				}

				if (iAmInDeal() && positionTurnToPlayCurrent == Consts.MY_TABLEPOSITION) {
					play();
				}

			}

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					// mainForm.initPotWidget(potCurrent);
					mainForm.initActionWidget(actionModel);
					mainForm.initPlayerWidget(listCurrentPlayer);

					if (handModel != null) {
						if (!initAnalyseWidgetPreFlopDone && iAmInDeal() && dealStepCurrent.equals(DealStep.PRE_FLOP)) {
							mainForm.initAnalyseWidget(handModel, preFlopAnalyser.getHandPreFlopPower());
							initAnalyseWidgetPreFlopDone = true;
						}
					}
				}
			});

		} catch (HandNoValidException e) {
			log.warn(e.getMessage());
		} finally {
			positionTurnToPlayOld = positionTurnToPlayCurrent;
		}
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

	public int positionDealer() {

		int position = 0;

		for (PlayerBlock playerBlock : listCurrentPlayerBlock) {
			// TODO optimize
			Color colorScaned = robot.getPixelColor(playerBlock.getDealer().x, playerBlock.getDealer().y);

			if (playerBlock.isPlayerDealer(colorScaned)) {
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

		// Action Context
		BigDecimal toCall = ocrService.scanCheckOrCallButton();

		// Players Context
		LastPlayerBetType lastPlayerBetType = lastPlayerBetType(toCall);
		
		//
		StrategyContext strategyContext = new StrategyContext(nbTurnOfBet, nbPlayerCurrent, nbAction, lastPlayerBetType);
		strategyContexts.add(strategyContext);

		//System.out.println(strategyContext);

		switch (dealStepCurrent) {
		case PRE_FLOP:
			bettingDecision = preFlopAnalyser.decide(strategyContext);
			break;
		case FLOP:
		case TURN:
		case RIVER:
			bettingDecision = postFlopAnalyser.decide(dealStepCurrent, handModel, strategyContext);
			break;
		default:
			break;
		}

		nbTurnOfBet++;

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

	private boolean iAmInDeal() {
		PlayerBlock playerBlock = PlayerBlock.PLAYER_4;
		Color colorScanedActive = robot.getPixelColor(playerBlock.getActive().x, playerBlock.getActive().y);
		return Consts.register && playerBlock.isActivePlayer(colorScanedActive);
	}

	private LastPlayerBetType lastPlayerBetType(BigDecimal toCall) {
		
		LastPlayerBetType lastPlayerBetType = LastPlayerBetType.UNKNOW;

		if (toCall.compareTo(BigDecimal.ZERO) == 0) {
			lastPlayerBetType = LastPlayerBetType.NO_BET;
		}
		else if (toCall.compareTo(Consts.BB) > 0) {
			lastPlayerBetType = LastPlayerBetType.BET;
		}
		else {
			lastPlayerBetType = LastPlayerBetType.CALL;
		}
		
		System.out.println("........................................");
		System.out.println("- toCall : " + toCall);
		System.out.println("- lastPlayerBetType : " + lastPlayerBetType);
		System.out.println("........................................");
		
		return lastPlayerBetType;
	}
}