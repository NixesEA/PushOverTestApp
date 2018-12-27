package com.nixesea.pushovertestapp.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("userTokenConst")
    @Expose
    private String userToken;

    @SerializedName("title")
    @Expose
    private Integer title;

    @SerializedName("message")
    @Expose
    private String message;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
