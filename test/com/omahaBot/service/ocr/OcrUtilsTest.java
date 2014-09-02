package com.omahaBot.service.ocr;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.TessAPI1.TessPageSegMode;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.vietocr.ImageHelper;

import org.junit.Ignore;
import org.junit.Test;

import com.omahaBot.enums.Block;
import com.omahaBot.exception.ScanOcrException;

public class OcrUtilsTest {

	@Test
	public void testCleanPot() throws ScanOcrException {
		BigDecimal stack;
		
		stack = OcrUtils.cleanPot(",Pot:1200€");
		assertTrue(",Pot:1200€", stack.doubleValue() == 1200);

		stack = OcrUtils.cleanPot(",Pot:0,58€");
		assertTrue(",Pot:0,58€", stack.doubleValue() == 0.58);

		stack = OcrUtils.cleanPot(",POT : 12,456€");
		assertTrue(",POT : 12,456€", stack.doubleValue() == 12.456);
		
		stack = OcrUtils.cleanPot("Pot: 0,16% I");
		assertTrue("Pot: 0,16% I", stack.doubleValue() == 0.16);
		
		stack = OcrUtils.cleanPot("");
		assertTrue("vide", stack.doubleValue() == 0);
	}
	
	@Test
	public void testStack() throws ScanOcrException {
		BigDecimal stack;

		stack = OcrUtils.cleanStack("123456789.01 €");
		assertTrue("123456789.01", stack.doubleValue() == 123456789.01);
		
		stack = OcrUtils.cleanStack("All In");
		assertTrue("All In", stack.doubleValue() == -1);
		
		stack = OcrUtils.cleanStack("");
		assertTrue("vide", stack.doubleValue() == 0);
	}

	@Test
	@Ignore("TODO à voir")
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
	@Ignore("TODO à voir")
	public void test3() {

		String ocr = "";

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File("C:/_DEV/caps/PLAYER_1PLAYER_STACK.png"));
			bufferedImage = OcrUtils.rezizeImage(bufferedImage, Block.ZOOM_RESIZE);
			ocr = Tesseract.getInstance().doOCR(bufferedImage);
			System.out.println("--> : " + ocr);
			// assertTrue(ocr.equals("9678€"));
			bufferedImage = ImageIO.read(new File("C:/_DEV/caps/PLAYER_1PLAYER_STACK.png"));
			bufferedImage = ImageHelper.convertImageToGrayscale(bufferedImage);
			bufferedImage = OcrUtils.rezizeImage(bufferedImage, Block.ZOOM_RESIZE);
			Tesseract.getInstance().setTessVariable(OcrUtils.TESS_VAR_WHITELIST, "123456789€");
			Tesseract.getInstance().setPageSegMode(TessPageSegMode.PSM_SINGLE_LINE);
			ocr = Tesseract.getInstance().doOCR(bufferedImage);
			System.out.println("--> : " + ocr);
			// assertTrue(ocr.equals("9678€"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
