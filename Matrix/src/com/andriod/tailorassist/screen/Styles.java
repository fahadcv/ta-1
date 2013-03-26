package com.andriod.tailorassist.screen;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.andriod.tailorassist.R;

public class Styles {
	public static void setActionBarStyle(ActionBar aBar, Resources res){
		BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(res, R.drawable.bg_topbar));
		aBar.setBackgroundDrawable(background);
	}
}
