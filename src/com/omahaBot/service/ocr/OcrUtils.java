package com.omahaBot.service.ocr;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.TessAPI1.TessPageSegMode;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.vietocr.ImageHelper;

import com.omahaBot.enums.Block;

public class OcrUtils {

	private final static Logger LOGGER = Logger.getLogger(OcrUtils.class.getName());

	private final static String PATTERN_DOUBLE = "(?<double>[0-9\\s]+((\\.|\\,)[0-9]+)?)";

	private final static String PATTERN_STACK = "(" + PATTERN_DOUBLE + "\\s*€" + ")";

	public final static String TESS_VAR_WHITELIST = "tessedit_char_whitelist";

	public final static String WHITELIST_CARD_RANK = "A234567891JQK";

	public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width,
				rect.height);
		return dest;
	}

	public static BufferedImage rezizeImage(BufferedImage src, int zoom) {

		// TODO : TYPE_INT_ARGB à revoir
		int type = src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType();

		BufferedImage dest = new BufferedImage(src.getWidth() * zoom, src.getHeight() * zoom,
				type);
		Graphics2D g = dest.createGraphics();
		g.drawImage(src, 0, 0, src.getWidth() * zoom, src.getHeight() * zoom, null);
		g.dispose();

		return dest;
	}

	public static Double cleanStack(String src) {
		src = src.trim();
		src = src.replaceAll("[,;]", ".");
		// src = src.replaceAll("[oO]", "0");
		// src = src.replaceAll("[sS]", "5");
		// src = src.replaceAll("[iI]", "1");
		// src = src.replaceAll("(\r\n|\n)", "");
		src = src.replaceAll(" ", "");

		String result = "";

		Pattern pattern = Pattern.compile(PATTERN_STACK, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);

		if (matcher.find()) {
			result = matcher.group("double");
		}

		result = result.replaceFirst(",", ".");

		try {
			return Double.valueOf(result);
		} catch (NumberFormatException e) {
			//LOGGER.warning(e.getMessage());
			return 0.0;
		}
	}

	public static String cleanDealId(String src) {
		src = src.trim();
		src = src.replaceAll("#", "");
		src = src.replaceAll("(\r\n|\n)", "");
		src = src.replaceAll(" ", "");
		// src = src.substring(src.length() - Consts.HAND_NUM_LENGHT,
		// src.length());

		return src;
	}

	public static Double cleanPot(String src) {

		src = src.trim();
		src = src.replaceAll("[,;]", ".");
		src = src.replaceAll("[oO]", "0");
		src = src.replaceAll("[sS]", "5");
		src = src.replaceAll("[iI]", "1");
		src = src.replaceAll("(\r\n|\n)", "");
		src = src.replaceAll(" ", "");

		String result = "";

		Pattern pattern = Pattern.compile(PATTERN_STACK, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);

		if (matcher.find()) {
			result = matcher.group("double");
		}

		result = result.replaceFirst(",", ".");

		try {
			return Double.valueOf(result);
		} catch (NumberFormatException e) {
			//LOGGER.warning(e.getMessage());
			return 0.0;
		}
	}

	public static String scanCardRank(Tesseract instance, BufferedImage bufferedImage) throws TesseractException {

		bufferedImage = ImageHelper.convertImageToGrayscale(bufferedImage);

		instance.setTessVariable(OcrUtils.TESS_VAR_WHITELIST, OcrUtils.WHITELIST_CARD_RANK);
		instance.setPageSegMode(TessPageSegMode.PSM_SINGLE_WORD);
		String result = instance.doOCR(bufferedImage).trim().toUpperCase();

		result = result.trim();
		result = result.replaceAll("(\r\n|\n)", "");
		result = result.replaceAll("1Q", "10");

		//System.out.println("--> scanCardRank : " + result);

		return result;
	}

	public static String scanCardRank(Tesseract1 instance, BufferedImage bufferedImage) throws TesseractException {
		bufferedImage = ImageHelper.convertImageToGrayscale(bufferedImage);
		bufferedImage = OcrUtils.rezizeImage(bufferedImage, Block.ZOOM_RESIZE);

		instance.setTessVariable(OcrUtils.TESS_VAR_WHITELIST, OcrUtils.WHITELIST_CARD_RANK);
		instance.setPageSegMode(TessPageSegMode.PSM_SINGLE_WORD);
		String result = instance.doOCR(bufferedImage).trim().toUpperCase();

		result = result.trim();
		result = result.replaceAll("(\r\n|\n)", "");
		result = result.replaceAll("1Q", "10");

		//System.out.println("--> scanCardRank : " + result);

		return result;
	}
}
