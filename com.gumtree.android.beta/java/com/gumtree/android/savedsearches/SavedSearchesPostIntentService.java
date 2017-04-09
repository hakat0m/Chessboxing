package com.gumtree.android.savedsearches;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.alerts.Alert;
import com.gumtree.android.alerts.AlertSerializer;
import com.gumtree.android.alerts.parser.AlertResponseParser;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import javax.inject.Inject;

public class SavedSearchesPostIntentService extends IntentService {
    private static final String EXTRA_IS_EMAIL_ENABLED = "is_email_enabled";
    private static final String EXTRA_SEARCH_ID = "search_id";
    private static final String EXTRA_SEARCH_URL = "search_url";
    @Inject
    BaseAccountManager accountManager;
    private ICapiClient capi = this.capiClientManager.getCapiClient();
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;
    private SavedSearchesDao savedSearchesDao = new SavedSearchesDao(GumtreeApplication.getContext());

    public SavedSearchesPostIntentService() {
        super("SavedSearchesPostIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void saveCapiSavedSearch(String str, String str2, boolean z) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, SavedSearchesPostIntentService.class);
        intent.putExtra(EXTRA_SEARCH_URL, str);
        intent.putExtra(EXTRA_SEARCH_ID, str2);
        intent.putExtra(EXTRA_IS_EMAIL_ENABLED, z);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_SEARCH_ID);
        String stringExtra2 = intent.getStringExtra(EXTRA_SEARCH_URL);
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_IS_EMAIL_ENABLED, false);
        Alert alert = new Alert();
        alert.setLink(stringExtra2);
        alert.setEmailAlertEnabled(booleanExtra);
        stringExtra2 = new AlertSerializer().serialize(alert);
        this.capi.authorize(this.accountManager.getAuthentication());
        Result execute = this.capi.post("alerts").withContent(stringExtra2).execute(new AlertResponseParser());
        if (!execute.hasError()) {
            try {
                this.savedSearchesDao.update(stringExtra, (Alert) execute.getData());
            } catch (Exception e) {
                Log.e("Error when trying to update a saved search in the DB");
            }
        }
    }
}
