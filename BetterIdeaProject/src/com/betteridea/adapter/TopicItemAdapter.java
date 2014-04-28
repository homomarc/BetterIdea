package com.betteridea.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.TopicItem;

public class TopicItemAdapter extends BaseAdapter {
	// 1. item is rouletteItem
	private ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
	private Context context;
	private boolean ownTopics = false;
	private int lastPosition = -1;
	
	public TopicItemAdapter(Context context, ArrayList<TopicItem> topicItems){
		this.context = context;
		this.topicItems = topicItems;
		this.ownTopics = true;
	}
	
	public TopicItemAdapter(Context context, ArrayList<TopicItem> topicItems, TopicItem rouletteItem){
		this.context = context;
		this.topicItems = topicItems;
		if(topicItems.size()>0){
			if(!topicItems.get(0).isRouletteItem())
				topicItems.add(0, rouletteItem);
		}else{
			topicItems.add(rouletteItem);
		}
	}
	
	public TopicItemAdapter(Context context, JSONArray jsonArray){
		this.context = context;
		for(int i=0;i<jsonArray.length();i++){
			try {
				topicItems.add(new TopicItem(jsonArray.getJSONObject(i),false));
			} catch (JSONException e) {
				Log.v("test", e.toString());
			}
		}
	}
	
	@Override
	public int getCount() {
		return topicItems.size();
	}
	
	public TopicItem getRouletteItem(){
		return topicItems.get(0).isRouletteItem() ? topicItems.get(0) : null;
	}
	
	public void setOwnTopics(boolean ownTopics){
		this.ownTopics = ownTopics;
	}

	@Override
	public Object getItem(int position) {
		return topicItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		// Instantiate View

		if(position == 0 && topicItems.get(position).isRouletteItem()){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.topic_roulette_layout, null);

			TextView titleView = (TextView) view.findViewById(R.id.topic_roulette_title);
			TextView descriptionView = (TextView) view.findViewById(R.id.topic_roulette_description);
			TextView timestampView = (TextView) view.findViewById(R.id.topic_roulette_timestamp);
			
			titleView.setText(topicItems.get(position).getTitle());
			descriptionView.setText(topicItems.get(position).getDescription());
			timestampView.setText(topicItems.get(position).getTimestamp());
			
		}else{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			//Falls eigene Topics --> closable layout wählen
			if(ownTopics==true){
				view = inflater.inflate(R.layout.topic_own_item_layout, null);
			}else{
				view = inflater.inflate(R.layout.topic_item_layout, null);
			}
			// Get Resources
			TextView titleView = (TextView) view.findViewById(R.id.topic_feed_title);
			TextView descriptionView = (TextView) view.findViewById(R.id.topic_feed_description);
			TextView timestampView = (TextView) view.findViewById(R.id.topic_feed_timestamp);
			
			// set contents to resources
			titleView.setText(topicItems.get(position).getTitle());
			descriptionView.setText(topicItems.get(position).getDescription());
			timestampView.setText(topicItems.get(position).getTimestamp());
			
			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    view.startAnimation(animation);
		    lastPosition = position;
		}
		
		return view;
	}
	
	public void setRouletteItem(TopicItem rouletteItem){
		if(topicItems.get(0).isRouletteItem())
			topicItems.set(0,rouletteItem);
		else
			topicItems.add(0,rouletteItem);
	}	

}