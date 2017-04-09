package com.gumtree.android.common.http;

import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.common.http.model.ICapiClient;

public class CapiClientManager {
    private final ApplicationDataManager applicationDataManager;
    private CapiClientFactory capiClientFactory;

    public interface CapiClientFactory {
        ICapiClient getCapiClient(boolean z, ApplicationDataManager applicationDataManager);
    }

    public CapiClientManager(ApplicationDataManager applicationDataManager, CapiClientFactory capiClientFactory) {
        this.capiClientFactory = capiClientFactory;
        this.applicationDataManager = applicationDataManager;
    }

    public ICapiClient getCapiClient() {
        return this.capiClientFactory.getCapiClient(false, this.applicationDataManager);
    }

    public ICapiClient getOkHttpCapiClient() {
        return this.capiClientFactory.getCapiClient(true, this.applicationDataManager);
    }

    public void setCapiClientFactory(CapiClientFactory capiClientFactory) {
        this.capiClientFactory = capiClientFactory;
    }
}
