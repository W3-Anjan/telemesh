package com.w3engineers.unicef.telemesh.data.local.feed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.w3engineers.ext.strom.application.data.helper.local.base.BaseDao;
import com.w3engineers.unicef.telemesh.data.local.db.TableNames;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FeedDao extends BaseDao<FeedEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFeed(FeedEntity feedEntity);


    @Query("SELECT * FROM " + TableNames.FEED)
    Flowable<List<FeedEntity>> getAllFeed();
}
