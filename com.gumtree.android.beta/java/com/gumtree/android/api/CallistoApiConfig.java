package com.gumtree.android.api;

import android.support.annotation.NonNull;
import com.gumtree.android.api.callisto.CallistoConfig;
import java.net.URL;
import javax.inject.Inject;

public final class CallistoApiConfig implements CallistoConfig {
    private final EnvironmentSettings settings;

    @Inject
    public CallistoApiConfig(EnvironmentSettings environmentSettings) {
        this.settings = environmentSettings;
    }

    @NonNull
    public URL getBaseURL() {
        String baseCallistoEndpoint = this.settings.getEnvironment().getBaseCallistoEndpoint();
        try {
            return new URL(baseCallistoEndpoint);
        } catch (Throwable e) {
            throw new IllegalStateException("Cannot provide a decent base URL using " + baseCallistoEndpoint, e);
        }
    }
}
