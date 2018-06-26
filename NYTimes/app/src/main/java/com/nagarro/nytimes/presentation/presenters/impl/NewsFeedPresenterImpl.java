package com.nagarro.nytimes.presentation.presenters.impl;

import com.nagarro.nytimes.domain.executor.Executor;
import com.nagarro.nytimes.domain.executor.MainThread;
import com.nagarro.nytimes.domain.interactors.base.NewsFeedInteractor;
import com.nagarro.nytimes.domain.interactors.impl.NewsFeedInteractorImpl;
import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.domain.repository.FeedRepository;
import com.nagarro.nytimes.presentation.presenters.NewsFeedPresenter;
import com.nagarro.nytimes.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * NewsFeedPresenter Implementation..
 */
public class NewsFeedPresenterImpl extends AbstractPresenter implements NewsFeedPresenter,
        NewsFeedInteractor.Callback {

    private final FeedRepository mFeedRepository;
    private NewsFeedPresenter.View mView;

    public NewsFeedPresenterImpl(Executor executor,
                                 MainThread mainThread,
                                 View view, FeedRepository feedRepository) {
        super(executor, mainThread);
        mView = view;
        mFeedRepository = feedRepository;
    }

    public void getNewsFeeds() {
        mView.showProgress();
        NewsFeedInteractor newsFeedInteractor = new NewsFeedInteractorImpl(mExecutor, mMainThread, this, mFeedRepository);
        newsFeedInteractor.execute();
    }

    @Override
    public void onMessageRetrieved(List<Result> results) {
        mView.hideProgress();
        mView.newsFeedResponse(results);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        mView.showError(error);
    }
}
