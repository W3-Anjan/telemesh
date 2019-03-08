package com.w3engineers.unicef.telemesh.ui.messagefeed;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.w3engineers.ext.strom.application.ui.base.BaseFragment;
import com.w3engineers.ext.strom.application.ui.base.ItemClickListener;
import com.w3engineers.unicef.TeleMeshApplication;
import com.w3engineers.unicef.telemesh.R;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;
import com.w3engineers.unicef.telemesh.data.local.usertable.UserEntity;
import com.w3engineers.unicef.telemesh.data.provider.ServiceLocator;
import com.w3engineers.unicef.telemesh.databinding.FragmentMessageFeedBinding;
import com.w3engineers.unicef.telemesh.ui.userprofile.UserProfileActivity;

import java.util.List;

public class MessageFeedFragment extends BaseFragment implements ItemClickListener<FeedEntity> {

    private FragmentMessageFeedBinding mMessageFeedBinding;
    private ServiceLocator serviceLocator;
    private MessageFeedViewModel mMessageFeedViewModel;


    public MessageFeedFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_feed;
    }

    @Override
    protected void startUI() {

        mMessageFeedViewModel = getViewModel();
        mMessageFeedBinding = (FragmentMessageFeedBinding) getViewDataBinding();

        subscribeForMessageFeed();


        mMessageFeedBinding.buttonFeed.setOnClickListener(this);



    }

    private void subscribeForMessageFeed(){

        mMessageFeedViewModel.getAllFeed().observe(this, new Observer<List<FeedEntity>>() {
            @Override
            public void onChanged(@Nullable List<FeedEntity> feedEntities) {
                for(int i=0; i< feedEntities.size(); i++){
                    Toast.makeText(TeleMeshApplication.getContext(), "FeedEntity:"+ feedEntities.get(i).getFeedTitle(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private MessageFeedViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                serviceLocator = ServiceLocator.getInstance();
                return (T) serviceLocator.getMessageFeedViewModel();
            }
        }).get(MessageFeedViewModel.class);
    }

    @Override
    public void onItemClick(View view, FeedEntity item) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.buttonFeed:
                mMessageFeedViewModel.broadcastMessageTest();
                break;

        }

    }
}
