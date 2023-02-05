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

import com.restaurant.cashier.guielements.UITable;

public class DialogBoxes {
	
	public class SetNumberCustomers {
		JFrame frm;
		final static String DB_TITLE = "Numero clienti";
		final static String DB_CAPTION = "Seleziona il numero di clienti al tavolo";
		private JComboBox<Integer> cbNCustomers;
		private List<UITable> chainedTbls;
		
		
		public SetNumberCustomers(final JFrame frame) {
			// TODO Auto-generated constructor stub
			this.frm = frame;
		}
		
		public JComboBox<Integer> getCbNCustomers() {
			return cbNCustomers;
		}

		public void setCbNCustomers(final JComboBox<Integer> cbNCustomers) {
			this.cbNCustomers = cbNCustomers;
		}

		public List<UITable> getChainedTbls() {
			return chainedTbls;
		}

		public void setChainedTbls(final List<UITable> chainedTbls) {
			this.chainedTbls = chainedTbls;
		}

		public int show() {
			setChainedTbls(new ArrayList<>());
			final BorderLayout layout = new BorderLayout();
			final JPanel topPnl = new JPanel(layout);
			final JLabel lbl = new JLabel(DB_CAPTION);
			topPnl.add(lbl, BorderLayout.NORTH);
			final JPanel centerPnl = new JPanel(new BorderLayout(5, 5));
			final List<Integer> elems = new ArrayList<>();
			final int maxSits = getMaximumSits(CashierApplication.getSelectedTable());
			for (int i = 1; i <= maxSits; i++) {
				elems.add(i);
			}
			setCbNCustomers(new JComboBox(elems.toArray()));
			centerPnl.add(getCbNCustomers(), BorderLayout.CENTER);
			topPnl.add(centerPnl);
			final Object[] options = {"Conferma", "Annulla"};
			return 	JOptionPane.showOptionDialog(frm, topPnl, DB_TITLE, 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, 0);
		}
		
		private int getMaximumSits(final UITable selectedTable) {
			int chain;
			chain = calculateChain(0, selectedTable, true);
			chain = calculateChain(chain, selectedTable.getSx_NextTbl(), false);
			return chain * CashierApplication.getMaxSits();
			
		}
		
		private int calculateChain(final int chainSeq, final UITable currTbl, final boolean dirDx) {
			int chain = chainSeq;
			if (currTbl == null || currTbl.isOccupied()) {
				return chainSeq;
			} else {
				chain++;
				getChainedTbls().add(currTbl);
				if (dirDx) {
					chain = calculateChain(chain, currTbl.getDx_NextTbl(), dirDx);
				} else {
					chain = calculateChain(chain, currTbl.getSx_NextTbl(), dirDx);
				}
				
				return chain;
			}
		}
		
	}
	
	public class PayTableBill {
		JFrame frm;
		List<UITable> payingTbls;
		public JComboBox<Integer> cbPayedBills;
		final static String DB_TITLE = "Pagamento conto";
		
		public PayTableBill(final JFrame frame, final List<UITable> chainedTbls) {
			this.frm = frame;
			this.payingTbls = chainedTbls;
		}
		
		public int show() {
			final String[] columnNames = {
					"Persone al tavolo",
					"Costo a persona",
					"Pagamenti saldati",
					"Totale pagato"
			};
			final UITable tbl = payingTbls.get(0);
			final double	quoteBill =  tbl.getBill() / tbl.getNofCustomers();
			final int 	payedQuotes = (int)(tbl.getTotalPayed() / quoteBill);
			//Object[][] data = {{"8", "40", "3", "120/320"}};
			final Object[][] data = {{Integer.toString(tbl.getNofCustomers()), 
				Double.toString(quoteBill), 
				Integer.toString(payedQuotes), 
				Double.toString(tbl.getTotalPayed()) + "/" + Double.toString(tbl.getBill())}};
			final List<Integer> elems = new ArrayList<>();
			for (int i = 0; i < payingTbls.get(0).getNofCustomers() - payedQuotes; i++) {
				elems.add(i + 1);
			}
			cbPayedBills = new JComboBox(elems.toArray());
			final JTable table = new JTable(data, columnNames);
			table.setPreferredSize(new Dimension(550, 25));
			table.setRowSelectionAllowed(false);
			table.getTableHeader().setReorderingAllowed(false);
			table.setDefaultEditor(Object.class, null);
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			final JScrollPane sp = new JScrollPane(table);
			final BorderLayout layout = new BorderLayout();
			final JPanel container = new JPanel(layout);
			container.add(sp, BorderLayout.NORTH);
			final JPanel centerPnl = new JPanel(new BorderLayout(5, 5));
			final JLabel lblSumTb = new JLabel("Aggiungi pagamento quote");
			centerPnl.add(lblSumTb, BorderLayout.WEST);
			centerPnl.add(cbPayedBills, BorderLayout.CENTER);
			container.add(centerPnl);
			final Object[] options = {"Conferma", "Annulla"};
			return JOptionPane.showOptionDialog(frm, container, DB_TITLE, 
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, 0);
		}
	}

}
