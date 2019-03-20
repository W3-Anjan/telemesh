package com.w3engineers.unicef.telemesh.ui.messagefeed;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.w3engineers.ext.strom.application.ui.base.BaseFragment;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;
import com.w3engineers.unicef.telemesh.data.provider.ServiceLocator;
import com.w3engineers.unicef.telemesh.databinding.FragmentMessageFeedBinding;
import com.w3engineers.unicef.telemesh.ui.messagefeeddetails.MessageFeedDetailsActivity;


public class MessageFeedFragment extends BaseFragment {

    private FragmentMessageFeedBinding mMessageFeedBinding;
    private ServiceLocator mServiceLocator;
    private MessageFeedViewModel mMessageFeedViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_feed;
    }

    @Override
    protected void startUI() {
        mMessageFeedBinding = (FragmentMessageFeedBinding) getViewDataBinding();
        initGui();
        subscribeForMessageFeed();
        openMessageFeedDetails();
    }

    /**
     * Subscribe for message feed update
     */
    private void subscribeForMessageFeed() {

        mMessageFeedViewModel.loadFeedList()
                .observe(this, feedEntities -> getAdapter()
                        .resetWithList(feedEntities));
        mMessageFeedViewModel.loadFeedList();
    }


    private MessageFeedAdapter getAdapter() {
        return (MessageFeedAdapter) mMessageFeedBinding
                .messageRecyclerView.getAdapter();
    }

    /**
     * Initialize the view
     */
    private void initGui() {
        mMessageFeedViewModel = getViewModel();
        mMessageFeedBinding.messageRecyclerView.setItemAnimator(null);
        mMessageFeedBinding.messageRecyclerView.setHasFixedSize(true);
        mMessageFeedBinding.messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MessageFeedAdapter messageFeedAdapter = new MessageFeedAdapter(getActivity(), mMessageFeedViewModel);
        mMessageFeedBinding.messageRecyclerView.setAdapter(messageFeedAdapter);
    }

    /**
     * Get the view model from the view model factory
     *
     * @return ViewModel
     */

    private MessageFeedViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                mServiceLocator = ServiceLocator.getInstance();
                return (T) mServiceLocator.getMessageFeedViewModel();
            }
        }).get(MessageFeedViewModel.class);
    }

    /**
     * Open the message details activity
     */
    private void openMessageFeedDetails() {

        mMessageFeedViewModel.getMessageFeedDetails().observe(this, feedEntity -> {
            Intent intent = new Intent(getActivity(), MessageFeedDetailsActivity.class);
            intent.putExtra(FeedEntity.class.getName(), feedEntity);
            startActivity(intent);
        });

    }

}
