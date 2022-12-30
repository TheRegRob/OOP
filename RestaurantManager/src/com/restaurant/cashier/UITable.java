package com.restaurant.cashier;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UITable {
	public boolean 	occupied;
	public int		nOfSits;
	Point 			location;
	JPanel 			pnlDest;
	
	
	URL url = CashierEnv.class.getResource("/TableRestaurant.png");
	ImageIcon tbImage = new ImageIcon(url);
	JLabel lbl_Tab = new JLabel();
	int width = 105;
	int height = 56;
	
	public UITable(Point loc, JPanel dest) {
		location = loc;
		pnlDest = dest;
	}
    
	public void addTable() {
		lbl_Tab.setIcon(new ImageIcon(tbImage.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
		lbl_Tab.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
		lbl_Tab.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Tab.setBounds(location.x, location.y, width, height);
		pnlDest.add(lbl_Tab);
	}
    
}
