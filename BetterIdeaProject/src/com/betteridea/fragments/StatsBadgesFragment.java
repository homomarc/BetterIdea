package com.betteridea.fragments;

/**
 * Author: 		Better Idea
 * Description:	StatsBadgesFragment dient der Anzeige der zukünftigen Badges/Ränge-Funktion
 * 
 * TODOS:		keine
 * 
 */

import com.betteridea.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatsBadgesFragment extends Fragment {
	
	// Konstruktor zur Orientation-Drehung notwendig
	public StatsBadgesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.stats_fragment_badges, container, false);
        return rootView;
    }
}