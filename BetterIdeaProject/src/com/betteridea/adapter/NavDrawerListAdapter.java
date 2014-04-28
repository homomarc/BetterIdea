package com.betteridea.adapter;

/**
 * Author: 		Better Idea
 * Description:	NavigationDrawerListAdapter für das setzen der Navigation
 * 				erforderlich mit einzelnen NavDrawerItems, die einen Text
 * 				sowie eine Ikone enthalten.
 * 
 * TODOS:		keine
 * 
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}
	
	@Override
	public int getCount(){
		return navDrawerItems.size();
	}
	
	@Override
	public Object getItem(int position){
		return navDrawerItems.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.navigation_list_item, null);
		}
		// Viewobjekte zuordnen
		TextView titleView = (TextView) convertView.findViewById(R.id.text_navigation_entry);
		ImageView iconView = (ImageView) convertView.findViewById(R.id.image_icon);
		// Viewobjekte mit Text füllen
		titleView.setText(navDrawerItems.get(position).getTitle()); 
		iconView.setImageResource(navDrawerItems.get(position).getIcon());
		
		return convertView;
	}
}