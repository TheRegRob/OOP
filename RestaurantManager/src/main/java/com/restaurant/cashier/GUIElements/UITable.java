package com.restaurant.cashier.GUIElements;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.restaurant.cashier.CashierApplication;

public class UITable {
	
	private boolean 		occupied;
	private boolean			booked;
	private int				nOfSits;
	private String			tableID;
	private double			bill;
	private double			totalPayed;
	private int				nOfCustomers;
	private List<UITable>	chainedTbls;
	private UITable			sx_NextTbl;
	private UITable			dx_NextTbl;
	private UIArrowChain	arrow;
	
	Point 					location;
	JPanel 					pnlDest;
	TableListener			listener;
	
	public static Border bdr_AboveFree 		= BorderFactory.createLineBorder(Color.GREEN, 	2);
	public static Border bdr_OutsideFree 	= BorderFactory.createLineBorder(Color.GREEN, 	1);
	public static Border bdr_AboveBusy		= BorderFactory.createLineBorder(Color.RED, 	2);
	public static Border bdr_OutsideBusy 	= BorderFactory.createLineBorder(Color.RED, 	1);
	
	public static Border bdr_FreeSelected 	= BorderFactory.createLineBorder(Color.GREEN, 	3);
	public static Border bdr_BusySelected 	= BorderFactory.createLineBorder(Color.RED, 	3);
	
	
	URL url = CashierApplication.class.getResource("/UITable_Image.png");
	ImageIcon tbImage = new ImageIcon(url);
	public JLabel lbl_Tab = new JLabel();
	
	int width = 105;
	int height = 83;
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public int getNofSits() {
		return nOfSits;
	}

	public void setNofSits(int nOfSits) {
		this.nOfSits = nOfSits;
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(String tableID) {
		this.tableID = tableID;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public double getTotalPayed() {
		return totalPayed;
	}

	public void setTotalPayed(double totalPayed) {
		this.totalPayed = totalPayed;
	}

	public int getNofCustomers() {
		return nOfCustomers;
	}

	public void setNofCustomers(int nOfCustomers) {
		this.nOfCustomers = nOfCustomers;
	}

	public List<UITable> getChainedTbls() {
		return chainedTbls;
	}

	public void setChainedTbls(List<UITable> chainedTbls) {
		this.chainedTbls = chainedTbls;
	}

	public UITable getSx_NextTbl() {
		return sx_NextTbl;
	}

	public void setSx_NextTbl(UITable sx_NextTbl) {
		this.sx_NextTbl = sx_NextTbl;
	}

	public UITable getDx_NextTbl() {
		return dx_NextTbl;
	}

	public void setDx_NextTbl(UITable dx_NextTbl) {
		this.dx_NextTbl = dx_NextTbl;
	}

	public UIArrowChain getArrow() {
		return arrow;
	}

	public void setArrow(UIArrowChain arrow) {
		this.arrow = arrow;
	}

	public UITable(Point loc, JPanel dest, int n_seats, String id) {
		location = loc;
		pnlDest = dest;
		setNofSits(n_seats);
		setTableID(id);
		setChainedTbls(null);
		setDx_NextTbl(null);
		setBill(0);
		setTotalPayed(0);
		int i = Character.getNumericValue(id.charAt(0) - 1);
		int j = CashierApplication.getCashierInstance().getColumnIDs().indexOf(id.charAt(1));
		if (CashierApplication.getCashierInstance().getTables()[i][j] != CashierApplication.getCashierInstance().getTables()[i][0]) {
			setSx_NextTbl(CashierApplication.getCashierInstance().getTables()[i][j - 1]);
			CashierApplication.getCashierInstance().getTables()[i][j - 1].setDx_NextTbl(this);
		} else {
			setSx_NextTbl(null);
		}
		setOccupied(false);
	}
    
	public void addTable() {
		listener = new TableListener();
		lbl_Tab.setIcon(new ImageIcon(tbImage.getImage().getScaledInstance(100, 75, Image.SCALE_AREA_AVERAGING)));
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
		setArrow(new UIArrowChain(this));
	}
	
	class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			CashierApplication.toggleTableStatus(getTableID());
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
			if (CashierApplication.getSelectedTable() == null || getTableID() != CashierApplication.getSelectedTable().getTableID()) {
				if (isOccupied()) {
					for (int i = 0; i < getChainedTbls().size(); i++) {
						getChainedTbls().get(i).updateBorder(bdr_AboveBusy);
					}
				} else {
					updateBorder(bdr_AboveFree);
				}
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (CashierApplication.getSelectedTable() == null || getTableID() != CashierApplication.getSelectedTable().getTableID()) {
				if (isOccupied()) {
					updateBorder(bdr_OutsideBusy);
					for (int i = 0; i < getChainedTbls().size(); i++) {
						getChainedTbls().get(i).updateBorder(bdr_OutsideBusy);
					}
				} else {
					updateBorder(bdr_OutsideFree);
				}
			}
		}
		
	}
    
}
