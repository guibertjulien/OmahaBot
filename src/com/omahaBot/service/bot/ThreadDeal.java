package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.enums.PlayerAction;
import com.omahaBot.model.ActionModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDeal extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDeal.class.getName());

	private String oldDealId = "0", currentDealId;

	private DealModel dealModel;

	private ThreadDealStep threadDealStep;
	
	public ThreadDeal(MainForm mainForm) {
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
		
		System.out.println("########## START ThreadDeal ##########");
		
		initialize();
		
		while (running) {
			// scan du dealId toutes les 1s
			currentDealId = ocrService.scanDealId();// critère de rupture
			
			if (!currentDealId.isEmpty() && !oldDealId.equals(currentDealId)) {
				System.out.println("--> NEW DEAL : " + currentDealId);
				
				oldDealId = currentDealId;

				dealModel = new DealModel();
				dealModel.setDealId(currentDealId);
				
				arretThreadChild();
				
				// demarrage d'un nouveau thread
				threadDealStep = new ThreadDealStep(mainForm);
				threadDealStep.start();
				
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initDealWidget(dealModel);
						mainForm.initPotWidget(0.0);
						
						ActionModel actionModel = new ActionModel();
						actionModel.setActivePlayer(0);
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

		System.out.println("########## STOP ThreadDeal  ##########");
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