package com.betteridea.adapter;

/**
 * Author: 		Better Idea
 * Description:	StatsAdapter ist für die Verwaltung/Positionsbestimmung
 * 				der einzelnen Fragment-Tabs in der StatsActivity erforderlich.
 * 
 * TODOS:		keine
 * 
 */

import com.betteridea.fragments.StatsBadgesFragment;
import com.betteridea.fragments.StatsFriendsFragment;
import com.betteridea.fragments.StatsOverallFragment;
import com.betteridea.fragments.StatsOverviewFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class StatsAdapter extends FragmentPagerAdapter {
	
    public StatsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
 
    @Override
    public Fragment getItem(int index) {
    	//Position der einzelnen Fragment-Tabs
        switch (index) {
        case 0:
            return new StatsOverviewFragment();
        case 1:
            return new StatsFriendsFragment();
        case 2:
            return new StatsOverallFragment();
        case 3:
            return new StatsBadgesFragment();
        }
        return null;
    }
 
    @Override
    public int getCount() {
        // Get itemcount entspricht Anzahl der Tabs
        return 4;
    }
}