package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.PixelConsts;
import com.omahaBot.enums.PlayerAction;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadAction extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private ActionModel actionModel;

	private int positionPlayerTurnPlayOld = 0;

	private ArrayList<PlayerModel> listPlayer = new ArrayList<PlayerModel>();

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

		listPlayer.clear();
	
		while (running) {

			// critère de rupture : tour du joueur
			final int positionPlayerTurnPlay = positionPlayerTurnPlay();

			if (positionPlayerTurnPlayOld != positionPlayerTurnPlay) {

				currentPot = ocrService.scanPot();

				initListCurrentPlayer();
				//initLastActionPlayer(positionPlayerTurnPlayOld);
				initLastAction();

				actionModel = new ActionModel();
				actionModel.setActivePlayer(positionPlayerTurnPlay);
				actionModel.setNbPlayer(currentNbPlayer);
				actionModel.setListPlayer(listPlayer);
				actionModel.setPlayerAction(currentLastAction);
				actionModel.setLastBet(lastBet);

				oldNbPlayer = currentNbPlayer;

				oldPot = currentPot;

				positionPlayerTurnPlayOld = positionPlayerTurnPlay;

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initPotWidget(currentPot);
						mainForm.initActionWidget(actionModel);
						mainForm.initPlayerWidget(listPlayer);
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

	@Override
	public void arretThreadChild() {
		// nothing
	}

	public int positionPlayerTurnPlay() {

		int position = positionPlayerTurnPlayOld;

		for (PlayerBlock playerBlock : PlayerBlock.values()) {
			Color colorScaned = robot.getPixelColor(playerBlock.getTurnPlay().x, playerBlock.getTurnPlay().y);

			if (!colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR1)
					&& !colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR2)) {
				position = playerBlock.ordinal() + 1;
				break;
			}
		}

		return position;
	}

	public void initListCurrentPlayer() {

		currentNbPlayer = 0;
		
		for (PlayerBlock playerBlock : PlayerBlock.values()) {
			PlayerModel playerModel;
			
			if (firstLoop) {
				playerModel = ocrService.scanPlayer(playerBlock);
				listPlayer.add(playerModel);
			}
			else {
				playerModel = listPlayer.get(playerBlock.ordinal());
			}
			
			Color colorScaned = robot.getPixelColor(playerBlock.getInPlay().x, playerBlock.getInPlay().y);
			
			if (colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
				playerModel.setActiv(true);
				playerModel.setStack(ocrService.scanPlayerStack(playerBlock));
			}
			else {
				playerModel.setActiv(false);
				//playerModel.setStack(ocrService.scanPlayerStack(playerBlock));
			}
				
			currentNbPlayer++;
		}

		if (firstLoop) {
			firstLoop = false;
		}
	}

//	public void initLastActionPlayer(int positionPlayerTurnPlayOld) {
//		if (positionPlayerTurnPlayOld > 0) {
//			PlayerModel playerModel = listPlayer.get(positionPlayerTurnPlayOld - 1);
//			
//			if (playerModel.getStack() == null) {
//				//playerModel.setAction(PlayerAction.FOLD);	
//			} else if (playerModel.getStack() == 0.0) {
//				playerModel.setAction(PlayerAction.ALLIN);
//			} else {
//				if (oldPot == currentPot) {
//					playerModel.setAction(PlayerAction.CHECK);
//				} else if (currentPot > oldPot) {
//					playerModel.setAction(PlayerAction.RAISE);
//				}
//			}
//		}
//	}

	public void initLastAction() {

		lastBet = 0.0;

		if (oldPot == currentPot) {
			if (oldNbPlayer == currentNbPlayer) {
				currentLastAction = PlayerAction.FOLD;
			}
			else {
				currentLastAction = PlayerAction.CHECK;
			}
		}
		else if (currentPot > oldPot) {
			// currentLastAction = PlayerAction.ALLIN;

			if (oldLastAction.equals(PlayerAction.RAISE)) {
				currentLastAction = PlayerAction.RERAISE;
			}
			else {
				currentLastAction = PlayerAction.RAISE;
			}

			lastBet = currentPot - oldPot;
		}

		oldLastAction = currentLastAction;
	}

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