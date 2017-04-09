package com.gumtree.android.events;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.features.Feature;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class OnFeaturesReceivedEvent {
    private final List<Feature> mFeatures;
    private final ResultError mResultError;

    public OnFeaturesReceivedEvent(List<Feature> list, ResultError resultError) {
        int i;
        int i2 = 1;
        if (list == null) {
            i = 1;
        } else {
            i = 0;
        }
        if (resultError != null) {
            i2 = 0;
        }
        Validate.isTrue(i2 ^ i, "Exactly one of Result and Error must be null!", new Object[0]);
        this.mFeatures = list;
        this.mResultError = resultError;
    }

    @NonNull
    public List<Feature> getFeatures() {
        return this.mFeatures;
    }

    public ResultError getResultError() {
        return this.mResultError;
    }
}
