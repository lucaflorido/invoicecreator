package it.progess.invoicecreator.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtilConverter {
	public static Date convertDateFromString(String date){
		Date newDate = null;
		try{
			if(date != null && date.equals("") == false){
				newDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return newDate;
	}
	public static String convertStringFromDate(Date date){
		String dateString = "";
		if (date != null){
			dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
		}
		return dateString;
	}
}
