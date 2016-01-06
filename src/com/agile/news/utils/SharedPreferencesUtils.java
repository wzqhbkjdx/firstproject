package com.agile.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
	
	private static SharedPreferences sp;
	private static String SP_NAME = "config";
	
	public static void saveString(Context context, String key, String value)
	{
		if(sp == null)
			sp = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
		sp.edit().putString(SP_NAME, value).commit();
	}
	
	public static String getString(Context context, String key)
	{
		if(sp == null)
			sp = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
		return sp.getString(key, "");
	}

}
