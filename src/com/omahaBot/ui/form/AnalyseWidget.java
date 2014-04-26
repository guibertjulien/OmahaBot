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

import com.omahaBot.enums.PreFlopPower;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.CombinaisonModel;
import com.omahaBot.model.DrawModel;
import com.omahaBot.model.HandModel;

public class AnalyseWidget extends Composite {
	private Table table_boardDraw;
	private Table table_hand;
	private Label lbl_boardDrawValue;
	private Label lbl_handValue;
	private Label lbl_rankPowerValue;
	private Label lbl_suitPowerValue;

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
		lblHand.setText("Hand :");

		lbl_handValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_handValue = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lbl_handValue.widthHint = 150;
		lbl_handValue.setLayoutData(gd_lbl_handValue);
		lbl_handValue.setText("{}");

		Label lbl_rankPower = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_rankPower.setText("RankPower :");

		lbl_rankPowerValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_rankPowerValue = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lbl_rankPowerValue.widthHint = 150;
		lbl_rankPowerValue.setLayoutData(gd_lbl_rankPowerValue);
		lbl_rankPowerValue.setText("{}");

		Label lbl_suitPower = new Label(grpPreflopAnalyse, SWT.NONE);
		lbl_suitPower.setText("SuitPower :");

		lbl_suitPowerValue = new Label(grpPreflopAnalyse, SWT.NONE);
		GridData gd_lbl_suitPowerValue = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_lbl_suitPowerValue.widthHint = 150;
		lbl_suitPowerValue.setLayoutData(gd_lbl_suitPowerValue);
		lbl_suitPowerValue.setText("{}");

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Label lbl_board = new Label(composite, SWT.NONE);
		lbl_board.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_board.setText("BoardDraws for :");

		lbl_boardDrawValue = new Label(composite, SWT.NONE);
		GridData gd_lbl_boardDrawValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lbl_boardDrawValue.widthHint = 150;
		lbl_boardDrawValue.setLayoutData(gd_lbl_boardDrawValue);
		lbl_boardDrawValue.setText("{}");

		TableViewer tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_boardDraw = tableViewer.getTable();
		table_boardDraw.setSize(new Point(0, 200));
		GridData gd_table_boardDraw = new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1);
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
		tblclmnDraw.setWidth(60);
		tblclmnDraw.setText("Draws");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnBestHand = tableViewerColumn_3.getColumn();
		tblclmnBestHand.setToolTipText("Best hole cards");
		tblclmnBestHand.setWidth(50);
		tblclmnBestHand.setText("Nuts");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnOuts = tableViewerColumn_2.getColumn();
		tblclmnOuts.setResizable(false);
		tblclmnOuts.setWidth(50);
		tblclmnOuts.setText("Outs");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPercent = tableViewerColumn.getColumn();
		tblclmnPercent.setResizable(false);
		tblclmnPercent.setWidth(40);
		tblclmnPercent.setText("%");

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));

		Label lbl_hand = new Label(composite_1, SWT.NONE);
		lbl_hand.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lbl_hand.setText("HandDraws for :");

		Label lbl_handDrawValue = new Label(composite_1, SWT.NONE);
		GridData gd_lbl_handDrawValue = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lbl_handDrawValue.widthHint = 150;
		lbl_handDrawValue.setLayoutData(gd_lbl_handDrawValue);
		lbl_handDrawValue.setText("{}");

		TableViewer tableViewer_1 = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_hand = tableViewer_1.getTable();
		GridData gd_table_hand = new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1);
		gd_table_hand.heightHint = 150;
		table_hand.setLayoutData(gd_table_hand);
		table_hand.setLinesVisible(true);
		table_hand.setSize(new Point(0, 200));
		table_hand.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_5.getColumn();
		tableColumn.setWidth(120);
		tableColumn.setText("Type");
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_6.getColumn();
		tableColumn_1.setWidth(60);
		tableColumn_1.setText("Draws");
		tableColumn_1.setResizable(false);
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_7.getColumn();
		tableColumn_2.setWidth(50);
		tableColumn_2.setToolTipText("Best hole cards");
		tableColumn_2.setText("Nuts");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_8.getColumn();
		tableColumn_3.setWidth(50);
		tableColumn_3.setText("Outs");
		tableColumn_3.setResizable(false);
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_9.getColumn();
		tableColumn_4.setWidth(40);
		tableColumn_4.setText("%");
		tableColumn_4.setResizable(false);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void displayBoardDraw(BoardModel boardModel) {
		table_boardDraw.removeAll();

		ArrayList<DrawModel> listDraw = boardModel.initDraw();

		lbl_boardDrawValue.setText(boardModel.toStringByRank());

		for (DrawModel drawModel : listDraw) {
			TableItem item1 = new TableItem(table_boardDraw, SWT.NONE);
			item1.setText(new String[] { drawModel.getType().name(), drawModel.getDrawString(),
					drawModel.displayNuts(), String.valueOf(drawModel.getNbOut()),
					String.valueOf(drawModel.getPercent()) });
		}
	}

	public void displayCombinaisonDraw(ArrayList<CombinaisonModel> combinaisons) {
		table_hand.removeAll();

		for (CombinaisonModel combinaisonModel : combinaisons) {
			ArrayList<DrawModel> listDraw = combinaisonModel.initDraw();
			
			for (DrawModel drawModel : listDraw) {
				TableItem item1 = new TableItem(table_hand, SWT.NONE);
				item1.setText(new String[] { drawModel.getType().name(), drawModel.getDrawString(),
						drawModel.displayNuts(), String.valueOf(drawModel.getNbOut()),
						String.valueOf(drawModel.getPercent()) });
			}	
		}
	}
	
	public void displayPreFlopAnalyse(HandModel myHand, PreFlopPower preFlopPower) {
		lbl_handValue.setText(myHand.toStringByRank());
		lbl_rankPowerValue.setText(preFlopPower.getPreFlopRank().toString());
		lbl_suitPowerValue.setText(preFlopPower.getPreFlopSuit().toString());
	}
}
