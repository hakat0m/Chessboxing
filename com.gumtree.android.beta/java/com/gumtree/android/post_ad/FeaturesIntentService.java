package com.gumtree.android.post_ad;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnFeaturesReceivedEvent;
import com.gumtree.android.features.parser.FeaturesParser;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.model.Features;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.inject.Inject;

public class FeaturesIntentService extends IntentService {
    private static final String EXTRA_AD_ID = "AdId";
    private static final String EXTRA_ATTRIBUTES = "Attributes";
    private static final String EXTRA_CATEGORY_ID = "CategoryId";
    private static final String EXTRA_LOCATION_ID = "LocationId";
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    BaseAccountManager mAccountManager;
    @Inject
    EventBus mEventBus;

    public FeaturesIntentService() {
        super("FeaturesIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void start(String str, String str2, String str3, HashMap<String, String> hashMap) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, FeaturesIntentService.class);
        intent.putExtra(EXTRA_CATEGORY_ID, str);
        intent.putExtra(EXTRA_LOCATION_ID, str2);
        intent.putExtra(EXTRA_AD_ID, str3);
        intent.putExtra(EXTRA_ATTRIBUTES, hashMap);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        getApplicationContext().getContentResolver().delete(Features.URI, null, null);
        String stringExtra = intent.getStringExtra(EXTRA_CATEGORY_ID);
        String stringExtra2 = intent.getStringExtra(EXTRA_LOCATION_ID);
        String stringExtra3 = intent.getStringExtra(EXTRA_AD_ID);
        HashMap hashMap = (HashMap) intent.getSerializableExtra(EXTRA_ATTRIBUTES);
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        capiClient.authorize(this.mAccountManager.getAuthentication());
        String str = "features/ad.json";
        try {
            String str2;
            ResultError build;
            ICapiClient withParam = capiClient.get("features/ad.json").withParam(StatefulActivity.NAME_LOCATION_ID, stringExtra2.isEmpty() ? "10000392" : stringExtra2);
            String str3 = StatefulActivity.NAME_CATEGORY_ID;
            if (stringExtra.isEmpty()) {
                str2 = PaymentConverter.DEFAULT_PAYMENT_METHOD_ID;
            } else {
                str2 = stringExtra;
            }
            capiClient = withParam.withParam(str3, str2);
            if (!(stringExtra3 == null || stringExtra3.equals(DraftAd.NEW_AD_ID))) {
                capiClient.withParam("adId", stringExtra3);
            }
            addAttributeParams(capiClient, hashMap);
            Result executeDatabase = capiClient.executeDatabase(new FeaturesParser(stringExtra, stringExtra2), new ContentProviderDataStorage(getApplicationContext()));
            List list = (List) executeDatabase.getData();
            if (list == null) {
                Exception runtimeException = new RuntimeException(executeDatabase.getError().getMessage());
                build = ResultError.build(runtimeException);
                CrashlyticsHelper.getInstance().catchGumtreeException(runtimeException);
            } else {
                build = null;
            }
            this.mEventBus.post(new OnFeaturesReceivedEvent(list, build));
        } catch (Exception e) {
            this.mEventBus.post(new OnFeaturesReceivedEvent(null, ResultError.build(e)));
        }
    }

    private void addAttributeParams(ICapiClient iCapiClient, HashMap<String, String> hashMap) {
        for (Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            iCapiClient.withParam(getAttributeKey(str), (String) entry.getValue());
        }
    }

    private String getAttributeKey(String str) {
        return String.format("attr[%s]", new Object[]{str});
    }
}
