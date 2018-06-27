package com.nagarro.nytimes;

import com.nagarro.nytimes.domain.executor.Executor;
import com.nagarro.nytimes.domain.executor.MainThread;
import com.nagarro.nytimes.domain.interactors.base.NewsFeedInteractor;
import com.nagarro.nytimes.domain.interactors.impl.NewsFeedInteractorImpl;
import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.domain.repository.NewsFeedRepository;
import com.nagarro.nytimes.service.APIInterface;
import com.nagarro.nytimes.service.ApiClient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedInteractorTest {
    private MainThread mMainThread;
    @Mock private Executor mExecutor;
    @Mock private NewsFeedRepository mMessageRepository;
    @Mock private NewsFeedInteractor.Callback mMockedCallback;
    @Mock private APIInterface apiService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //mMainThread = new TestMainThread();

    }

    @Test
    public void testGetNewsFeed(){
        NewsFeedInteractorImpl interactor = new NewsFeedInteractorImpl(mExecutor, mMainThread, mMockedCallback, mMessageRepository);
        interactor.run();

        Mockito.verify(mMessageRepository).getNewsFeed();
    }
}
