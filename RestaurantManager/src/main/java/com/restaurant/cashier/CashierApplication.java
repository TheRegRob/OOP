package com.restaurant.cashier;

import java.util.List;

import com.restaurant.cashier.guielements.CashierGUI;
import com.restaurant.cashier.guielements.UITable;
/**
 * 
 * @author robin
 * Class to instantiate the application GUI and its components
 *
 **/
public final class CashierApplication {
	private final static int MAX_SITS = 6;
	private final static CashierGUI CASHIER_INSTANCE = new CashierGUI();
	private static UITable selectedTable;
	
	private CashierApplication() {
		setSelectedTable(null);
	}
	public static CashierGUI getCashierInstance() {
		return CASHIER_INSTANCE;
	}
	public static void setSelectedTable(final UITable selectedTable) {
		CashierApplication.selectedTable = selectedTable;
	}
	public static UITable getSelectedTable() {
		return selectedTable;
	}
	public static int getMaxSits() {
		return MAX_SITS;
	}
	/**
	 * Main method of the cashier application. It creates a CashierGUI variable 
	 * that fill with it's components
	 * @param args The command line arguments
	 **/
	public static void main(final String args[]){
			getCashierInstance().setAppIcon();   
			getCashierInstance().setDimensions(950, 500);
			getCashierInstance().setupComponents();
			getCashierInstance().show();
	    }
	/**
	 * Function that is called when a clear of the table selection is needed
	 **/
	public static void clearSelection() {
		if (getSelectedTable().isOccupied()) {
			getSelectedTable().updateBorder(UITable.BDR_OUTSIDE_BUSY);
			} else {
			getSelectedTable().updateBorder(UITable.BDR_OUTSIDE_FREE);
			}
		setSelectedTable(null);
	}
	/**
	 * Function that based on the flags of the tables, sets it in the
	 * correct way (red border if it's occupied, green otherwise, 
	 * thicker border if selected and so on).
	 * @param id | String - Its the identifier of the table.
	 **/
	public static void toggleTableStatus(final String id) {
		if (getSelectedTable() == null) {
			setSelectedTable(getTblFromID(id));
			if (getSelectedTable().isOccupied()) {
				final List<UITable> chainedTbls = getSelectedTable().getChainedTbls();
				for (final UITable tbl : chainedTbls) {
					tbl.updateBorder(UITable.BDR_BUSY_SELECTED);
				}
			} else {
				getSelectedTable().updateBorder(UITable.BDR_FREE_SELECTED);
			}	
		} else {
			if (getSelectedTable().getTableID().equals(id)) {
				if (getSelectedTable().isOccupied()) {
					final List<UITable> chainedTbls = getSelectedTable().getChainedTbls();
					for (final UITable tbl : chainedTbls) {
						tbl.updateBorder(UITable.BDR_ABOVE_BUSY);
					}
				} else {
					getSelectedTable().updateBorder(UITable.BDR_ABOVE_FREE);
				}
				setSelectedTable(null);
			} else {
				if (getSelectedTable().isOccupied()) {
					final List<UITable> chainedTbls = getSelectedTable().getChainedTbls();
					for (final UITable tbl : chainedTbls) {
						tbl.updateBorder(UITable.BDR_OUTSIDE_BUSY);
					}
				} else {
					getSelectedTable().updateBorder(UITable.BDR_OUTSIDE_FREE);
				}
				setSelectedTable(getTblFromID(id));
				if (getSelectedTable().isOccupied()) {
					final List<UITable> chainedTbls = getSelectedTable().getChainedTbls();
					for (final UITable tbl : chainedTbls) {
						tbl.updateBorder(UITable.BDR_BUSY_SELECTED);
					}
				} else {
					getSelectedTable().updateBorder(UITable.BDR_FREE_SELECTED);
				}
			}
		}
		updateBtnStatus(getSelectedTable());
	}
	/**
	 * Function that retrieves the table element from the instance list
	 * by its identifier (eg. table.id(3B) = table[2][1])
	 * @param id | String - It's the identifier of the table
	 * @return the UITable element referred to the identifier
	 **/
	public static UITable getTblFromID (final String id) {
		final int i = Character.getNumericValue(id.charAt(0) - 1);
		final int j = getCashierInstance().getColumnIDs().indexOf(id.charAt(1));
		return getCashierInstance().getTables()[i][j];
	}
	/**
	 * Function to update the button enabling status based on the selected
	 * table flags (eg. if we select a free table, the button "Occupy table"
	 * will become enabled and the other two will become disabled)
	 * @param selectedTable
	 */
	public static void updateBtnStatus(final UITable selectedTable) {
		if (selectedTable == null) {
			CashierApplication.getCashierInstance().getBtnOccupyTable().setEnabled(false);
			CashierApplication.getCashierInstance().getBtnFreeTable().setEnabled(false);
			CashierApplication.getCashierInstance().getBtnPayBill().setEnabled(false);
		} else {
			if (selectedTable.isOccupied()) {
				CashierApplication.getCashierInstance().getBtnOccupyTable().setEnabled(false);
				CashierApplication.getCashierInstance().getBtnFreeTable().setEnabled(true);
				CashierApplication.getCashierInstance().getBtnPayBill().setEnabled(true);
			} else {
				CashierApplication.getCashierInstance().getBtnOccupyTable().setEnabled(true);
				CashierApplication.getCashierInstance().getBtnFreeTable().setEnabled(false);
				CashierApplication.getCashierInstance().getBtnPayBill().setEnabled(false);
			}
		}
	}
}
