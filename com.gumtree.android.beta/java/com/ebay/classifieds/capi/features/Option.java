package com.ebay.classifieds.capi.features;

import android.support.annotation.NonNull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "option", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class Option {
    @NonNull
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "feature-duration")
    private final FeatureDuration featureDuration;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "feature-price", required = false)
    private final Price price;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Option)) {
            return false;
        }
        Option option = (Option) obj;
        FeatureDuration featureDuration = getFeatureDuration();
        FeatureDuration featureDuration2 = option.getFeatureDuration();
        if (featureDuration != null ? !featureDuration.equals(featureDuration2) : featureDuration2 != null) {
            return false;
        }
        Price price = getPrice();
        Price price2 = option.getPrice();
        if (price == null) {
            if (price2 == null) {
                return true;
            }
        } else if (price.equals(price2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        FeatureDuration featureDuration = getFeatureDuration();
        int hashCode = (featureDuration == null ? 43 : featureDuration.hashCode()) + 59;
        Price price = getPrice();
        hashCode *= 59;
        if (price != null) {
            i = price.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "Option(featureDuration=" + getFeatureDuration() + ", price=" + getPrice() + ")";
    }

    public static OptionBuilder builder() {
        return new OptionBuilder();
    }

    @NonNull
    public FeatureDuration getFeatureDuration() {
        return this.featureDuration;
    }

    public Price getPrice() {
        return this.price;
    }

    public Option(@Element(name = "feature-duration") FeatureDuration featureDuration, @Element(name = "feature-price") Price price) {
        this.featureDuration = featureDuration;
        this.price = price;
    }
}
