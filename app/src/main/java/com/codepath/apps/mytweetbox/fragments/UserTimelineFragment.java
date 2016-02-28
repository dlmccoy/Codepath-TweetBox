package com.codepath.apps.mytweetbox.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytweetbox.TwitterApplication;
import com.codepath.apps.mytweetbox.adapters.TweetsArrayAdapter;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserTimelineFragment extends TweetsListFragment {
    private static final String TAG = "UserTimelineFrag";

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets, getArguments().getString("screenName"));

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    void populateTimeline() {
        long maxId = 0;

        // If some tweets have already been loaded, choose the max id based on the last one.
        if (!tweets.isEmpty()) {
            maxId = tweets.get(tweets.size() - 1).getUid();
        }

        //Fetch the home timeline.
        String screenName = getArguments().getString("screenName");
        client.getUserTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, response.toString());

                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.d(TAG, errorResponse.toString());
                } else {
                    Log.d(TAG, "THE ERRORRESPONSE WAS NULL: " + throwable.toString());
                }
            }
        }, screenName, maxId);
    }
}
