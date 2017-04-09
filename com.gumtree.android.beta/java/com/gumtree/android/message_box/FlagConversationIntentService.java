package com.gumtree.android.message_box;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationFlaggedEvent;
import javax.inject.Inject;

public class FlagConversationIntentService extends IntentService {
    private static final String EXTRA_CONVERSATION_ID = "com.gumtree.android.message_box.conversation_id";
    private static final String TAG = FlagConversationIntentService.class.getSimpleName();
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus mEventBus;

    public FlagConversationIntentService() {
        super("FlagConversationIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void deleteConversation(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, FlagConversationIntentService.class);
        intent.putExtra(EXTRA_CONVERSATION_ID, str);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_CONVERSATION_ID);
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        stringExtra = String.format("users/%s/conversations/%s.json", new Object[]{this.accountManager.getUsername(), stringExtra});
        capiClient.authorize(this.accountManager.getAuthentication());
        try {
            capiClient.put(stringExtra).execute();
            this.mEventBus.post(new OnConversationFlaggedEvent(true, null));
        } catch (Throwable e) {
            Log.e(TAG, "There was an error trying to flag a conversation ", e);
            this.mEventBus.post(new OnConversationFlaggedEvent(false, e));
        }
    }
}
