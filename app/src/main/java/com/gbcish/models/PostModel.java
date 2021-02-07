package com.gbcish.models;


import java.util.ArrayList;

public class PostModel {
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

    public String getPost_location() {
        return post_location;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    public String getPost_price() {
        return post_price;
    }

    public void setPost_price(String post_price) {
        this.post_price = post_price;
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

    public String post_title;
    public String post_category;
    public String post_description;
    public String post_location;
    public String post_price;
    public String post_pushkey;
    public String upload_time;
    public String user_id;
    public ArrayList<PostImages> imageUrl = null;
    PostModel(){};
      public PostModel(String post_title, String post_category, String post_description, String post_location, String post_price, ArrayList<PostImages> imageUrl,
                       String post_pushkey, String upload_time, String user_id
                     ){
        this.post_title = post_title;
        this.post_category = post_category;
        this.post_description = post_description;
        this.post_location = post_location;
        this.post_price = post_price;
        this.imageUrl = imageUrl;
        this.post_pushkey =post_pushkey;
        this.upload_time =upload_time;
        this.user_id = user_id;

    }

}
