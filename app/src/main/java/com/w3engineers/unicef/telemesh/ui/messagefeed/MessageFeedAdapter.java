package com.w3engineers.unicef.telemesh.ui.messagefeed;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.w3engineers.ext.strom.application.ui.base.BaseAdapter;
import com.w3engineers.ext.strom.application.ui.base.BaseViewHolder;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;
import com.w3engineers.unicef.telemesh.databinding.ItemMessageFeedBinding;

import java.util.List;

/**
 * Adapter that fetches the {@link FeedEntity} from database and show the converted view.
 */

public class MessageFeedAdapter extends BaseAdapter<FeedEntity> {
    private MessageFeedViewModel mMessageFeedViewModel;
    private Context mContext;

    public MessageFeedAdapter(Context context, MessageFeedViewModel messageFeedViewModel) {
        this.mMessageFeedViewModel = messageFeedViewModel;
        this.mContext = context;
    }

    @Override
    public boolean isEqual(FeedEntity left, FeedEntity right) {
        String leftUserId = left.getFeedId();
        String rightUserId = right.getFeedId();
        return !TextUtils.isEmpty(leftUserId)
                && !TextUtils.isEmpty(rightUserId)
                && leftUserId.equals(rightUserId);
    }

    /**
     * Update the adapter with the new data
     *
     * @param feedEntities fetched from the database
     */
    public void resetWithList(List<FeedEntity> feedEntities) {
        List<FeedEntity> feedEntityList = getItems();
        feedEntityList.clear();
        notifyDataSetChanged();
        addItem(feedEntities);
    }

    @Override
    public BaseViewHolder<FeedEntity> newViewHolder(ViewGroup parent, int viewType) {
        return new MessageFeedViewHolder(inflate(parent, R.layout.item_message_feed));
    }

    private class MessageFeedViewHolder extends BaseViewHolder<FeedEntity> {
        private ItemMessageFeedBinding mItemMessageFeedBinding;

        private MessageFeedViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            this.mItemMessageFeedBinding = (ItemMessageFeedBinding) viewDataBinding;
        }

        @Override
        public void bind(FeedEntity feedEntity) {
            mItemMessageFeedBinding.setFeedEntity(feedEntity);
            mItemMessageFeedBinding.setMessageFeedViewModel(mMessageFeedViewModel);
            // Static logo showing
            Glide.with(mContext)
                    .load(R.drawable.ic_unicef)
                    .into(mItemMessageFeedBinding.imageViewFeedProviderLogo);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
