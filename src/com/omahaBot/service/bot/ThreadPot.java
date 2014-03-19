package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

import com.omahaBot.ui.form.MainForm;

public class ThreadPot extends MyThread {

	private final static Logger LOGGER = Logger.getLogger(ThreadDealStep.class.getName());

	private Double oldPot = 0.0, currentPot;
	
	private static int REFRESH_POT = 100;

	public ThreadPot(MainForm mainForm) {
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

		System.out.println("## START ThreadPot");
		
		while (running) {
			// scan du pot toutes les 100ms
			currentPot = ocrService.scanPot();

			if (!oldPot.equals(currentPot)) {
				//System.out.println("--> NEW POT : " + currentPot);
				oldPot = currentPot;

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						mainForm.initPotWidget(currentPot);
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

	@Override
	public void arretThreadChild() {
		// nothing
	}
}