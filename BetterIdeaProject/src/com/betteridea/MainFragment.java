package com.betteridea;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betteridea.adapter.TopicItemAdapter;
import com.betteridea.models.TopicItem;

public class MainFragment extends Fragment {
	ListView topicList;
	TopicItemAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.main_layout, container, false);
		
		topicList = (ListView) view.findViewById(R.id.list_topic_feed);
		
		ArrayList<TopicItem> topicItems = new ArrayList<TopicItem>();
		topicItems.add(new TopicItem("�bungen f�r Handballtraining","Ich suche Handballtrainings�bung zum Aufw�rmen.","14:43, 01.04.2014",false));
		topicItems.add(new TopicItem("Ideenmanagementapp f�r die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App f�r die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche M�glichkeiten es f�r Ideenapps gibt.","18:12, 01.04.2014",false));
		topicItems.add(new TopicItem("Thema 3","Wir haben die Aufgabe bekommen, eine native App f�r die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche M�glichkeiten es f�r Ideenapps gibt.","18:12, 01.04.2014",false));
		
		TopicItem topicRouletteItem = new TopicItem("Ideenmanagementapp f�r die Mobile Vorlesung entwickeln","Wir haben die Aufgabe bekommen, eine native App f�r die Mobile Vorlesung zu entwickeln. Uns fehlen Ideen, welche M�glichkeiten es f�r Ideenapps gibt.","",true);

		adapter = new TopicItemAdapter(getActivity().getApplicationContext(), topicItems, topicRouletteItem);
		
		topicList.setAdapter(adapter);
		
		return view;
	}
}