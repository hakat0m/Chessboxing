package com.gumtree.android.api.bing;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class BingAdsImpressionRequest {
    private transient String rguid;
    private List<ShownAd> shownAds = new ArrayList();

    public void add(BingAd bingAd) {
        this.shownAds.add(new ShownAd(bingAd.getPosition(), bingAd.getImpressionToken()));
    }

    public List<ShownAd> getShownAds() {
        return this.shownAds;
    }

    public void addAll(@NonNull List<BingAd> list) {
        for (BingAd add : list) {
            add(add);
        }
    }

    public void setRguid(String str) {
        this.rguid = str;
    }

    public String getRguid() {
        return this.rguid;
    }
}
