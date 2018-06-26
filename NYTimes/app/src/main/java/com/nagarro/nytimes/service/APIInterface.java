package com.nagarro.nytimes.service;

import com.nagarro.nytimes.domain.model.NewFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("7.json")
    Call<NewFeed> getNewsFeed(@Query("api-key") String apiKey);
}
