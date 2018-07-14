package com.reweyou.master.kavyanjali.data.model;

import com.google.gson.annotations.SerializedName;

public class PoemModel {

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "PoemModel{" +
                        "image = '" + image + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}