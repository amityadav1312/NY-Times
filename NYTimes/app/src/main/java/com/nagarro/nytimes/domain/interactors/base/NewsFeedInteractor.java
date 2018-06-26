package com.nagarro.nytimes.domain.interactors.base;

import com.nagarro.nytimes.domain.model.Result;

import java.util.List;

public interface NewsFeedInteractor extends Interactor {

    interface Callback {

        void onMessageRetrieved(List<Result> results);

        void onRetrievalFailed(String error);
    }
}
