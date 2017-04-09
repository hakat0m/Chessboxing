package com.gumtree.android.managead;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.EnvironmentSettings;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.http.GumtreeCAPIIntentFactory;
import com.gumtree.android.common.http.HttpIntentRequest;
import com.gumtree.android.common.transport.Response;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnAdDeletedEvent;
import com.gumtree.android.model.ManagedAds;
import com.gumtree.android.vip.VIPContactFragment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DeleteAdIntentService extends IntentService {
    public static final String SHOULD_SEND_EVENT = "shouldSendEvent";
    @Inject
    EnvironmentSettings environmentSettings;
    @Inject
    EventBus eventBus;

    class Ad {
        private String categoryId;
        private String id;

        private Ad() {
        }
    }

    public DeleteAdIntentService() {
        super("com.gumtree.android.AD_DELETE");
        GumtreeApplication.component().inject(this);
    }

    public static void delete(boolean z) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, DeleteAdIntentService.class);
        intent.putExtra(SHOULD_SEND_EVENT, z);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(SHOULD_SEND_EVENT, false);
        List<Ad> ads = getAds();
        List arrayList = new ArrayList();
        for (Ad ad : ads) {
            try {
                Track.eventDeleteAdAttempt(ad.categoryId, ad.id);
                Response executeDeleteOnServer = executeDeleteOnServer(ad);
                if (executeDeleteOnServer.getStatusCode() == 204) {
                    deleteAd(ad.id);
                    Track.eventDeleteAdSuccess(ad.categoryId, ad.id);
                } else {
                    arrayList.add(new ResponseException(ResultError.build(executeDeleteOnServer.getStatusCode())));
                    Track.eventDeleteAdFail(ad.categoryId, ad.id);
                }
            } catch (Throwable e) {
                CrashlyticsHelper.getInstance().catchGumtreeException(e);
                Track.eventDeleteAdFail(ad.categoryId, ad.id);
            }
        }
        if (booleanExtra) {
            this.eventBus.post(new OnAdDeletedEvent(true, arrayList));
        }
    }

    private Response executeDeleteOnServer(Ad ad) throws IOException {
        Response response = null;
        try {
            response = new HttpIntentRequest(new GumtreeCAPIIntentFactory(this.environmentSettings).getHttpIntentForAdDelete(ad.id, null)).makeRequest(GumtreeApplication.getHttpTransport());
            return response;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void deleteAd(String str) {
        getContentResolver().delete(ManagedAds.URI, "_id=?", new String[]{str});
    }

    public List<Ad> getAds() {
        Cursor query;
        Throwable e;
        List<Ad> arrayList = new ArrayList();
        try {
            query = getContentResolver().query(ManagedAds.URI, new String[]{"_id", VIPContactFragment.CATEGORY_ID}, "is_deleted=1", null, null);
            while (query.moveToNext()) {
                try {
                    Ad ad = new Ad();
                    ad.id = query.getString(query.getColumnIndex("_id"));
                    ad.categoryId = query.getString(query.getColumnIndex(VIPContactFragment.CATEGORY_ID));
                    arrayList.add(ad);
                } catch (Exception e2) {
                    e = e2;
                }
            }
            query.close();
            if (query != null) {
                query.close();
            }
        } catch (Exception e3) {
            e = e3;
            query = null;
            try {
                CrashlyticsHelper.getInstance().catchGumtreeException(e);
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (Throwable th) {
                e = th;
                if (query != null) {
                    query.close();
                }
                throw e;
            }
        } catch (Throwable th2) {
            e = th2;
            query = null;
            if (query != null) {
                query.close();
            }
            throw e;
        }
        return arrayList;
    }
}
