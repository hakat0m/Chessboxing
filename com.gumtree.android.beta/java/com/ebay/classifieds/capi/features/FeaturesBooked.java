package com.ebay.classifieds.capi.features;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "features-booked", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class FeaturesBooked {
    @ElementList(inline = true, name = "feat:feature-booked", required = false)
    private final List<FeatureBooked> featureBookedList;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeaturesBooked)) {
            return false;
        }
        FeaturesBooked featuresBooked = (FeaturesBooked) obj;
        List featureBookedList = getFeatureBookedList();
        List featureBookedList2 = featuresBooked.getFeatureBookedList();
        if (featureBookedList == null) {
            if (featureBookedList2 == null) {
                return true;
            }
        } else if (featureBookedList.equals(featureBookedList2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        List featureBookedList = getFeatureBookedList();
        return (featureBookedList == null ? 43 : featureBookedList.hashCode()) + 59;
    }

    public String toString() {
        return "FeaturesBooked(featureBookedList=" + getFeatureBookedList() + ")";
    }

    public List<FeatureBooked> getFeatureBookedList() {
        return this.featureBookedList;
    }

    public FeaturesBooked(@ElementList(name = "feature-booked") List<FeatureBooked> list) {
        this.featureBookedList = list;
    }
}
