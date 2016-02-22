package com.codepath.apps.mytweetbox.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mytweetbox.R;
import com.codepath.apps.mytweetbox.models.Tweet;
import com.codepath.apps.mytweetbox.models.User;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private static final String TAG = "TweetsArrayAdapter";

    public class ViewHolder {
        @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
        @Bind(R.id.tvUsername) TextView tvUsername;
        @Bind(R.id.tvName) TextView tvName;
        @Bind(R.id.tvBody) TextView tvBody;
        @Bind(R.id.tvCreated) TextView tvCreated;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.item_tweet, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        Log.d(TAG, tweet.getUser().toString());
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);


        // Populate the data into the template view using the data object
        User user = tweet.getUser();
        viewHolder.tvUsername.setText("@" + user.getScreenName());
        viewHolder.tvName.setText(user.getName());

        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvCreated.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);

        // Return the completed view to render on screen
        return convertView;

    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
