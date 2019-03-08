package com.w3engineers.unicef.telemesh.ui.messagefeed;


import android.arch.lifecycle.LiveData;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.w3engineers.ext.strom.application.ui.base.BaseRxViewModel;
import com.w3engineers.unicef.TeleMeshApplication;
import com.w3engineers.unicef.telemesh.data.broadcast.UiThreadCallback;
import com.w3engineers.unicef.telemesh.data.helper.RightMeshDataSource;
import com.w3engineers.unicef.telemesh.data.helper.RmDataHelper;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedDataSource;
import com.w3engineers.unicef.telemesh.data.local.feed.FeedEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Sikder Faysal Ahmed on [11-Oct-2018 at 12:28 PM].
 * * ----------------------------------------------------------------------------
 * * Project: telemesh.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [11-Oct-2018 at 12:28 PM].
 * * --> <Second Editor> on [11-Oct-2018 at 12:28 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [11-Oct-2018 at 12:28 PM].
 * * --> <Second Reviewer> on [11-Oct-2018 at 12:28 PM].
 * * ============================================================================
 **/
public class MessageFeedViewModel extends BaseRxViewModel{

    private FeedDataSource mFeedDataSource;
    int count = 0;

    public MessageFeedViewModel(FeedDataSource feedDataSource) {
        this.mFeedDataSource = feedDataSource;

    }


    public void broadcastMessageTest(){

        byte [] msg = "HelloWorld".getBytes();
        //RightMeshDataSource.getRmDataSource().broadcastMessage(msg);
    }



    public LiveData<List<FeedEntity>> getAllFeed() {
        return mFeedDataSource.getAllFeed();
    }


    /*private void onSuccessMine(String s){
        count = count + 1;
        Toast.makeText(TeleMeshApplication.getContext(), count+ "Msg: " + s,  Toast.LENGTH_LONG).show();
    }

    private void onError(Throwable e) {

    }*/

}
