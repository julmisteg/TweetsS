package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaetanejulmiste on 5/23/16.
 */
@Table(name = "User")
public class User  extends Model implements Parcelable{
    @Column(name="name")
    private String name;
    @Column(name = "uid")
            //, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;
    @Column(name="profileImageUrl")
    private String profileImageUrl;


    public User() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    private String screenName;


    public String getName() {

        return name;
    }

    public long getUid() {

        return uid;
    }

    public String getScreenName() {

        return screenName;
    }

    public String getProfileImageUrl() {

        return profileImageUrl;
    }

    public static User fromJSON (JSONObject jsonObject){
        User user = new User();
        try {
            user.name =jsonObject.getString("name");
            user.uid =jsonObject.getLong("id");
            user.screenName =jsonObject.getString("screen_name");
            user.profileImageUrl =jsonObject.getString("profile_image_url");
            user.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }





        return user;
    }



    public static User getById(String userId) {
        return new Select()
                .from(User.class)
                .where("id = ?", userId)
                .executeSingle();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.profileImageUrl);

    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.profileImageUrl = in.readString();

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
