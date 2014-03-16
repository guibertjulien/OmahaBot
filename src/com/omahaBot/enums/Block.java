package com.omahaBot.enums;

import java.awt.Rectangle;

public enum Block {
	
	POT(new Rectangle(477, 97, 270, 22)),
	HAND_ID(new Rectangle(50, 42, 126, 13)),
	WINDOWS_TITLE(new Rectangle(24, 7, 1072, 15));

	public static final int ZOOM_RESIZE = 2;

	private Rectangle rect;

	Block(Rectangle rect) {
		this.rect = rect;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	
}
