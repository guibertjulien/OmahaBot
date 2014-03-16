package com.omahaBot.enums;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

public enum Button {
	PLAYER_1(0, 0), PLAYER_2(0, 0), PLAYER_3(876, 535), PLAYER_4(0, 0), PLAYER_5(0, 0), PLAYER_6(0, 0);

	private int posX;
	private int posY;

	private static Color pixelColor = new Color(196, 194, 33);

	Button(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public static int getButtonPosition() {
		
		Robot robot;
		try {
			robot = new Robot();

			int position = 1;

			for (Button button : Button.values()) {

				Color pixelColorScaned = robot.getPixelColor(button.getPosX(), button.getPosY());
				
				if (pixelColorScaned.equals(pixelColor)) {
					return position;
				}

				position++;
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
