package com.restaurant.cashier.guielements;

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
import com.restaurant.cashier.listeners.CashierEventListener;
import com.restaurant.utils.Logger;
import com.restaurant.utils.Logger.TypeLog;

public class CashierGUI {
	private static final String COLUMNS_IDS = "ABCDEFGH";
	
	private static final int 	TBL_STARTING_X_POS = 15;
	private static final int 	TBL_STARTING_Y_POS = 40;
	private static final int 	TBL_OFFSET_X_VAL	= 130;
	private static final int 	TBL_OFFSET_Y_VAL	= 90;
	final CashierEventListener 	listener			= new CashierEventListener();
	
	private final JFrame frame = new JFrame("RestaurantManager - Cassa");
	
	private UITable[][] tables;
	private JTextPane 	tbLog;
	private JButton		btnOccupyTable;
	private JButton		btnFreeTable;
	private JButton		btnPayBill;
	//private JPanel		pnlTables;
	private UITreeView  tblTree = new UITreeView(); 
	
	/*------------------- Getters and setters -------------------*/
	
	public JFrame getFrame() {
		return frame;
	}

	public String getColumnIDs() {
		return COLUMNS_IDS;
	}

	public UITable[][] getTables() {
		return tables.clone();
	}

	public void setTables(final UITable[][] tables) {
		this.tables = tables.clone();
	}
	
	public JButton getBtnOccupyTable() {
		return btnOccupyTable;
	}

	public void setBtnOccupyTable(final JButton btnOccupyTable) {
		this.btnOccupyTable = btnOccupyTable;
	}

	public JButton getBtnFreeTable() {
		return btnFreeTable;
	}

	public void setBtnFreeTable(final JButton btnFreeTable) {
		this.btnFreeTable = btnFreeTable;
	}

	public JButton getBtnPayBill() {
		return btnPayBill;
	}

	public void setBtnPayBill(final JButton btnPayBill) {
		this.btnPayBill = btnPayBill;
	}

	public UITreeView getTblTree() {
		return tblTree;
	}

	public void setTblTree(final UITreeView tblTree) {
		this.tblTree = tblTree;
	}

	public JTextPane getTbLog() {
		return tbLog;
	}

	public void setTbLog(final JTextPane tbLog) {
		this.tbLog = tbLog;
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
	
	public void setDimensions(final int x, final int y) {
		this.getFrame().setSize(x, y);
	}
	
	public void setupComponents() {
		final JPanel container 		= setupContainer();
		final JPanel pnlLeft 		= setupLeftPnl();
		final JPanel pnlCenter 		= setupCenterPnl();
		final JPanel pnlRight 		= setupRightPnl();
		final JScrollPane tbLog 	= setupTextbox();
		final JPanel pnlTables = setupPnlTables();
		setupAreaTable(4, 4, pnlTables);
		pnlCenter.add(pnlTables, BorderLayout.CENTER);
		pnlRight.add(tbLog, BorderLayout.CENTER);
		pnlLeft.add(getTblTree(), BorderLayout.CENTER);
		
		container.add(pnlLeft, setupConstraints(0, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 420));
		container.add(pnlCenter, setupConstraints(1, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 410));
		container.add(pnlRight, setupConstraints(2, 0, GridBagConstraints.HORIZONTAL, 0.7, new Insets(0, 10, 0, 10), 420));
		getFrame().getContentPane().add(container);
		this.getFrame().setResizable(false);
		this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getFrame().setLocationRelativeTo(null);	

	}
	/*-----------------------------------------------------------*/
	
	/* START - Container */
	private JPanel setupContainer() {
		return new JPanel(new GridBagLayout());
	}
	/* END - Container*/
	
	/* START - Left Panel */
	private JPanel setupLeftPnl() {
		final JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Stato degli ordini"), BorderLayout.NORTH);
		return pnl;
	}
	/* END - Left Panel */
	
	/* START - Center Panel */
	private JPanel setupCenterPnl() {
		final JPanel pnl 		= new JPanel(new BorderLayout());
		final JPanel pnlButtons	= setupPnlButtons();
		pnl.add(setupLblPanel("Stato dei tavoli"), BorderLayout.NORTH);
		pnl.add(pnlButtons, BorderLayout.SOUTH);
		return pnl;
	}
	
	private JLabel setupLblPanel(final String title) {
		return new JLabel(title, SwingConstants.CENTER);
	}
	
	private JPanel setupPnlTables() {
		final JPanel pnl = new JPanel(null);
		pnl.setBorder(new LineBorder(Color.BLACK, 1, true));
		return pnl;
	}
	
	private JPanel setupPnlButtons() {
		final JPanel pnl 		= new JPanel(new GridLayout());
		final String[] texts 	= new String[3];
		final String[] actCmds 	= new String[3];
		texts[0] 				= "Occupa tavolo";
		texts[1] 				= "Libera tavolo";
		texts[2] 				= "Pagamento conto";
		actCmds[0]				= CashierEventListener.EVENT_LIST[CashierEventListener.EventsIdx.ev_BtnOccupyTbl.getValue()];
		actCmds[1]				= CashierEventListener.EVENT_LIST[CashierEventListener.EventsIdx.ev_BtnClearTbl.getValue()];
		actCmds[2]				= CashierEventListener.EVENT_LIST[CashierEventListener.EventsIdx.ev_BtnPayBill.getValue()];
		final JButton[] btns 	= setupButtons(3, texts, actCmds);
		setBtnOccupyTable(btns[0]);
		setBtnFreeTable(btns[1]);
		setBtnPayBill(btns[2]);
		for (int i = 0; i < 3; i++) {
			pnl.add(btns[i]);
		}
		return pnl;
	}
	
	private JButton[] setupButtons(final int nOfButton, final String[] texts, final String[] cmds) {
		final JButton[] btnList = new JButton[nOfButton];
		for(int i = 0; i < nOfButton; i++) {
			final JButton btn = new JButton(texts[i]);
			btn.setActionCommand(cmds[i]);
			btn.addActionListener(listener);
			btn.setEnabled(false);
			btnList[i] = btn;
		}
		return btnList;
	}
	
	private void setupAreaTable(final int rows, final int columns, final JPanel pnl) {
		setTables(new UITable[rows][columns]);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				final int x 		= TBL_STARTING_X_POS + (TBL_OFFSET_X_VAL * j);
				final int y 		= TBL_STARTING_Y_POS + (TBL_OFFSET_Y_VAL * i);
				final Point pt 		= new Point(x, y);
				final UITable tbl	= new UITable(pt, pnl, 5, Integer.toString(i + 1) + getColumnIDs().charAt(j));
				getTables()[i][j] 	= tbl;
				getTables()[i][j].addTable();
			}
		}
	}
	/* END - Center Panel */
	
	/* START - Right Panel */
	private JPanel setupRightPnl() {
		final JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Cronologia eventi"), BorderLayout.NORTH);
		
		return pnl;
	}
	
	private JScrollPane setupTextbox() {
		this.setTbLog(new JTextPane());
		final JScrollPane scroll = new JScrollPane(getTbLog());
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBorder(new LineBorder(Color.BLACK, 1, true));
	    Logger.Log(TypeLog.tl_Info, "Restaurant Manager avviato");
	    return scroll;
	}
	/* END - Left Panel */
	
	/* START - Layouts */
	private GridBagConstraints setupConstraints(final int grdX, final int grdY, final int pos, final double wgtX, final Insets borders, final int height) {
		final GridBagConstraints gbc = new GridBagConstraints();
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
