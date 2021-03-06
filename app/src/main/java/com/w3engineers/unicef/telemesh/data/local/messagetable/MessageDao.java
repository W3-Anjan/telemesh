package com.w3engineers.unicef.telemesh.data.local.messagetable;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.w3engineers.ext.strom.application.data.helper.local.base.BaseDao;
import com.w3engineers.unicef.telemesh.data.helper.constants.Constants;
import com.w3engineers.unicef.telemesh.data.local.db.ColumnNames;
import com.w3engineers.unicef.telemesh.data.local.db.TableNames;

import java.util.List;
import io.reactivex.Flowable;

/*
 * ============================================================================
 * Copyright (C) 2019 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * ============================================================================
 */

/**
 * Access message table. Read, Write, Update operations
 * are performed here
 */

@Dao
public interface MessageDao extends BaseDao<MessageEntity> {

    /**
     * <h1>Retrieve all messages by user id</h1>
     * <p>The messages send or received with a very specific user
     * will be retrieve. User id must not empty</p>
     *
     * @param friendsId : String (required) must not null or empty
     * @return : Flowable list of messaged
     */

    @NonNull
    @Query("SELECT * FROM " + TableNames.MESSAGE + " WHERE " + ColumnNames.COLUMN_FRIENDS_ID + " = :friendsId ORDER BY " +ColumnNames.COLUMN_MESSAGE_TIME+" ASC")
    Flowable<List<MessageEntity>> getAllMessages(@NonNull String friendsId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long writeMessage(@NonNull MessageEntity messageEntity);

    /*
     * <h1>Provide last row id</h1>
     * <p>Sometime we need last row id for different purpose</p>
     *
     * @return : Long
     */
    // This api is not used in app layer
    /*@Query("SELECT " + ColumnNames.ID + " FROM " + TableNames.MESSAGE + " ORDER BY " + ColumnNames.ID + " DESC LIMIT 1")
    long getLastRowId();*/

    /**
     * <h1>Update message status</h1>
     * <p>Message status seen , delivery, read, unread  will update </p>
     *
     * @param messageId:String  (required) must not null or empty
     * @param messageStatus:int (required) must not null or empty
     * @return : Long
     */
    @Query("UPDATE " + TableNames.MESSAGE + " SET " + ColumnNames.COLUMN_MESSAGE_STATUS + " = :messageStatus WHERE "
            + ColumnNames.COLUMN_MESSAGE_ID + " LIKE :messageId")
    long updateMessageStatus(@NonNull String messageId, int messageStatus);

    /**
     * <h>Get specific message by id</h1>
     *
     * @param messageId : String (Required)
     * @return : MessageEntity
     */
    @NonNull
    @Query("SELECT * FROM " + TableNames.MESSAGE + " WHERE " + ColumnNames.COLUMN_MESSAGE_ID + " LIKE :messageId LIMIT 1")
    ChatEntity getMessageById(@NonNull String messageId);

    /**
     * Mark all message as read
     *
     * @param friendsId    : friends id
     * @return : long
     */
    @Query("UPDATE " + TableNames.MESSAGE + " SET " + ColumnNames.COLUMN_MESSAGE_STATUS +
            " = " + Constants.MessageStatus.STATUS_READ + " WHERE " + ColumnNames.COLUMN_FRIENDS_ID +
            " LIKE :friendsId AND " + ColumnNames.COLUMN_MESSAGE_STATUS + " = " + Constants.MessageStatus.STATUS_UNREAD)
    long updateMessageAsRead(@NonNull String friendsId);

    @Query("UPDATE " + TableNames.MESSAGE + " SET " + ColumnNames.COLUMN_MESSAGE_STATUS
            + "=:toStatus WHERE " + ColumnNames.COLUMN_MESSAGE_STATUS + "=:fromStatus")
    long changeMessageStatusFrom(int fromStatus, int toStatus);

    @NonNull
    @Query("SELECT * FROM " + TableNames.MESSAGE + " ORDER BY " + ColumnNames.ID + " DESC LIMIT 1")
    Flowable<MessageEntity> getLastInsertedMessage();

    // This api is not used in app layer
    /*@Query("DELETE FROM " + TableNames.MESSAGE)
    void deleteAllUsers();*/

    // This api is not used in app layer
    /*@Query("SELECT * FROM " + TableNames.MESSAGE + " WHERE " + ColumnNames.COLUMN_MESSAGE_ID
            + " = :messageId" + " AND " + ColumnNames.COLUMN_FRIENDS_ID + " = :friendsId" + " LIMIT 1")
    MessageEntity getMessageByFriendAndMessageId(String friendsId, String messageId);*/

    /*@Query("SELECT * FROM " + TableNames.MESSAGE + " WHERE " + ColumnNames.COLUMN_MESSAGE_ID
            + " = :messageId" + " AND " + ColumnNames.COLUMN_FRIENDS_ID + " = :friendsId" + " LIMIT 1")
    boolean hasChatEntityExist(@NonNull String friendsId, @NonNull String messageId);*/
}
