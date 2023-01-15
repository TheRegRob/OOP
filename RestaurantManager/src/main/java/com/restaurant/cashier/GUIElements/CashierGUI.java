package com.restaurant.cashier.GUIElements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.restaurant.cashier.CashierApplication;
import com.restaurant.cashier.Listeners.CashierEventListener;
import com.restaurant.utils.Logger;
import com.restaurant.utils.Logger.TypeLog;

public class CashierGUI {
	private final String columnIDs = "ABCDEFGH";
	
	private static final int 	TBL_STARTING_X_POS = 15;
	private static final int 	TBL_STARTING_Y_POS = 40;
	private static final int 	TBL_OFFSET_X_VAL	= 130;
	private static final int 	TBL_OFFSET_Y_VAL	= 90;
	final CashierEventListener 	listener			= new CashierEventListener();
	
	private final JFrame frame = new JFrame("RestaurantManager - Cassa");
	
	private UITable[][] tables;
	private JTextPane 	tb_Log;
	private JButton		btn_OccupyTable;
	private JButton		btn_FreeTable;
	private JButton		btn_PayBill;
	private JPanel		pnl_Tables;
	private UITreeView  tblTree = new UITreeView(); 
	
	/*------------------- Getters and setters -------------------*/
	
	public JFrame getFrame() {
		return frame;
	}

	public String getColumnIDs() {
		return columnIDs;
	}

	public UITable[][] getTables() {
		return tables;
	}

	public void setTables(UITable[][] tables) {
		this.tables = tables;
	}
	
	public JButton getBtn_OccupyTable() {
		return btn_OccupyTable;
	}

	public void setBtn_OccupyTable(JButton btn_OccupyTable) {
		this.btn_OccupyTable = btn_OccupyTable;
	}

	public JButton getBtn_FreeTable() {
		return btn_FreeTable;
	}

	public void setBtn_FreeTable(JButton btn_FreeTable) {
		this.btn_FreeTable = btn_FreeTable;
	}

	public JButton getBtn_PayBill() {
		return btn_PayBill;
	}

	public void setBtn_PayBill(JButton btn_PayBill) {
		this.btn_PayBill = btn_PayBill;
	}

	public UITreeView getTblTree() {
		return tblTree;
	}

	public void setTblTree(UITreeView tblTree) {
		this.tblTree = tblTree;
	}

	public JTextPane getTb_Log() {
		return tb_Log;
	}

	public void setTb_Log(JTextPane tb_Log) {
		this.tb_Log = tb_Log;
	}
	
	/*-----------------------------------------------------------*/
	
	/*------------------- Setup Operations ----------------------*/
	public void show() {
		this.getFrame().setVisible(true);
	}
	
	public void setAppIcon() {
        final Toolkit defaultToolkit 	= Toolkit.getDefaultToolkit();
        final URL imageResource 		= CashierApplication.class.getResource("/RestMng_Icon.png");
        final Image image 				= defaultToolkit.getImage(imageResource);
        final Taskbar taskbar 			= Taskbar.getTaskbar();
		taskbar.setIconImage(image);
	}
	
	public void setDimensions(int x, int y) {
		this.getFrame().setSize(x, y);
	}
	
	public void setupComponents() {
		JPanel container 		= setupContainer();
		JPanel pnl_Left 		= setupLeftPnl();
		JPanel pnl_Center 		= setupCenterPnl();
		JPanel pnl_Right 		= setupRightPnl();
		JScrollPane tb_Log 		= setupTextbox();
		pnl_Tables = setupPnlTables();
		setupAreaTable(4, 4, pnl_Tables);
		pnl_Center.add(pnl_Tables, BorderLayout.CENTER);
		pnl_Right.add(tb_Log, BorderLayout.CENTER);
		pnl_Left.add(getTblTree(), BorderLayout.CENTER);
		
		container.add(pnl_Left, setConstraints(0, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 420));
		container.add(pnl_Center, setConstraints(1, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 410));
		container.add(pnl_Right, setConstraints(2, 0, GridBagConstraints.HORIZONTAL, 0.7, new Insets(0, 10, 0, 10), 420));
		getFrame().getContentPane().add(container);
		this.getFrame().setResizable(false);
		this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getFrame().setLocationRelativeTo(null);	

	}
	/*-----------------------------------------------------------*/
	
