package com.w3engineers.unicef.telemesh.data.local.feed;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.w3engineers.unicef.telemesh.data.local.db.ColumnNames;
import com.w3engineers.unicef.telemesh.data.local.db.DbBaseEntity;
import com.w3engineers.unicef.telemesh.data.local.db.TableNames;

import java.util.Date;

@SuppressLint("ParcelCreator")
@Entity(tableName = TableNames.FEED,
        indices = {@Index(value = {ColumnNames.COLUMN_FEED_ID}, unique = true)})
public class FeedEntity extends DbBaseEntity {

    /**
     * instance variable  and  table column
     *
     */



    @ColumnInfo(name = ColumnNames.COLUMN_FEED_ID)
    public String feedId;

    @ColumnInfo(name = ColumnNames.COLUMN_FEED_PROVIDER_NAME)
    public String feedProviderName;

    @ColumnInfo(name = ColumnNames.COLUMN_FEED_PROVIDER_LOGO)
    public String feedProviderLogo;

    @ColumnInfo(name = ColumnNames.COLUMN_FEED_TITLE)
    public String feedTitle;

    @ColumnInfo(name = ColumnNames.COLUMN_FEED_DETAIL)
    public String feedDetail;


    @ColumnInfo(name = ColumnNames.COLUMN_FEED_TIME)
    public Date feedTime;

    @ColumnInfo(name = ColumnNames.COLUMN_FEED_READ_STATUS)
    public boolean feedReadStatus;



    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getFeedProviderName() {
        return feedProviderName;
    }

    public void setFeedProviderName(String feedProviderName) {
        this.feedProviderName = feedProviderName;
    }

    public String getFeedProviderLogo() {
        return feedProviderLogo;
    }

    public void setFeedProviderLogo(String feedProviderLogo) {
        this.feedProviderLogo = feedProviderLogo;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedDetail() {
        return feedDetail;
    }

    public void setFeedDetail(String feedDetail) {
        this.feedDetail = feedDetail;
    }

    public Date getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(Date feedTime) {
        this.feedTime = feedTime;
    }

    public boolean isFeedReadStatus() {
        return feedReadStatus;
    }

    public void setFeedReadStatus(boolean feedReadStatus) {
        this.feedReadStatus = feedReadStatus;
    }




}
