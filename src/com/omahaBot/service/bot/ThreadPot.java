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

public class ThreadPot extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadBoard.class.getName());

	private DealModel dealModel;

	private Double potOld = 0.0;

	private Double lastBet = 0.0;

	private ArrayList<PlayerBlock> listPlayerIN;

	private static int REFRESH_POT = 100;

	public ThreadPot(MainForm mainForm, DealModel dealModel) {
		super();
		this.mainForm = mainForm;
		this.dealModel = dealModel;
		
		listPlayerIN = new ArrayList<PlayerBlock>(EnumSet.allOf(PlayerBlock.class));

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

		System.out.println("## START ThreadPot");
		
		while (running) {
			// scan du pot toutes les 100ms
			final Double currentPot = ocrService.scanPot();

			if (!potOld.equals(currentPot)) {
				System.out.println("--> NEW POT : " + currentPot);

				// nb player

				// active player
				initListPlayerIN();
				System.out.println("nb players : " + listPlayerIN.size());

				if (currentPot > potOld) {
					lastBet = currentPot - potOld;
				}

				potOld = currentPot;

				dealModel.setCurrentPot(currentPot);
				dealModel.setNbPlayer(listPlayerIN.size());

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

		System.out.println("## STOP ThreadPot");
	}

	public void arret() {
		running = false;
	}

	public void initListPlayerIN() {

		ArrayList<PlayerBlock> listPlayer = new ArrayList<>(listPlayerIN);
		
		for (PlayerBlock playerBlock : listPlayer) {
			Color colorScaned = robot.getPixelColor(playerBlock.getPlayIN().x, playerBlock.getPlayIN().y);
			
			System.out.println(colorScaned);

			if (!colorScaned.equals(PixelConsts.PLAYER_IN_COLOR)) {
				listPlayerIN.remove(playerBlock);
			}
		}
	}
}