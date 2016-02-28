package com.codepath.apps.mytweetbox.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codepath.apps.mytweetbox.TwitterApplication;
import com.codepath.apps.mytweetbox.TwitterClient;
import com.codepath.apps.mytweetbox.activities.TweetDetailActivity;
import com.codepath.apps.mytweetbox.listeners.EndlessScrollListener;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

/**
 * Created by dmccoy on 2/23/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private static final String TAG = "MentionsTimelineFrag";

    public void populateTimeline() {

        long maxId = 0;

        // If some tweets have already been loaded, choose the max id based on the last one.
        if (!tweets.isEmpty()) {
            maxId = tweets.get(tweets.size() - 1).getUid();
        }

        Log.d(TAG, "Max id is: " + Long.toString(maxId));

        //Fetch the home timeline.
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
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
}
