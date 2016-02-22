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
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
