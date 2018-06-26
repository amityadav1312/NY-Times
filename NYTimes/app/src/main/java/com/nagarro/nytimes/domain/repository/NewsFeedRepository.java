package com.nagarro.nytimes.domain.repository;

import android.util.Log;

import com.nagarro.nytimes.domain.interactors.ResponseListener;
import com.nagarro.nytimes.domain.model.NewFeed;
import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.service.APIInterface;
import com.nagarro.nytimes.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedRepository implements FeedRepository {

    private ResponseListener responseListener;
    private String paramApiKey;

    public NewsFeedRepository(String apiKey) {
        paramApiKey = apiKey;
    }

    @Override
    public void getNewsFeed() {

        APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<NewFeed> call = apiService.getNewsFeed(paramApiKey);
        call.enqueue(new Callback<NewFeed>() {
            List<Result> newsFeeds = null;

            @Override
            public void onResponse(Call<NewFeed> call, Response<NewFeed> response) {
                newsFeeds = response.body().getResults();
                responseListener.onSuccess(newsFeeds);
            }

            @Override
            public void onFailure(Call<NewFeed> call, Throwable t) {
                // Log error here since request failed
                Log.d("Repository", t.getLocalizedMessage());
                responseListener.onFailure(t.getLocalizedMessage());
            }
        });
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }
}
