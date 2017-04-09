package com.ebay.classifieds.capi.features;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@NamespaceList({@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1"), @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")})
@Root(name = "feature-duration", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class FeatureDuration {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "period")
    private final Period period;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value")
    private final int value;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeatureDuration)) {
            return false;
        }
        FeatureDuration featureDuration = (FeatureDuration) obj;
        if (getValue() != featureDuration.getValue()) {
            return false;
        }
        Period period = getPeriod();
        Period period2 = featureDuration.getPeriod();
        if (period == null) {
            if (period2 == null) {
                return true;
            }
        } else if (period.equals(period2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int value = getValue() + 59;
        Period period = getPeriod();
        return (period == null ? 43 : period.hashCode()) + (value * 59);
    }

    public String toString() {
        return "FeatureDuration(value=" + getValue() + ", period=" + getPeriod() + ")";
    }

    public static FeatureDurationBuilder builder() {
        return new FeatureDurationBuilder();
    }

    public int getValue() {
        return this.value;
    }

    public Period getPeriod() {
        return this.period;
    }

    public FeatureDuration(@Element(name = "value") int i, @Element(name = "period") Period period) {
        this.value = i;
        this.period = period;
    }
}
