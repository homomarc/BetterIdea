package com.betteridea.adapter;

/**
 * Author: 		Better Idea
 * Description:	IdeaItemAdapter
 * 
 * TODOS:		keine
 * 
 */

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betteridea.R;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.Item;
import com.betteridea.models.TopicItem;

public class IdeaItemAdapter extends BaseAdapter{
	private ArrayList<Item> items = new ArrayList<Item>();
	private Context context;
	private int lastPosition = -1;
	
	public IdeaItemAdapter(Context context, ArrayList<IdeaItem> ideaItems, TopicItem topicItem){
		this.context = context;
		this.items.add(0,topicItem);
		this.items.addAll(ideaItems);
	}
	
	public IdeaItemAdapter(Context context, JSONArray jsonObjects, TopicItem topicItem){
		this.context = context;
		items.add(0,topicItem);
		
		// Neue IdeaItems ausJSONObjectarray erstellen
		for(int i=0;i<jsonObjects.length();i++){
			try {
				JSONObject obj = jsonObjects.getJSONObject(i);
				items.add(new IdeaItem(obj));
			} catch (JSONException e) {
				Log.v("IdeaItemAdapter", "JSONException: " + e.toString());
			} catch (Exception ex){
				Log.v("IdeaItemAdapter", "Exception: " + ex.toString());
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
		// Erste Position ist als Topic gekennzeichnet --> gelber Hintergrund
		if(position == 0){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.topic_overview_item_layout, null);
			
			// Viewobjekte zuordnen
			TextView titleText = (TextView) view.findViewById(R.id.topic_overview_title);
			TextView descriptionText = (TextView) view.findViewById(R.id.topic_overview_description);
			TextView timestampText = (TextView) view.findViewById(R.id.topic_overview_timestamp);
			
			TopicItem topicItem = (TopicItem) items.get(position);
			
			// Viewobjekte mit Text füllen
			titleText.setText(topicItem.getTitle());
			descriptionText.setText(topicItem.getDescription());
			timestampText.setText(topicItem.getTimestamp());
			
			// Animationsparameter
			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    view.startAnimation(animation);
		    lastPosition = position;
			
			return view;
			
		//Ideen zum Topic
		}else{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.idea_item_layout, null);
			
			// Viewobjekte zuordnen
			TextView userText = (TextView) view.findViewById(R.id.text_idea_user);
			TextView text = (TextView) view.findViewById(R.id.text_idea);
			TextView timeStamp = (TextView) view.findViewById(R.id.topic_feed_timestamp);
			
			IdeaItem item = (IdeaItem) items.get(position);
			
			// Viewobjekte mit Text füllen
			text.setText(item.getText());
			timeStamp.setText(item.getDate());
			userText.setText(item.getAuthorID());
			
			// Animationsparameter
			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		    view.startAnimation(animation);
		    lastPosition = position;
			
			return view;
		}
	}
	
	public void addIdea(IdeaItem idea){
		items.add(idea);
	}
}