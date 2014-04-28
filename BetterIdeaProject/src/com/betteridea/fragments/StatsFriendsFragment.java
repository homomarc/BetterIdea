package com.betteridea.fragments;

/**
 * Author: 		Better Idea
 * Description:	StatsFriendsFragment dient der Anzeige der 
 * 				zukünftigen Ränge im Vergleich zu seinen Freunden.
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

public class StatsFriendsFragment extends Fragment {
	
	// Konstruktor zur Orientation-Drehung notwendig
	public StatsFriendsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.stats_fragment_friends, container, false);
        return rootView;
    }
}
