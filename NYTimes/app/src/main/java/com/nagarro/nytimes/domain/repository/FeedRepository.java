package com.nagarro.nytimes.domain.repository;

import com.nagarro.nytimes.domain.interactors.ResponseListener;

public interface FeedRepository {
    void getNewsFeed();
    void setResponseListener(ResponseListener responseListener);
}
