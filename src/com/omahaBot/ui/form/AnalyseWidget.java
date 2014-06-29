package com.omahaBot.ui.form;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.BoardModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;

public class AnalyseWidget extends Composite {
	private Table table_boardDraw;
	private Table table_hand;
	private Label lbl_boardDrawValue;
	private Label lbl_handDrawValue;
	private Label lbl_handValue;
	private Label lbl_pairLevelValue;
	private Label lbl_suitLevelValue;
	private Label lbl_connectorLevelValue;
	private Label lbl_powerValue;
	private Label lbl_infos;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AnalyseWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		Group grpPreflopAnalyse = new Group(this, SWT.NONE);
		grpPreflopAnalyse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPreflopAnalyse.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		grpPreflopAnalyse.setText("PreFlop Analyse");
		grpPreflopAnalyse.setLayout(new GridLayout(2, false));

		Label lblHand = new Label(grpPreflopAnalyse, SWT.NONE);
		lblHand.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHand.setText("Hand:");

		lbl_handValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_handValue = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lbl_handValue.widthHint = 150;
		lbl_handValue.setLayoutData(gd_lbl_handValue);
		lbl_handValue.setText("{}");

		Label lbl_pairLevel = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_pairLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_pairLevel.setText("Pair:");

		lbl_pairLevelValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_rankPowerValue = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lbl_rankPowerValue.widthHint = 150;
		lbl_pairLevelValue.setLayoutData(gd_lbl_rankPowerValue);
		lbl_pairLevelValue.setText("{}");

		Label lbl_suitLevel = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_suitLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_suitLevel.setText("Suited:");

		lbl_suitLevelValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_suitPowerValue = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_lbl_suitPowerValue.widthHint = 150;
		lbl_suitLevelValue.setLayoutData(gd_lbl_suitPowerValue);
		lbl_suitLevelValue.setText("{}");
		
		Label lbl_connectorLevel = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_connectorLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_connectorLevel.setText("Connected:");
		
		lbl_connectorLevelValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_connectorLevelValue = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lbl_connectorLevelValue.widthHint = 150;
		lbl_connectorLevelValue.setLayoutData(gd_lbl_connectorLevelValue);
		lbl_connectorLevelValue.setText("{}");
		
		Label lbl_power = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_power.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_power.setText("POWER:");
		
		lbl_powerValue = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_powerValue.setAlignment(SWT.CENTER);
		lbl_powerValue.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		GridData gd_lbl_powerValue = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lbl_powerValue.widthHint = 150;
		lbl_powerValue.setLayoutData(gd_lbl_powerValue);
		lbl_powerValue.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lbl_powerValue.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		new Label(grpPreflopAnalyse, SWT.NONE);
		
