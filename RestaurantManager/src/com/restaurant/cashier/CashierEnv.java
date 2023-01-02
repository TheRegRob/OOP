package com.restaurant.cashier;

public class CashierEnv{
	public static final	int MAX_SITS = 6;
	public boolean tableIsSelected = false;
	public static UITable selectedTable = null;
	
	public final static CashierImpl ci = new CashierImpl();	
	
	public static void main(String args[]){
			ci.setAppIcon();   
			ci.setDimensions(950, 500);
			ci.setupComponents();
			ci.show();
	    }
	
	public static void clearSelection() {
		if (selectedTable.occupied) {
			selectedTable.updateBorder(UITable.bdr_OutsideBusy);
		} else {
			selectedTable.updateBorder(UITable.bdr_OutsideFree);
		}
		selectedTable = null;
	}
	
	public static void toggleTableStatus(String id) {
		if (selectedTable == null) {
			selectedTable = getTblFromID(id);
			if (selectedTable.occupied) {
				selectedTable.updateBorder(UITable.bdr_BusySelected);
			} else {
				selectedTable.updateBorder(UITable.bdr_FreeSelected);
			}	
		} else {
			if (selectedTable.tableID == id) {
				if (selectedTable.occupied) {
					selectedTable.updateBorder(UITable.bdr_AboveBusy);
				} else {
					selectedTable.updateBorder(UITable.bdr_AboveFree);
				}
				selectedTable = null;
			} else {
				if (selectedTable.occupied) {
					selectedTable.updateBorder(UITable.bdr_OutsideBusy);
				} else {
					selectedTable.updateBorder(UITable.bdr_OutsideFree);
				}
				selectedTable = getTblFromID(id);
				if (selectedTable.occupied) {
					selectedTable.updateBorder(UITable.bdr_BusySelected);
				} else {
					selectedTable.updateBorder(UITable.bdr_FreeSelected);
				}
			}
		}
		updateBtnStatus(selectedTable);
	}
	
	public static UITable getTblFromID (String id) {
		int i = Character.getNumericValue(id.charAt(0) - 1);
		int j = ci.columnIDs.indexOf(id.charAt(1));
		return ci.tables[i][j];
	}
	
	public static void updateBtnStatus(UITable selectedTable) {
		if (selectedTable == null) {
			CashierEnv.ci.btn_OccupyTable.setEnabled(false);
			CashierEnv.ci.btn_FreeTable.setEnabled(false);
			CashierEnv.ci.btn_PayBill.setEnabled(false);
		} else {
			if (selectedTable.occupied) {
				CashierEnv.ci.btn_OccupyTable.setEnabled(false);
				CashierEnv.ci.btn_FreeTable.setEnabled(true);
				CashierEnv.ci.btn_PayBill.setEnabled(true);
			} else {
				CashierEnv.ci.btn_OccupyTable.setEnabled(true);
				CashierEnv.ci.btn_FreeTable.setEnabled(false);
				CashierEnv.ci.btn_PayBill.setEnabled(false);
			}
		}
	}
}
