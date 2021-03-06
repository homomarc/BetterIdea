package com.betteridea.fragments;

/**
 * Author: 		Better Idea
 * Description:	StatsOverviewFragment dient der Anzeige der Userdaten, wie
 * 				beispielsweise Score, Credits, die Anzahl der verfassten Topics
 * 				und Ideen.
 * 
 * TODOS:		keine
 * 
 */

import org.json.JSONException;

import com.betteridea.R;
import com.betteridea.connection.Service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class StatsOverviewFragment extends Fragment {
	
	// Konstruktor zur Orientation-Drehung notwendig
	public StatsOverviewFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.stats_fragment_overview, container, false);
        
        try {
        	TextView userName = (TextView)rootView.findViewById(R.id.textViewUserName); 
        	userName.setText(Service.userData.getString("userName"));
        	TextView score = (TextView)rootView.findViewById(R.id.textViewScore); 
        	score.setText(Service.userData.getString("score"));
        	TextView credit = (TextView)rootView.findViewById(R.id.textViewCredit); 
        	credit.setText(Service.userData.getString("credits"));
        	TextView topic = (TextView)rootView.findViewById(R.id.textViewTopics); 
        	topic.setText(Service.userData.getString("topicCount"));
        	TextView ideas = (TextView)rootView.findViewById(R.id.textViewIdeas); 
        	ideas.setText(Service.userData.getString("ideaCount"));
		} catch (JSONException e) {
			e.printStackTrace();
		}  
        return rootView;
    }
}