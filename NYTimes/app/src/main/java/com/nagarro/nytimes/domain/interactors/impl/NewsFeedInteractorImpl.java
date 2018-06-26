package com.nagarro.nytimes.domain.interactors.impl;

import com.nagarro.nytimes.domain.executor.Executor;
import com.nagarro.nytimes.domain.executor.MainThread;
import com.nagarro.nytimes.domain.interactors.ResponseListener;
import com.nagarro.nytimes.domain.interactors.base.AbstractInteractor;
import com.nagarro.nytimes.domain.interactors.base.NewsFeedInteractor;
import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.domain.repository.FeedRepository;

import java.util.List;

public class NewsFeedInteractorImpl extends AbstractInteractor implements NewsFeedInteractor, ResponseListener<List<Result>> {
    private FeedRepository mFeedRepository;
    private NewsFeedInteractor.Callback mCallback;

    public NewsFeedInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, FeedRepository feedRepository) {
        super(threadExecutor, mainThread);
        mFeedRepository = feedRepository;
        mFeedRepository.setResponseListener(this);
        mCallback = callback;
    }

    @Override
    public void run() {
        mFeedRepository.getNewsFeed();
    }

    @Override
    public void onSuccess(List<Result> results) {
        mCallback.onMessageRetrieved(results);
    }

    @Override
    public void onFailure(String error) {
        mCallback.onRetrievalFailed(error);
    }
}
