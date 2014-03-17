package com.omahaBot.ui.form;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.CardModel;
import com.omahaBot.model.DealStepModel;

public class BoardWidget extends Composite {

	private Group grpBoard;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Label lblNewLabel_4;

	private final static String DEFAULT_FLOP = "[FLOP]";
	private final static String DEFAULT_TURN = "[TURN]";
	private final static String DEFAULT_RIVER = "[RIVER]";

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BoardWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new RowLayout(SWT.HORIZONTAL));

		grpBoard = new Group(this, SWT.NONE);
		grpBoard.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpBoard.setText("Board");
		grpBoard.setLayout(new RowLayout(SWT.HORIZONTAL));

		lblNewLabel = new Label(grpBoard, SWT.NONE);
		lblNewLabel_1 = new Label(grpBoard, SWT.NONE);
		lblNewLabel_2 = new Label(grpBoard, SWT.NONE);
		lblNewLabel_3 = new Label(grpBoard, SWT.NONE);
		lblNewLabel_4 = new Label(grpBoard, SWT.NONE);

		init();
	}

	private void init() {
		lblNewLabel.setText(DEFAULT_FLOP);
		lblNewLabel_1.setText(DEFAULT_FLOP);
		lblNewLabel_2.setText(DEFAULT_FLOP);
		lblNewLabel_3.setText(DEFAULT_TURN);
		lblNewLabel_4.setText(DEFAULT_RIVER);
	}

	public void init(DealStepModel dealStepModel) {
		
		grpBoard.setText("Board : " + dealStepModel.getDealStep().name());
		
		ArrayList<CardModel> listCard = new ArrayList<>(dealStepModel.getListBoardCard());
		
		switch (dealStepModel.getDealStep()) {
		case FLOP:
			if (listCard.size() == 3) {
				lblNewLabel.setText(listCard.get(0).toString());
				lblNewLabel_1.setText(listCard.get(1).toString());
				lblNewLabel_2.setText(listCard.get(2).toString());
			}
			break;
		case TURN:
			if (listCard.size() == 4) {
				lblNewLabel_3.setText(listCard.get(3).toString());
			}
			break;
		case RIVER:
			if (listCard.size() == 5) {
				lblNewLabel_4.setText(listCard.get(4).toString());
			}
			break;

		default:
			init();
			break;
		}
	}
}
