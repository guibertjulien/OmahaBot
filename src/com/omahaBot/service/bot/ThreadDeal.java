package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.model.DealModel;
import com.omahaBot.ui.form.MainForm;

public class ThreadDeal extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDeal.class.getName());

	private String dealIdOld = "0";

	private DealModel dealModel = new DealModel();

	private ThreadBoard threadBoard;
	
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
		
		while (running) {
			// scan du dealId toutes les 1s
			final String dealId = ocrService.scanDealId();// critère de rupture

			Assert.assertEquals(dealId.isEmpty(), false);
			
			if (!dealId.isEmpty() && !dealIdOld.equals(dealId)) {
				System.out.println("--> NEW DEAL : " + dealId);
				dealIdOld = dealId;

				initDeal();
				dealModel.setDealId(dealId);

				arretThreadChild();
				
				// demarrage d'un nouveau thread
				threadBoard = new ThreadBoard(mainForm, dealModel);
				threadBoard.start();
				
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initDealWidget(dealModel);
						//mainForm.initPlayerWidget(dealModel.getPlayers());
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
    	if (threadBoard != null && threadBoard.isAlive()) {
    		threadBoard.arret();	
    	}
	}
    
	public void initDeal() {
		dealModel = ocrService.scanNewDeal();
	}

}