		lbl_infos = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_infos.setAlignment(SWT.CENTER);
		lbl_infos.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lbl_infos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_infos.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbl_infos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));

		Label lbl_board = new Label(composite, SWT.NONE);
		lbl_board.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_board.setText("BoardDraws for :");

		lbl_boardDrawValue = new Label(composite, SWT.NONE);
		GridData gd_lbl_boardDrawValue = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lbl_boardDrawValue.widthHint = 150;
		lbl_boardDrawValue.setLayoutData(gd_lbl_boardDrawValue);
		lbl_boardDrawValue.setText("{}");

		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_boardDraw = tableViewer.getTable();
		table_boardDraw.setSize(new Point(0, 200));
		GridData gd_table_boardDraw = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_table_boardDraw.heightHint = 150;
		table_boardDraw.setLayoutData(gd_table_boardDraw);
		table_boardDraw.setLinesVisible(true);
		table_boardDraw.setHeaderVisible(true);
		table_boardDraw.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDrawType = tableViewerColumn_4.getColumn();
		tblclmnDrawType.setWidth(120);
		tblclmnDrawType.setText("Type");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDraw = tableViewerColumn_1.getColumn();
		tblclmnDraw.setResizable(false);
		tblclmnDraw.setWidth(80);
		tblclmnDraw.setText("Draws");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnBestHand = tableViewerColumn_3.getColumn();
		tblclmnBestHand.setToolTipText("");
		tblclmnBestHand.setWidth(50);
		tblclmnBestHand.setText("Nuts");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnOuts = tableViewerColumn_2.getColumn();
		tblclmnOuts.setResizable(false);
		tblclmnOuts.setWidth(40);
		tblclmnOuts.setText("Outs");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPercent = tableViewerColumn.getColumn();
		tblclmnPercent.setResizable(false);
		tblclmnPercent.setWidth(30);
		tblclmnPercent.setText("%");

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_1.setLayout(new GridLayout(2, false));

		Label lbl_hand = new Label(composite_1, SWT.NONE);
		lbl_hand.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_hand.setText("HandDraws for :");

		lbl_handDrawValue = new Label(composite_1, SWT.NONE);
		GridData gd_lbl_handDrawValue = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lbl_handDrawValue.widthHint = 150;
		lbl_handDrawValue.setLayoutData(gd_lbl_handDrawValue);
		lbl_handDrawValue.setText("{}");

		TableViewer tableViewer_1 = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_hand = tableViewer_1.getTable();
		table_hand.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		GridData gd_table_hand = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_table_hand.heightHint = 150;
		table_hand.setLayoutData(gd_table_hand);
		table_hand.setLinesVisible(true);
		table_hand.setSize(new Point(0, 200));
		table_hand.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnDraw_1 = tableViewerColumn_5.getColumn();
		tblclmnDraw_1.setWidth(250);
		tblclmnDraw_1.setText("Draw");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnOuts_1 = tableViewerColumn_7.getColumn();
		tblclmnOuts_1.setWidth(40);
		tblclmnOuts_1.setToolTipText("");
		tblclmnOuts_1.setText("Outs");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_9.getColumn();
		tableColumn_4.setWidth(30);
		tableColumn_4.setText("%");
		tableColumn_4.setResizable(false);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void displayBoardDraw(BoardModel boardModel) {
		table_boardDraw.removeAll();

		if (boardModel == null) {
			lbl_boardDrawValue.setText("");
		}
		else {
			lbl_boardDrawValue.setText(boardModel.toStringByRank());

//			for (DrawModel drawModel : boardModel.initDraw()) {
//				TableItem item1 = new TableItem(table_boardDraw, SWT.NONE);
//				item1.setText(new String[] { drawModel.getType().name(), drawModel.getDrawString(),
//						drawModel.displayNuts(), String.valueOf(drawModel.getNbOut()),
//						String.valueOf(drawModel.getPercent()) });
//			}
		}
	}

	public void displayHandDraws(HandModel myHand, BoardModel board, ArrayList<DrawModel> handDraws) {
		table_hand.removeAll();
		
		if (myHand == null) {
			lbl_handDrawValue.setText("");
		}
		else {
			lbl_handDrawValue.setText("[" + myHand.toStringByRank() + "][" + board.toStringByRank() + "]");

				for (DrawModel drawModel : handDraws) {
					if (drawModel != null) {
						TableItem item1 = new TableItem(table_hand, SWT.NONE);
						item1.setText(new String[] { drawModel.toString(), "", "" });
					}
				}
		}
	}

	public void displayPreFlopAnalyse(HandModel myHand, HandPreFlopPower handPreFlopPower) {
		init();
		
		if (myHand == null) {
			lbl_handValue.setText("");
			lbl_pairLevelValue.setText("");
			lbl_suitLevelValue.setText("");
			lbl_connectorLevelValue.setText("");
			lbl_powerValue.setText("");
			lbl_infos.setText("");
		} else {
			lbl_handValue.setText(myHand.toStringByRank());
			lbl_pairLevelValue.setText(handPreFlopPower.getPreFlopPairLevel().toString());
			lbl_suitLevelValue.setText(handPreFlopPower.getPreFlopSuitLevel().toString());
			lbl_connectorLevelValue.setText(handPreFlopPower.getPreFlopStraightLevel().toString());
			lbl_powerValue.setText(String.valueOf(handPreFlopPower.getPower()));
			
			if (handPreFlopPower.isTrashHand()) {
				lbl_infos.setText("POUBELLE !");
			}
			else if (handPreFlopPower.isBestHand()) {
				lbl_infos.setText("TOP 30: " + handPreFlopPower.getBestHandLevel());
			}
			
		}
	}

	private void init() {
		table_boardDraw.removeAll();
		lbl_boardDrawValue.setText("");
		table_hand.removeAll();
		lbl_handDrawValue.setText("");

	}
}
