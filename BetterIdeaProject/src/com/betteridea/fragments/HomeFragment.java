package com.betteridea.fragments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.betteridea.R;
import com.betteridea.R.id;
import com.betteridea.R.layout;
import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.logic.TopicRoulette;
import com.betteridea.models.TopicItem;

public class HomeFragment extends Fragment {
	ListView topicList;
	TopicItemAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.main_layout, container, false);
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
		topicItems.add(new TopicItem("Übungen für Handballtraining","Ich suche Handballtrainingsübung zum Aufwärmen.","14:43, 01.04.2014",false));
		topicItems.add(new TopicItem("Ideenmanagementapp für die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","18:12, 01.04.2014",false));
		topicItems.add(new TopicItem("Thema 3","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","18:12, 01.04.2014",false));
		
		TopicItem topicRouletteItem = null;
		try{
			TopicRoulette.loadTopicCache();
			topicRouletteItem = new TopicItem(TopicRoulette.getNextTopic());
		}catch(IOException ex){
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT);
		}catch(JSONException ex){
			Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT);
		}
//		TopicRoulette.getNextTopic() -> JSONObject
		
		//TopicItem topicRouletteItem = new TopicItem("Ideenmanagementapp für die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App für die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche Möglichkeiten es für Ideenapps gibt.","",true);

		if(topicRouletteItem != null)
			adapter = new TopicItemAdapter(getActivity().getApplicationContext(), topicItems, topicRouletteItem);
		
		topicList.setAdapter(adapter);
		
		return view;
	}
}