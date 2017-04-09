package com.gumtree.android.notifications.handlers;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.ConversationsPage;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationsListResultEvent;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.message_box.ConversationsDao;
import com.gumtree.android.message_box.ConversationsRequest;
import com.gumtree.android.message_box.GumtreeConversationsManager;
import com.gumtree.android.message_box.MessagesDao;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.OnPushConversationReceived;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import java.util.ArrayList;
import java.util.List;

public class ConversationMessageNotificationHandler {
    public static final String GCM_CONVERSATION_AD_ID = "AdId";
    public static final String GCM_CONVERSATION_ID = "ConversationId";
    private static final String TAG = ConversationMessageNotificationHandler.class.getSimpleName();

    public void handlePushNotification(Context context, Bundle bundle, EventBus eventBus, String str) {
        Track.prepare((Application) context.getApplicationContext());
        Track.viewMessageNotificationReceived();
        Result execute = new ConversationsRequest(0, 100, null).execute();
        if (execute != null) {
            ConversationsPage conversationsPage = (ConversationsPage) execute.getData();
            if (conversationsPage != null) {
                String string = bundle.getString(GCM_CONVERSATION_ID);
                String string2 = bundle.getString(GCM_CONVERSATION_AD_ID);
                List unreadConversations = getUnreadConversations(conversationsPage.getConversations());
                persistConversations(unreadConversations, context, str);
                eventBus.post(new OnConversationsListResultEvent(true, null, conversationsPage));
                eventBus.post(new OnPushConversationReceived(string));
                if (new GCMSettingsProvider().isNotificationTypeEnabled(NotificationType.CHAT_MESSAGE) && !isConversationVisible(string, string2)) {
                    Notification notification = NotificationConversationFactory.getNotification(context, unreadConversations);
                    if (notification != null) {
                        ((NotificationManager) context.getSystemService(ConversationActivity.NOTIFICATION)).notify(NotificationType.CHAT_MESSAGE.getType(), notification);
                    }
                }
            }
        }
    }

    private boolean isConversationVisible(String str, String str2) {
        return str.equals(GumtreeConversationsManager.get().getCurrentConversationId()) || str2.equals(GumtreeConversationsManager.get().getCurrentConversationAdId());
    }

    private List<Conversation> getUnreadConversations(List<Conversation> list) {
        List<Conversation> arrayList = new ArrayList();
        for (Conversation conversation : list) {
            if (conversation.getNumUnreadMsg() > 0) {
                arrayList.add(conversation);
            }
        }
        return arrayList;
    }

    private void persistConversations(List<Conversation> list, Context context, String str) {
        Throwable e;
        Batch createBatchOperation = new ContentProviderDataStorage(context.getApplicationContext()).createBatchOperation();
        ContentResolver contentResolver = context.getContentResolver();
        ConversationsDao conversationsDao = new ConversationsDao();
        MessagesDao messagesDao = new MessagesDao();
        for (Conversation conversation : list) {
            String uid = conversation.getUid();
            if (conversationsDao.containsConversationByConversationId(conversation.getUid(), contentResolver)) {
                conversationsDao.updateConversationByConversationId(conversation, conversation.getUid(), createBatchOperation);
            } else {
                int i;
                Object adOwnerEmail = conversation.getAdOwnerEmail();
                if (TextUtils.isEmpty(adOwnerEmail) || !adOwnerEmail.equals(str)) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (i != 0) {
                    conversationsDao.persist(conversation, 1, createBatchOperation);
                } else {
                    Conversation conversationByAdId = conversationsDao.getConversationByAdId(conversation.getAdId(), contentResolver);
                    if (conversationByAdId != null) {
                        conversationsDao.updateConversationByAdId(conversation, conversation.getAdId(), createBatchOperation);
                        uid = conversationByAdId.getUid();
                    } else {
                        conversationsDao.persist(conversation, 1, createBatchOperation);
                    }
                }
            }
            List userMessages = conversation.getUserMessages();
            if (userMessages != null && userMessages.size() > 0) {
                UserMessage userMessage = (UserMessage) userMessages.get(0);
                userMessage.setConversationId(uid);
                if (!messagesDao.containsUserMessage(userMessage, contentResolver)) {
                    messagesDao.persistMessagesBatch(userMessage, createBatchOperation);
                }
            }
        }
        try {
            createBatchOperation.execute();
            return;
        } catch (RemoteException e2) {
            e = e2;
        } catch (OperationApplicationException e3) {
            e = e3;
        }
        Log.e(TAG, "There was an error trying to save a conversation with message", e);
    }
}
