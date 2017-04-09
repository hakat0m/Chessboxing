package com.gumtree.android.vip.api;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.AdSlot.AdSlotItem;
import com.gumtree.android.ads.parser.AdParser;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.model.Ads;
import com.gumtree.android.vip.VIPContactFragment;
import com.gumtree.android.vip.model.Advert;
import javax.inject.Inject;

public class VipDetailsIntentService extends IntentService {
    private static final String EQUAL_QUESTION_MARK = "=?";
    private static final String ID = "id";
    private static final String IS_LIST = "is_list";
    private static final String IS_NEARBY = "is_nearby";
    private static final String IS_VIP = "is_vip";
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;

    public class AdEvent {
        private final Ad advert;
        private final String error;

        private AdEvent(Ad ad) {
            this.advert = ad;
            this.error = null;
        }

        private AdEvent(String str) {
            this.error = str;
            this.advert = null;
        }

        protected static AdEvent success(Ad ad) {
            return new AdEvent(ad);
        }

        protected static AdEvent failure(String str) {
            return new AdEvent(str);
        }

        public boolean isSuccess() {
            return this.error == null;
        }

        public Ad getAdvert() {
            return this.advert;
        }
    }

    public VipDetailsIntentService() {
        super("DETAILS");
        GumtreeApplication.component().inject(this);
    }

    public static void start(long j, Context context) {
        Intent intent = new Intent(context, VipDetailsIntentService.class);
        intent.putExtra(ID, j);
        intent.putExtra(IS_VIP, false);
        context.startService(intent);
    }

    public static void start(long j, boolean z, boolean z2, Context context) {
        Intent intent = new Intent(context, VipDetailsIntentService.class);
        intent.putExtra(ID, j);
        intent.putExtra(IS_NEARBY, z);
        intent.putExtra(IS_VIP, true);
        intent.putExtra(IS_LIST, true);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        String valueOf = String.valueOf(intent.getLongExtra(ID, 0));
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        String str = "ads/" + valueOf + ".json";
        capiClient.withParam("_metadata", "true");
        Result execute = capiClient.get(str).execute(new AdParser());
        if (execute.hasError()) {
            this.eventBus.post(AdEvent.failure(execute.getError().getMessage()));
            trackView(intent, null);
            return;
        }
        try {
            insertIntoDB(valueOf, execute).execute();
        } catch (Exception e) {
            this.eventBus.post(AdEvent.failure(ResultError.build(e).getMessage()));
        }
        trackView(intent, (Ad) execute.getData());
        this.eventBus.post(AdEvent.success((Ad) execute.getData()));
    }

    private void trackView(Intent intent, Ad ad) {
        boolean booleanExtra = intent.getBooleanExtra(IS_NEARBY, false);
        boolean booleanExtra2 = intent.getBooleanExtra(IS_VIP, false);
        boolean booleanExtra3 = intent.getBooleanExtra(IS_LIST, true);
        String valueOf = String.valueOf(intent.getLongExtra(ID, 0));
        if (booleanExtra2) {
            String str = BuildConfig.FLAVOR;
            if (!(ad == null || ad.getAdSlot() == null || ad.getAdSlot().getAdSlotItemList().size() <= 0)) {
                str = ((AdSlotItem) ad.getAdSlot().getAdSlotItemList().get(0)).getVipCustomTab().getLabelText();
            }
            Cursor loadInBackground = getAdLoader(valueOf).loadInBackground();
            if (loadInBackground.moveToFirst()) {
                Advert advert = new Advert(loadInBackground);
                Track.viewVIP(advert.getCategoryId(), advert.getCategoryName(), valueOf, booleanExtra, booleanExtra3, str);
            }
            loadInBackground.close();
        }
    }

    private Batch insertIntoDB(String str, Result<Ad> result) {
        Batch createBatchOperation = new ContentProviderDataStorage(getApplicationContext()).createBatchOperation();
        AdDao adDao = new AdDao();
        if (getContentResolver().query(Ads.URI, null, "uid=?", new String[]{str}, null).getCount() == 0) {
            adDao.insert(str, (Ad) result.getData(), createBatchOperation);
        } else {
            adDao.update((Ad) result.getData(), createBatchOperation);
        }
        return createBatchOperation;
    }

    private CursorLoader getAdLoader(String str) {
        return new CursorLoader(getApplicationContext(), Ads.URI, new String[]{"uid", VIPContactFragment.CATEGORY_ID, "category_name", NewPostAdCategoryActivity.EXTRA_TITLE, "website_link", StatefulActivity.EXTRA_VISIBLE_MAP, StatefulActivity.NAME_LATITUDE, StatefulActivity.NAME_LONGITUDE, "full_location_name", "price_amount", "description", "urgent", "freespee", "start_date_time", "phone", "reply", "reply_url", "contact_name", "contact_postingSince", "reply_template", "user_id", "price_frequency", "ad_slot", "account_id"}, "uid=?", new String[]{str}, null);
    }
}
