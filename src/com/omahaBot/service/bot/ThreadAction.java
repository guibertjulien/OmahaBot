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
import com.omahaBot.ui.form.MainForm;

public class ThreadAction extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private ActionModel actionModel;

	private int nbPlayerOld = 99;

	private ArrayList<PlayerBlock> listCurrentPlayer;

	private static int REFRESH_POT = 100;

	public ThreadAction(MainForm mainForm) {
		super();
		this.mainForm = mainForm;

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

		System.out.println("## START ThreadAction");
		
		while (running) {
			// nbPlayer ?
			initListCurrentPlayer();
			final int nbCurrentPlayer = listCurrentPlayer.size();// critère de rupture
			
			if (nbPlayerOld != nbCurrentPlayer) {

				nbPlayerOld = nbCurrentPlayer;

				actionModel = new ActionModel();
				actionModel.setNbPlayer(nbCurrentPlayer);
				
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initActionWidget(actionModel);
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