package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.omahaBot.ui.form.MainForm;

public class ThreadPot extends MyThread {

	private static final Logger log = Logger.getLogger(ThreadPot.class);

	private Double oldPot = 0.0, currentPot;

	private static int REFRESH_POT = 100;

	public ThreadPot(MainForm mainForm) {
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

		log.debug(">> START ThreadPot");

		while (running) {
			// scan du pot toutes les 100ms
			currentPot = ocrService.scanPot();

			if (!oldPot.equals(currentPot)) {
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

		log.debug("<< STOP ThreadPot");
	}

	@Override
	public void arretThreadChild() {
		// nothing
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}
}