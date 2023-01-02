package com.restaurant.cashier;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
			db_SetNumberCustomers db = new db_SetNumberCustomers(CashierEnv.ci.frame);
			int res = db.show();
			if (res == JOptionPane.OK_OPTION) {
				List<UITable> tmpList = new ArrayList<>();
				if (db.cb_NCustomers.getSelectedIndex() <= CashierEnv.MAX_SITS) {
					tmpList.add(db.chainedTbls.get(0));
				} else {
					int tmpVal = db.cb_NCustomers.getSelectedIndex() + 1;
					int i = 0;
					while (tmpVal >= 6) {
						tmpList.add(db.chainedTbls.get(i));
						i++;
						tmpVal -= 6;
					}
					if (tmpVal > 0) {
						tmpList.add(db.chainedTbls.get(i));
					}
				}
				for (UITable tbl : tmpList) {
					tbl.occupied = true;
					tbl.mergedTbls = tmpList;
					tbl.updateBorder(UITable.bdr_OutsideBusy);
					//tbl.addMergeArrow();
				}
				CashierEnv.selectedTable.occupied = true;
				CashierEnv.clearSelection();
			}
		}
	}

	public void mng_OccupyTbl() {
		/*ID_Table;NofPeople;Hrs*/
		
	}
	
	public void mng_ClearTbl() {
		/*ID_Table*/
		
	}
	
	public void mng_PayBill() {
		/* ID_Table */
	}
	
	public void mng_OrderReceived() {
		/* MQTT_Order */
	}
	
	public void mng_ChangedOrder() {
		/* MQTT_Order */
		
	}
	
	public void mng_AskedBill() {
		/* MQTT_AskedBill */
		
	}
	
	public void mng_DishTakenCharge() {
		/* MQTT_DishTakenCharge */
		
	}
	
	public void mng_DishPrepared() {
		/* MQTT_DishPreared */
	}
}
