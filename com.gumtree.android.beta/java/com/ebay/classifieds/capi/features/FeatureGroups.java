package com.ebay.classifieds.capi.features;

import java.util.List;
import org.apache.commons.lang3.Validate;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class FeatureGroups {
    @ElementList(name = "feature-group")
    private final List<Feature> featureGroup;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeatureGroups)) {
            return false;
        }
        FeatureGroups featureGroups = (FeatureGroups) obj;
        List featureGroup = getFeatureGroup();
        List featureGroup2 = featureGroups.getFeatureGroup();
        if (featureGroup == null) {
            if (featureGroup2 == null) {
                return true;
            }
        } else if (featureGroup.equals(featureGroup2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        List featureGroup = getFeatureGroup();
        return (featureGroup == null ? 43 : featureGroup.hashCode()) + 59;
    }

    public String toString() {
        return "FeatureGroups(featureGroup=" + getFeatureGroup() + ")";
    }

    public List<Feature> getFeatureGroup() {
        return this.featureGroup;
    }

    public static FeatureGroupsBuilder builder() {
        return new FeatureGroupsBuilder();
    }

    public FeatureGroups(@ElementList(name = "feature-group") List<Feature> list) {
        this.featureGroup = (List) Validate.notEmpty(list);
    }
}
