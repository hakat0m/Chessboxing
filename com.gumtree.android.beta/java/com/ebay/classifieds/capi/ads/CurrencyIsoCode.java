package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "currency-iso-code", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public final class CurrencyIsoCode {
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value", required = false)
    private final String value;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CurrencyIsoCode)) {
            return false;
        }
        CurrencyIsoCode currencyIsoCode = (CurrencyIsoCode) obj;
        String value = getValue();
        String value2 = currencyIsoCode.getValue();
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
        return "CurrencyIsoCode(value=" + getValue() + ")";
    }

    public static CurrencyIsoCodeBuilder builder() {
        return new CurrencyIsoCodeBuilder();
    }

    public String getValue() {
        return this.value;
    }

    public CurrencyIsoCode(@Element(name = "value") String str) {
        this.value = str;
    }
}
