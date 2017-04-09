package com.gumtree.android.message_box;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationDeletedEvent;
import java.util.Collections;
import javax.inject.Inject;

public class DeleteConversationIntentService extends IntentService {
    private static final String EXTRA_CONVERSATION = "com.gumtree.android.message_box.conversation";
    private static final String TAG = FlagConversationIntentService.class.getSimpleName();
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;

    public DeleteConversationIntentService() {
        super("DeleteConversationIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void deleteConversation(Conversation conversation) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, DeleteConversationIntentService.class);
        intent.putExtra(EXTRA_CONVERSATION, conversation);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        Conversation conversation = (Conversation) intent.getSerializableExtra(EXTRA_CONVERSATION);
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        String format = String.format("users/%s/conversations/%s", new Object[]{this.accountManager.getUsername(), conversation.getUid()});
        capiClient.authorize(this.accountManager.getAuthentication());
        try {
            capiClient.delete(format).execute();
            this.eventBus.post(new OnConversationDeletedEvent(true, null));
            if (!new ConversationsSyncHelper(getApplicationContext()).syncDBConversations(Collections.singletonList(conversation))) {
                Log.w("Unable to clear the local cache");
            }
        } catch (Throwable e) {
            Log.e(TAG, "There was an error trying to delete a conversation ", e);
            this.eventBus.post(new OnConversationDeletedEvent(false, e));
        }
    }
}
