package com.omahaBot.ui.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class PotWidget extends Composite {

	private Label lblNewLabel;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PotWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new RowLayout(SWT.HORIZONTAL));

		Group grpPot = new Group(this, SWT.NONE);
		grpPot.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpPot.setText("Pot");
		grpPot.setLayout(new RowLayout(SWT.HORIZONTAL));

		lblNewLabel = new Label(grpPot, SWT.NONE);
		lblNewLabel.setLayoutData(new RowData(150, SWT.DEFAULT));
	}

	public void init(Double pot) {
		lblNewLabel.setText(pot.toString());	
	}
}
