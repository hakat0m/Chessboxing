package com.gumtree.android.common.location;

public interface LocationProvider {
    AppLocation getDeviceLocation();

    AppLocation getGlobalBuyerLocation();

    void setDeviceLocation(AppLocation appLocation);

    void setGlobalBuyerLocation(AppLocation appLocation);
}
