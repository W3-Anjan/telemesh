package com.w3engineers.unicef.telemesh.ui.splashscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.w3engineers.ext.strom.util.helper.data.local.SharedPref;
import com.w3engineers.unicef.telemesh.data.helper.constants.Constants;


/*
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * ============================================================================
 */
public class SplashViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isUserRegistered = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public MutableLiveData<Boolean> getIsUserRegistered() {
        return isUserRegistered;
    }

    public void getUserRegistrationStatus() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> isUserRegistered.postValue(SharedPref.getSharedPref(getApplication()
                .getApplicationContext()).readBoolean(Constants.preferenceKey.IS_USER_REGISTERED)),
                Constants.DefaultValue.DELAY_INTERVAL);

    }
}