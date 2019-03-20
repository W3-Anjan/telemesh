package com.w3engineers.unicef.util.helper;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;

import java.util.List;

public class FeedEntityDiffCallback extends DiffUtil.Callback {


    private List<FeedEntity> mOldList;
    private List<FeedEntity> mNewList;

    public FeedEntityDiffCallback(List<FeedEntity> oldList, List<FeedEntity> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }


    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return mOldList.get(oldPosition).getId() == mNewList.get(newPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        return mOldList.get(oldPosition).equals(mNewList.get(newPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
