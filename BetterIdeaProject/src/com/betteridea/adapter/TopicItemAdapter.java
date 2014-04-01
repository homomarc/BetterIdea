package com.betteridea.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.TopicItem;

public class TopicItemAdapter extends BaseAdapter {
	private ArrayList<TopicItem> topicItems;
	private Context context;
	
	public TopicItemAdapter(Context context, ArrayList<TopicItem> topicItems){
		this.context = context;
		this.topicItems = topicItems;
	}
	
	@Override
	public int getCount() {
		return topicItems.size();
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
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
		
		return view;
	}

}
