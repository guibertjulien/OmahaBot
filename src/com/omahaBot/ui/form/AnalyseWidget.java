package com.omahaBot.ui.form;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Group;

public class AnalyseWidget extends Composite {
	private Text valuePreflop;
	private Text valueFlop;
	private Text valueTurn;
	private Text valueRiver;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnalyseWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblPreflop = new Label(this, SWT.NONE);
		lblPreflop.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPreflop.setText("PREFLOP");
		
		valuePreflop = new Text(this, SWT.BORDER);
		valuePreflop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblFlop = new Label(this, SWT.NONE);
		lblFlop.setText("FLOP");
		
		valueFlop = new Text(this, SWT.BORDER);
		valueFlop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTurn = new Label(this, SWT.NONE);
		lblTurn.setText("TURN");
		
		valueTurn = new Text(this, SWT.BORDER);
		valueTurn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblRiver = new Label(this, SWT.NONE);
		lblRiver.setText("RIVER");
		
		valueRiver = new Text(this, SWT.BORDER);
		valueRiver.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("DRAW");
		
		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDraw = tableViewerColumn_1.getColumn();
		tblclmnDraw.setWidth(100);
		tblclmnDraw.setText("Draw");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_2.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Outs");
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("%");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
