package com.gumtree.android.locations;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.locations.Location;
import com.ebay.classifieds.capi.locations.LocationsApi;
import com.gumtree.android.GumtreeApplication;
import javax.inject.Inject;
import javax.inject.Named;

public class LocationChildrenRequest implements Request<Location> {
    @Named("xmlClient")
    @Inject
    ICapiClient capiClient;
    private final long locationId;

    public LocationChildrenRequest(long j) {
        GumtreeApplication.component().inject(this);
        this.locationId = j;
    }

    public Result<Location> execute() {
        try {
            return new Result(((LocationsApi) this.capiClient.api(LocationsApi.class)).location(this.locationId, 1).toBlocking().first());
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getCause() instanceof ApiException) {
                return new Result((ApiException) e.getCause());
            }
            throw e;
        }
    }
}
