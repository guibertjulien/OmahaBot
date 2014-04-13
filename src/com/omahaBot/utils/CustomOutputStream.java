package com.omahaBot.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

public class CustomOutputStream extends OutputStream {
	private StyledText textArea;

	public CustomOutputStream(StyledText textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(final int b) throws IOException {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (b != '\n') {
					// redirects data to the text area
					textArea.append(String.valueOf((char) b));
					// scrolls the text area to the end of data
					textArea.setSelection(textArea.getText().length());
				}
			}
		});
	}
	
	
}