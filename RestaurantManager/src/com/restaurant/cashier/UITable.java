package com.restaurant.cashier;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class UITable {
	
	public boolean 	occupied;
	public boolean	selected;
	public int		nOfSits;
	public String	tableID;
	public int		nOfCustomers;
	public Date		OccupyTime;
	
	Point 			location;
	JPanel 			pnlDest;
	TableListener	listener;
	
	public static Border bdr_AboveFree 	= BorderFactory.createLineBorder(Color.GREEN, 		2);
	public static Border bdr_OutsideFree 	= BorderFactory.createLineBorder(Color.GREEN, 	1);
	public static Border bdr_AboveBusy	= BorderFactory.createLineBorder(Color.RED, 		2);
	public static Border bdr_OutsideBusy 	= BorderFactory.createLineBorder(Color.RED, 	1);
	
	public static Border bdr_FreeSelected = BorderFactory.createLineBorder(Color.GREEN, 	4);
	public static Border bdr_BusySelected = BorderFactory.createLineBorder(Color.RED, 		4);
	
	
	
	
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
	
	public  void updateBorder(Border border) {
		this.lbl_Tab.setBorder(border);
	}
	
	class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			CashierEnv.toggleTableStatus(tableID);
			if (selected) {
				if (occupied) {
					updateBorder(bdr_BusySelected);
				} else {
					updateBorder(bdr_FreeSelected);
				}
			} else {
				if (occupied) {
					updateBorder(bdr_AboveBusy);
				} else {
					updateBorder(bdr_AboveFree);
				}
			}
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
			if (!selected) {
				if (occupied) {
					updateBorder(bdr_AboveBusy);
				} else {
					updateBorder(bdr_AboveFree);
				}
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!selected) {
				if (occupied) {
					updateBorder(bdr_OutsideBusy);
				} else {
					updateBorder(bdr_OutsideFree);
				}
			}
		}
		
	}
    
}
