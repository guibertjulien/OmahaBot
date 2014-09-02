package com.omahaBot.strategy;

public class BluffTemplate {

	/*
	 * FLUSH_DRAW
	 * fin de position (CUT-OFF or BUTTOn)
	 * pas de mise precedente
	 * betBig_fold
	 * first time (FLOP or TURN)
	 * % gain
	 */
	public boolean isFlushDraw() {
		return false;	
	}
	
	/*
	 * FLUSH
	 * fin de position (CUT-OFF or BUTTOn)
	 * pas de mise precedente
	 * betSmall_fold
	 * first time (FLOP or TURN or RIVER)
	 * % gain
	 */
	public boolean isFlush() {
		return false;
	}
	
	/*
	 * dernier de parole (BUTTON)
	 * pas de bet
	 * betSmall_fold 
	 */
	public boolean noBetAndButton() {
		return false;
	}
	
	/*
	 * HU
	 */ 
}
