package com.omaha.service.ocr;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.TessAPI1.TessPageSegMode;
import net.sourceforge.vietocr.ImageHelper;

import org.eclipse.swt.events.KeyEvent;
import org.junit.Test;

import com.omahaBot.consts.Consts;
import com.omahaBot.enums.Block;
import com.omahaBot.service.ocr.OcrServiceImpl;
import com.omahaBot.service.ocr.OcrUtils;

public class OcrUtilsTest {

	@Test
	public void test() {
		Double stack = OcrUtils.cleanPot(",Pot:1200€");
		assertTrue(",Pot:1200€", stack.equals(Double.valueOf(1200)));

		stack = OcrUtils.cleanPot(",Pot:0,58€");
		assertTrue(",Pot:0,58€", stack.equals(Double.valueOf(0.58)));

		stack = OcrUtils.cleanPot(",POT : 12,456€");
		assertTrue(",Pot:0,58€", stack.equals(Double.valueOf(12.456)));
	}

	@Test
	public void test2() {

		String ocr = "";

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("C:/_DEV/caps/testCard.png"));
			ocr = Tesseract.getInstance().doOCR(bufferedImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("--> : " + ocr);

	}

	@Test
	public void test3() {

		String ocr = "";

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("C:/_DEV/caps/PLAYER_1PLAYER_STACK.png"));
			bufferedImage = OcrUtils.rezizeImage(bufferedImage, Block.ZOOM_RESIZE);
			ocr = Tesseract.getInstance().doOCR(bufferedImage);
			System.out.println("--> : " + ocr);
			//assertTrue(ocr.equals("9678€"));
			bufferedImage = ImageIO.read(new File("C:/_DEV/caps/PLAYER_1PLAYER_STACK.png"));			
			bufferedImage = ImageHelper.convertImageToGrayscale(bufferedImage);
			bufferedImage = OcrUtils.rezizeImage(bufferedImage, Block.ZOOM_RESIZE);
			Tesseract.getInstance().setTessVariable(OcrUtils.TESS_VAR_WHITELIST, "123456789€");
			Tesseract.getInstance().setPageSegMode(TessPageSegMode.PSM_SINGLE_LINE);
			ocr = Tesseract.getInstance().doOCR(bufferedImage);
			System.out.println("--> : " + ocr);
			//assertTrue(ocr.equals("9678€"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	

	@Test
	public void testShortCut() {
		try {
			Robot robot = new Robot();
			
			robot.keyPress(java.awt.event.KeyEvent.VK_0);
			
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
