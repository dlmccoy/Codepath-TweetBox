package com.codepath.apps.mytweetbox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mytweetbox.R;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.codepath.apps.mytweetbox.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity {
    private Tweet mTweet;
    @Bind(R.id.tvBody) TextView tvBody;
    @Bind(R.id.tvUsername) TextView tvUsername;
    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.ivProfileImage) ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        mTweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));

        tvBody.setText(mTweet.getBody());
        User user = mTweet.getUser();
        tvUsername.setText(user.getScreenName());
        tvName.setText(user.getName());

        Glide.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);

    }

}
