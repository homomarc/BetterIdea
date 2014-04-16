package com.betteridea.fragments;

import com.betteridea.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatsOverallFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.stats_fragment_overall, container, false);
         
        /*"[{'score'=650,'name'=Stephan Grabaum},{'score'=500,'name'=Axel Ludwig},
         * {'score'=450,'name'=Marc Boeckle},{'score'=350,'name'=Rene Kirchhof}]"
         */
        
        return rootView;
    }
}
