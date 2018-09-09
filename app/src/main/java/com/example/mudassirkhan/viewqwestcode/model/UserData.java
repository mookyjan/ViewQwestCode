package com.example.mudassirkhan.viewqwestcode.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {

    @SerializedName("users")
    private List<User> userList;

    @SerializedName("has_more")
    private boolean hasMore;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> mPeopleList) {
        this.userList = mPeopleList;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
