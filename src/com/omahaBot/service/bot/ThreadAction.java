package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.BettingDecision;
import com.omahaBot.enums.DealStep;
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.PreFlopPower;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.HandModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadAction extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private final static String START_LOG = "        ";

	private ActionModel actionModel;

	private int positionPlayerTurnPlayOld = 0;

	// private ArrayList<PlayerModel> listPlayer = new ArrayList<PlayerModel>();

	private ArrayList<PlayerBlock> listCurrentPlayerBlock;

	private ArrayList<PlayerModel> listCurrentPlayer;

	private static int REFRESH_POT = 100;

	private boolean firstLoop = true;

	private Double oldPot = 0.0, currentPot;

	private int oldNbPlayer = 0, currentNbPlayer = 0;

	private PlayerAction oldLastAction = PlayerAction.UNKNOW, currentLastAction = PlayerAction.UNKNOW;

	private Double lastBet = 0.0;

	private final DealStep dealStep;

	private final HandModel myHand;

	private final BoardModel board;

	private boolean firstTurnBet = true;

	public ThreadAction(MainForm mainForm, DealStep dealStep, HandModel myHand, BoardModel board) {
		super();
		this.mainForm = mainForm;
		this.dealStep = dealStep;
		this.myHand = myHand;
		this.board = board;

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

		System.out.println(START_LOG + ">> START ThreadAction : " + this.getId());

		initialize();

		listCurrentPlayerBlock = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));
		listCurrentPlayer = new ArrayList<PlayerModel>();

		while (running) {

			// critère de rupture : tour du joueur
			final int positionPlayerTurnPlay = positionPlayerTurnPlay();

			if (positionPlayerTurnPlayOld != positionPlayerTurnPlay) {
				System.out.println(START_LOG + "---------------------------------------");
				System.out.println(START_LOG + "NEW ACTION");

				currentPot = ocrService.scanPot();

				currentNbPlayer = nbPlayerActive();

				initListCurrentPlayer();
				// initLastActionPlayer(positionPlayerTurnPlayOld);

				actionModel = new ActionModel();
				actionModel.setPositionPlayerTurnPlay(positionPlayerTurnPlay);

				actionModel.setNbPlayer(currentNbPlayer);
				actionModel.setListPlayer(listCurrentPlayer);
				actionModel.setPlayerAction(currentLastAction);
				actionModel.setLastBet(lastBet);

				oldNbPlayer = currentNbPlayer;

				oldPot = currentPot;

				if (Consts.register && positionPlayerTurnPlay == Consts.MY_TABLEPOSITION) {
					play();
				}

				final PreFlopPower preFlopPower = preFlopAnalyserServiceImpl.analyseHand(myHand);

				positionPlayerTurnPlayOld = positionPlayerTurnPlay;

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initPotWidget(currentPot);
						mainForm.initActionWidget(actionModel);
						mainForm.initPlayerWidget(listCurrentPlayer);
						mainForm.initAnalyseWidget(myHand, preFlopPower);
					}
				});
			}
			try {
				// pause
				sleep(REFRESH_POT);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		System.out.println(START_LOG + "<< STOP ThreadAction : " + this.getId());
	}

	private void play() {

		BettingDecision bettingDecision = BettingDecision.CHECK_FOLD;

		switch (dealStep) {
		case PRE_FLOP:
			bettingDecision = preFlopAnalyserServiceImpl.decide(myHand, firstTurnBet);
			break;
		case FLOP:
		case TURN:
		case RIVER:
			bettingDecision = postFlopAnalyserServiceImpl.decide(myHand, board, firstTurnBet);
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

	@Override
	public void arretThreadChild() {
		// nothing
	}

	/**
	 * listCurrentPlayerBlock
	 */
	public int positionPlayerTurnPlay() {

		int position = positionPlayerTurnPlayOld;

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

	public int nbPlayerActive() {
		ArrayList<PlayerBlock> listPlayerBlock = new ArrayList<>(listCurrentPlayerBlock);

		for (PlayerBlock playerBlock : listPlayerBlock) {
			Color colorScaned = robot.getPixelColor(playerBlock.getActive().x, playerBlock.getActive().y);

			if (!playerBlock.isActivePlayer(colorScaned)) {
				System.out.println(START_LOG + playerBlock.name() + " OUT");
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

	// public void initLastActionPlayer(int positionPlayerTurnPlayOld) {
	// System.out.println("initLastActionPlayer() : " +
	// positionPlayerTurnPlayOld);
	//
	// if (positionPlayerTurnPlayOld <= 0) {
	// LOGGER.log(Level.WARNING, "positionPlayerTurnPlayOld : ",
	// positionPlayerTurnPlayOld);
	// } else {
	// PlayerModel playerModel = listCurrentPlayer.get(positionPlayerTurnPlayOld
	// - 1);
	// Double lastBetPlayer = 0.0;
	//
	// System.out.println("oldPot : " + oldPot + "/ currentPot : " +
	// currentPot);
	//
	// // playerModel.setAction(PlayerAction.SB);
	// // playerModel.setAction(PlayerAction.BB);
	//
	// if (!playerModel.isActiv()) {
	// currentLastAction = PlayerAction.FOLD;
	// } else if (playerModel.getStack() == 0.0) {
	// currentLastAction = PlayerAction.ALLIN;
	// lastBetPlayer = currentPot - oldPot;
	// } else if (oldPot.equals(currentPot)) {
	// currentLastAction = PlayerAction.CHECK;
	// } else if (currentPot > oldPot) {
	// if (oldLastAction.equals(PlayerAction.CHECK)) {
	// currentLastAction = PlayerAction.BET;
	// }
	// else if (oldLastAction.equals(PlayerAction.RAISE)) {
	// currentLastAction = PlayerAction.RERAISE;
	// }
	// else {
	// currentLastAction = PlayerAction.RAISE;
	// }
	//
	// lastBetPlayer = currentPot - oldPot;
	// }
	// else {
	// currentLastAction = PlayerAction.UNKNOW;
	// }
	//
	// playerModel.setAction(currentLastAction);
	// playerModel.setLastBet(lastBetPlayer);
	//
	// oldLastAction = currentLastAction;
	//
	// if (lastBetPlayer > 0) {
	// lastBet = lastBetPlayer;
	// }
	//
	// // playerModel.setAction(PlayerAction.CALL);
	//
	// }
	// }

	@Override
	public void initialize() {
		oldPot = ocrService.scanPot();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mainForm.initPotWidget(oldPot);
			}
		});
	}
}