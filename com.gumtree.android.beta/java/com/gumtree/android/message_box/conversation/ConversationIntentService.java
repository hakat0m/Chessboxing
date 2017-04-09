package com.gumtree.android.message_box.conversation;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationResultEvent;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.message_box.ConversationRequest;
import com.gumtree.android.message_box.MessagesDao;
import javax.inject.Inject;

public class ConversationIntentService extends IntentService {
    public static final int DEFAULT_TAIL = 100;
    private static final String EXTRA_CONVERSATION_ID = "conversationId";
    private static final String EXTRA_TAIL = "TAIL";
    private static final String TAG = ConversationIntentService.class.getSimpleName();
    @Inject
    EventBus mEventBus;

    public ConversationIntentService() {
        super("ConversationIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void start(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, ConversationIntentService.class);
        intent.putExtra(EXTRA_CONVERSATION_ID, str);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        try {
            int intExtra = intent.getIntExtra(EXTRA_TAIL, DEFAULT_TAIL);
            String stringExtra = intent.getStringExtra(EXTRA_CONVERSATION_ID);
            Result execute = new ConversationRequest(stringExtra, intExtra).execute();
            if (execute != null) {
                Conversation conversation = (Conversation) execute.getData();
                conversation.setUid(stringExtra);
                if (conversation != null) {
                    Batch createBatchOperation = new ContentProviderDataStorage(getApplicationContext()).createBatchOperation();
                    MessagesDao messagesDao = new MessagesDao();
                    messagesDao.deleteConversationMessages(conversation.getUid(), getContentResolver());
                    for (UserMessage userMessage : conversation.getUserMessages()) {
                        userMessage.setConversationId(conversation.getUid());
                        messagesDao.persistMessagesBatch(userMessage, createBatchOperation);
                    }
                    createBatchOperation.execute();
                    this.mEventBus.post(new OnConversationResultEvent(true, null, conversation));
                    return;
                }
            }
            this.mEventBus.post(new OnConversationResultEvent(false, null, null));
        } catch (Throwable e) {
            Log.e(TAG, "There was an error trying to get a list of conversations ", e);
            this.mEventBus.post(new OnConversationResultEvent(false, e, null));
        }
    }
}
