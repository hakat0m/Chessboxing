package com.gumtree.android.message_box;

import android.content.Context;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.model.Conversations;
import java.util.List;

public class ConversationsSyncHelper {
    private static final String TAG = ConversationsSyncHelper.class.getSimpleName();
    private Context context;
    private ConversationsDao conversationsDao = new ConversationsDao();
    private MessagesDao messagesDao = new MessagesDao();

    public ConversationsSyncHelper(Context context) {
        this.context = context;
    }

    public boolean syncDBConversations(List<Conversation> list) {
        Batch createBatchOperation = new ContentProviderDataStorage(this.context.getApplicationContext()).createBatchOperation();
        List toSyncConversations = getToSyncConversations();
        deleteNotToSyncConversations(createBatchOperation);
        for (Conversation conversation : list) {
            String toSyncConversationId = getToSyncConversationId(toSyncConversations, conversation.getAdId());
            if (toSyncConversationId != null) {
                updateToSyncConversation(createBatchOperation, conversation, toSyncConversationId);
            } else {
                persistConversation(createBatchOperation, conversation);
            }
        }
        deleteOrphanMessages(createBatchOperation);
        try {
            createBatchOperation.execute();
            return true;
        } catch (Throwable e) {
            Log.e(TAG, "There was an error trying to synch a list of conversations ", e);
            return false;
        }
    }

    private void deleteOrphanMessages(Batch batch) {
        this.messagesDao.deleteOrphanMessages(batch);
    }

    private void persistConversation(Batch batch, Conversation conversation) {
        this.conversationsDao.persist(conversation, 1, batch);
        persistMessage(batch, conversation);
    }

    private void updateToSyncConversation(Batch batch, Conversation conversation, String str) {
        this.conversationsDao.updateConversationByAdId(conversation, conversation.getAdId(), batch);
        persistMessage(batch, conversation);
    }

    private void persistMessage(Batch batch, Conversation conversation) {
        List<UserMessage> userMessages = conversation.getUserMessages();
        if (userMessages != null && userMessages.size() > 0) {
            for (UserMessage userMessage : userMessages) {
                userMessage.setConversationId(conversation.getUid());
                this.messagesDao.updateMessagesBatch(userMessage, batch);
            }
        }
    }

    private List<Conversation> getToSyncConversations() {
        return this.conversationsDao.getToSyncConversations(this.context.getContentResolver());
    }

    private void deleteNotToSyncConversations(Batch batch) {
        batch.delete(Conversations.URI).withSelection("sync_status!=?", new String[]{String.valueOf(0)});
    }

    private String getToSyncConversationId(List<Conversation> list, String str) {
        String str2 = null;
        for (Conversation conversation : list) {
            String uid;
            if (conversation.getAdId().equals(str)) {
                uid = conversation.getUid();
            } else {
                uid = str2;
            }
            str2 = uid;
        }
        return str2;
    }
}
