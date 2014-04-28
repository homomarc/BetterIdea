package com.betteridea.fragments;

/**
 * Author: 		Better Idea
 * Description:	StatsOverallFragment dient der Anzeige der aktuellen Ränge im Vergleich
 * 				zur globalen Rangliste. Der eigene Rang wird ebenfalls angezeigt.				
 * 
 * TODOS:		keine
 * 
 */

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
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

public class StatsOverallFragment extends Fragment{
	
	// Konstruktor zur Orientation-Drehung notwendig
	public StatsOverallFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.stats_fragment_overall, container, false);
        String userRank = null;
        String jsString;
        JSONArray jsArray = null;
        try {
        	jsString = new ServiceExecuter().execute("rankList").get();
			jsArray = new JSONArray(jsString);
			userRank = new ServiceExecuter().execute("getUserRank").get();
			
			System.out.println("TEST json Array for RankList: "+jsArray.toString());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
        // 
        try {
        	// Arrays für Ränge erstellen
        	String[] scoreListArray = new String[4];
        	String[] rankListArray = {"1","2","3","4"};
        	String[] scoreListArrayOwn = new String[1];
        	String[] rankListArrayOwn = new String[1];
        	
        	scoreListArrayOwn[0] = Service.userData.getString("userName") + " (" + Service.userData.getString("score") + ")";
        	rankListArrayOwn[0] = userRank;
        	
        	for(int i=0;i<4;i++){
				scoreListArray[i] = jsArray.getJSONObject(i).getString("name") + " (" + jsArray.getJSONObject(i).getString("score") + ")";
        	}
        	
        	//Rank 1.-4.
    	    ListView rankList = (ListView) rootView.findViewById(R.id.listViewRank);
    	    ArrayAdapter<String> adapterRank = new ArrayAdapter<String>(getActivity(),
    	        R.layout.stats_fragment_overall_lv_rank, R.id.label, rankListArray);
    	    rankList.setAdapter(adapterRank);
    	    
        	 // Scorenames
    		ListView scoreList = (ListView) rootView.findViewById(R.id.listViewName);
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
    	        R.layout.stats_fragment_overall_lv, R.id.label, scoreListArray);
    	    scoreList.setAdapter(adapter);
    	    
    	    // Own Scorename
    		ListView scoreListOwn = (ListView) rootView.findViewById(R.id.listViewOwnName);
    	    ArrayAdapter<String> adapterOwn = new ArrayAdapter<String>(getActivity(),
    	        R.layout.stats_fragment_overall_lv, R.id.label, scoreListArrayOwn);
    	    scoreListOwn.setAdapter(adapterOwn);
    	    
    	    //Rank 1.-4.
    	    ListView rankListOwn = (ListView) rootView.findViewById(R.id.listViewOwnRank);
    	    ArrayAdapter<String> adapterOwnRank = new ArrayAdapter<String>(getActivity(),
    	        R.layout.stats_fragment_overall_lv_rank, R.id.label, rankListArrayOwn);
    	    rankListOwn.setAdapter(adapterOwnRank);
    	    
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return rootView;
    }
}