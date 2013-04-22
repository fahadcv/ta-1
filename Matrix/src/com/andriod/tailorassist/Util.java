package com.andriod.tailorassist;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.widget.EditText;

public class Util {
	static SimpleDateFormat DBdateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat viewDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static boolean isNumeric(String value){
		try{
			Long.parseLong(value);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	public static boolean isValidMobileNumber(String value){
		boolean flag = false;
		try{
			Long.parseLong(value);
			
		
				switch(value.length()){
				case 7:
					if(value.charAt(0)=='2')
						flag = true;
					break;
				case 10:
					flag = true;
					break;
				case 11:
					if(value.charAt(0)=='0')
						flag = true;
					break;
				case 12:
					if(value.startsWith("91"))
						flag = true;
					break;
				
				default :
					flag = false;
					break;
				}
			
		
		}catch(NumberFormatException e){
			flag = false;
		}
		return flag;
	} 
	public static boolean isEmpty(String data){
		return data == null || "".equals(data.trim());
	}
	public static boolean isModified(EditText editField, String oldValue){
		return !(Util.isEmpty(oldValue) && (editField == null || Util.isEmpty(editField.getText().toString()))
				|| (editField != null && editField.getText()!=null && editField.getText().toString().trim().equals(oldValue))) ;
	
	}
	public static String convertToDateview(String dbDate){
		
		try {
			return viewDateFormat.format(DBdateFormat.parse(dbDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
