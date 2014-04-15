package com.betteridea.adapter;

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
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new StatsOverviewFragment();
        case 1:
            // Games fragment activity
            return new StatsFriendsFragment();
        case 2:
            // Movies fragment activity
            return new StatsOverallFragment();
        case 3:
            // Movies fragment activity
            return new StatsBadgesFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}