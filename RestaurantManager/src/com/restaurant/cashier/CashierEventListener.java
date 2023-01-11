package com.restaurant.cashier;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.restaurant.cashier.DialogBoxes.db_PayTableBill;
import com.restaurant.cashier.DialogBoxes.db_SetNumberCustomers;
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
		private static Date				occupyDatetime;
		
		private static void sortTmpTbl(List<UITable> list) {
			boolean loopAgain = false;
			for (UITable tbl : list) {
				if (list.indexOf(tbl) < list.size() - 1) {
					int posX_prev = CashierEnv.ci.columnIDs.indexOf(tbl.tableID.charAt(1));
					int posX_next = CashierEnv.ci.columnIDs.indexOf(list.get(list.indexOf(tbl) + 1).tableID.charAt(1));
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
			db_SetNumberCustomers db_SetSetCust = db.new db_SetNumberCustomers(CashierEnv.ci.frame);
			int res = db_SetSetCust.show();
			if (res == JOptionPane.OK_OPTION) {
				nOfCustomers = db_SetSetCust.cb_NCustomers.getSelectedIndex() + 1;
				occupyDatetime = Calendar.getInstance().getTime();
				List<UITable> tmpList = new ArrayList<>();
				if (db_SetSetCust.cb_NCustomers.getSelectedIndex() + 1 <= CashierEnv.MAX_SITS) {
					tmpList.add(db_SetSetCust.chainedTbls.get(0));
				} else {
					int tmpVal = nOfCustomers;
					int i = 0;
					while (tmpVal >= 6) {
						tmpList.add(db_SetSetCust.chainedTbls.get(i));
						i++;
						tmpVal -= 6;
					}
					if (tmpVal > 0) {
						tmpList.add(db_SetSetCust.chainedTbls.get(i));
					}
				}
				sortTmpTbl(tmpList);
				occupiedTables = tmpList;
				List<UITable> copyList = new ArrayList<>(tmpList);
				
				for (UITable tbl : tmpList) {
					tbl.occupied = true;
					tbl.nOfCustomers = nOfCustomers;
					tbl.chainedTbls = copyList;
					tbl.updateBorder(UITable.bdr_OutsideBusy);
					if (tmpList.indexOf(tbl) < tmpList.size() - 1) {
						tbl.addMergeArrow();
					}
				}
				TreePath tp = CashierEnv.ci.tblTree.searchTable(CashierEnv.selectedTable);
				if (tp == null) {
					CashierEnv.ci.tblTree.addObject(CashierEnv.ci.tblTree.rootNode, CashierEnv.selectedTable);
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
						TreePath tp2 = CashierEnv.ci.tblTree.searchTable(CashierEnv.selectedTable);
						CashierEnv.ci.tblTree.addObject((DefaultMutableTreeNode)tp2.getLastPathComponent(), strDish);
					}
				}
				CashierEnv.selectedTable.occupied = true;
				CashierEnv.clearSelection();
				logEvents();
			}
		}
	
		public static void logEvents() {
			if (nOfCustomers > CashierEnv.MAX_SITS) {
				String tblSits 	= Integer.toString(nOfCustomers);
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < occupiedTables.size(); i++) {
					str.append(occupiedTables.get(i).tableID);
					if (i + 1 < occupiedTables.size()) {
						str.append(", ");
					}
				}
				Logger.Log(TypeLog.tl_Info, "Occupati tavoli " + str + " per " + tblSits + " persone");
			} else {
				String tblSits 	= Integer.toString(mng_OccupyTbl.nOfCustomers);
				String tblID 	= mng_OccupyTbl.occupiedTables.get(0).tableID;
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
			nOfCustomers = CashierEnv.selectedTable.nOfCustomers;
			occupiedTables = new ArrayList<>(CashierEnv.selectedTable.chainedTbls);
			for (int i = 0; i < CashierEnv.selectedTable.chainedTbls.size(); i++) {
				UITable tbl = CashierEnv.selectedTable.chainedTbls.get(i);
				if (tbl.arrow != null) {
					tbl.arrow.removeArrow();
				}
				tbl.occupied = false;
				tbl.nOfCustomers = 0;
				tbl.updateBorder(UITable.bdr_OutsideFree);
			}
			CashierEnv.selectedTable.chainedTbls = null;
			CashierEnv.clearSelection();
			logEvents();
		}
		public static void logEvents() {
			if (nOfCustomers > CashierEnv.MAX_SITS) {
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < occupiedTables.size(); i++) {
					str.append(occupiedTables.get(i).tableID);
					if (i + 1 < occupiedTables.size()) {
						str.append(", ");
					}
				}
				Logger.Log(TypeLog.tl_Info, "Liberati tavoli " + str);
			} else {
				String tblID 	= mng_OccupyTbl.occupiedTables.get(0).tableID;
				Logger.Log(TypeLog.tl_Info, "Liberato tavolo " + tblID);
			}
		}
		
	}
	
	static class mng_PayBill {
		/* ID_Table */
		public static void updatePayment() {
			DialogBoxes db = new DialogBoxes();
			db_PayTableBill db_PayBill = db.new db_PayTableBill(CashierEnv.ci.frame, CashierEnv.selectedTable.chainedTbls);
			int res = db_PayBill.show();
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
