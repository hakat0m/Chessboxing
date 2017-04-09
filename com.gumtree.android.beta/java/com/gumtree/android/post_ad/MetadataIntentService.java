package com.gumtree.android.post_ad;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnMetadataReceivedEvent;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.metadata.parser.MetadataParser;
import com.gumtree.android.model.Metadata;
import com.gumtree.android.model.MetadataSupportedValuesOptions;
import java.util.List;
import javax.inject.Inject;

public class MetadataIntentService extends IntentService {
    private static final String EXTRA_CATEGORY_ID = "CategoryId";
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus mEventBus;

    public MetadataIntentService() {
        super("MetadataIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void start(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, MetadataIntentService.class);
        intent.putExtra(EXTRA_CATEGORY_ID, str);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        getContentResolver().delete(Metadata.URI, null, null);
        getContentResolver().delete(MetadataSupportedValuesOptions.URI, null, null);
        String stringExtra = intent.getStringExtra(EXTRA_CATEGORY_ID);
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        capiClient.authorize(this.accountManager.getAuthentication());
        stringExtra = "ads/metadata/" + stringExtra + ".json";
        try {
            ResultError build;
            Result executeDatabase = capiClient.get(stringExtra).executeDatabase(new MetadataParser(stringExtra, new UrlMatcher(BuildConfig.FLAVOR).getIdFromUrl(stringExtra)), new ContentProviderDataStorage(getApplicationContext()));
            List list = (List) executeDatabase.getData();
            if (list == null) {
                build = ResultError.build(new RuntimeException(executeDatabase.getError().getMessage()));
            } else {
                build = null;
            }
            this.mEventBus.post(new OnMetadataReceivedEvent(list, build));
        } catch (Exception e) {
            this.mEventBus.post(new OnMetadataReceivedEvent(null, ResultError.build(e)));
        }
    }
}
