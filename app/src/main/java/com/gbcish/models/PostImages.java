package com.gbcish.models;

import java.io.Serializable;

public class PostImages implements Serializable {

    public String imageUrl = null;
    public PostImages(){};

    public PostImages(String imageUrl) {
        this.imageUrl =imageUrl;
    }
}
