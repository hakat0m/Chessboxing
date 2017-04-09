package com.gumtree.android.savedsearches;

import android.app.IntentService;
import android.content.Intent;

public class SavedSearchesIntentService extends IntentService {
    public SavedSearchesIntentService() {
        super("SavedSearchesIntentService");
    }

    protected void onHandleIntent(Intent intent) {
        SavedSearchSync.executeSavedSearchesSync(this);
    }
}
