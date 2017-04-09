package com.gumtree.android.favourites;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ads.parser.AdsListParser;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import javax.inject.Inject;

public class FavouriteCapiIntentService extends IntentService {
    private static final String EXTRA_AD_ID = "extra_ad";
    private static final String EXTRA_MAKE_FAVOURITE = "extra_favourite";
    private static final String FAVOURITE = "favourite";
    private static final String UNFAVOURITE = "unfavourite";
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;
    private ICapiClient mCapi = this.capiClientManager.getCapiClient();
    private SavedFavouritesDao mSavedFavouritesDao = new SavedFavouritesDao(GumtreeApplication.getContext());

    public FavouriteCapiIntentService() {
        super("FavouriteCapiIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void saveServerFavourite(String str, boolean z) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, FavouriteCapiIntentService.class);
        intent.putExtra(EXTRA_AD_ID, str);
        intent.putExtra(EXTRA_MAKE_FAVOURITE, z);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_AD_ID);
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_MAKE_FAVOURITE, false);
        try {
            Result execute;
            String format = String.format("users/%s/watchlist/%s.json", new Object[]{this.accountManager.getUsername(), stringExtra});
            this.mCapi.authorize(this.accountManager.getAuthentication());
            if (booleanExtra) {
                execute = this.mCapi.put(format).execute(new AdsListParser(format, String.valueOf(System.currentTimeMillis())));
            } else {
                execute = this.mCapi.delete(format).execute(new AdsListParser(format, String.valueOf(System.currentTimeMillis())));
            }
            if (!execute.hasError()) {
                try {
                    this.mSavedFavouritesDao.confirmFavourite(stringExtra, booleanExtra);
                    sendFavouriteUpdateEvent(stringExtra, booleanExtra);
                } catch (Throwable e) {
                    Log.e("Error when trying to confirm a" + (booleanExtra ? FAVOURITE : UNFAVOURITE) + " ad in the database" + stringExtra, e);
                }
            }
        } catch (Throwable e2) {
            Log.e("There was an error trying to " + (booleanExtra ? FAVOURITE : UNFAVOURITE) + " an ad", e2);
        }
    }

    private void sendFavouriteUpdateEvent(String str, boolean z) {
        Intent intent = new Intent(FavouriteIntentService.ACTION_AD_FAVOURITED);
        intent.putExtra(FavouriteIntentService.AD_FAVOURITED_ID, str);
        intent.putExtra(FavouriteIntentService.AD_FAVOURITED_MAKE_FAVOURITED, z);
        GumtreeApplication.getContext().sendBroadcast(intent);
    }
}
