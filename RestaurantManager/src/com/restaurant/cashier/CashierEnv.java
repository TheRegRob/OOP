package com.restaurant.cashier;

public class CashierEnv{
	public static void main(String args[]){
		final CashierImpl ci = new CashierImpl();
	       ci.setDimensions(800, 500);
	       ci.setupComponents();
	       ci.show();
	    }
}
