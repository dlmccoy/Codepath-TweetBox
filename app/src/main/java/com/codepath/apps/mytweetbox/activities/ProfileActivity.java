package com.codepath.apps.mytweetbox.activities;

import android.os.Binder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mytweetbox.R;
import com.codepath.apps.mytweetbox.TwitterApplication;
import com.codepath.apps.mytweetbox.TwitterClient;
import com.codepath.apps.mytweetbox.fragments.UserTimelineFragment;
import com.codepath.apps.mytweetbox.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private TwitterClient client;
    private User user;

    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
    @Bind(R.id.tvTagline) TextView tvTagline;
    @Bind(R.id.tvFollowing) TextView tvFollowing;
    @Bind(R.id.tvFollowers) TextView tvFollowers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        client = TwitterApplication.getRestClient();

        // Get the screenName extra from the intent.
        String screenName = getIntent().getStringExtra("screenName");

        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);

                populateUserProfile(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, responseString);
            }
        }, screenName);



        if (savedInstanceState == null) {
            // Create the UserTimelineFragment.
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            // Display the user fragment within this activity dynamically.
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserTimeline, userTimelineFragment);
            ft.commit();
        }
    }

    public void populateUserProfile(User user) {
        getSupportActionBar().setTitle("@" + user.getScreenName());
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());

        // Update the followers and following counts.
        tvFollowers.setText(Integer.toString(user.getFollowersCount()));
        tvFollowing.setText(Integer.toString(user.getFollowingCount()));

        // Load the profile image.
        Glide.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

}
