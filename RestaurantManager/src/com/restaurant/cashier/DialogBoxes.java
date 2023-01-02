package com.restaurant.cashier;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogBoxes {
	
	public static class db_SetNumberCustomers {
		JFrame frm;
		String db_Title = "Numero clienti";
		String db_Caption = "Seleziona il numero di clienti al tavolo";
		public JComboBox<Integer> cb_NCustomers;
		public List<UITable> chainedTbls;
		
		
		public db_SetNumberCustomers(JFrame frame) {
			// TODO Auto-generated constructor stub
			this.frm = frame;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int show() {
			chainedTbls = new ArrayList<>();
			BorderLayout layout = new BorderLayout();
			JPanel topPnl = new JPanel(layout);
			JLabel lbl = new JLabel(db_Caption);
			topPnl.add(lbl, BorderLayout.NORTH);
			JPanel centerPnl = new JPanel(new BorderLayout(5, 5));
			List<Integer> elems = new ArrayList<Integer>();
			int maxSits = getMaximumSits(CashierEnv.selectedTable);
			for (int i = 1; i <= maxSits; i++) {
				elems.add(i);
			}
			cb_NCustomers = new JComboBox(elems.toArray());
			centerPnl.add(cb_NCustomers, BorderLayout.CENTER);
			topPnl.add(centerPnl);
			Object[] options = new Object[] {"Conferma", "Annulla"};
			int result = JOptionPane.showOptionDialog(frm, topPnl, db_Title, 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, 0);
			return result;
		}
		
		private int getMaximumSits(UITable selectedTable) {
			int chain;
			chain = calculateChain(0, selectedTable, true);
			chain = calculateChain(chain, selectedTable.sx_NextTbl, false);
			return chain * CashierEnv.MAX_SITS;
			
		}
		
		private int calculateChain(int chainSeq, UITable currTbl, boolean dirDx) {
			int chain = chainSeq;
			if (currTbl == null || currTbl.occupied) {
				return chainSeq;
			} else {
				chain++;
				chainedTbls.add(currTbl);
				if (dirDx) {
					chain = calculateChain(chain, currTbl.dx_NextTbl, dirDx);
				} else {
					chain = calculateChain(chain, currTbl.sx_NextTbl, dirDx);
				}
				
				return chain;
			}
		}
		
	}

}
