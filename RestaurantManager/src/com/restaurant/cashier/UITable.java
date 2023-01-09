package com.restaurant.cashier;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class UITable {
	
	public boolean 			occupied;
	public boolean			booked;
	public int				nOfSits;
	public String			tableID;
	public double			bill;
	public double			totalPayed;
	public int				nOfCustomers;
	public Date				occupyTime;
	public List<UITable>	chainedTbls;
	public UITable			sx_NextTbl;
	public UITable			dx_NextTbl;
	public ArrowChain		arrow;
	
	Point 					location;
	JPanel 					pnlDest;
	TableListener			listener;
	
	public static Border bdr_AboveFree 	= BorderFactory.createLineBorder(Color.GREEN, 		2);
	public static Border bdr_OutsideFree 	= BorderFactory.createLineBorder(Color.GREEN, 	1);
	public static Border bdr_AboveBusy	= BorderFactory.createLineBorder(Color.RED, 		2);
	public static Border bdr_OutsideBusy 	= BorderFactory.createLineBorder(Color.RED, 	1);
	
	public static Border bdr_FreeSelected = BorderFactory.createLineBorder(Color.GREEN, 	3);
	public static Border bdr_BusySelected = BorderFactory.createLineBorder(Color.RED, 		3);
	
	
	URL url = CashierEnv.class.getResource("/TableRestaurant.png");
	ImageIcon tbImage = new ImageIcon(url);
	public JLabel lbl_Tab = new JLabel();
	
	int width = 105;
	int height = 56;
	
	public UITable(Point loc, JPanel dest, int n_seats, String id) {
		location = loc;
		pnlDest = dest;
		nOfSits = n_seats;
		tableID = id;
		chainedTbls = null;
		dx_NextTbl = null;
		bill = 0;
		totalPayed  = 0;
		int i = Character.getNumericValue(id.charAt(0) - 1);
		int j = CashierEnv.ci.columnIDs.indexOf(id.charAt(1));
		if (CashierEnv.ci.tables[i][j] != CashierEnv.ci.tables[i][0]) {
			sx_NextTbl = CashierEnv.ci.tables[i][j - 1];
			CashierEnv.ci.tables[i][j - 1].dx_NextTbl = this;
		} else {
			sx_NextTbl = null;
		}
		occupied = false;
	}
    
	public void addTable() {
		listener = new TableListener();
		lbl_Tab.setIcon(new ImageIcon(tbImage.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
		lbl_Tab.setBorder(bdr_OutsideFree);
		lbl_Tab.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Tab.setBounds(location.x, location.y, width, height);
		lbl_Tab.addMouseListener(listener);
		pnlDest.add(lbl_Tab);
	}
	
	public void updateBorder(Border border) {
		this.lbl_Tab.setBorder(border);
	}
	
	public void addMergeArrow() {
		arrow = new ArrowChain(this);
	}
	
	
	class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			CashierEnv.toggleTableStatus(tableID);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (CashierEnv.selectedTable == null || tableID != CashierEnv.selectedTable.tableID) {
				if (occupied) {
					for (int i = 0; i < chainedTbls.size(); i++) {
						chainedTbls.get(i).updateBorder(bdr_AboveBusy);
					}
				} else {
					updateBorder(bdr_AboveFree);
				}
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (CashierEnv.selectedTable == null || tableID != CashierEnv.selectedTable.tableID) {
				if (occupied) {
					updateBorder(bdr_OutsideBusy);
					for (int i = 0; i < chainedTbls.size(); i++) {
						chainedTbls.get(i).updateBorder(bdr_OutsideBusy);
					}
				} else {
					updateBorder(bdr_OutsideFree);
				}
			}
		}
		
	}
    
}
