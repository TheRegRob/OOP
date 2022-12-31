package com.restaurant.cashier;

public class CashierEnv{
	public boolean tableSelected = false;
	
	public final static CashierImpl ci = new CashierImpl();	
	
	public static void main(String args[]){
			ci.setAppIcon();   
			ci.setDimensions(950, 500);
			ci.setupComponents();
			ci.show();
	    }
	
	public static void toggleTableStatus(String id) {
		searchloop:
		for (int i = 0; i < ci.tables.length; i++) {
			for (int j = 0; j < ci.tables[0].length; j++) {
				if (ci.tables[i][j].selected) {
					ci.tables[i][j].selected = false;
					if (ci.tables[i][j].occupied) {
						ci.tables[i][j].updateBorder(UITable.bdr_OutsideBusy);
					} else {
						ci.tables[i][j].updateBorder(UITable.bdr_OutsideFree);
					}
					if (ci.tables[i][j].tableID == id) {
						if (ci.tables[i][j].occupied) {
							ci.tables[i][j].updateBorder(UITable.bdr_AboveBusy);
						} else {
							ci.tables[i][j].updateBorder(UITable.bdr_AboveFree);
						}
						CashierEnv.ci.btn_OccupyTable.setEnabled(false);
						CashierEnv.ci.btn_FreeTable.setEnabled(false);
						CashierEnv.ci.btn_PayBill.setEnabled(false);
						return;
					}
					break searchloop;
				}
			}
		}
		setloop:
		for (int i = 0; i < ci.tables.length; i++) {
			for (int j = 0; j < ci.tables[0].length; j++) {
				if (ci.tables[i][j].tableID ==  id) {
					ci.tables[i][j].selected = true;
					if (ci.tables[i][j].occupied) {
						CashierEnv.ci.btn_OccupyTable.setEnabled(false);
						CashierEnv.ci.btn_FreeTable.setEnabled(true);
						CashierEnv.ci.btn_PayBill.setEnabled(true);
					} else {
						CashierEnv.ci.btn_OccupyTable.setEnabled(true);
						CashierEnv.ci.btn_FreeTable.setEnabled(false);
						CashierEnv.ci.btn_PayBill.setEnabled(false);
					}
					break setloop;
				}
			}
		}
	}
}