	/* START - Container */
	private JPanel setupContainer() {
		JPanel container = new JPanel(new GridBagLayout());
		return container;
	}
	/* END - Container*/
	
	/* START - Left Panel */
	private JPanel setupLeftPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Stato degli ordini"), BorderLayout.NORTH);
		return pnl;
	}
	/* END - Left Panel */
	
	/* START - Center Panel */
	private JPanel setupCenterPnl() {
		JPanel pnl 			= new JPanel(new BorderLayout());
		JPanel pnl_Buttons	= setupPnlButtons();
		pnl.add(setupLblPanel("Stato dei tavoli"), BorderLayout.NORTH);
		pnl.add(pnl_Buttons, BorderLayout.SOUTH);
		return pnl;
	}
	
	private JLabel setupLblPanel(String title) {
		JLabel lbl = new JLabel(title, SwingConstants.CENTER);
		return lbl;
	}
	
	private JPanel setupPnlTables() {
		JPanel pnl = new JPanel(null);
		pnl.setBorder(new LineBorder(Color.BLACK, 1, true));
		return pnl;
	}
	
	private JPanel setupPnlButtons() {
		JPanel pnl 			= new JPanel(new GridLayout());
		String[] texts 		= new String[3];
		String[] actCmds 	= new String[3];
		texts[0] 			= "Occupa tavolo";
		texts[1] 			= "Libera tavolo";
		texts[2] 			= "Pagamento conto";
		actCmds[0]			= CashierEventListener.eventList[CashierEventListener.EventsIdx.ev_BtnOccupyTbl.getValue()];
		actCmds[1]			= CashierEventListener.eventList[CashierEventListener.EventsIdx.ev_BtnClearTbl.getValue()];
		actCmds[2]			= CashierEventListener.eventList[CashierEventListener.EventsIdx.ev_BtnPayBill.getValue()];
		JButton[] btns = setupButtons(3, texts, actCmds);
		setBtn_OccupyTable(btns[0]);
		setBtn_FreeTable(btns[1]);
		setBtn_PayBill(btns[2]);
		for (int i = 0; i < 3; i++) {
			pnl.add(btns[i]);
		}
		return pnl;
	}
	
	private JButton[] setupButtons(int nOfButton, String[] texts, String[] cmds) {
		JButton[] btnList = new JButton[nOfButton];
		for(int i = 0; i < nOfButton; i++) {
			JButton btn = new JButton(texts[i]);
			btn.setActionCommand(cmds[i]);
			btn.addActionListener(listener);
			btn.setEnabled(false);
			btnList[i] = btn;
		}
		return btnList;
	}
	
	private void setupAreaTable(int rows, int columns, JPanel pnl) {
		setTables(new UITable[rows][columns]);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int x = TBL_STARTING_X_POS + (TBL_OFFSET_X_VAL * j);
				int y = TBL_STARTING_Y_POS + (TBL_OFFSET_Y_VAL * i);
				Point pt = new Point(x, y);
				UITable tbl = new UITable(pt, pnl, 5, Integer.toString(i + 1) + getColumnIDs().charAt(j));
				getTables()[i][j] = tbl;
				getTables()[i][j].addTable();
			}
		}
	}
	/* END - Center Panel */
	
	/* START - Right Panel */
	private JPanel setupRightPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Cronologia eventi"), BorderLayout.NORTH);
		
		return pnl;
	}
	
	private JScrollPane setupTextbox() {
		this.setTb_Log(new JTextPane());
		final JScrollPane scroll = new JScrollPane(getTb_Log());
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBorder(new LineBorder(Color.BLACK, 1, true));
	    Logger.Log(TypeLog.tl_Info, "Restaurant Manager avviato");
	    return scroll;
	}
	/* END - Left Panel */
	
	/* START - Layouts */
	private GridBagConstraints setConstraints(int grdX, int grdY, int pos, double wgtX, Insets borders, int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx 	= grdX;
		gbc.gridy 	= grdY;
		gbc.fill 	= pos;
		gbc.weightx = wgtX;
		gbc.insets 	= borders;
		gbc.ipady 	= height;
		return gbc;
	}
	/* END - Layouts */
}
