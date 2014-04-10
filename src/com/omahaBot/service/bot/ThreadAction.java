package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.enums.PlayerShortcut;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadAction extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

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

	public ThreadAction(MainForm mainForm) {
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

		System.out.println("## START ThreadAction");

		initialize();

		listCurrentPlayerBlock = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));
		listCurrentPlayer = new ArrayList<PlayerModel>();

		while (running) {

			// critère de rupture : tour du joueur
			final int positionPlayerTurnPlay = positionPlayerTurnPlay();

			if (positionPlayerTurnPlayOld != positionPlayerTurnPlay) {

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

				if (positionPlayerTurnPlay == Consts.MY_TABLEPOSITION) {
					play();
				}

				positionPlayerTurnPlayOld = positionPlayerTurnPlay;

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initPotWidget(currentPot);
						mainForm.initActionWidget(actionModel);
						mainForm.initPlayerWidget(listCurrentPlayer);
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

		System.out.println("## STOP ThreadAction");
	}

	private void play() {

		try {
			MyRobot robot = new MyRobot();
			robot.clickAction(PlayerShortcut.random());
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
				LOGGER.log(Level.INFO, playerBlock.name() + " OUT");
				listCurrentPlayerBlock.remove(playerBlock);
			}
		}

		System.out.println("nbPlayerActive : " + listCurrentPlayerBlock.size());

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

		if (firstLoop) {
			firstLoop = false;
		}
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