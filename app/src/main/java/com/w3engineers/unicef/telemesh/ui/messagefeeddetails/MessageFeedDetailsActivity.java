package com.w3engineers.unicef.telemesh.ui.messagefeeddetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;

import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;
import com.w3engineers.unicef.telemesh.data.provider.ServiceLocator;
import com.w3engineers.unicef.telemesh.databinding.ActivityMessageFeedDetailsBinding;

import java.util.Objects;

/**
 * This class represents the details of a feed message.
 */

public class MessageFeedDetailsActivity extends BaseActivity {

    private FeedEntity mFeedEntity;
    private ServiceLocator mServiceLocator;
    private MessageFeedDetailsViewModel mMessageFeedDetailsViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_feed_details;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int statusBarColor() {
        return R.color.colorPrimary;
    }

    @Override
    protected void startUI() {
        getData();
        initGui();
        updateFeedMessageReadStatusIfNecessary();
    }

    /**
     * Initialize the View
     */
    private void initGui() {
        mMessageFeedDetailsViewModel = getViewModel();
        ActivityMessageFeedDetailsBinding mActivityMessageFeedDetailsBinding = (ActivityMessageFeedDetailsBinding) getViewDataBinding();
        if (mFeedEntity != null) {
            mActivityMessageFeedDetailsBinding.setFeedEntity(mFeedEntity);
            setTitle(mFeedEntity.getFeedProviderName());
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mActivityMessageFeedDetailsBinding.messageFeedDetails.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Get the parcelable data
     */
    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            mFeedEntity = intent.getParcelableExtra(FeedEntity.class.getName());
        }

    }

    /**
     * Get the view model from the view model factory
     *
     * @return ViewModel
     */

    private MessageFeedDetailsViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                mServiceLocator = ServiceLocator.getInstance();
                return (T) mServiceLocator.getMessageFeedDetailsViewModel();
            }
        }).get(MessageFeedDetailsViewModel.class);
    }

    private void updateFeedMessageReadStatusIfNecessary() {
        if (mFeedEntity != null && mFeedEntity.getId() != 0L && !mFeedEntity.isFeedRead())
            mMessageFeedDetailsViewModel.updateTheFeedReadStatus(mFeedEntity.getId());
    }
}
