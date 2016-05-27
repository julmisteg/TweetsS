package com.codepath.apps.mysimpletweets.models;

import android.text.format.DateUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gaetanejulmiste on 5/23/16.
 */

@Table(name = "Tweet")
public class Tweet  extends Model {
    @Column(name="body")
    private String body;
    @Column(name = "uid") //, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;
    @Column(name="user")
    private User user;
    @Column(name="createdAt")
    private String createdAt;


    public Tweet() {
        super();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



    public User getUser() {
        return user;
    }



    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }



    public static Tweet fromJSON (JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body =jsonObject.getString("text");
            tweet.uid =jsonObject.getLong("id");
            tweet.createdAt =jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i =0 ; i < jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                User user = tweet.user;

                if(tweet != null){

                    tweet.user = user;
                    user.save();
                    tweet.save();
                    tweets.add(tweet);


                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }



    public String getCreatedAtRelativeTimeAgo() {
        return getRelativeTimeAgo(createdAt);
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
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






    public static List<Tweet> getAll(int type) {

            return new Select()
                    .from(Tweet.class)
                    .orderBy("timestamp DESC")
                    .execute();

    }

    public static List<Tweet> getUserTweets(String userId) {
        return new Select()
                .from(Tweet.class)
                .where("User = ?", userId)
                .orderBy("timestamp DESC")
                .execute();
    }

    public static void deleteAll() {
        new Delete()
                .from(Tweet.class)
                .execute();
    }

}
