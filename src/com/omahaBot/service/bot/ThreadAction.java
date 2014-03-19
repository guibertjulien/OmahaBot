package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.consts.PixelConsts;
import com.omahaBot.enums.PlayerBlock;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadAction extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private ActionModel actionModel;

	private int nbPlayerOld = 99;

	private int positionPlayerTurnPlayOld = 0;

	private ArrayList<PlayerBlock> listCurrentPlayerBlock;

	private ArrayList<PlayerModel> listCurrentPlayer;

	private static int REFRESH_POT = 100;

	private boolean firstLoop = true;

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

		listCurrentPlayerBlock = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));
		listCurrentPlayer = new ArrayList<PlayerModel>();

		while (running) {

			// critère de rupture : tour du joueur
			final int positionPlayerTurnPlay = positionPlayerTurnPlay();

			if (positionPlayerTurnPlayOld != positionPlayerTurnPlay) {

				positionPlayerTurnPlayOld = positionPlayerTurnPlay;

				initListCurrentPlayer();

				actionModel = new ActionModel();
				actionModel.setActivePlayer(positionPlayerTurnPlay);
				actionModel.setNbPlayer(nbPlayer());
				actionModel.setListPlayer(listCurrentPlayer);

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
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

	@Override
	public void arretThreadChild() {
		// nothing
	}

	public int positionPlayerTurnPlay() {

		int position = positionPlayerTurnPlayOld;

		for (PlayerBlock playerBlock : listCurrentPlayerBlock) {
			Color colorScaned = robot.getPixelColor(playerBlock.getTurnPlay().x, playerBlock.getTurnPlay().y);
			
			if (!colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR1)
					&& !colorScaned.equals(PixelConsts.PLAYER_NOT_TURN_PLAY_COLOR2)) {
				position = playerBlock.ordinal() + 1;
				break;
			}
		}
		
		return position;
	}

	public int nbPlayer() {

		ArrayList<PlayerBlock> listPlayerBlock = new ArrayList<>(listCurrentPlayerBlock);

		for (PlayerBlock playerBlock : listPlayerBlock) {
			Color colorScaned = robot.getPixelColor(playerBlock.getInPlay().x, playerBlock.getInPlay().y);

			if (!colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
				listCurrentPlayerBlock.remove(playerBlock);
			}
		}

		System.out.println("NB : " + listCurrentPlayerBlock.size());

		return listCurrentPlayerBlock.size();

	}

	public void initListCurrentPlayer() {

		for (PlayerBlock playerBlock : PlayerBlock.values()) {
			Color colorScaned = robot.getPixelColor(playerBlock.getInPlay().x, playerBlock.getInPlay().y);

			if (colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
				if (firstLoop) {
					listCurrentPlayer.add(ocrService.scanPlayer(playerBlock));
				}
				else {
					PlayerModel playerModel = listCurrentPlayer.get(playerBlock.ordinal());
					playerModel.setStack(ocrService.scanPlayerStack(playerBlock));
				}
			}
			else {
				if (firstLoop) {
					listCurrentPlayer.add(new PlayerModel());
				}
			}
		}

		if (firstLoop) {
			firstLoop = false;
		}
	}
}