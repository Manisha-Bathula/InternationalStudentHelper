package com.gbcish.models;


import java.io.Serializable;
import java.util.ArrayList;

public class PostModel implements Serializable {


    public String post_title;
    public String post_category;
    public String post_description;
    public String post_rent;
    public String post_city;
    public String post_street;
    public String post_province;
    public String post_postal_code;
    public String post_pushkey;
    public String upload_time;
    public String user_id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public  String key;
    public ArrayList<PostImages> imageUrl = null;
    PostModel(){};


    public PostModel(String post_title, String post_category, String post_description, String post_rent, String post_city, String post_street, String post_province, String post_postal_code, String post_pushkey, String upload_time, String user_id,String key, ArrayList<PostImages> imageUrl) {
        this.post_title = post_title;
        this.post_category = post_category;
        this.post_description = post_description;
        this.post_rent = post_rent;
        this.post_city = post_city;
        this.post_street = post_street;
        this.post_province = post_province;
        this.post_postal_code = post_postal_code;
        this.post_pushkey = post_pushkey;
        this.upload_time = upload_time;
        this.user_id = user_id;
        this.key=key;
        this.imageUrl = imageUrl;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_category() {
        return post_category;
    }

    public void setPost_category(String post_category) {
        this.post_category = post_category;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public String getPost_rent() {
        return post_rent;
    }

    public void setPost_rent(String post_rent) {
        this.post_rent = post_rent;
    }

    public String getPost_city() {
        return post_city;
    }

    public void setPost_city(String post_city) {
        this.post_city = post_city;
    }

    public String getPost_street() {
        return post_street;
    }

    public void setPost_street(String post_street) {
        this.post_street = post_street;
    }

    public String getPost_province() {
        return post_province;
    }

    public void setPost_province(String post_province) {
        this.post_province = post_province;
    }

    public String getPost_postal_code() {
        return post_postal_code;
    }

    public void setPost_postal_code(String post_postal_code) {
        this.post_postal_code = post_postal_code;
    }

    public String getPost_pushkey() {
        return post_pushkey;
    }

    public void setPost_pushkey(String post_pushkey) {
        this.post_pushkey = post_pushkey;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<PostImages> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<PostImages> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullAddress(){

        return getPost_street()+","+getPost_city()+","+getPost_province()+","+getPost_postal_code();
    }
}
