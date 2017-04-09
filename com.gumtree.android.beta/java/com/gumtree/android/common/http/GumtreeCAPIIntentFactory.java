package com.gumtree.android.common.http;

import android.support.annotation.NonNull;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.EnvironmentSettings;
import com.gumtree.android.api.environment.Environment;
import java.util.Locale;
import org.apache.commons.lang3.Validate;

public class GumtreeCAPIIntentFactory extends CAPIIntentFactory {
    public static final String PROD_ENDPOINT = Environment.LIVE.getBaseCapiEndpoint();
    private final EnvironmentSettings environmentSettings;

    public GumtreeCAPIIntentFactory(@NonNull EnvironmentSettings environmentSettings) {
        this.environmentSettings = (EnvironmentSettings) Validate.notNull(environmentSettings);
    }

    public String getBaseUrl() {
        if (GumtreeApplication.isDebug()) {
            return this.environmentSettings.getEnvironment().getBaseCapiEndpoint();
        }
        return PROD_ENDPOINT;
    }

    public String getRegistrationUrl() {
        if (isQA()) {
            return "https://my.qa6.gumtree.com/login";
        }
        return "https://my.gumtree.com/create-account/";
    }

    public String getForgotPasswordUrl() {
        if (isQA()) {
            return "https://my.qa6.gumtree.com/forgotten-password";
        }
        return "https://my.gumtree.com/forgotten-password";
    }

    public String getUpdateEmailUrl() {
        if (isQA()) {
            return "https://my.qa6.gumtree.com/manage-account/";
        }
        return "https://my.gumtree.com/manage-account/";
    }

    public Locale getLocale() {
        return Locale.UK;
    }

    public String getFixedPathIfAny() {
        return "api";
    }

    public boolean isQA() {
        return getBaseUrl().contains("qa");
    }
}
