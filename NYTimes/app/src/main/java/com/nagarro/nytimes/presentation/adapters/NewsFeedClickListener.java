package com.nagarro.nytimes.presentation.adapters;

import com.nagarro.nytimes.domain.model.Result;

/**
 * Created by charurani on 30-06-2016.
 */

public interface NewsFeedClickListener {
    void listItemClicked(Result result);
}
