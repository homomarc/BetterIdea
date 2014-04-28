package com.betteridea.connection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueStore {
	
	/*
	 * Keys:
	 *  1. userData
	 *  2. 
	 */
	
	public static boolean store(Activity activity, String key, String value){
		try {
			SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(key, value);
			editor.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	public static String get(Activity activity, String key){
		try {
			SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
			String value = sharedPref.getString(key, "error");
			return value;
		} catch (Exception e) {
			return "false";
		}
	}
}
