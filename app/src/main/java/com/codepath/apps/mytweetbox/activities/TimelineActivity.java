package com.codepath.apps.mytweetbox.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.codepath.apps.mytweetbox.R;
import com.codepath.apps.mytweetbox.TwitterApplication;
import com.codepath.apps.mytweetbox.TwitterClient;
import com.codepath.apps.mytweetbox.adapters.TweetsArrayAdapter;
import com.codepath.apps.mytweetbox.listeners.EndlessScrollListener;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements ComposeFragment.ComposeTweetListener {
    private final static String TAG = "TimelineActivity";
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private FloatingActionButton fab;

    private TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fabCompose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComposeDialog();
            }
        });


        client = TwitterApplication.getRestClient();

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
                return true;
            }
        });

        populateTimeline();
    }

    private void populateTimeline() {

        long maxId = 0;

        // If some tweets have already been loaded, choose the max id based on the last one.
        if (!aTweets.isEmpty()) {
            maxId = tweets.get(tweets.size() - 1).getUid();
        }

        //Fetch the home timeline.
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, response.toString());

                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }
        }, maxId);
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
                aTweets.clear();
                populateTimeline();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }
        });
    }
}
