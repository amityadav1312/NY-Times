package com.nagarro.nytimes.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagarro.nytimes.R;
import com.nagarro.nytimes.customviews.RoundedImageView;
import com.nagarro.nytimes.domain.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Result> mNewsFeedResults;
    private NewsFeedClickListener mClickListener;

    public NewsFeedAdapter(Context context) {
        this.mContext = context;
    }

    public void setNewsFeedModel(List<Result> newsFeeds) {
        mNewsFeedResults = newsFeeds;
    }

    public void setClickListener(NewsFeedClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.layout_news_feed_item, parent, false);
        return new NewsFeedItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NewsFeedItemHolder holder = (NewsFeedItemHolder) viewHolder;
        //Picasso.with(mContext).load(mNewsFeedResults.get(position).getUrl()).into(holder.mNewsFeedImage);
        holder.mNewsFeedTitle.setText(mNewsFeedResults.get(position).getTitle());
        holder.mNewsFeedDescription.setText(mNewsFeedResults.get(position).getAbstract());
        holder.mNewsFeedDate.setText(mNewsFeedResults.get(position).getPublishedDate());
    }

    @Override
    public int getItemCount() {
        return mNewsFeedResults != null ? mNewsFeedResults.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class NewsFeedItemHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.img_news_feed)
        RoundedImageView mNewsFeedImage;
        @BindView(R.id.tv_news_title)
        TextView mNewsFeedTitle;
        @BindView(R.id.tv_news_description)
        TextView mNewsFeedDescription;
        @BindView(R.id.tv_date)
        TextView mNewsFeedDate;

        public NewsFeedItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mView = itemView;
            this.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.listItemClicked(mNewsFeedResults.get(getAdapterPosition()));
                }
            });
        }
    }
}
