package com.betteridea.fragments;

import org.json.JSONException;

import com.betteridea.R;
import com.betteridea.connection.Services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class StatsOverviewFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.stats_fragment_overview, container, false);
        
        try {
        	TextView userName = (TextView)rootView.findViewById(R.id.textViewUserName); 
        	userName.setText(Services.userData.getString("userName"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return rootView;
    }
}
