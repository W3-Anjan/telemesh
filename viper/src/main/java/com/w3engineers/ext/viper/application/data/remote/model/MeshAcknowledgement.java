package com.w3engineers.ext.viper.application.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * ============================================================================
 */
public class MeshAcknowledgement implements Parcelable {

    public int id;
    public MeshPeer mMeshPeer;

    public MeshAcknowledgement(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.mMeshPeer, flags);
    }

    protected MeshAcknowledgement(Parcel in) {
        this.id = in.readInt();
        this.mMeshPeer = in.readParcelable(MeshPeer.class.getClassLoader());
    }

    public static final Creator<MeshAcknowledgement> CREATOR = new Creator<MeshAcknowledgement>() {
        @Override
        public MeshAcknowledgement createFromParcel(Parcel source) {
            return new MeshAcknowledgement(source);
        }

        @Override
        public MeshAcknowledgement[] newArray(int size) {
            return new MeshAcknowledgement[size];
        }
    };
}
