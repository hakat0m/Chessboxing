package com.ebay.classifieds.capi.features;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "feature-booked", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class FeatureBooked {
    @Element(name = "feature-price", required = false)
    private final Price featurePrice;
    @Attribute(name = "group", required = false)
    private final String group;
    @Attribute(name = "name", required = false)
    private final String name;
    @Element(name = "option", required = false)
    private final Option option;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeatureBooked)) {
            return false;
        }
        FeatureBooked featureBooked = (FeatureBooked) obj;
        String group = getGroup();
        String group2 = featureBooked.getGroup();
        if (group != null ? !group.equals(group2) : group2 != null) {
            return false;
        }
        group = getName();
        group2 = featureBooked.getName();
        if (group != null ? !group.equals(group2) : group2 != null) {
            return false;
        }
        Option option = getOption();
        Option option2 = featureBooked.getOption();
        if (option != null ? !option.equals(option2) : option2 != null) {
            return false;
        }
        Price featurePrice = getFeaturePrice();
        Price featurePrice2 = featureBooked.getFeaturePrice();
        if (featurePrice == null) {
            if (featurePrice2 == null) {
                return true;
            }
        } else if (featurePrice.equals(featurePrice2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String group = getGroup();
        int hashCode = (group == null ? 43 : group.hashCode()) + 59;
        String name = getName();
        hashCode = (name == null ? 43 : name.hashCode()) + (hashCode * 59);
        Option option = getOption();
        hashCode = (option == null ? 43 : option.hashCode()) + (hashCode * 59);
        Price featurePrice = getFeaturePrice();
        hashCode *= 59;
        if (featurePrice != null) {
            i = featurePrice.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "FeatureBooked(group=" + getGroup() + ", name=" + getName() + ", option=" + getOption() + ", featurePrice=" + getFeaturePrice() + ")";
    }

    public static FeatureBookedBuilder builder() {
        return new FeatureBookedBuilder();
    }

    public String getGroup() {
        return this.group;
    }

    public String getName() {
        return this.name;
    }

    public Option getOption() {
        return this.option;
    }

    public Price getFeaturePrice() {
        return this.featurePrice;
    }

    public FeatureBooked(@Attribute(name = "group") String str, @Attribute(name = "name") String str2, @Element(name = "option") Option option, @Element(name = "feature-price") Price price) {
        this.group = str;
        this.name = str2;
        this.option = option;
        this.featurePrice = price;
    }
}
