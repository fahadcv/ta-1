package com.andriod.tailorassist;

public class Util {
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
}
