package com.omahaBot.ui.form;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.omahaBot.model.ActionModel;
import com.omahaBot.model.BoardModel;
import com.omahaBot.model.DealModel;
import com.omahaBot.model.DealStepModel;
import com.omahaBot.model.PlayerModel;
import com.omahaBot.model.draw.DrawModel;
import com.omahaBot.model.hand.HandModel;
import com.omahaBot.model.hand.HandPreFlopPower;
import com.omahaBot.service.ai.PostFlopAnalyser;
import com.omahaBot.service.bot.ThreadTable;
import com.omahaBot.service.ocr.OcrService;
import com.omahaBot.service.ocr.OcrServiceImpl;
import com.omahaBot.utils.CustomOutputStream;

/**
 * TODO BUILDER, HELPER...
 * @author Julien
 *
 */
public class MainForm {

	private OcrService ocrService = OcrServiceImpl.getInstance();

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
	private Button btn_checkTable;

	private StyledText styledText;
	private PrintStream standardOut;
	
	private MainForm mainForm;
	private TabItem tbtm3;
	
	private AnalyseWidget analyseWidget;
	private Button btn_screenCaps;

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
		shell.setMinimumSize(new Point(0, 0));
		shell.setLocation(new Point(0, 0));
		shell.setMinimumSize(390, 800);
		shell.setSize(390, 800);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));

		mainForm = this;
		
		Group grpActions = new Group(shell, SWT.NONE);
		grpActions.setSize(new Point(0, 100));
		grpActions.setText("Actions");
		RowLayout rl_grpActions = new RowLayout(SWT.HORIZONTAL);
		grpActions.setLayout(rl_grpActions);

		btn_checkTable = new Button(grpActions, SWT.NONE);
		btn_checkTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ocrService.checkTable();
			}
		});
		btn_checkTable.setText("CHECK TABLE");

		final Button btnNewButton = new Button(grpActions, SWT.TOGGLE);
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		btnNewButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnNewButton.setLayoutData(new RowData(100, SWT.DEFAULT));

		btnNewButton.setText("START BOT");

		Button btn_clear = new Button(grpActions, SWT.NONE);
		btn_clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.setText("");
			}
		});
		btn_clear.setText("Clear log");
		
		btn_screenCaps = new Button(grpActions, SWT.TOGGLE);
		btn_screenCaps.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ocrService.screenCaps();
			}
		});
		btn_screenCaps.setText("ScreenCaps");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			private ThreadTable threadDeal;

			@Override
			public void widgetSelected(SelectionEvent e) {
				// printLog();

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
					threadDeal = new ThreadTable(mainForm);
					threadDeal.start();
				}
			}
		});
		// shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);

		TabItem tbtm1 = new TabItem(tabFolder, SWT.NONE);
		tbtm1.setText("Table");

		Composite composite1 = new Composite(tabFolder, SWT.NONE);
		tbtm1.setControl(composite1);
		composite1.setLayout(new GridLayout(1, false));

		TabItem tbtm2 = new TabItem(tabFolder, SWT.NONE);
		tbtm2.setText("Logs");

		Composite composite2 = new Composite(tabFolder, SWT.NONE);
		tbtm2.setControl(composite2);
		composite2.setLayout(new GridLayout(1, false));

		styledText = new StyledText(composite2, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		styledText.setEditable(false);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		dealBlockWidget = new DealBlockWidget(composite1, SWT.NONE);

		boardWidget = new BoardWidget(composite1, SWT.NONE);

		potWidget = new PotWidget(composite1, SWT.NONE);

		actionBlockWidget = new ActionBlockWidget(composite1, SWT.NONE);

		final Group grpTable = new Group(composite1, SWT.NONE);
		grpTable.setText("Table");
		grpTable.setLayout(new GridLayout(2, false));
		playerBlockWidget_6 = new PlayerBlockWidget(grpTable, SWT.NONE, 6);
		playerBlockWidget_1 = new PlayerBlockWidget(grpTable, SWT.NONE, 1);
		playerBlockWidget_5 = new PlayerBlockWidget(grpTable, SWT.NONE, 5);
		playerBlockWidget_2 = new PlayerBlockWidget(grpTable, SWT.NONE, 2);
		playerBlockWidget_4 = new PlayerBlockWidget(grpTable, SWT.NONE, 4);
		playerBlockWidget_3 = new PlayerBlockWidget(grpTable, SWT.NONE, 3);

		// styledText.setEditable(false);
		
		tbtm3 = new TabItem(tabFolder, SWT.NONE);
		tbtm3.setText("AI");
		
		analyseWidget = new AnalyseWidget(tabFolder, SWT.NONE);
		tbtm3.setControl(analyseWidget);	
		
		redirectConsole();
	}

	public void initDealWidget(DealModel dealModel) {
		dealBlockWidget.setDealModel(dealModel);
	}

	public void initPlayerWidget(List<PlayerModel> listPlayer) {
		// TODO optimize
		playerBlockWidget_1.setPlayerModel(listPlayer.get(0));
		playerBlockWidget_2.setPlayerModel(listPlayer.get(1));
		playerBlockWidget_3.setPlayerModel(listPlayer.get(2));
		playerBlockWidget_4.setPlayerModel(listPlayer.get(3));
		playerBlockWidget_5.setPlayerModel(listPlayer.get(4));
		playerBlockWidget_6.setPlayerModel(listPlayer.get(5));
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
	
	public void initAnalyseWidget(BoardModel boardModel, ArrayList<DrawModel> boardDraws) {
		analyseWidget.displayBoardDraw(boardModel, boardDraws);
	}
	
	public void initAnalyseWidget(HandModel myHand, BoardModel board, ArrayList<DrawModel> handDraws, PostFlopAnalyser postFlopAnalyser) {
		analyseWidget.displayHandDraws(myHand, board, handDraws, postFlopAnalyser);
	}

	public void initAnalyseWidget(HandModel myHand, HandPreFlopPower handPreFlopPower) {
		analyseWidget.init();
		analyseWidget.displayPreFlopAnalyse(myHand, handPreFlopPower);
	}
	
	public void redirectConsole() {
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(styledText));
		
		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);
		
//		Properties prop = new Properties();
//    	InputStream input = null;
// 
//		String filename = "config.properties";
//		input = MainForm.class.getClassLoader().getResourceAsStream(filename);
//		if (input == null) {
//			System.out.println("Sorry, unable to find " + filename);
//			return;
//		}
// 
//		// load a properties file from class path, inside static method
//		try {
//			prop.load(input);
//
//			File file = new File(prop.getProperty("sysout.file"));
//			FileOutputStream fos;
//
//			fos = new FileOutputStream(file);
//			PrintStream ps = new PrintStream(fos);
//			System.setOut(ps);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
	}
}
