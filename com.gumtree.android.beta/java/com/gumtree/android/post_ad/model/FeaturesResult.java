package com.gumtree.android.post_ad.model;

import com.gumtree.android.features.Feature;
import com.gumtree.android.features.Feature.FeatureType;
import java.io.Serializable;
import java.util.List;

public class FeaturesResult implements Serializable {
    public static final int NETWORK_ERROR = 2;
    public static final int PARSER_ERROR = 3;
    public static final int UNKNOWN_ERROR = 1;
    private int errorCode;
    private List<Feature> features;
    private Feature insertion;

    public boolean hasError() {
        if (this.errorCode == 0) {
            return false;
        }
        return true;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setError(int i) {
        this.errorCode = i;
    }

    public List<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<Feature> list) {
        this.features = list;
    }

    public Feature getInsertion() {
        if (this.insertion != null) {
            return this.insertion;
        }
        int indexOf = getFeatures().indexOf(new Feature(FeatureType.INSERTION.getValue()));
        if (indexOf >= 0) {
            return (Feature) getFeatures().get(indexOf);
        }
        return null;
    }

    public void setInsertion(Feature feature) {
        this.insertion = feature;
    }
}
