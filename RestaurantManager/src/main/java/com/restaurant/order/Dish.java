package com.restaurant.order;

import javax.swing.ImageIcon;

public class Dish {
	public static enum dishStates{
		ds_Ordered,
		ds_Preparing,
		ds_Sent
	}
	
	public int 			numberID;
	public String		name;
	public int			maxSelection;
	public ImageIcon	picture;
	public dishStates	state;
	public double		price;
}
