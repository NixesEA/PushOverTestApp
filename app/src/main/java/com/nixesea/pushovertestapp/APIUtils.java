package com.nixesea.pushovertestapp;

public class APIUtils {

    private APIUtils() {}

    public static final String BASE_URL = "https://api.pushover.net/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
