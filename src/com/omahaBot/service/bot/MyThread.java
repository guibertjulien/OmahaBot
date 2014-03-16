package com.omahaBot.service.bot;

import java.awt.Robot;

import com.omahaBot.service.ocr.OcrServiceImpl;
import com.omahaBot.ui.form.MainForm;

public class MyThread extends Thread {

	protected volatile boolean running = true;

	protected OcrServiceImpl ocrService = OcrServiceImpl.getInstance();

	protected Robot robot;

	protected static int REFRESH = 1000;
	
	protected MainForm mainForm;
}
