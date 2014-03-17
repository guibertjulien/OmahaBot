package com.omahaBot.ui.form;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.omahaBot.model.ActionModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.service.bot.ThreadDeal;
import com.omahaBot.service.ocr.OcrServiceImpl;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MainForm {

	private final static Logger LOGGER = Logger.getLogger(MainForm.class.getName());

	private OcrServiceImpl ocrService = OcrServiceImpl.getInstance();

	protected Shell shell;

	private DealBlockWidget dealBlockWidget;
	private PlayerBlockWidget playerBlockWidget_1;
	private PlayerBlockWidget playerBlockWidget_2;
	private PlayerBlockWidget playerBlockWidget_3;
	private PlayerBlockWidget playerBlockWidget_4;
	private PlayerBlockWidget playerBlockWidget_5;
	private PlayerBlockWidget playerBlockWidget_6;
	private BoardWidget boardWidget;
	
	private static boolean start = false;
	private PotWidget potWidget;
	private ActionBlockWidget actionBlockWidget;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLocation(new Point(0, 0));
		shell.setMinimumSize(390, 800);
		shell.setSize(390, 800);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));

		Group grpActions = new Group(shell, SWT.NONE);
		grpActions.setText("Actions");
		RowLayout rl_grpActions = new RowLayout(SWT.HORIZONTAL);
		grpActions.setLayout(rl_grpActions);

		dealBlockWidget = new DealBlockWidget(shell, SWT.NONE);

		final Button btnNewButton = new Button(grpActions, SWT.NONE);
		btnNewButton.setLayoutData(new RowData(100, SWT.DEFAULT));
		
		final MainForm mainForm = this;
		
		btnNewButton.setText("START BOT");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			private ThreadDeal threadDeal;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (start) {
					start = false;
					btnNewButton.setText("START BOT");
					if (threadDeal != null && threadDeal.isAlive()) {
						threadDeal.arret();
					}
				}
				else {
					start = true;
					btnNewButton.setText("STOP BOT");
					threadDeal = new ThreadDeal(mainForm);
					threadDeal.start();
				}
			}
		});

		boardWidget = new BoardWidget(shell, SWT.NONE);
		
		potWidget = new PotWidget(shell, SWT.NONE);
		
		actionBlockWidget = new ActionBlockWidget(shell, SWT.NONE);
		
		final Group grpTable = new Group(shell, SWT.NONE);
		grpTable.setText("Table");
		grpTable.setLayout(new GridLayout(2, false));
		playerBlockWidget_6 = new PlayerBlockWidget(grpTable, SWT.NONE, 6);
		playerBlockWidget_1 = new PlayerBlockWidget(grpTable, SWT.NONE, 1);
		playerBlockWidget_5 = new PlayerBlockWidget(grpTable, SWT.NONE, 5);
		playerBlockWidget_2 = new PlayerBlockWidget(grpTable, SWT.NONE, 2);
		playerBlockWidget_4 = new PlayerBlockWidget(grpTable, SWT.NONE, 4);
		playerBlockWidget_3 = new PlayerBlockWidget(grpTable, SWT.NONE, 3);
	}

	public void initDealWidget(DealModel dealModel) {
		dealBlockWidget.setDealModel(dealModel);
	}

	public void initPlayerWidget(List<PlayerModel> players) {
		playerBlockWidget_1.setPlayerModel(players.get(0));
		playerBlockWidget_2.setPlayerModel(players.get(1));
		playerBlockWidget_3.setPlayerModel(players.get(2));
		playerBlockWidget_4.setPlayerModel(players.get(3));
		playerBlockWidget_5.setPlayerModel(players.get(4));
		playerBlockWidget_6.setPlayerModel(players.get(5));
	}

	public void initBoardWidget(DealStepModel dealStepModel) {
		boardWidget.init(dealStepModel);
	}
	
	public void initPotWidget(Double pot) {
		potWidget.init(pot);
	}
	
	public void initActionWidget(ActionModel actionModel) {
		actionBlockWidget.setActionModel(actionModel);
	}
}
