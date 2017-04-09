package com.gumtree.android.post_ad.feature.service;

import android.app.IntentService;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnOrderCreatedEvent;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.OrderConfirmation;
import com.gumtree.android.features.parser.OrderConfirmationParser;
import com.gumtree.android.features.parser.OrderRequestSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

public class SubmitOrderIntentService extends IntentService {
    private static final String AD_ID = "adId";
    private static final String AD_ID_SPACE = " adId ";
    private static final String FEATURE = "feature : ";
    private static final String FEATURES = "features";
    @Inject
    BaseAccountManager accountManager;
    private ICapiClient capi;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus eventBus;

    public SubmitOrderIntentService() {
        super("SubmitOrderIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void submit(ArrayList<Feature> arrayList, String str) {
        Intent intent = new Intent(GumtreeApplication.getContext(), SubmitOrderIntentService.class);
        intent.putExtra(FEATURES, arrayList);
        intent.putExtra(AD_ID, str);
        GumtreeApplication.getContext().startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        Iterator it;
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra(FEATURES);
        String stringExtra = intent.getStringExtra(AD_ID);
        if (Log.verboseLoggingEnabled()) {
            it = arrayList.iterator();
            while (it.hasNext()) {
                Log.v(FEATURE + ((Feature) it.next()) + AD_ID_SPACE + stringExtra);
            }
        }
        it = arrayList.iterator();
        while (it.hasNext()) {
            Log.v(FEATURE + ((Feature) it.next()) + AD_ID_SPACE + stringExtra);
        }
        try {
            String convertToXml = convertToXml(arrayList, stringExtra);
            this.capi = this.capiClientManager.getCapiClient();
            this.capi.authorize(this.accountManager.getAuthentication());
            this.capi.withContent(convertToXml);
            this.capi.post(getRelativeUrl());
            Result execute = this.capi.execute(new OrderConfirmationParser());
            if (!(execute == null || execute.getData() == null || ((OrderConfirmation) execute.getData()).getAdId() != null)) {
                ((OrderConfirmation) execute.getData()).setAdId(stringExtra);
            }
            this.eventBus.post(new OnOrderCreatedEvent(execute));
        } catch (IOException e) {
            this.eventBus.post(new OnOrderCreatedEvent(new Result(ResultError.unknown())));
        }
    }

    private String getRelativeUrl() {
        return "orders";
    }

    private String convertToXml(ArrayList<Feature> arrayList, String str) throws IOException {
        return new OrderRequestSerializer(arrayList).createOrderRequest(str);
    }
}
