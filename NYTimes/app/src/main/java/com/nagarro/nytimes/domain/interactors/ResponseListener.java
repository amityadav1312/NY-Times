package com.nagarro.nytimes.domain.interactors;


import com.nagarro.nytimes.domain.interactors.base.Interactor;


public interface ResponseListener<T> {

    void onSuccess(T result);
    void onFailure(String error);
}
