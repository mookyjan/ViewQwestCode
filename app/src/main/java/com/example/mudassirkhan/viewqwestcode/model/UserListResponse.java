package com.example.mudassirkhan.viewqwestcode.model;

import com.google.gson.annotations.SerializedName;

public class UserListResponse {

    @SerializedName("data")
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
