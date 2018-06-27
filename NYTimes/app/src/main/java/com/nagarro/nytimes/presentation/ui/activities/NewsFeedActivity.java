package com.nagarro.nytimes.presentation.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nagarro.nytimes.BuildConfig;
import com.nagarro.nytimes.R;
import com.nagarro.nytimes.constants.ApplicationConstants;
import com.nagarro.nytimes.domain.executor.impl.ThreadExecutor;
import com.nagarro.nytimes.domain.model.Result;
import com.nagarro.nytimes.domain.repository.NewsFeedRepository;
import com.nagarro.nytimes.presentation.adapters.NewsFeedAdapter;
import com.nagarro.nytimes.presentation.adapters.NewsFeedClickListener;
import com.nagarro.nytimes.presentation.presenters.NewsFeedPresenter.View;
import com.nagarro.nytimes.presentation.presenters.impl.NewsFeedPresenterImpl;
import com.nagarro.nytimes.threading.MainThreadImpl;
import com.nagarro.nytimes.utility.PermissionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * News feed activity...
 */
public class NewsFeedActivity extends AppCompatActivity implements View, NewsFeedClickListener {

    @BindView(R.id.rv_news_list)
    RecyclerView mNewsFeedRecyclerView;
    @BindView(R.id.progress_bar_news_feed)
    ProgressBar mProgressBar;

    private NewsFeedPresenterImpl mPresenter;
    private NewsFeedAdapter mNewsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);
        mPresenter = new NewsFeedPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, new NewsFeedRepository(BuildConfig.API_KEY));

        if(PermissionUtils.checkInternetPermission(this)){
            mPresenter.getNewsFeeds();
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, getString(R.string.error_getting_feeds), Toast.LENGTH_LONG).show();
    }

    @Override
    public void newsFeedResponse(List<Result> results) {
        initializeAdapter(results);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.REQUEST_PERMISSION_INTERNET:
                if (ContextCompat.checkSelfPermission(NewsFeedActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.getNewsFeeds();
                }else{
                    Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void initializeAdapter(List<Result> newsFeeds){
        LinearLayoutManager linearLayoutManagerForChatList = new LinearLayoutManager(this);
        linearLayoutManagerForChatList.setOrientation(LinearLayoutManager.VERTICAL);
        mNewsFeedRecyclerView.setLayoutManager(linearLayoutManagerForChatList);
        mNewsFeedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mNewsFeedAdapter = new NewsFeedAdapter(this);
        mNewsFeedAdapter.setClickListener(this);
        mNewsFeedAdapter.setNewsFeedModel(newsFeeds);
        mNewsFeedRecyclerView.setAdapter(mNewsFeedAdapter);
    }

    @Override
    public void listItemClicked(Result result) {
        if(result != null){
            Intent detailIntent = new Intent(this, NewsDetailsActivity.class);
            detailIntent.putExtra(ApplicationConstants.BUNDLE_CONSTANT_TITLE, result.getTitle());
            detailIntent.putExtra(ApplicationConstants.BUNDLE_CONSTANT_DESCRIPTION, result.getAbstract());
            startActivity(detailIntent);
        }
    }
}
