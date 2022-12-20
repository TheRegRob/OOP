package com.restaurant.cashier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class CashierImpl {
	private static final int TBL_STARTING_X_POS = 15;
	private static final int TBL_STARTING_Y_POS = 40;
	private static final int TBL_OFFSET_X_VAL	= 130;
	private static final int TBL_OFFSET_Y_VAL	= 90;
	
	private final JFrame frame = new JFrame("RestaurantManager - Cassa");
	
	public void show() {
		this.frame.setVisible(true);
	}
	
	public void setDimensions(int x, int y) {
		this.frame.setSize(x, y);
	}
	
	public void setupComponents() {
		JPanel container = setupContainer();
		JPanel pnl_Left = setupLeftPnl();
		JScrollPane tb_Log = setupTextbox();
		pnl_Left.add(setupLblPanel("Stato dei tavoli"), BorderLayout.NORTH);
		JPanel pnl_Tables = setupPnlTables();
		setupAreaTable(4, 4, pnl_Tables);
		pnl_Left.add(pnl_Tables, BorderLayout.CENTER);
		JPanel pnl_Buttons = setupPnlButtons();
		pnl_Left.add(pnl_Buttons, BorderLayout.SOUTH);
		container.add(pnl_Left, setConstraints(0, 0, GridBagConstraints.HORIZONTAL, 0.6, new Insets(0, 10, 0, 0), 420));
		container.add(tb_Log, setConstraints(2, 0, GridBagConstraints.HORIZONTAL, 0.4, new Insets(0, 15, 0, 10), 430));
		frame.getContentPane().add(container);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);	

	}
	
	public JScrollPane setupTextbox() {
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		final JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    textArea.append("- Restaurant Manager -");
	    return scroll;
	}
	
	public JPanel setupContainer() {
		JPanel container = new JPanel(new GridBagLayout());
		return container;
	}
	
	public JPanel setupLeftPnl() {
		JPanel pnl = new JPanel(new BorderLayout());
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
		JPanel pnl = new JPanel(new GridLayout());
		String[] texts = new String[3];
		texts[0] = "Occupa tavolo";
		texts[1] = "Visualizza ordini";
		texts[2] = "Pagamento conto";
		JButton[] btns = setupButtons(3, texts);
		for (int i = 0; i < 3; i++) {
			pnl.add(btns[i]);
		}
		return pnl;
	}
	
	public JButton[] setupButtons(int nOfButton, String[] texts) {
		JButton[] btnList = new JButton[nOfButton];
		for(int i = 0; i < nOfButton; i++) {
			JButton btn = new JButton(texts[i]);
			btnList[i] = btn;
		}
		return btnList;
	}
	
	public void setupAreaTable(int rows, int columns, JPanel pnl) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int x = TBL_STARTING_X_POS + (TBL_OFFSET_X_VAL * i);
				int y = TBL_STARTING_Y_POS + (TBL_OFFSET_Y_VAL * j);
				Point pt = new Point(x, y);
				UITable tbl = new UITable(pt, pnl);
			}
		}
	}
	
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
	
}
