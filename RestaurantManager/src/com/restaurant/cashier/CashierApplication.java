package com.restaurant.cashier;

import java.util.List;

import com.restaurant.cashier.GUIElements.CashierGUI;
import com.restaurant.cashier.GUIElements.UITable;

public class CashierApplication{
	public static final	int MAX_SITS = 6;
	public boolean tableIsSelected = false;
	public static UITable selectedTable = null;
	
	public final static CashierGUI cashierInstance = new CashierGUI();	
	
	public static void main(String args[]){
			cashierInstance.setAppIcon();   
			cashierInstance.setDimensions(950, 500);
			cashierInstance.setupComponents();
			cashierInstance.show();
	    }
	
	public static void clearSelection() {
		if (selectedTable.isOccupied()) {
			selectedTable.updateBorder(UITable.bdr_OutsideBusy);
		} else {
			selectedTable.updateBorder(UITable.bdr_OutsideFree);
		}
		selectedTable = null;
	}
	
	public static void toggleTableStatus(String id) {
		if (selectedTable == null) {
			selectedTable = getTblFromID(id);
			if (selectedTable.isOccupied()) {
				List<UITable> chainedTbls = selectedTable.getChainedTbls();
				for (int i = 0; i < chainedTbls.size(); i++) {
					chainedTbls.get(i).updateBorder(UITable.bdr_BusySelected);
				}
			} else {
				selectedTable.updateBorder(UITable.bdr_FreeSelected);
			}	
		} else {
			if (selectedTable.getTableID() == id) {
				if (selectedTable.isOccupied()) {
					List<UITable> chainedTbls = selectedTable.getChainedTbls();
					for (int i = 0; i < chainedTbls.size(); i++) {
						chainedTbls.get(i).updateBorder(UITable.bdr_AboveBusy);
					}
				} else {
					selectedTable.updateBorder(UITable.bdr_AboveFree);
				}
				selectedTable = null;
			} else {
				if (selectedTable.isOccupied()) {
					List<UITable> chainedTbls = selectedTable.getChainedTbls();
					for (int i = 0; i < chainedTbls.size(); i++) {
						chainedTbls.get(i).updateBorder(UITable.bdr_OutsideBusy);
					}
				} else {
					selectedTable.updateBorder(UITable.bdr_OutsideFree);
				}
				selectedTable = getTblFromID(id);
				if (selectedTable.isOccupied()) {
					List<UITable> chainedTbls = selectedTable.getChainedTbls();
					for (int i = 0; i < chainedTbls.size(); i++) {
						chainedTbls.get(i).updateBorder(UITable.bdr_BusySelected);
					}
				} else {
					selectedTable.updateBorder(UITable.bdr_FreeSelected);
				}
			}
		}
		updateBtnStatus(selectedTable);
	}
	
	public static UITable getTblFromID (String id) {
		int i = Character.getNumericValue(id.charAt(0) - 1);
		int j = cashierInstance.getColumnIDs().indexOf(id.charAt(1));
		return cashierInstance.getTables()[i][j];
	}
	
	public static void updateBtnStatus(UITable selectedTable) {
		if (selectedTable == null) {
			CashierApplication.cashierInstance.getBtn_OccupyTable().setEnabled(false);
			CashierApplication.cashierInstance.getBtn_FreeTable().setEnabled(false);
			CashierApplication.cashierInstance.getBtn_PayBill().setEnabled(false);
		} else {
			if (selectedTable.isOccupied()) {
				CashierApplication.cashierInstance.getBtn_OccupyTable().setEnabled(false);
				CashierApplication.cashierInstance.getBtn_FreeTable().setEnabled(true);
				CashierApplication.cashierInstance.getBtn_PayBill().setEnabled(true);
			} else {
				CashierApplication.cashierInstance.getBtn_OccupyTable().setEnabled(true);
				CashierApplication.cashierInstance.getBtn_FreeTable().setEnabled(false);
				CashierApplication.cashierInstance.getBtn_PayBill().setEnabled(false);
			}
		}
	}
}
