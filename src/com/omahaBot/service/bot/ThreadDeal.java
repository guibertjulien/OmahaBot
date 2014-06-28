package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.omahaBot.enums.PlayerAction;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDeal extends MyThread {

	private static final Logger log = Logger.getLogger(ThreadDeal.class);

	private String oldDealId = "0", currentDealId;

	private DealModel dealModel;

	private ThreadDealStep threadDealStep;

	public ThreadDeal(MainForm mainForm) {
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
		log.debug(">> START ThreadDeal : " + this.getId());

		initialize();

		while (running) {
			// scan du dealId toutes les 1s
			currentDealId = ocrService.scanDealId();// critère de rupture

			if (!currentDealId.isEmpty() && !oldDealId.equals(currentDealId)) {
				log.debug("NEW DEAL : " + currentDealId);

				oldDealId = currentDealId;

				dealModel = new DealModel();
				dealModel.setDealId(currentDealId);

				arretThreadChild();

				if (running) {
					// demarrage d'un nouveau thread
					threadDealStep = new ThreadDealStep(mainForm);
					threadDealStep.start();
				}

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

			try {
				// pause
				sleep(REFRESH);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		log.debug("<< STOP ThreadDeal : " + this.getId());
	}

	@Override
	public void arretThreadChild() {
		if (threadDealStep != null && threadDealStep.isAlive()) {
			threadDealStep.arret();
		}
	}

	@Override
	public void initialize() {
	}
}