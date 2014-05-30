package com.omahaBot.service.bot;

import java.awt.Robot;

import com.omahaBot.service.ai.AnalyserServiceImpl;
import com.omahaBot.service.ai.PreFlopAnalyserServiceImpl;
import com.omahaBot.service.ocr.OcrServiceImpl;
import com.omahaBot.ui.form.MainForm;

public abstract class MyThread extends Thread implements IThread{

	protected volatile boolean running = true;

	protected OcrServiceImpl ocrService = OcrServiceImpl.getInstance();

	// TODO singleton ?
	protected PreFlopAnalyserServiceImpl preFlopAnalyserServiceImpl = new PreFlopAnalyserServiceImpl();

	// TODO singleton ?
	protected AnalyserServiceImpl analyserService = new AnalyserServiceImpl();
	
	protected Robot robot;

	protected static int REFRESH = 1000;
	
	protected MainForm mainForm;
	
	@Override
    public void arret() {
        running = false;
    	arretThreadChild();
    }
}
