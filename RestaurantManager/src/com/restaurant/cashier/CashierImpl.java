package com.restaurant.cashier;

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
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.restaurant.utils.Logger;
import com.restaurant.utils.Logger.TypeLog;

public class CashierImpl {
	String columnIDs = "ABCDEFGH";
	
	private static final int 	TBL_STARTING_X_POS = 15;
	private static final int 	TBL_STARTING_Y_POS = 40;
	private static final int 	TBL_OFFSET_X_VAL	= 130;
	private static final int 	TBL_OFFSET_Y_VAL	= 90;
	final CashierEventListener 	listener			= new CashierEventListener();
	
	public final JFrame frame = new JFrame("RestaurantManager - Cassa");
	
	public UITable[][] 	tables;
	public JTextPane 	tb_Log;
	public JButton		btn_OccupyTable;
	public JButton		btn_FreeTable;
	public JButton		btn_PayBill;
	public JPanel		pnl_Tables;
	
	public void show() {
		this.frame.setVisible(true);
	}
	
	public void setAppIcon() {
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        final URL imageResource = CashierEnv.class.getResource("/RestMng_Icon.png");
        final Image image = defaultToolkit.getImage(imageResource);
        final Taskbar taskbar = Taskbar.getTaskbar();
		taskbar.setIconImage(image);
	}
	
	public void setDimensions(int x, int y) {
		this.frame.setSize(x, y);
	}
	
	public void setupComponents() {
		JPanel container = setupContainer();
		JPanel pnl_Left = setupLeftPnl();
		JPanel pnl_Center = setupCenterPnl();
		JPanel pnl_Right = setupRightPnl();
		JScrollPane tb_Log = setupTextbox();
		pnl_Tables = setupPnlTables();
		setupAreaTable(4, 4, pnl_Tables);
		pnl_Center.add(pnl_Tables, BorderLayout.CENTER);
		pnl_Right.add(tb_Log, BorderLayout.CENTER);
		JTree tv_TreeOrders = setupTreeView();
		pnl_Left.add(tv_TreeOrders, BorderLayout.CENTER);
		container.add(pnl_Left, setConstraints(0, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 450));
		container.add(pnl_Center, setConstraints(1, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 10, 0, 0), 420));
		container.add(pnl_Right, setConstraints(2, 0, GridBagConstraints.HORIZONTAL, 0.7, new Insets(0, 10, 0, 10), 430));
		frame.getContentPane().add(container);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);	

	}
	/* START - Container */
	public JPanel setupContainer() {
		JPanel container = new JPanel(new GridBagLayout());
		return container;
	}
	/* END - Container*/
	
	/* START - Left Panel */
	public JPanel setupLeftPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Stato degli ordini"), BorderLayout.NORTH);
		return pnl;
	}
	/* END - Left Panel */
	
	/* START - Center Panel */
	public JPanel setupCenterPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Stato dei tavoli"), BorderLayout.NORTH);
		JPanel pnl_Buttons = setupPnlButtons();
		pnl.add(pnl_Buttons, BorderLayout.SOUTH);
		return pnl;
	}
	
	public JLabel setupLblPanel(String title) {
		JLabel lbl = new JLabel(title, SwingConstants.CENTER);
		return lbl;
	}
	
	public JPanel setupPnlTables() {
		JPanel pnl = new JPanel(null);
		pnl.setBorder(new LineBorder(Color.BLACK, 1, true));
		return pnl;
	}
	
	public JPanel setupPnlButtons() {
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
		btn_OccupyTable = btns[0];
		btn_FreeTable	= btns[1];
		btn_PayBill		= btns[2];
		for (int i = 0; i < 3; i++) {
			pnl.add(btns[i]);
		}
		return pnl;
	}
	
	public JButton[] setupButtons(int nOfButton, String[] texts, String[] cmds) {
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
	
	public void setupAreaTable(int rows, int columns, JPanel pnl) {
		tables = new UITable[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int x = TBL_STARTING_X_POS + (TBL_OFFSET_X_VAL * j);
				int y = TBL_STARTING_Y_POS + (TBL_OFFSET_Y_VAL * i);
				Point pt = new Point(x, y);
				UITable tbl = new UITable(pt, pnl, 5, Integer.toString(i + 1) + columnIDs.charAt(j));
				tables[i][j] = tbl;
				tables[i][j].addTable();
			}
		}
	}
	/* END - Center Panel */
	
	/* START - Right Panel */
	public JPanel setupRightPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(setupLblPanel("Cronologia eventi"), BorderLayout.NORTH);
		
		return pnl;
	}
	
	public JScrollPane setupTextbox() {
		this.tb_Log = new JTextPane();
		final JScrollPane scroll = new JScrollPane(tb_Log);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scroll.setBorder(new LineBorder(Color.BLACK, 1, true));
	    Logger.Log(TypeLog.tl_Info, "Restaurant Manager avviato");
	    return scroll;
	}
	/* END - Left Panel */
	

	public JTree setupTreeView() {
		JTree treeView = new JTree();
		treeView.setBorder(new LineBorder(Color.BLACK, 1, true));
		return treeView;
	}
	
	/* START - Layouts */
	public GridBagConstraints setConstraints(int grdX, int grdY, int pos, double wgtX, Insets borders, int height) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = grdX;
		gbc.gridy = grdY;
		gbc.fill = pos;
		gbc.weightx = wgtX;
		gbc.insets = borders;
		gbc.ipady = height;
		return gbc;
	}
	/* END - Layouts */
}
