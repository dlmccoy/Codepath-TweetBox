package com.codepath.apps.mytweetbox.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.mytweetbox.fragments.HomeTimelineFragment;
import com.codepath.apps.mytweetbox.fragments.MentionsTimelineFragment;

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;
    private final String TAB_TITLES[] = {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }

    }

    // Return the tab title.
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    // Return the number of tabs.
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}