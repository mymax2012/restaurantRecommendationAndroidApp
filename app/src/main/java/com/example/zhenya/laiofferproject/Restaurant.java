package com.example.zhenya.laiofferproject;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenya on 10/26/2016.
 */

public class Restaurant {
    private String name;
    private String address;
    private String type;

    private double lat;
    private double lng;
    private Bitmap thumbnail;
    private Bitmap rating;
    private double stars;
    private List<String>categories;

    public Restaurant(){
        name = "";
        address = "";
        type = "";
        lat = 0.0;
        lng = 0.0;
        stars = 0.0;
        categories = new ArrayList<String>();
    }

    public Restaurant(String name, String address, String type, double lat, double lng, Bitmap thumbnail,Bitmap rating) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.thumbnail = thumbnail;
        this.rating = rating;
        categories = new ArrayList<String>();
    }


    /**
     * Getters for private attributes of Restaurant class.
     */
    public String getName() { return this.name; }


    public String getAddress() { return this.address; }


    public String getType() { return this.type; }

    public double getLat() { return lat; }


    public double getLng() { return lng; }

    public Bitmap getThumbnail() { return thumbnail; }

    public Bitmap getRating(){return rating;}

    public double getStars(){return stars;}

    public List<String> getCategories(){ return categories; }



    public void setName(String n){
        this.name = n;
    }
    public void setAddress(String a){
        this.address = a;
    }
    public void setType(String t){
        this.type =t;
    }
    public void setLat(double l){
        this.lat= l;
    }
    public void setLng(double l){
        this.lng= l;
    }
    public void setThumbnail(Bitmap t){
        this.thumbnail = t;
    }
    public void setRating(Bitmap r){
        this.rating = r;

    }
    public void setStars(double star){
        this.stars = star;
    }
    public void setCategories(List<String> cat){
        this.categories= cat;
    }
}
