package com.example.mudassirkhan.viewqwestcode.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("name")
    public String name;

    @SerializedName("image")
    public String image;

    @SerializedName("items")
    public List<String> items;
}
