package com.nagarro.nytimes.presentation.presenters;

import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.presentation.ui.NewsFeedView;

import java.util.List;


public interface NewsFeedPresenter {

    interface View extends NewsFeedView {
        void newsFeedResponse(List<Result> results);
    }
}
