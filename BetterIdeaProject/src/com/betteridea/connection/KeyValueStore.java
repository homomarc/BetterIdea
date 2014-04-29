package com.betteridea.connection;

/**
 * Author: 		Better Idea
 * Description:	KeyValueStore
 * 				Userdaten werden im KeyValueStore gespeichert, dadurch wird beispielsweise der Login beschleunigt.
 * 
 * TODOS:		keine
 * 
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueStore {
	//Daten in KeyValueStore übergeben
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
	//Daten aus KeyValueStore erhalten
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