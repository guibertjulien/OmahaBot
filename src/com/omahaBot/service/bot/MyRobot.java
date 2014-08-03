package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

import org.apache.log4j.Logger;

import com.omahaBot.enums.BettingDecision;
import com.omahaBot.service.ai.PreFlopAnalyser;

public class MyRobot extends Robot {

	private static final Logger log = Logger.getLogger(PreFlopAnalyser.class);

	public static final int MIN = 1000;
	public static final int MAX = 3000;

	public MyRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void clickAction(BettingDecision bettingDecision, long threadId) {

		if (log.isDebugEnabled()) {
			log.debug(">> clickAction");
		}

		Random rand = new Random();
		int randomNumber = rand.nextInt(MAX - MIN + 1) + MIN;

		delay(randomNumber);
		keyPress(bettingDecision.getShortcut());

		log.debug("threadId=" + threadId + " / random=" + randomNumber + " / playerShortcut=" + bettingDecision);

		System.out.println("KEYPRESS at " + randomNumber);
	}
}
