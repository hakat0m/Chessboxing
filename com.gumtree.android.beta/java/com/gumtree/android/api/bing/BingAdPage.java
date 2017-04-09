package com.gumtree.android.api.bing;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BingAdPage {
    @SerializedName("value")
    private List<BingAd> bingAds;
    private String impressionFeedbackUrl;

    public String getImpressionFeedbackUrl() {
        return this.impressionFeedbackUrl;
    }

    public List<BingAd> getBingAds() {
        return this.bingAds;
    }
}
