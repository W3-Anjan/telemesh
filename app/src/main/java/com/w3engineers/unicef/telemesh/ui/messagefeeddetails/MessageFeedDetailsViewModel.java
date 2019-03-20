package com.w3engineers.unicef.telemesh.ui.messagefeeddetails;

import com.w3engineers.ext.strom.application.ui.base.BaseRxViewModel;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedDataSource;

public class MessageFeedDetailsViewModel extends BaseRxViewModel {
    // FeedDataSource instance
    private final FeedDataSource mFeedDataSource;

    public MessageFeedDetailsViewModel(FeedDataSource feedDataSource) {
        this.mFeedDataSource = feedDataSource;
    }

    /**
     * Update the feed read status
     *
     * @param feedId selected feed id
     */
    public void updateTheFeedReadStatus(long feedId) {
        if (feedId != 0L) {
            mFeedDataSource.updateFeedMessageReadStatus(feedId);
        }

    }
}
