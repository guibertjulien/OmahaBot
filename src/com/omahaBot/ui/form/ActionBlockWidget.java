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

import com.omahaBot.model.ActionModel;

public class ActionBlockWidget extends Composite {
	private DataBindingContext m_bindingContext;

	private ActionModel actionModel;
	private Label lbl1;
	private Label lbl2;
	private Label lbl3;
	private Label value1;
	private Label value2;
	private Label value3;
	private Label lbl4;
	private Label value4;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ActionBlockWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpInfosDeal = new Group(this, SWT.NONE);
		grpInfosDeal.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		grpInfosDeal.setText("Infos action");
		grpInfosDeal.setLayout(new GridLayout(2, false));
		
				lbl2 = new Label(grpInfosDeal, SWT.NONE);
				lbl2.setText("Nb player :");
		
				value2 = new Label(grpInfosDeal, SWT.NONE);
				GridData gd_valuePot = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_valuePot.widthHint = 150;
				value2.setLayoutData(gd_valuePot);
				
						lbl1 = new Label(grpInfosDeal, SWT.NONE);
						lbl1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
						lbl1.setText("Player turn to play");
				
						value1 = new Label(grpInfosDeal, SWT.NONE);
						GridData gd_valueId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
						gd_valueId.widthHint = 150;
						value1.setLayoutData(gd_valueId);
						
								lbl3 = new Label(grpInfosDeal, SWT.NONE);
								lbl3.setText("Last action : ");
								
										value3 = new Label(grpInfosDeal, SWT.NONE);
										GridData gd_valueDealStep = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
										gd_valueDealStep.widthHint = 150;
										value3.setLayoutData(gd_valueDealStep);
										
										lbl4 = new Label(grpInfosDeal, SWT.NONE);
										lbl4.setText("Last bet :");
										
										value4 = new Label(grpInfosDeal, SWT.NONE);
										GridData gd_value4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
										gd_value4.widthHint = 150;
										value4.setLayoutData(gd_value4);

		m_bindingContext = initDataBindings();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public ActionModel getActionModel() {
		return actionModel;
	}

	public void setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
		if (m_bindingContext != null)
			m_bindingContext.dispose();
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextValue2ObserveWidget = WidgetProperties.text().observe(value2);
		IObservableValue nbPlayerActionModelObserveValue = PojoProperties.value("nbPlayer").observe(actionModel);
		bindingContext.bindValue(observeTextValue2ObserveWidget, nbPlayerActionModelObserveValue, null, null);
		//
		IObservableValue observeTextValue3ObserveWidget = WidgetProperties.text().observe(value3);
		IObservableValue playerActionActionModelObserveValue = PojoProperties.value("playerAction").observe(actionModel);
		bindingContext.bindValue(observeTextValue3ObserveWidget, playerActionActionModelObserveValue, null, null);
		//
		IObservableValue observeTextValue4ObserveWidget = WidgetProperties.text().observe(value4);
		IObservableValue lastBetActionModelObserveValue = PojoProperties.value("lastBet").observe(actionModel);
		bindingContext.bindValue(observeTextValue4ObserveWidget, lastBetActionModelObserveValue, null, null);
		//
		IObservableValue observeTextValue1ObserveWidget_1 = WidgetProperties.text().observe(value1);
		IObservableValue positionPlayerTurnPlayActionModelObserveValue = PojoProperties.value("positionPlayerTurnPlay").observe(actionModel);
		bindingContext.bindValue(observeTextValue1ObserveWidget_1, positionPlayerTurnPlayActionModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
