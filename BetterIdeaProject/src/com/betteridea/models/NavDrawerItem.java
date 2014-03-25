package com.betteridea.models;

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
