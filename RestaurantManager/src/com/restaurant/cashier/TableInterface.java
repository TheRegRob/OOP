package com.restaurant.cashier;

import javax.swing.JPanel;

public interface TableInterface {
	void show();
	
	void setDimensions(int x, int y);
	
	void setupAreaTable(int rows, int columns, JPanel pnl);
}
