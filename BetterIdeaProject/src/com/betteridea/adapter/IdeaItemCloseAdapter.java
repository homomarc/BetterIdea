package com.betteridea.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.Item;
import com.betteridea.models.TopicItem;

public class IdeaItemCloseAdapter extends BaseAdapter{
	ArrayList<Item> items = new ArrayList<Item>();
	Context context;
	
	public IdeaItemCloseAdapter(Context context, ArrayList<IdeaItem> ideaItems, TopicItem topicItem){
		this.context = context;
		this.items.add(0,topicItem);
		this.items.addAll(ideaItems);
	}
	
	public IdeaItemCloseAdapter(Context context, JSONArray jsonObjects, TopicItem topicItem){
		this.context = context;
		items.add(0,topicItem);
		Log.v("test", "L�nge des JSONArray: " + jsonObjects.length());
		for(int i=0;i<jsonObjects.length();i++){
			try {
				JSONObject obj = jsonObjects.getJSONObject(i);
//				Log.v("test", "JSONObject: " + obj.toString());
				
				items.add(new IdeaItem(obj));
			} catch (JSONException e) {
				Log.v("test", "JSONException: " + e.toString());
			} catch (Exception ex){
				Log.v("test", "Exception: " + ex.toString());
			}
		}
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		if(position == 0){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.topic_overview_item_layout, null);
			
			TextView titleText = (TextView) view.findViewById(R.id.topic_overview_title);
			TextView descriptionText = (TextView) view.findViewById(R.id.topic_overview_description);
			TextView timestampText = (TextView) view.findViewById(R.id.topic_overview_timestamp);
			
			TopicItem topicItem = (TopicItem) items.get(position);
			
			titleText.setText(topicItem.getTitle());
			descriptionText.setText(topicItem.getDescription());
			timestampText.setText(topicItem.getTimestamp());
			
			return view;
			
		}else{
			IdeaItem item = (IdeaItem) items.get(position);
			
			Log.v("test", "Uncovered: " + item.isUncovered());
			
			if(item.isUncovered()){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.idea_item_layout, null);
				
				TextView userText = (TextView) view.findViewById(R.id.text_idea_user);
				TextView text = (TextView) view.findViewById(R.id.text_idea);
				TextView timeStamp = (TextView) view.findViewById(R.id.topic_feed_timestamp);
				
				text.setText(item.getText());
				//TODO: ID und kein Name!!!
				timeStamp.setText(item.getDate());
				userText.setText(item.getAuthorID());
				
				return view;
			}else{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.idea_item_covered_layout, null);
				
				return view;
			}
		}
	}
	
	public int getCountUncovered(){
		int count = 0;
		for(int i=1;i<items.size();i++){
			IdeaItem item = (IdeaItem) items.get(i);
			if(!item.isUncovered())
				count++;
		}
		return count;
	}

}