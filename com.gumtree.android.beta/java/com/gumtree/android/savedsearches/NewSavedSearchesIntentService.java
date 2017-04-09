package com.gumtree.android.savedsearches;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;

public class NewSavedSearchesIntentService extends IntentService {
    private static final String EXTRA_ACTION = "ActionToBePerformed";
    private static final int EXTRA_ACTION_DELETE = 0;
    private static final int EXTRA_ACTION_DELETE_ALL = 1;
    private static final String EXTRA_NEW_SAVED_SEARCH_ID = "NewSavedSearchId";
    private static final String LOG_TAG = "NewSavedSIntentService";

    public NewSavedSearchesIntentService() {
        super("SavedSearchesIntentService");
    }

    public static void deleteNewSavedSearch(int i) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, NewSavedSearchesIntentService.class);
        intent.putExtra(EXTRA_NEW_SAVED_SEARCH_ID, i);
        intent.putExtra(EXTRA_ACTION, EXTRA_ACTION_DELETE);
        context.startService(intent);
    }

    public static void clearNewSavedSearch() {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, NewSavedSearchesIntentService.class);
        intent.putExtra(EXTRA_ACTION, EXTRA_ACTION_DELETE_ALL);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        switch (intent.getIntExtra(EXTRA_ACTION, -1)) {
            case EXTRA_ACTION_DELETE /*0*/:
                new NewSavedSearchesDao(getContentResolver()).removeNewSavedSearchFromLocalId((long) intent.getIntExtra(EXTRA_NEW_SAVED_SEARCH_ID, EXTRA_ACTION_DELETE));
                return;
            case EXTRA_ACTION_DELETE_ALL /*1*/:
                new NewSavedSearchesDao(getContentResolver()).clearNewSavedSearches();
                return;
            default:
                Log.w(LOG_TAG, "NewSavedSearchesIntentService called without a valid action");
                return;
        }
    }
}
