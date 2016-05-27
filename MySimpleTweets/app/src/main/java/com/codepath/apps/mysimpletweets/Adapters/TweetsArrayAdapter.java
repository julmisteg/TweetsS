package com.codepath.apps.mysimpletweets.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.codepath.apps.mysimpletweets.R.layout.item_tweet;

/**
 * Created by gaetanejulmiste on 5/23/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    private static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvUserName;
        TextView tvTimestamp;
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        //super(context,android.R.layout.simple_list_item_1, tweets);
        super(context,0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        Tweet tweet = getItem(position);
        if(convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(item_tweet,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTimestamp =(TextView)convertView.findViewById(R.id.tvTimestamp);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

       /*

       ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
         tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
         Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        */

        viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        viewHolder.tvTimestamp.setText(tweet.getCreatedAtRelativeTimeAgo());
        //Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
        return  convertView;
    }
}
