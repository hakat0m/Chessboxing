package com.ebay.classifieds.capi.features;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "period", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public final class Period {
    public static final String DAY = "DAY";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value", required = false)
    private final String value;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PeriodValue {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Period)) {
            return false;
        }
        Period period = (Period) obj;
        String value = getValue();
        String value2 = period.getValue();
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

    public String toString() {
        return "Period(value=" + getValue() + ")";
    }

    public static PeriodBuilder builder() {
        return new PeriodBuilder();
    }

    public String getValue() {
        return this.value;
    }

    public Period(@Element(name = "value") String str) {
        this.value = str;
    }
}
