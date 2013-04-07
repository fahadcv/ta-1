package com.andriod.tailorassist.conf;

public class AppConfig {
	public static class Types{
		 final public static int SHIRT = 0;
		    final public static int TROUSER = 1;    
		    final public static int OTHERS = 2;

	}
	static final String[] trouser = new String[]{"CM","NPC","S Pkt", "C Pkt","2Pleet","1Pleet"};
	public static String[] extraButtons(int Type){
		return trouser;
	}
}
