package com.betteridea.models;

/**
 * Author: 		Better Idea
 * Description:	Modelklasse f�r die Datenhaltung der einzelnen NavigationDrawerItems.
 * 				Enth�lt die einzelnen Texte des Navigationsmen�s und die zugeh�rigen Ikonen.
 * 
 * TODOS:		keine
 * 
 */

public class NavDrawerItem {
	private String title;
	private int icon;
	
	public NavDrawerItem(){}
	
	public NavDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getIcon(){
		return icon;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
}	
