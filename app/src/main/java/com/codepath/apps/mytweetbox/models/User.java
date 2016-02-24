package com.codepath.apps.mytweetbox.models;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    String name;
    long uid;
    String screenName;
    String profileImageUrl;
    String tagline;
    int followersCount;
    int followingCount;

    public int getFollowingCount() { return followingCount; }

    public String getTagline() { return tagline; }

    public int getFollowersCount() { return followersCount; }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public User() {}

    public static User fromJSON(JSONObject json) {
        User user =  new User();

        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.tagline = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.followingCount = json.getInt("friends_count");
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
