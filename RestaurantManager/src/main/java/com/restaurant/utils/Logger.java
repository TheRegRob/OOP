package com.restaurant.utils;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.restaurant.cashier.CashierApplication;

public class Logger {
	
	public enum TypeLog {
		tl_Info,
		tl_CustomerEvent,
		tl_KitchenEvent,
		tl_Debug,
		tl_Error
	}

	public static void Log(TypeLog t_Log, String txt_message) {
		switch (t_Log) {
		case tl_Info:
			printText(txt_message, Color.BLACK);
			break;
		case tl_CustomerEvent:
			printText(txt_message, Color.GREEN);
			break;
		case tl_KitchenEvent:
			printText(txt_message, Color.BLUE);
			break;
		case tl_Debug:
			printText(txt_message, Color.ORANGE);
			break;
		case tl_Error:
			printText(txt_message, Color.RED);
			break;
		default:
			break;
		
		}
	}
	
	private static void printText(String txt_message, Color color) {
		StyledDocument doc = CashierApplication.getCashierInstance().getTbLog().getStyledDocument();
		Style style = CashierApplication.getCashierInstance().getTbLog().addStyle("Color Style", null);
		StyleConstants.setForeground(style, color);
		try {
			doc.insertString(doc.getLength(), getTime() + " - " + txt_message + "\n", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private static String getTime() {
		String str_Date;
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		str_Date = dateFormat.format(date);
		return str_Date;
	}

}
