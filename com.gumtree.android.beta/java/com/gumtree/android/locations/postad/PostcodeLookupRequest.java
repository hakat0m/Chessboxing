package com.gumtree.android.locations.postad;

import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.locations.Locations;
import com.ebay.classifieds.capi.locations.LocationsApi;
import com.gumtree.android.GumtreeApplication;
import javax.inject.Inject;
import javax.inject.Named;

public class PostcodeLookupRequest implements Request<Locations> {
    @Named("xmlClient")
    @Inject
    ICapiClient capiClient;
    private final String postCode;

    public PostcodeLookupRequest(String str) {
        GumtreeApplication.component().inject(this);
        this.postCode = str;
    }

    public Result<Locations> execute() {
        if (this.postCode == null) {
            return null;
        }
        return executeGet();
    }

    private Result<Locations> executeGet() {
        try {
            return new Result(((LocationsApi) this.capiClient.api(LocationsApi.class)).postcode(this.postCode.replaceAll("\\s+", BuildConfig.FLAVOR)).toBlocking().first());
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getCause() instanceof ApiException) {
                return new Result((ApiException) e.getCause());
            }
            throw e;
        }
    }
}
