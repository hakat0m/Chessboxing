package com.gumtree.android.common.http;

import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.common.http.CapiClientManager.CapiClientFactory;
import com.gumtree.android.common.http.model.ICapiClient;

public class BaseCapiClientFactory implements CapiClientFactory {
    public ICapiClient getCapiClient(boolean z, ApplicationDataManager applicationDataManager) {
        return new CapiClient(GumtreeApplication.getBaseUrl(), GumtreeApplication.getTrackingInfo(), z, applicationDataManager);
    }
}
