package com.codepath.apps.mytweetbox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mytweetbox.R;
import com.codepath.apps.mytweetbox.TwitterApplication;
import com.codepath.apps.mytweetbox.TwitterClient;
import com.codepath.apps.mytweetbox.adapters.TweetsArrayAdapter;
import com.codepath.apps.mytweetbox.adapters.TweetsPagerAdapter;
import com.codepath.apps.mytweetbox.fragments.ComposeFragment;
import com.codepath.apps.mytweetbox.fragments.HomeTimelineFragment;
import com.codepath.apps.mytweetbox.fragments.MentionsTimelineFragment;
import com.codepath.apps.mytweetbox.fragments.TweetsListFragment;
import com.codepath.apps.mytweetbox.listeners.EndlessScrollListener;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.ComposeTweetListener {
    private final static String TAG = "TimelineActivity";

    @Bind(R.id.fabCompose) FloatingActionButton fab;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) PagerSlidingTabStrip pagerTabs;

//    private HomeTimelineFragment fragmentTweetsList;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient();

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComposeDialog();
            }
        });

//        if (savedInstanceState == null) {
//            fragmentTweetsList = (HomeTimelineFragment) getSupportFragmentManager().findFragmentById(
//                    R.id.fragment_timeline);
//        }

        // Set the viewpager adapter
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        // Attach the viewpager to the tabs.
        pagerTabs.setViewPager(viewPager);


    }

    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeFragment composeFragment = ComposeFragment.newInstance("Compose a Tweet");
        composeFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onComposeTweet(String tweet) {
        Log.d(TAG, "Sending a tweet: " + tweet);
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, response.toString());
//                fragmentTweetsList.clearTweets();
//                fragmentTweetsList.populateTimeline();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    // Handle a press of the profile icon.
    public void onProfileView(MenuItem menuItem) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}
