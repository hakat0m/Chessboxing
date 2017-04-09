package com.gumtree.android.message_box;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.model.Messages;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao {
    public static final String QUERY = "=?";
    public static final String QUERY_AND = "=? AND ";

    public List<UserMessage> getConversationMessages(String str, ContentResolver contentResolver) {
        List<UserMessage> arrayList = new ArrayList();
        Cursor query = contentResolver.query(Messages.URI, null, "conversation_uid = '" + str + "'", null, null);
        while (query.moveToNext()) {
            arrayList.add(new UserMessage(query.getString(query.getColumnIndex("id")), str, query.getString(query.getColumnIndex("sender_name")), query.getString(query.getColumnIndex("direction")), query.getString(query.getColumnIndex("msg_content")), query.getInt(query.getColumnIndex("answered")) == 1, query.getString(query.getColumnIndex("post_time_stamp"))));
        }
        return arrayList;
    }

    public Uri persist(UserMessage userMessage, int i, ContentResolver contentResolver) {
        return contentResolver.insert(Messages.URI, getContentValuesForInsert(userMessage, i));
    }

    public int persist(String str, UserMessage userMessage, int i, ContentResolver contentResolver) {
        return contentResolver.update(Messages.URI, getContentValuesForInsert(userMessage, i), "_id=?", new String[]{str});
    }

    public boolean containsUserMessage(UserMessage userMessage, ContentResolver contentResolver) {
        String[] strArr = new String[]{userMessage.getConversationId(), userMessage.getPostTimeStamp(), userMessage.getMsgContent()};
        if (contentResolver.query(Messages.URI, null, "conversation_uid=? AND post_time_stamp=? AND msg_content=?", strArr, null).moveToNext()) {
            return true;
        }
        return false;
    }

    private ContentValues getContentValuesForInsert(UserMessage userMessage, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", userMessage.getId());
        contentValues.put("conversation_uid", userMessage.getConversationId());
        contentValues.put("sender_name", userMessage.getSenderName());
        contentValues.put("direction", userMessage.getDirection());
        contentValues.put("msg_content", userMessage.getMsgContent());
        contentValues.put("answered", Integer.valueOf(userMessage.isAnswered() ? 1 : 0));
        contentValues.put("post_time_stamp", userMessage.getPostTimeStamp());
        contentValues.put("sync_status", Integer.valueOf(i));
        return contentValues;
    }

    public void persistMessagesBatch(UserMessage userMessage, Batch batch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", userMessage.getId());
        contentValues.put("conversation_uid", userMessage.getConversationId());
        contentValues.put("sender_name", userMessage.getSenderName());
        contentValues.put("direction", userMessage.getDirection());
        contentValues.put("msg_content", userMessage.getMsgContent());
        contentValues.put("answered", Integer.valueOf(userMessage.isAnswered() ? 1 : 0));
        contentValues.put("post_time_stamp", userMessage.getPostTimeStamp());
        contentValues.put("sync_status", Integer.valueOf(1));
        batch.insert(Messages.URI).withValues(contentValues);
    }

    public void updateMessagesBatch(UserMessage userMessage, Batch batch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", userMessage.getId());
        contentValues.put("conversation_uid", userMessage.getConversationId());
        contentValues.put("sender_name", userMessage.getSenderName());
        contentValues.put("direction", userMessage.getDirection());
        contentValues.put("msg_content", userMessage.getMsgContent());
        contentValues.put("answered", Integer.valueOf(userMessage.isAnswered() ? 1 : 0));
        contentValues.put("post_time_stamp", userMessage.getPostTimeStamp());
        contentValues.put("sync_status", Integer.valueOf(1));
        batch.delete(Messages.URI).withSelection("conversation_uid=? AND post_time_stamp=? AND msg_content=?", new String[]{userMessage.getConversationId(), userMessage.getPostTimeStamp(), userMessage.getMsgContent()});
        batch.insert(Messages.URI).withValues(contentValues);
    }

    public void deleteOrphanMessages(Batch batch) {
        batch.delete(Messages.URI).withSelection("conversation_uid NOT IN (SELECT uid FROM conversations)", new String[0]);
    }

    public void deleteConversationMessages(String str, ContentResolver contentResolver) {
        contentResolver.delete(Messages.URI, "conversation_uid=? AND sync_status not in (?, ?) ", new String[]{str, String.valueOf(0), String.valueOf(4)});
    }

    public void cleanTable(ContentResolver contentResolver) {
        Log.v("Start clean-up for messages table");
        contentResolver.delete(Messages.URI, null, null);
    }
}
