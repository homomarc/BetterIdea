package com.betteridea.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betteridea.R;
import com.betteridea.models.TopicItem;

public class TopicCloseFragment extends Fragment{
 private TopicItem item;
  
 public TopicCloseFragment(TopicItem item){
 	this.item = item;
 }
  
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
 	View view = inflater.inflate(R.layout.topic_close_overview_layout, container, false);
 	 
 	ListView ideaList = (ListView) view.findViewById(R.id.list_topic_overview);
 	 
 	return view;
 }
  
 @Override
 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
 	inflater.inflate(R.menu.topic, menu);
 }
}