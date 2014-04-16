package com.betteridea.fragments;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betteridea.R;
import com.betteridea.adapter.IdeaItemAdapter;
import com.betteridea.connection.Services;
import com.betteridea.models.IdeaItem;
import com.betteridea.models.TopicItem;

public class TopicFragment extends Fragment{
	private TopicItem rouletteItem;
	
	public TopicFragment(TopicItem rouletteItem){
		this.rouletteItem = rouletteItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
		View view = inflater.inflate(R.layout.topic_overview_layout, container, false);
		
		ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
		
		setHasOptionsMenu(true);
		
		try{
			Services.showTopic(rouletteItem.getTopicID());
			IdeaItemAdapter adapter = new IdeaItemAdapter(getActivity(), Services.topicContent, rouletteItem);
			
			ideaList.setAdapter(adapter);
		}catch(IOException ex){
			Log.v("test", "IOException: " + ex.toString());
		}
		
		//bArrayList<IdeaItem> ideaItems = new ArrayList<IdeaItem>();
		
//		ideaItems.add(new IdeaItem("Ideenmanagementapp, die die Kreativität fördert. Gut strukturiert mit verschiedenen Lesezeichenoptionen",
//									"MaSteRe1999", 
//									"Hinzugefügt am 03.03.2014. 14:43"));
//		ideaItems.add(new IdeaItem("Ideenmanagementapp, die die Kreativität fördert. Gut strukturiert mit verschiedenen Lesezeichenoptionen",
//									"MaSteRe1999", 
//									"Hinzugefügt am 03.03.2014. 14:43"));
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		inflater.inflate(R.menu.topic, menu);
	}
}
