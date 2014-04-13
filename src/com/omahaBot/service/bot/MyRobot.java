package com.omahaBot.service.bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;
import java.util.logging.Logger;

import com.omahaBot.enums.PlayerShortcut;

public class MyRobot extends Robot {

	private final static Logger LOGGER = Logger.getLogger(MyRobot.class.getName());

	public static final int MIN = 1000;
	public static final int MAX = 5000;

	public MyRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void clickAction(PlayerShortcut playerShortcut, long threadId) {

		Random rand = new Random();
		int randomNumber = rand.nextInt(MAX - MIN + 1) + MIN;

		System.out.println("threadId : " + threadId + " / random : " + randomNumber + " / playerShortcut : " + playerShortcut);
		//LOGGER.log(Level.INFO, "ramdom : {0} / playerShortcut : {1}", new Object[] { randomNumber, playerShortcut });

		delay(randomNumber);
		this.keyPress(playerShortcut.getShortcut());
	}
}
