package com.omahaBot.ui.form;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.PlayerModel;
import org.eclipse.swt.widgets.Group;

public class PlayerBlockWidget extends Composite {
	private DataBindingContext m_bindingContext;

	private PlayerModel playerModel;
	private Label lblstack;
	private Label lblname;
	private Label lblPlayer;
	private Label lblplayerAction;
	private Label lbllastBet;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PlayerBlockWidget(Composite parent, int style, int position) {
		super(parent, SWT.BORDER);
		setLayout(new GridLayout(1, false));

		lblPlayer = new Label(this, SWT.NONE);
		lblPlayer.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblPlayer.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblPlayer.setText("Player " + position);

		lblname = new Label(this, SWT.NONE);
		GridData gd_lblname = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblname.widthHint = 150;
		lblname.setLayoutData(gd_lblname);
		lblname.setText("[name]");

		lblstack = new Label(this, SWT.NONE);
		GridData gd_lblstack = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblstack.widthHint = 150;
		lblstack.setLayoutData(gd_lblstack);
		lblstack.setText("[stack]");
		
		lblplayerAction = new Label(this, SWT.NONE);
		GridData gd_lblplayerAction = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblplayerAction.widthHint = 150;
		lblplayerAction.setLayoutData(gd_lblplayerAction);
		lblplayerAction.setText("[action]");
		
		lbllastBet = new Label(this, SWT.NONE);
		GridData gd_lbllastBet = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lbllastBet.widthHint = 150;
		lbllastBet.setLayoutData(gd_lbllastBet);
		lbllastBet.setText("[lastBet]");
		
		position++;
		m_bindingContext = initDataBindings();
	}

	public PlayerModel getPlayerModel() {
		return playerModel;
	}

	public void setPlayerModel(PlayerModel playerModel) {
		this.playerModel = playerModel;
		if (m_bindingContext != null)
			m_bindingContext.dispose();
		m_bindingContext = initDataBindings();
		
		if (playerModel.isActiv()) {
			setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		}
		else {
			setBackground(SWTResourceManager.getColor(240,240,240));
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextLbl_nameObserveWidget = WidgetProperties.text().observe(lblname);
		IObservableValue namePlayerModelObserveValue = PojoProperties.value("name").observe(playerModel);
		bindingContext.bindValue(observeTextLbl_nameObserveWidget, namePlayerModelObserveValue, null, null);
		//
		IObservableValue observeTextLblNewLabel_2ObserveWidget = WidgetProperties.text().observe(lblstack);
		IObservableValue stackPlayerModelObserveValue = PojoProperties.value("stack").observe(playerModel);
		bindingContext.bindValue(observeTextLblNewLabel_2ObserveWidget, stackPlayerModelObserveValue, null, null);
		//
		IObservableValue observeTextLblplayerActionObserveWidget = WidgetProperties.text().observe(lblplayerAction);
		IObservableValue actionPlayerModelObserveValue = PojoProperties.value("action").observe(playerModel);
		bindingContext.bindValue(observeTextLblplayerActionObserveWidget, actionPlayerModelObserveValue, null, null);
		//
		IObservableValue observeTextLbllastBetObserveWidget = WidgetProperties.text().observe(lbllastBet);
		IObservableValue lastBetPlayerModelObserveValue = PojoProperties.value("lastBet").observe(playerModel);
		bindingContext.bindValue(observeTextLbllastBetObserveWidget, lastBetPlayerModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
