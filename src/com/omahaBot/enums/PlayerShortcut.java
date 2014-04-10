package com.omahaBot.enums;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PlayerShortcut {
	FOLD(KeyEvent.VK_1),
	FOLD_ALWAYS(KeyEvent.VK_2),
	CHECK(KeyEvent.VK_3),
	CALL(KeyEvent.VK_4),
	CHECK_CALL(KeyEvent.VK_5),
	BET_RAISE(KeyEvent.VK_6),
	ALLIN(KeyEvent.VK_7);

	private static final List<PlayerShortcut> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	private int shortcut;

	private PlayerShortcut(int shortcut) {
		this.shortcut = shortcut;
	}

	public int getShortcut() {
		return shortcut;
	}

	public void setShortcut(int shortcut) {
		this.shortcut = shortcut;
	}

	public static PlayerShortcut random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
