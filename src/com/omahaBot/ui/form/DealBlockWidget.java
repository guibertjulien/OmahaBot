package com.omahaBot.ui.form;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.DealModel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class DealBlockWidget extends Composite {
	private DataBindingContext m_bindingContext;

	private DealModel dealModel;
	private Label valueId;
	private Label valuePot;
	private Label lbl1;
	private Label lbl3;
	private Label lbl4;
	private Label valueDealStep;
	private Label valueNbPlayer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DealBlockWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpInfosDeal = new Group(this, SWT.NONE);
		grpInfosDeal.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpInfosDeal.setText("Infos Deal");
		grpInfosDeal.setLayout(new GridLayout(2, false));

		lbl1 = new Label(grpInfosDeal, SWT.NONE);
		lbl1.setText("Hand ID :");

		valueId = new Label(grpInfosDeal, SWT.NONE);
		GridData gd_valueId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_valueId.widthHint = 150;
		valueId.setLayoutData(gd_valueId);
		valueId.setText("[dealId]");

		Label lbl2 = new Label(grpInfosDeal, SWT.NONE);
		lbl2.setText("Pot :");

		valuePot = new Label(grpInfosDeal, SWT.NONE);
		GridData gd_valuePot = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_valuePot.widthHint = 150;
		valuePot.setLayoutData(gd_valuePot);
		valuePot.setText("[currentPot]");

		lbl3 = new Label(grpInfosDeal, SWT.NONE);
		lbl3.setText("Step :");

		valueDealStep = new Label(grpInfosDeal, SWT.NONE);
		GridData gd_valueDealStep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_valueDealStep.widthHint = 150;
		valueDealStep.setLayoutData(gd_valueDealStep);
		valueDealStep.setText("[dealStep]");

		lbl4 = new Label(grpInfosDeal, SWT.NONE);
		lbl4.setText("nb Player :");

		valueNbPlayer = new Label(grpInfosDeal, SWT.NONE);
		GridData gd_valueNbPlayer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_valueNbPlayer.widthHint = 150;
		valueNbPlayer.setLayoutData(gd_valueNbPlayer);
		valueNbPlayer.setText("[nbPlayer]");

		m_bindingContext = initDataBindings();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public DealModel getDealModel() {
		return dealModel;
	}

	public void setDealModel(DealModel dealModel) {
		this.dealModel = dealModel;
		if (m_bindingContext != null)
			m_bindingContext.dispose();
		m_bindingContext = initDataBindings();
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextLbldealIdObserveWidget = WidgetProperties.text().observe(valueId);
		IObservableValue dealIdDealModelObserveValue = PojoProperties.value("dealId").observe(dealModel);
		bindingContext.bindValue(observeTextLbldealIdObserveWidget, dealIdDealModelObserveValue, null, null);
		//
		IObservableValue observeTextLblcurrentpotObserveWidget = WidgetProperties.text().observe(valuePot);
		IObservableValue currentPotDealModelObserveValue = PojoProperties.value("currentPot").observe(dealModel);
		bindingContext.bindValue(observeTextLblcurrentpotObserveWidget, currentPotDealModelObserveValue, null, null);
		//
		IObservableValue observeTextLblDealStepObserveWidget = WidgetProperties.text().observe(valueDealStep);
		IObservableValue stepDealModelObserveValue = PojoProperties.value("step").observe(dealModel);
		bindingContext.bindValue(observeTextLblDealStepObserveWidget, stepDealModelObserveValue, null, null);
		//
		IObservableValue observeTextValueNbPlayerObserveWidget = WidgetProperties.text().observe(valueNbPlayer);
		IObservableValue nbPlayerDealModelObserveValue = PojoProperties.value("nbPlayer").observe(dealModel);
		bindingContext.bindValue(observeTextValueNbPlayerObserveWidget, nbPlayerDealModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
