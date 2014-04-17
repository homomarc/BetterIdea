package com.betteridea.fragments;

import org.json.JSONException;

import com.betteridea.R;
import com.betteridea.connection.Service;
import com.betteridea.connection.ServiceExecuter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StatsOverallFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		
        View rootView = inflater.inflate(R.layout.stats_fragment_overall, container, false);
         
        new ServiceExecuter().execute("rankList");
        
        try {
        	String[] scoreName = new String[10];
        	String[] scorePoints = new String[10];
        	for(int i=1;i<4;i++){
        		scoreName[i] = Service.rankList.getJSONObject(i).getString("name");
        		scorePoints[i] = Service.rankList.getJSONObject(i).getString("score");
        	}
        	 // use your custom layout
    		ListView scoreList = (ListView) rootView.findViewById(R.id.listView);
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
    	        R.layout.stats_fragment_overall_lv, R.id.label, scoreName);
    	    scoreList.setAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        	   

		
        /*"[{'score'=650,'name'=Stephan Grabaum},{'score'=500,'name'=Axel Ludwig},
         * {'score'=450,'name'=Marc Boeckle},{'score'=350,'name'=Rene Kirchhof}]"
         */
        
        return rootView;
    }
}
