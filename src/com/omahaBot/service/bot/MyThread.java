package com.omahaBot.service.bot;

import java.awt.Robot;

import com.omahaBot.service.ai.PostFlopAnalyser;
import com.omahaBot.service.ai.PreFlopAnalyser;
import com.omahaBot.service.ocr.OcrService;
import com.omahaBot.service.ocr.OcrServiceImpl;
import com.omahaBot.ui.form.MainForm;

public abstract class MyThread extends Thread implements IThread{

	protected volatile boolean running = true;

	protected OcrService ocrService = OcrServiceImpl.getInstance();

	// TODO singleton ?
	protected PreFlopAnalyser preFlopAnalyser = new PreFlopAnalyser();
	protected PostFlopAnalyser postFlopAnalyser = new PostFlopAnalyser();
	
	protected Robot robot;

	protected static int REFRESH = 100;
	
	protected MainForm mainForm;
	
	@Override
    public void arret() {
        running = false;
    	arretThreadChild();
    }
}
