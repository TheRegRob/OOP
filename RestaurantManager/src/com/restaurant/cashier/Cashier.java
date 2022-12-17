package com.restaurant.cashier;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

public class Cashier {
	public static void main(String args[]) throws IOException{
	       JFrame frame = new JFrame("RestaurantManager - Cassa");
	       URL url = Cashier.class.getResource("/TableRestaurant.png");
	       ImageIcon tbImage = new ImageIcon(url);
	       JPanel panel = new JPanel();
	       
	       panel.setBounds(0, 0, 503, 395);
	       frame.setSize(503, 423);
	       frame.getContentPane().setLayout(null);
	       panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
	       
	       frame.getContentPane().add(panel);
	       panel.setLayout(null);
	       
	       JLabel lbl_Tab1 = new JLabel();
	       lbl_Tab1.setIcon(new ImageIcon(tbImage.getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
	       lbl_Tab1.setHorizontalAlignment(SwingConstants.CENTER);
	       lbl_Tab1.setBounds(6, 44, 105, 56);
	       panel.add(lbl_Tab1);
	       
	       JLabel lbl_Tab2 = new JLabel();
	       lbl_Tab2.setIcon(new ImageIcon(new ImageIcon("/Users/robin/Downloads/TableRestaurant.png").getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
	       lbl_Tab2.setHorizontalAlignment(SwingConstants.CENTER);
	       lbl_Tab2.setBounds(134, 58, 70, 29);
	       panel.add(lbl_Tab2);
	       
	       JLabel lbl_Tab3 = new JLabel();
	       lbl_Tab3.setIcon(new ImageIcon(new ImageIcon("/Users/robin/Downloads/TableRestaurant.png").getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
	       lbl_Tab3.setHorizontalAlignment(SwingConstants.CENTER);
	       lbl_Tab3.setBounds(6, 131, 70, 29);
	       panel.add(lbl_Tab3);
	       
	       JLabel lbl_Tab4 = new JLabel();
	       lbl_Tab4.setIcon(new ImageIcon(new ImageIcon("/Users/robin/Downloads/TableRestaurant.png").getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
	       lbl_Tab4.setHorizontalAlignment(SwingConstants.CENTER);
	       lbl_Tab4.setBounds(7, 215, 70, 29);
	       panel.add(lbl_Tab4);
	       
	       JLabel lbl_Tab5 = new JLabel();
	       lbl_Tab5.setIcon(new ImageIcon(new ImageIcon("/Users/robin/Downloads/TableRestaurant.png").getImage().getScaledInstance(75, 50, Image.SCALE_SMOOTH)));
	       lbl_Tab5.setHorizontalAlignment(SwingConstants.CENTER);
	       lbl_Tab5.setBounds(131, 133, 70, 29);
	       panel.add(lbl_Tab5);
	       
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setVisible(true);
	    }
}
