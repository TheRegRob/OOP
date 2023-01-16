package com.restaurant.cashier.Listeners;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.restaurant.cashier.CashierApplication;
import com.restaurant.cashier.DialogBoxes;
import com.restaurant.cashier.DialogBoxes.PayTableBill;
import com.restaurant.cashier.DialogBoxes.SetNumberCustomers;
import com.restaurant.cashier.GUIElements.UITable;
import com.restaurant.utils.Logger;
import com.restaurant.utils.Logger.TypeLog;

public class CashierEventListener implements ActionListener {
	
	String ev_Cmd;
	
	
	public static String[] eventList = { 
			/* Cashier events */
			"btn_OccupyTable",
			"btn_ClearTable",
			"btn_PayBill",
			/* -------------- */
			/* Tables events */
			"mqtt_OrderReceived",
			"mqtt_ChangedOrder",
			"mqtt_AskedBill",
			/* ------------ */
			/* Kitchen events */
			"mqtt_DishTakenCharge",
			"mqtt_DishPrepared"
			/* -------------- */
			};
	
	public enum EventsIdx {
		ev_BtnOccupyTbl(0),
		ev_BtnClearTbl(1),
		ev_BtnPayBill(2),
		ev_MqttOrderReceived(3),
		ev_MqttChangedOrder(4),
		ev_MqttAskedBill(5),
		ev_MqttDishTakenCharge(6),
		ev_MqttDishPrepared(7);
		
		private int value;
		private static Map<Object, Object> map = new HashMap<>();
		
		private EventsIdx(int value) {
			this.value = value;
		}
		
		static {
			for (EventsIdx eventsIdx : EventsIdx.values()) {
				map.put(eventsIdx.value, eventsIdx);
			}
		}
		
		public static EventsIdx valueOf(int eventIdx) {
			return (EventsIdx) map.get(eventIdx);
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "btn_OccupyTable":
			mng_OccupyTbl.updateValues();
			break;
		case "btn_ClearTable":
			mng_ClearTbl.clearTable();
			break;
		case "btn_PayBill":
			mng_PayBill.updatePayment();
			break;
		default:
			break;
		}
	}
	

	static class mng_OccupyTbl {
		/*ID_Table;NofPeople;Hrs*/
		private static List<UITable>	occupiedTables;
		private static int				nOfCustomers;
		
		private static void sortTmpTbl(List<UITable> list) {
			boolean loopAgain = false;
			for (UITable tbl : list) {
				if (list.indexOf(tbl) < list.size() - 1) {
					int posX_prev = CashierApplication.getCashierInstance().getColumnIDs().indexOf(tbl.getTableID().charAt(1));
					int posX_next = CashierApplication.getCashierInstance().getColumnIDs().indexOf(list.get(list.indexOf(tbl) + 1).getTableID().charAt(1));
					if (posX_prev > posX_next) {
						UITable t1 = tbl;
						UITable t2 = list.get(list.indexOf(tbl)+ 1);
						int posT1 = list.indexOf(tbl);
						int posT2 = posT1 + 1;
						list.set(posT1, t2);
						list.set(posT2, t1);
						loopAgain = true;
					}
				}
				if (loopAgain) {
					sortTmpTbl(list);
				}
			}
		}
		
		public static void updateValues() {
			DialogBoxes db = new DialogBoxes();
			SetNumberCustomers db_SetSetCust = db.new SetNumberCustomers(CashierApplication.getCashierInstance().getFrame());
			int res = db_SetSetCust.show();
			if (res == JOptionPane.OK_OPTION) {
				nOfCustomers = db_SetSetCust.getCbNCustomers().getSelectedIndex() + 1;
				List<UITable> tmpList = new ArrayList<>();
				if (db_SetSetCust.getCbNCustomers().getSelectedIndex() + 1 <= CashierApplication.getMaxSits()) {
					tmpList.add(db_SetSetCust.getChainedTbls().get(0));
				} else {
					int tmpVal = nOfCustomers;
					int i = 0;
					while (tmpVal >= 6) {
						tmpList.add(db_SetSetCust.getChainedTbls().get(i));
						i++;
						tmpVal -= 6;
					}
					if (tmpVal > 0) {
						tmpList.add(db_SetSetCust.getChainedTbls().get(i));
					}
				}
				sortTmpTbl(tmpList);
				occupiedTables = tmpList;
				List<UITable> copyList = new ArrayList<>(tmpList);
				
				for (UITable tbl : tmpList) {
					tbl.setOccupied(true);
					tbl.setNofCustomers(nOfCustomers);
					tbl.setChainedTbls(copyList);
					tbl.updateBorder(UITable.bdr_OutsideBusy);
					if (tmpList.indexOf(tbl) < tmpList.size() - 1) {
						tbl.addMergeArrow();
					}
				}
				TreePath tp = CashierApplication.getCashierInstance().getTblTree().searchTable(CashierApplication.getSelectedTable());
				if (tp == null) {
					CashierApplication.getCashierInstance().getTblTree().addObject(CashierApplication.getCashierInstance().getTblTree().getRootNode(), CashierApplication.getSelectedTable());
					String strDish = new String();
					for (int i = 0; i < 6; i++) {
						switch(i) {
						case 0:
							strDish = "43";
							break;
						case 1:
							strDish = "60";
							break;
						case 2:
							strDish = "100";
							break;
						case 3:
							strDish = "23/A";
							break;
						case 4:
							strDish = "128";
							break;
						case 5:
							strDish = "180A";
							break;

						}
						TreePath tp2 = CashierApplication.getCashierInstance().getTblTree().searchTable(CashierApplication.getSelectedTable());
						CashierApplication.getCashierInstance().getTblTree().addObject((DefaultMutableTreeNode)tp2.getLastPathComponent(), strDish);
					}
				}
				CashierApplication.getSelectedTable().setOccupied(true);
				CashierApplication.clearSelection();
				logEvents();
			}
		}
	
