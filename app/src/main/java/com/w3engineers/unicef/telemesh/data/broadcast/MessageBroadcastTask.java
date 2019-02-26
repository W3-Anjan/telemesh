package com.w3engineers.unicef.telemesh.data.broadcast;

import android.annotation.SuppressLint;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.w3engineers.ext.viper.application.data.remote.BaseRmDataSource;
import com.w3engineers.ext.viper.application.data.remote.model.MeshData;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import io.left.rightmesh.android.AndroidMeshManager;
import io.left.rightmesh.id.MeshId;
import timber.log.Timber;

/**
 Created by Anjan Debnath on 6/28/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved..
 *
 * MessageBroadcastTask is used for sending tasks to the thread pool. When a callable is submitted,
 * a Future object is returned, allowing the thread pool manager to stop the task.
 */
public class MessageBroadcastTask implements Callable {


    // Keep a weak reference to the CustomThreadPoolManager singleton object, so we can send a
    // message. Use of weak reference is not a must here because CustomThreadPoolManager lives
    // across the whole application lifecycle
    private WeakReference<BroadcastManager> mCustomThreadPoolManagerWeakReference;
    private MeshData mMeshData;
    private BaseRmDataSource baseRmDataSource;

    public BaseRmDataSource getBaseRmDataSource() {
        return baseRmDataSource;
    }

    public void setBaseRmDataSource(BaseRmDataSource baseRmDataSource) {
        this.baseRmDataSource = baseRmDataSource;
    }



    public MeshData getMeshData() {
        return mMeshData;
    }

    public void setMeshData(MeshData mMeshData) {
        this.mMeshData = mMeshData;
    }




    @SuppressLint("TimberArgCount")
    @Override
    public Object call() {
        try {

            int sentStatus = -1;
            // check if thread is interrupted before lengthy operation
            if (Thread.interrupted()) throw new InterruptedException();



            try {
                Timber.e("Live Peers", "message: sent");
                   //Log.e("Live Peers", "message: sent");
                  sentStatus =  getBaseRmDataSource().sendMeshData(getMeshData());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // After work is finished, send a message to CustomThreadPoolManager
            /*Message message = Util.createMessage(Util.MESSAGE_ID, "Thread " +
                    String.valueOf(Thread.currentThread().getId()) + " " +
                    String.valueOf(Thread.currentThread().getName()) + " completed");

            if(mCustomThreadPoolManagerWeakReference != null
                    && mCustomThreadPoolManagerWeakReference.get() != null) {

                mCustomThreadPoolManagerWeakReference.get().sendMessageToUiThread(message);
            }*/

            return sentStatus;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCustomThreadPoolManager(BroadcastManager customThreadPoolManager) {
        this.mCustomThreadPoolManagerWeakReference = new WeakReference<BroadcastManager>(customThreadPoolManager);
    }
}
