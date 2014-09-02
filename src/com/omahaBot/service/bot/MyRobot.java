package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.service.ai.PreFlopAnalyser;

public class MyRobot extends Robot {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	public static final int MIN = 1500;
	public static final int FAST = 4000;
	public static final int MAX = 6500;

	public enum ClickSpeed {
		SLOW, FAST
	}

	public MyRobot() throws AWTException {
		super();
	}

	public void clickAction(BettingDecision bettingDecision, ClickSpeed clickSpeed) {

		if (log.isDebugEnabled()) {
			log.debug(">> clickAction");
		}

		Random rand = new Random();
		int randomNumber;

		if (clickSpeed.equals(ClickSpeed.SLOW)) {
			randomNumber = rand.nextInt(MAX - MIN + 1) + MIN;
		}
		else {
			randomNumber = rand.nextInt(FAST - MIN + 1) + MIN;
		}

		delay(randomNumber);
		keyPress(bettingDecision.getShortcut());

		System.out.println("KEYPRESS at " + randomNumber + "(" + bettingDecision.getShortcut() + ")");
	}
}
