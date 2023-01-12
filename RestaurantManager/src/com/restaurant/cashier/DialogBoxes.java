package com.restaurant.cashier;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DialogBoxes {
	
	public class db_SetNumberCustomers {
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
	
	public class db_PayTableBill {
		JFrame frm;
		List<UITable> payingTbls;
		public JComboBox<Integer> cb_PayedBills;
		String db_Title = "Pagamento conto";
		
		public db_PayTableBill(JFrame frame, List<UITable> chainedTbls) {
			this.frm = frame;
			this.payingTbls = chainedTbls;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int show() {
			String[] columnNames = {
					"Persone al tavolo",
					"Costo a persona",
					"Pagamenti saldati",
					"Totale pagato"
			};
			UITable tbl = payingTbls.get(0);
			double	quoteBill =  tbl.bill / tbl.nOfCustomers;
			int 	payedQuotes = (int)(tbl.totalPayed / quoteBill);
			//Object[][] data = {{"8", "40", "3", "120/320"}};
			Object[][] data = {{Integer.toString(tbl.nOfCustomers), 
				Double.toString(quoteBill), 
				Integer.toString(payedQuotes), 
				Double.toString(tbl.totalPayed) + "/" + Double.toString(tbl.bill)}};
			List<Integer> elems = new ArrayList<Integer>();
			for (int i = 0; i < payingTbls.get(0).nOfCustomers - payedQuotes; i++) {
				elems.add(i + 1);
			}
			cb_PayedBills = new JComboBox(elems.toArray());
			JTable table = new JTable(data, columnNames);
			table.setPreferredSize(new Dimension(550, 25));
			table.setRowSelectionAllowed(false);
			table.getTableHeader().setReorderingAllowed(false);
			table.setDefaultEditor(Object.class, null);
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			JScrollPane sp = new JScrollPane(table);
			BorderLayout layout = new BorderLayout();
			JPanel container = new JPanel(layout);
			container.add(sp, BorderLayout.NORTH);
			JPanel centerPnl = new JPanel(new BorderLayout(5, 5));
			JLabel lbl_SumTb = new JLabel("Aggiungi pagamento quote");
			centerPnl.add(lbl_SumTb, BorderLayout.WEST);
			centerPnl.add(cb_PayedBills, BorderLayout.CENTER);
			container.add(centerPnl);
			Object[] options = new Object[] {"Conferma", "Annulla"};
			int result = JOptionPane.showOptionDialog(frm, container, db_Title, 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, 0);
			return result;
		}
	}

}
