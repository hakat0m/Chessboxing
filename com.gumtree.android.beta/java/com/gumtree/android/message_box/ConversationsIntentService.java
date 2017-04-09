package com.gumtree.android.message_box;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.crashlytics.android.Crashlytics;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.conversations.ConversationsPage;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationsListResultEvent;
import javax.inject.Inject;

public class ConversationsIntentService extends IntentService {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 100;
    private static final String EXTRA_AD_ID = "adId";
    private static final String EXTRA_PAGE = "page";
    private static final String EXTRA_SIZE = "size";
    private static final String TAG = ConversationsIntentService.class.getSimpleName();
    @Inject
    BaseAccountManager accountManager;
    @Inject
    EventBus eventBus;

    public ConversationsIntentService() {
        super("ConversationsIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void start(int i, int i2, String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, ConversationsIntentService.class);
        intent.putExtra(EXTRA_PAGE, i);
        intent.putExtra(EXTRA_SIZE, i2);
        intent.putExtra(EXTRA_AD_ID, str);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        if (intent == null || !this.accountManager.isUserLoggedIn()) {
            Crashlytics.log("ConversationsIntentService has an empty intent so we are returning");
            return;
        }
        int intExtra = intent.getIntExtra(EXTRA_PAGE, DEFAULT_PAGE);
        int intExtra2 = intent.getIntExtra(EXTRA_SIZE, DEFAULT_SIZE);
        try {
            Result execute = new ConversationsRequest(intExtra, intExtra2, intent.getStringExtra(EXTRA_AD_ID)).execute();
            if (execute != null) {
                ConversationsPage conversationsPage = (ConversationsPage) execute.getData();
                if (conversationsPage != null) {
                    if (intExtra2 == 0) {
                        this.eventBus.post(new OnConversationsListResultEvent(true, null, conversationsPage));
                        return;
                    } else if (new ConversationsSyncHelper(getApplicationContext()).syncDBConversations(conversationsPage.getConversations())) {
                        this.eventBus.post(new OnConversationsListResultEvent(true, null, conversationsPage));
                        return;
                    }
                }
            }
            this.eventBus.post(new OnConversationsListResultEvent(false, null, null));
        } catch (Throwable e) {
            Log.e(TAG, "There was an error trying to get a list of conversations ", e);
            this.eventBus.post(new OnConversationsListResultEvent(false, e, null));
        }
    }
}
