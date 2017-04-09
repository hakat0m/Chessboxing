package com.gumtree.android.favourites;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.gumtree.android.GumtreeApplication;

public class FavouritesSyncIntentService extends IntentService {
    public static final String ACTION_FAVOURITES_FAILURE_SYNC = "com.gumtree.android.ACTION_FAVOURITES_FAILURE_SYNC";
    public static final String ACTION_FAVOURITES_FINISHED_SYNC = "com.gumtree.android.ACTION_FAVOURITES_FINISHED_SYNC";
    public static final String ACTION_FAVOURITES_NETWORK_ERROR_SYNC = "com.gumtree.android.ACTION_FAVOURITES_NETWORK_ERROR_SYNC";
    public static final String COM_GUMTREE_ANDROID = "com.gumtree.android";
    private final Context mContext = GumtreeApplication.getContext();

    public FavouritesSyncIntentService() {
        super("FavouritesSyncIntentService");
    }

    public static void start() {
        Context context = GumtreeApplication.getContext();
        context.startService(new Intent(context, FavouritesSyncIntentService.class));
    }

    protected void onHandleIntent(Intent intent) {
        try {
            new SavedFavouritesSync(this.mContext).execute();
            this.mContext.sendBroadcast(new Intent(ACTION_FAVOURITES_FINISHED_SYNC));
        } catch (Exception e) {
            if ((e instanceof ResponseException) && ((ResponseException) e).getError().isNetwork()) {
                this.mContext.sendBroadcast(new Intent(ACTION_FAVOURITES_NETWORK_ERROR_SYNC));
            } else {
                this.mContext.sendBroadcast(new Intent(ACTION_FAVOURITES_FAILURE_SYNC));
            }
        }
    }
}
