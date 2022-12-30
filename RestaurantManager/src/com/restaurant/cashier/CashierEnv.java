package com.restaurant.cashier;

public class CashierEnv{
	public final static CashierImpl ci = new CashierImpl();	
	public static void main(String args[]){
			
			ci.setAppIcon();   
			ci.setDimensions(950, 500);
			ci.setupComponents();
			ci.show();
	    }
}
