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
import com.omahaBot.model.DealModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadPlayer extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadBoard.class.getName());

	private DealModel dealModel;

	private int nbPlayerOld = 99;

	private ArrayList<PlayerBlock> listCurrentPlayer;

	private static int REFRESH_POT = 100;

	public ThreadPlayer(MainForm mainForm, DealModel dealModel) {
		super();
		this.mainForm = mainForm;
		this.dealModel = dealModel;

		listCurrentPlayer = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));

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

		System.out.println("## START ThreadPlayer");
		
		while (running) {
			// nbPlayer ?
			initListCurrentPlayer();
			final int nbCurrentPlayer = listCurrentPlayer.size();// critère de rupture
			
			if (nbPlayerOld != nbCurrentPlayer) {
				
				// TODO scan des players
				// TODO active player

				nbPlayerOld = nbCurrentPlayer;

				dealModel.setNbPlayer(nbCurrentPlayer);

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initDealWidget(dealModel);
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

		System.out.println("## STOP ThreadPlayer");
	}

	@Override
	public void arretThreadChild() {
		// nothing
	}

	public void initListCurrentPlayer() {

		ArrayList<PlayerBlock> listPlayer = new ArrayList<>(listCurrentPlayer);

		for (PlayerBlock playerBlock : listPlayer) {
			Color colorScaned = robot.getPixelColor(playerBlock.getPlayIN().x, playerBlock.getPlayIN().y);

			if (!colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
				listCurrentPlayer.remove(playerBlock);
			}
		}
	}
}