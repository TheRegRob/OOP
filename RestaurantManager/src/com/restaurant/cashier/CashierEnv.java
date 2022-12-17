package com.restaurant.cashier;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.awt.CardLayout;
import java.awt.Color;
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
	       final JScrollPane scroll = new JScrollPane();
	       scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	       frame.setSize(800, 500); 
	       JPanel container = new JPanel(new GridLayout(1, 3));
	       frame.add(container);
	       JPanel pnl_Tables = new JPanel();
	       
	       
	       pnl_Tables.setBounds(0, 38, 597, 382);
	       pnl_Tables.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
	       
	       UITable tab_1a = new UITable(new Point(5, 	40), pnl_Tables);
	       UITable tab_1b = new UITable(new Point(135, 	40), pnl_Tables);
	       UITable tab_1c = new UITable(new Point(265, 	40), pnl_Tables);
	       UITable tab_1d = new UITable(new Point(395, 	40), pnl_Tables);
	       UITable tab_2a = new UITable(new Point(5, 	130), pnl_Tables);
	       UITable tab_2b = new UITable(new Point(135, 	130), pnl_Tables);
	       UITable tab_2c = new UITable(new Point(265, 	130), pnl_Tables);
	       UITable tab_2d = new UITable(new Point(395, 	130), pnl_Tables);
	       UITable tab_3a = new UITable(new Point(5, 	220), pnl_Tables);
	       UITable tab_3b = new UITable(new Point(135, 	220), pnl_Tables);
	       UITable tab_3c = new UITable(new Point(265, 	220), pnl_Tables);
	       UITable tab_3d = new UITable(new Point(395, 	220), pnl_Tables);
	       UITable tab_4a = new UITable(new Point(5, 	310), pnl_Tables);
	       UITable tab_4b = new UITable(new Point(135, 	310), pnl_Tables);
	       UITable tab_4c = new UITable(new Point(265, 	310), pnl_Tables);
	       UITable tab_4d = new UITable(new Point(395, 	310), pnl_Tables);
	       
	       pnl_Tables.setLayout(null);
	       container.add(pnl_Tables, BorderLayout.CENTER);
	       container.add(scroll, BorderLayout.EAST);
	       container.setBorder(new TitledBorder("Container"));
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setLocationRelativeTo(null); /* AKA freme.CenterScreen() on .NET */
	       
	       frame.setVisible(true);
	    }
}
