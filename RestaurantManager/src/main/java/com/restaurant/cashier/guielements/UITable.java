package com.restaurant.cashier.guielements;

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
	private UITable			sxNextTbl;
	private UITable			dxNextTbl;
	private UIArrowChain	arrow;
	
	Point 					location;
	JPanel 					pnlDest;
	TableListener			listener;
	
	public static final Border BDR_ABOVE_FREE 		= BorderFactory.createLineBorder(Color.GREEN, 	2);
	public static final Border BDR_OUTSIDE_FREE 	= BorderFactory.createLineBorder(Color.GREEN, 	1);
	public static final Border BDR_ABOVE_BUSY		= BorderFactory.createLineBorder(Color.RED, 	2);
	public static final Border BDR_OUTSIDE_BUSY 	= BorderFactory.createLineBorder(Color.RED, 	1);
	
	public static final Border BDR_FREE_SELECTED 	= BorderFactory.createLineBorder(Color.GREEN, 	3);
	public static final Border BDR_BUSY_SELECTED 	= BorderFactory.createLineBorder(Color.RED, 	3);
	
	
	final URL url = CashierApplication.class.getResource("/UITable_Image.png");
	ImageIcon tbImage = new ImageIcon(url);
	public JLabel lblTab = new JLabel();
	
	int width = 105;
	int height = 83;
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(final boolean occupied) {
		this.occupied = occupied;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(final boolean booked) {
		this.booked = booked;
	}

	public int getNofSits() {
		return nOfSits;
	}

	public void setNofSits(final int nOfSits) {
		this.nOfSits = nOfSits;
	}

	public String getTableID() {
		return tableID;
	}

	public void setTableID(final String tableID) {
		this.tableID = tableID;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(final double bill) {
		this.bill = bill;
	}

	public double getTotalPayed() {
		return totalPayed;
	}

	public void setTotalPayed(final double totalPayed) {
		this.totalPayed = totalPayed;
	}

	public int getNofCustomers() {
		return nOfCustomers;
	}

	public void setNofCustomers(final int nOfCustomers) {
		this.nOfCustomers = nOfCustomers;
	}

	public List<UITable> getChainedTbls() {
		return chainedTbls;
	}

	public void setChainedTbls(final List<UITable> chainedTbls) {
		this.chainedTbls = chainedTbls;
	}

	public UITable getSx_NextTbl() {
		return sxNextTbl;
	}

	public void setSx_NextTbl(final UITable sx_NextTbl) {
		this.sxNextTbl = sx_NextTbl;
	}

	public UITable getDx_NextTbl() {
		return dxNextTbl;
	}

	public void setDx_NextTbl(final UITable dx_NextTbl) {
		this.dxNextTbl = dx_NextTbl;
	}

	public UIArrowChain getArrow() {
		return arrow;
	}

	public void setArrow(final UIArrowChain arrow) {
		this.arrow = arrow;
	}

	public UITable(final Point loc, final JPanel dest, final int n_seats, final String id) {
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
		lblTab.setIcon(new ImageIcon(tbImage.getImage().getScaledInstance(100, 75, Image.SCALE_AREA_AVERAGING)));
		lblTab.setBorder(BDR_OUTSIDE_FREE);
		lblTab.setHorizontalAlignment(SwingConstants.CENTER);
		lblTab.setBounds(location.x, location.y, width, height);
		lblTab.addMouseListener(listener);
		pnlDest.add(lblTab);
	}
	
	public void updateBorder(Border border) {
		this.lblTab.setBorder(border);
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
						getChainedTbls().get(i).updateBorder(BDR_ABOVE_BUSY);
					}
				} else {
					updateBorder(BDR_ABOVE_FREE);
				}
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (CashierApplication.getSelectedTable() == null || getTableID() != CashierApplication.getSelectedTable().getTableID()) {
				if (isOccupied()) {
					updateBorder(BDR_OUTSIDE_BUSY);
					for (int i = 0; i < getChainedTbls().size(); i++) {
						getChainedTbls().get(i).updateBorder(BDR_OUTSIDE_BUSY);
					}
				} else {
					updateBorder(BDR_OUTSIDE_FREE);
				}
			}
		}
		
	}
    
}