		public static void logEvents() {
			if (nOfCustomers > CashierApplication.getMaxSits()) {
				String tblSits 	= Integer.toString(nOfCustomers);
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < occupiedTables.size(); i++) {
					str.append(occupiedTables.get(i).getTableID());
					if (i + 1 < occupiedTables.size()) {
						str.append(", ");
					}
				}
				Logger.Log(TypeLog.tl_Info, "Occupati tavoli " + str + " per " + tblSits + " persone");
			} else {
				String tblSits 	= Integer.toString(mng_OccupyTbl.nOfCustomers);
				String tblID 	= mng_OccupyTbl.occupiedTables.get(0).getTableID();
				String perStr	= mng_OccupyTbl.nOfCustomers > 1 ? " persone" : " persona";
				Logger.Log(TypeLog.tl_Info, "Occupato tavolo " + tblID + " per " + tblSits + perStr);
			}	
		}
	}
	
	static class mng_ClearTbl {
		/*ID_Table*/
		private static int				nOfCustomers;
		private static List<UITable>	occupiedTables;
		
		public static void clearTable() {
			nOfCustomers = CashierApplication.getSelectedTable().getNofCustomers();
			occupiedTables = new ArrayList<>(CashierApplication.getSelectedTable().getChainedTbls());
			for (int i = 0; i < CashierApplication.getSelectedTable().getChainedTbls().size(); i++) {
				UITable tbl = CashierApplication.getSelectedTable().getChainedTbls().get(i);
				if (tbl.getArrow() != null) {
					tbl.getArrow().removeArrow();
				}
				tbl.setOccupied(false);
				tbl.setNofCustomers(0);
				tbl.updateBorder(UITable.bdr_OutsideFree);
			}
			CashierApplication.getSelectedTable().setChainedTbls(null);
			CashierApplication.clearSelection();
			logEvents();
		}
		public static void logEvents() {
			if (nOfCustomers > CashierApplication.getMaxSits()) {
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < occupiedTables.size(); i++) {
					str.append(occupiedTables.get(i).getTableID());
					if (i + 1 < occupiedTables.size()) {
						str.append(", ");
					}
				}
				Logger.Log(TypeLog.tl_Info, "Liberati tavoli " + str);
			} else {
				String tblID 	= mng_OccupyTbl.occupiedTables.get(0).getTableID();
				Logger.Log(TypeLog.tl_Info, "Liberato tavolo " + tblID);
			}
		}
		
	}
	
	static class mng_PayBill {
		/* ID_Table */
		public static void updatePayment() {
			DialogBoxes db = new DialogBoxes();
			PayTableBill db_PayBill = db.new PayTableBill(CashierApplication.getCashierInstance().getFrame(), CashierApplication.getSelectedTable().getChainedTbls());
			int res = db_PayBill.show();
			/* TODO: Completare */
		}
	}
	
	static class mng_OrderReceived {
		/* MQTT_Order */
	}
	
	static class mng_ChangedOrder {
		/* MQTT_Order */
		
	}
	
	static class mng_AskedBill {
		/* MQTT_AskedBill */
		
	}
	
	static class mng_DishTakenCharge {
		/* MQTT_DishTakenCharge */
		
	}
	
	static class mng_DishPrepared {
		/* MQTT_DishPreared */
	}
	
}
