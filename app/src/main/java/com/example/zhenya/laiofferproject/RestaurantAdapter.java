package com.example.zhenya.laiofferproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhenya on 10/26/2016.
 */


public class RestaurantAdapter extends BaseAdapter {


    Context context;
    List<Restaurant> restaurantData;


    public RestaurantAdapter(Context context, List<Restaurant> restaurantData) {
        this.context = context;
        this.restaurantData = restaurantData;
    }


    @Override
    public int getCount() {
        return restaurantData.size();
    }


    @Override
    public Restaurant getItem(int position) {
        return restaurantData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_restaurant_list_item,
                    parent, false);
        }

        TextView restaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
        TextView restaurantAddress = (TextView) convertView.findViewById(
                R.id.restaurant_address);
        TextView restaurantType = (TextView) convertView.findViewById(R.id.restaurant_type);
        ImageView restaurantThumbnail = (ImageView) convertView.findViewById(
                R.id.restaurant_thumbnail);

        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.restaurant_rating);
        //ratingBar.setIsIndicator(true);


        Restaurant r = restaurantData.get(position);
        restaurantName.setText(r.getName());
        restaurantAddress.setText(r.getAddress());
        restaurantType.setText(r.getType());
        restaurantThumbnail.setImageBitmap(r.getThumbnail());
        ratingBar.setRating((float)r.getStars());


        return convertView;

        /*TextView restaurantName = (TextView) convertView.findViewById(
                R.id.restaurant_name);

        TextView restaurantAddress = (TextView) convertView.findViewById(
                R.id.restaurant_address);

        TextView restaurantType = (TextView) convertView.findViewById(
                R.id.restaurant_type);

        ImageView restaurantRating = (ImageView) convertView.findViewById(
                R.id.restaurant_rating);

        ImageView restaurantThumbnail = (ImageView) convertView.findViewById(
                R.id.restaurant_thumbnail);


        Restaurant r = restaurantData.get(position);
        restaurantName.setText(r.getName());
        restaurantAddress.setText(r.getAddress());
        restaurantType.setText(r.getType());
        restaurantThumbnail.setImageBitmap(r.getThumbnail());
        restaurantRating.setImageBitmap(r.getRating());

        return convertView;*/
    }
}
