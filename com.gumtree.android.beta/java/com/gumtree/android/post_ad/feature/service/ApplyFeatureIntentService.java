package com.gumtree.android.post_ad.feature.service;

import android.app.IntentService;
import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.features.OrderConfirmation;
import com.gumtree.android.features.parser.ApplyFeatureRequestSerializer;
import java.io.IOException;
import javax.inject.Inject;

public class ApplyFeatureIntentService extends IntentService {
    private static final String ORDER = "order";
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;

    public ApplyFeatureIntentService() {
        super("ApplyFeatureIntentService");
    }

    public static void send(OrderConfirmation orderConfirmation) {
        Intent intent = new Intent(GumtreeApplication.getContext(), ApplyFeatureIntentService.class);
        intent.putExtra(ORDER, orderConfirmation);
        GumtreeApplication.getContext().startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        ComponentsManager.get().getAppComponent().inject(this);
        OrderConfirmation orderConfirmation = (OrderConfirmation) intent.getSerializableExtra(ORDER);
        try {
            String convertToXml = convertToXml(orderConfirmation);
            try {
                ICapiClient capiClient = this.capiClientManager.getCapiClient();
                capiClient.authorize(this.accountManager.getAuthentication());
                capiClient.withContent(convertToXml);
                capiClient.post(getRelativeUrl(orderConfirmation.getAdId()));
                capiClient.execute();
            } catch (Throwable e) {
                CrashlyticsHelper.getInstance().catchGumtreeException(e);
            }
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    private String getRelativeUrl(String str) {
        return "features/ad/" + str;
    }

    private String convertToXml(OrderConfirmation orderConfirmation) throws IOException {
        return new ApplyFeatureRequestSerializer(orderConfirmation).serialize();
    }
}
