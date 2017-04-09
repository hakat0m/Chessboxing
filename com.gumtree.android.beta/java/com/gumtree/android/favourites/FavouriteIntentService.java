package com.gumtree.android.favourites;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.dagger.ComponentsManager;
import javax.inject.Inject;

public class FavouriteIntentService extends IntentService {
    public static final String ACTION_AD_FAVOURITED = "com.gumtree.android.ACTION_AD_FAVOURITED";
    public static final String AD_FAVOURITED_ID = "com.gumtree.android.AD_FAVOURITED_ID";
    public static final String AD_FAVOURITED_MAKE_FAVOURITED = "com.gumtree.android.AD_FAVOURITED_MAKE_FAVOURITED";
    private static final String EXTRA_AD_ID = "extra_ad";
    private static final String EXTRA_MAKE_FAVOURITE = "extra_favourite";
    @Inject
    BaseAccountManager accountManager;
    private SavedFavouritesDao mSavedFavouritesDao = new SavedFavouritesDao(GumtreeApplication.getContext());

    public FavouriteIntentService() {
        super("FavouriteIntentService");
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public static void favourite(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, FavouriteIntentService.class);
        intent.putExtra(EXTRA_AD_ID, str);
        intent.putExtra(EXTRA_MAKE_FAVOURITE, true);
        context.startService(intent);
    }

    public static void unfavourite(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, FavouriteIntentService.class);
        intent.putExtra(EXTRA_AD_ID, str);
        intent.putExtra(EXTRA_MAKE_FAVOURITE, false);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_AD_ID);
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_MAKE_FAVOURITE, false);
        if (!updateInLocalDB(stringExtra, booleanExtra) && this.accountManager.isUserLoggedIn()) {
            FavouriteCapiIntentService.saveServerFavourite(stringExtra, booleanExtra);
        }
    }

    private boolean updateInLocalDB(String str, boolean z) {
        Throwable th;
        boolean z2 = false;
        try {
            boolean updateFavourite = this.mSavedFavouritesDao.updateFavourite(str, z);
            try {
                sendFavouriteUpdateEvent(str, z);
                return updateFavourite;
            } catch (Throwable e) {
                th = e;
                z2 = updateFavourite;
            }
        } catch (Throwable e2) {
            th = e2;
            Log.e("Error when trying to " + (z ? "favourite" : "unfavourite") + " ad with id" + str, th);
            return z2;
        }
    }

    private void sendFavouriteUpdateEvent(String str, boolean z) {
        Intent intent = new Intent(ACTION_AD_FAVOURITED);
        intent.putExtra(AD_FAVOURITED_ID, str);
        intent.putExtra(AD_FAVOURITED_MAKE_FAVOURITED, z);
        GumtreeApplication.getContext().sendBroadcast(intent);
    }
}
