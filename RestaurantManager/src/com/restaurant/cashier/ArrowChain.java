package com.restaurant.cashier;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ArrowChain {
	
	private UITable tbl;
	private JLabel	lbl;
	
	public ArrowChain(UITable tbl) {
		this.tbl = tbl;
		createArrow(tbl);
	}
	
	public void createArrow(UITable tbl) {
		int x = tbl.location.x + tbl.width - 8;
		int y = tbl.location.y + tbl.height / 3;
		this.lbl = new JLabel();
		this.lbl.setText("→");
		this.lbl.setFont(new Font("Calibri", Font.PLAIN, 24));
		this.lbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.lbl.setBounds(x, y, 40, 25);
		tbl.arrow = this;
		tbl.pnlDest.add(lbl);
		tbl.pnlDest.repaint();
	}
	
	public void removeArrow() {
		this.tbl.pnlDest.remove(this.lbl);
		this.tbl.arrow = null;
	}
	
}
