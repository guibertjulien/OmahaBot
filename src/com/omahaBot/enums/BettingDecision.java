package com.omahaBot.enums;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BettingDecision {
	FOLD_ALWAYS(KeyEvent.VK_NUMPAD1),
	CHECK_FOLD(KeyEvent.VK_NUMPAD2),
	CHECK_CALL(KeyEvent.VK_NUMPAD3),
	BET_RAISE_25(KeyEvent.VK_NUMPAD6),
	BET_RAISE_50(KeyEvent.VK_NUMPAD7),
	BET_RAISE_75(KeyEvent.VK_NUMPAD8),
	ALLIN(KeyEvent.VK_NUMPAD9);

	private static final List<BettingDecision> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	private int shortcut;

	private BettingDecision(int shortcut) {
		this.shortcut = shortcut;
	}

	public int getShortcut() {
		return shortcut;
	}

	public void setShortcut(int shortcut) {
		this.shortcut = shortcut;
	}

	public static BettingDecision random() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	public static BettingDecision randomBetween(BettingDecision ... array) {
		List<BettingDecision> list = Arrays.asList(array);
		return list.get(RANDOM.nextInt(list.size()));
	}
}
