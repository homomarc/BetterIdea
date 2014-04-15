package com.betteridea.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betteridea.R;
import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.models.IdeaItem;

public class TopicFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.topic_overview_layout, container, false);
		
		setHasOptionsMenu(true);
		
		ListView topicList = (ListView) view.findViewById(R.id.list_topic_overview);
		
		ArrayList<IdeaItem> ideaItems = new ArrayList<IdeaItem>();
		ideaItems.add(new IdeaItem("Ideenmanagementapp, die die Kreativität fördert. Gut strukturiert mit verschiedenen Lesezeichenoptionen",
									"MaSteRe1999", 
									"Hinzugefügt am 03.03.2014. 14:43"));
		ideaItems.add(new IdeaItem("Ideenmanagementapp, die die Kreativität fördert. Gut strukturiert mit verschiedenen Lesezeichenoptionen",
									"MaSteRe1999", 
									"Hinzugefügt am 03.03.2014. 14:43"));
		
		IdeaItemAdapter adapter = new IdeaItemAdapter(getActivity(), ideaItems);
		
		topicList.setAdapter(adapter);
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.topic, menu);
	}
}
