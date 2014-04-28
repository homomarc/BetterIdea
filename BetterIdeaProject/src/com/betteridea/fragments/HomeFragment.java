package com.betteridea.fragments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.betteridea.MainActivity;
import com.betteridea.R;
import com.betteridea.TopicActivity;
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.TopicItem;

public class HomeFragment extends Fragment {
	ListView topicList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		super.onCreateView(inflater, container, savedInstaceState);
		View view = inflater.inflate(R.layout.main_layout, container, false);

		MainActivity mainActivity = (MainActivity) getActivity();
		TopicItemAdapter adapter = mainActivity.getTopicItemAdapter();
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();

		Log.v("HomeFragment","Topics erstellt");
		
		try{
			JSONObject jsonObjString = null;
			try {
				jsonObjString = TopicRoulette.getNextTopic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TopicItem rouletteItem = new TopicItem(jsonObjString,true);
			topicItems.add(rouletteItem);
			TopicItem pseudoItem1 = new TopicItem("Pseudo Thema 1", "Beschreibung Beschreibung Beschreibung", "29.04.2014", false);
			TopicItem pseudoItem2 = new TopicItem("Pseudo Thema 2", "Beschreibung Beschreibung Beschreibung", "29.04.2014", false);
			TopicItem pseudoItem3 = new TopicItem("Pseudo Thema 3", "Beschreibung Beschreibung Beschreibung", "29.04.2014", false);
			TopicItem pseudoItem4 = new TopicItem("Pseudo Thema 4", "Beschreibung Beschreibung Beschreibung", "29.04.2014", false);
			topicItems.add(pseudoItem1);
			topicItems.add(pseudoItem2);
			topicItems.add(pseudoItem3);
			topicItems.add(pseudoItem4);
			
		}catch(JSONException ex){
			Log.v("test", "JSONException: " + ex.toString());
		}

		adapter = new TopicItemAdapter(getActivity(), topicItems);
		
		mainActivity.setTopicItemAdapter(adapter);
		topicList.setAdapter(adapter);
		
		topicList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				
				if(position == 0){
					TopicItemAdapter adapter = (TopicItemAdapter) parent.getAdapter();
					TopicItem item = adapter.getRouletteItem();
					Log.v("test", "RouletteItem geklickt");
					Intent intent = new Intent(getActivity(), TopicActivity.class);
					intent.putExtra("com.betteridea.models.TopicItem", item);
					startActivity(intent);
				}
			}
		});
	
		setHasOptionsMenu(true);
		return view;
	}
	
	 @Override
	  public void onResume() {
	     Log.e("DEBUG", "onResume of HomeFragment");
	     super.onResume();
	  }

	  @Override
	  public void onPause() {
	     Log.e("DEBUG", "OnPause of HomeFragment");
	     super.onPause();
	  }
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
	}

}