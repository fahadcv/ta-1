package com.andriod.tailorassist.conf;

public class AppConfig {
	public static class Types{
		 final public static int SHIRT = 0;
		    final public static int TROUSER = 1;    
		    final public static int OTHERS = 2;

	}
	static final String[] trouser = new String[]{"CM","Side Pocket","I Pleet","NPC", "Cross Pocket","II Pleet"};
	static final String[] shirt = new String[]{"Sm In","Sm Out","LBT"};
	
	public static String[] extraButtons(int type){
		switch (type){
		
		case Types.SHIRT:
			return shirt;
		case Types.TROUSER:
			return trouser;
		}
		return (null);
	}
}
