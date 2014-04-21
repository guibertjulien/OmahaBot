package com.omahaBot.ui.form;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.BoardDrawModel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;

public class AnalyseWidget extends Composite {
	private Table table_boardDraw;
	private Table table_hand;
	private Text text;
	private Text text_1;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnalyseWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Hand");
		
		text_1 = new Text(group, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBoard = new Label(group, SWT.NONE);
		lblBoard.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBoard.setText("Board");
		
		text = new Text(group, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_boardDraw = tableViewer.getTable();
		table_boardDraw.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		table_boardDraw.setLinesVisible(true);
		table_boardDraw.setHeaderVisible(true);
		table_boardDraw.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDraw = tableViewerColumn_1.getColumn();
		tblclmnDraw.setResizable(false);
		tblclmnDraw.setWidth(150);
		tblclmnDraw.setText("Board draw");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnOuts = tableViewerColumn_2.getColumn();
		tblclmnOuts.setResizable(false);
		tblclmnOuts.setWidth(100);
		tblclmnOuts.setText("Nb outs");
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPercent = tableViewerColumn.getColumn();
		tblclmnPercent.setResizable(false);
		tblclmnPercent.setWidth(50);
		tblclmnPercent.setText("%");
		
		TableViewer tableViewer_1 = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_hand = tableViewer_1.getTable();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void displayBoardDraw (ArrayList<BoardDrawModel> list) {
		for (BoardDrawModel boardDraw : list) {
			TableItem item1 = new TableItem(table_boardDraw, SWT.NONE);
			 item1.setText(new String[] { boardDraw.getBoardDrawPower().name(), String.valueOf(boardDraw.getNbOut()), String.valueOf(boardDraw.getPercent()) });
		}	 
	}
}
