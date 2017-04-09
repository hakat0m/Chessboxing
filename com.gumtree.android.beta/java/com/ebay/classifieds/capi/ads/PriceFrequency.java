package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "types", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class PriceFrequency {
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value", required = false)
    private String value;

    protected boolean canEqual(Object obj) {
        return obj instanceof PriceFrequency;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PriceFrequency)) {
            return false;
        }
        PriceFrequency priceFrequency = (PriceFrequency) obj;
        if (!priceFrequency.canEqual(this)) {
            return false;
        }
        String value = getValue();
        String value2 = priceFrequency.getValue();
        if (value == null) {
            if (value2 == null) {
                return true;
            }
        } else if (value.equals(value2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String value = getValue();
        return (value == null ? 43 : value.hashCode()) + 59;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return "PriceFrequency(value=" + getValue() + ")";
    }

    public String getValue() {
        return this.value;
    }
}
