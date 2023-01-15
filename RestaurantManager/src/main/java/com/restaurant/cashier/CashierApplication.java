package com.restaurant.cashier;

import java.util.List;

import com.restaurant.cashier.GUIElements.CashierGUI;
import com.restaurant.cashier.GUIElements.UITable;
/**
 * 
 * @author robin
 * Class to instantiate the application GUI and its components
 *
 **/
public class CashierApplication {
	public static final	int MAX_SITS = 6;
	public static UITable selectedTable = null;
	public final static CashierGUI cashierInstance = new CashierGUI();	
	/**
	 * Main method of the cashier application. It creates a CashierGUI variable 
	 * that fill with it's components
	 * @param args The command line arguments
	 **/
	public static void main(String args[]){
			cashierInstance.setAppIcon();   
			cashierInstance.setDimensions(950, 500);
			cashierInstance.setupComponents();
			cashierInstance.show();
	    }
	/**
	 * Function that is called when a clear of the table selection is needed
	 **/
	public static void clearSelection() {
		if (selectedTable.isOccupied()) {
			selectedTable.updateBorder(UITable.bdr_OutsideBusy);
			} else {
			selectedTable.updateBorder(UITable.bdr_OutsideFree);
			}
		selectedTable = null;
	}
	/**
	 * Function that based on the flags of the tables, sets it in the
	 * correct way (red border if it's occupied, green otherwise, 
	 * thicker border if selected and so on).
	 * @param id | String - Its the identifier of the table.
	 **/
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
	/**
	 * Function that retrieves the table element from the instance list
	 * by its identifier (eg. table.id(3B) = table[2][1])
	 * @param id | String - It's the identifier of the table
	 * @return the UITable element referred to the identifier
	 **/
	public static UITable getTblFromID (String id) {
		int i;
		i = Character.getNumericValue(id.charAt(0) - 1);
		int j = cashierInstance.getColumnIDs().indexOf(id.charAt(1));
		return cashierInstance.getTables()[i][j];
	}
	/**
	 * Function to update the button enabling status based on the selected
	 * table flags (eg. if we select a free table, the button "Occupy table"
	 * will become enabled and the other two will become disabled)
	 * @param selectedTable
	 */
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
