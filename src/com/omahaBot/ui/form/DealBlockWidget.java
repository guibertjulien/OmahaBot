package com.omahaBot.ui.form;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.DealModel;

public class DealBlockWidget extends Composite {
	private DataBindingContext m_bindingContext;

	private DealModel dealModel;
	private Label valueId;
	private Label lbl1;

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
		return bindingContext;
	}
}
