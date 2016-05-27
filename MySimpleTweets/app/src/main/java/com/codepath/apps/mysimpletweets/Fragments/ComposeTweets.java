package com.codepath.apps.mysimpletweets.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;


/**
 * Created by gaetanejulmiste on 5/26/16.
 */
public class ComposeTweets extends DialogFragment   {

    private Button btnSave;
    private EditText etTweetBody;
    private TextView etCount;
    private ImageView ivClosed;
    private ImageView ivPhoto;
    private TextView etUserId;
    private User user;
    private String profileImageUrl;
    private Tweet tweet;
    private  TwitterClient client;

    private OnNewTweetCreatedListener tweetCreatedListener;
   // private OnCreatedListener tweetsToParse;
    public static final int MAX_COUNT = 140;

    public ComposeTweets() {
    }


    public static ComposeTweets newInstance() {
        ComposeTweets frag = new ComposeTweets();
        return frag;
    }

    /*public static ComposeTweets newInstance(String profileImageUrl) {
        // get the user profile image URL to display
        ComposeTweets composeFragment = new ComposeTweets();
        Bundle args = new Bundle();
        args.putString("profileImageUrl", profileImageUrl);
        composeFragment.setArguments(args);
        return composeFragment;
    }*/




    public static ComposeTweets newInstance(User user) {
        ComposeTweets frag = new ComposeTweets();
       // frag.setStyle(STYLE_NORMAL, R.style.Theme_CustomDialog);
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        //args.putParcelable("tweet", tweet);
        frag.setArguments(args);
        return frag;
    }



   /* public void passData(){
     User dataToSend = new User();
     Intent i = new Intent();
     i.putExtra("name", name);
     i.putExtra("profileImageUrl", profileImageUrl); // using the (String name, Parcelable value) overload!
     startActivity(i); // dataToSend is now passed to the new Activity
 }*/


  /*  public interface OnCreatedListener {
        public void composeTweets(String user);
    }*/


    public interface OnNewTweetCreatedListener {
        public void composeTweet(String tweetBody);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Rect displayRectangle = new Rect();
        getDialog().getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


        View layout = inflater.inflate(R.layout.compose_tweets, null);
        layout.setMinimumWidth((int)(displayRectangle.width() * 0.8f));
        layout.setMinimumHeight((int)(displayRectangle.height() * 0.8f));

        return layout;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            tweetCreatedListener = (OnNewTweetCreatedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // client = com.codepath.apps.mysimpletweets.TwitterApplication.getRestClient();

        //tweetsToParse.composeTweets(user.getName().toString());
       // etUserId =(TextView) view.findViewById(R.id.etUserId);
      //  etUserId.setText(user.getName().toString());
         //  ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
         //ivPhoto.setImageResource();
        // profileImageUrl = getArguments().getParcelable("profileImageUrl");
        //   Picasso.with(view.getContext()).load(user.getProfileImageUrl()).fit().centerCrop().into(ivPhoto);

        etCount =(TextView) view.findViewById(R.id.tvCount) ;

        btnSave = (Button) view.findViewById(R.id.btnTweet);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweetCreatedListener.composeTweet(etTweetBody.getText().toString());
                getDialog().dismiss();
            }
        });


         ivClosed = (ImageView) view.findViewById(R.id.ivClosed);
         ivClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });




        etTweetBody = (EditText) view.findViewById(R.id.etTweetBody);
        etTweetBody.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if (length == 0) {
                    etCount.setText("");
                    etCount.setTextColor(BLACK);
                    btnSave.setEnabled(false);



                } else if (length <= MAX_COUNT) {
                    etCount.setText(Integer.toString(length));
                    etCount.setTextColor(BLACK);
                    btnSave.setEnabled(true);

                } else {
                    etCount.setText(Integer.toString(MAX_COUNT - length));
                    etCount.setTextColor(RED);
                    btnSave.setEnabled(false);

                }


            }
        });
    }


}
