package com.gumtree.android.auth.api;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.ActivateUserXmlBuilder;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnActivatedAccountEvent;
import javax.inject.Inject;

public class ActivateAccountIntentService extends IntentService {
    private static final String EXTRA_ACTIVATION_KEY = "com.gumtree.android.auth.activation_key";
    private static final String EXTRA_EMAIL = "com.gumtree.android.auth.email";
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;

    public ActivateAccountIntentService() {
        super("ActivateAccountIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void activate(String str, String str2) {
        Track.eventActivateAccountAttempt();
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, ActivateAccountIntentService.class);
        intent.putExtra(EXTRA_EMAIL, str);
        intent.putExtra(EXTRA_ACTIVATION_KEY, str2);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_EMAIL);
        String stringExtra2 = intent.getStringExtra(EXTRA_ACTIVATION_KEY);
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        String str = "users/activate";
        capiClient.withContent(new ActivateUserXmlBuilder(stringExtra2, stringExtra).build());
        try {
            capiClient.post("users/activate").execute();
            this.eventBus.post(new OnActivatedAccountEvent(true, null));
            Track.eventActivateAccountSuccess();
        } catch (ResponseException e) {
            this.eventBus.post(new OnActivatedAccountEvent(false, new ResponseException(e.getError())));
            Track.eventActivateAccountFail();
        }
    }
}
