package com.restaurant.cashier;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.tools.javac.Main;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class CashierEnv {
	public static void main(String args[]) throws IOException{
	       JFrame frame = new JFrame("RestaurantManager - Cassa");
	       URL url = CashierEnv.class.getResource("/TableRestaurant.png");
	       ImageIcon tbImage = new ImageIcon(url);
	       final JTextArea textArea = new JTextArea();
	       textArea.setLineWrap(true);
	       final JScrollPane scroll = new JScrollPane(textArea);
	       scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	       scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	       frame.setSize(800, 500); 
	       frame.setResizable(false);
	       JPanel container = new JPanel(new GridBagLayout());
	       GridBagConstraints gbc = new GridBagConstraints();
	       
	       frame.getContentPane().add(container);
	       JPanel pnl_Left = new JPanel(new BorderLayout());
	       JLabel lbl_Title = new JLabel("Stato dei tavoli", SwingConstants.CENTER);
	       pnl_Left.add(lbl_Title, BorderLayout.NORTH);
	       JPanel pnl_Tables = new JPanel(null);
	       pnl_Left.add(pnl_Tables, BorderLayout.CENTER);
	       JPanel pnl_Buttons = new JPanel(new GridLayout());
	       JButton btn_OccupaTavolo = new JButton("Occupa tavolo");
	       JButton btn_ViewOrdini = new JButton("Visualizza ordini");
	       JButton btn_PagaConto = new JButton("Pagamento conto");
	       pnl_Buttons.add(btn_OccupaTavolo);
	       pnl_Buttons.add(btn_ViewOrdini);
	       pnl_Buttons.add(btn_PagaConto);
	       pnl_Left.add(pnl_Buttons, BorderLayout.SOUTH);
	       pnl_Tables.setLayout(null);

	       
	       UITable tab_1a = new UITable(new Point(15, 	40), pnl_Tables);
	       UITable tab_1b = new UITable(new Point(145, 	40), pnl_Tables);
	       UITable tab_1c = new UITable(new Point(275, 	40), pnl_Tables);
	       UITable tab_1d = new UITable(new Point(405, 	40), pnl_Tables);
	       UITable tab_2a = new UITable(new Point(15, 	130), pnl_Tables);
	       UITable tab_2b = new UITable(new Point(145, 	130), pnl_Tables);
	       UITable tab_2c = new UITable(new Point(275, 	130), pnl_Tables);
	       UITable tab_2d = new UITable(new Point(405, 	130), pnl_Tables);
	       UITable tab_3a = new UITable(new Point(15, 	220), pnl_Tables);
	       UITable tab_3b = new UITable(new Point(145, 	220), pnl_Tables);
	       UITable tab_3c = new UITable(new Point(275, 	220), pnl_Tables);
	       UITable tab_3d = new UITable(new Point(405, 	220), pnl_Tables);
	       UITable tab_4a = new UITable(new Point(15, 	310), pnl_Tables);
	       UITable tab_4b = new UITable(new Point(145, 	310), pnl_Tables);
	       UITable tab_4c = new UITable(new Point(275, 	310), pnl_Tables);
	       UITable tab_4d = new UITable(new Point(405, 	310), pnl_Tables);
	       
	       gbc.fill = GridBagConstraints.HORIZONTAL;
	       gbc.gridx = 0;
	       gbc.gridy = 0;
	       gbc.weightx = 0.6;
	       gbc.insets = new Insets(0, 10, 0, 0);
	       gbc.ipady = 420;
	    		   
	       container.add(pnl_Left, gbc);
	       
	       gbc.gridx = 2;
	       gbc.gridy = 0;
	       gbc.weightx = 0.4;
	       gbc.ipady = 430;
	       gbc.anchor = GridBagConstraints.EAST;
	       gbc.insets = new Insets(0, 15, 0, 10);
	       container.add(scroll, gbc);
	       pnl_Tables.setBorder(new LineBorder(Color.BLACK, 1, true));
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
	       frame.setLocationRelativeTo(null); /* AKA frame.CenterScreen() on .NET */
	       frame.setVisible(true);
	       
	       textArea.append("-- Restaurant Manager --");
	    }
}
