package com.ebay.classifieds.capi.features;

import com.ebay.classifieds.capi.ads.CurrencyIsoCode;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class Price {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "amount")
    private final double amount;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "currency-iso-code")
    private final CurrencyIsoCode currencyIsoCode;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "reason", required = false)
    private final String reason;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Price)) {
            return false;
        }
        Price price = (Price) obj;
        if (Double.compare(getAmount(), price.getAmount()) != 0) {
            return false;
        }
        CurrencyIsoCode currencyIsoCode = getCurrencyIsoCode();
        CurrencyIsoCode currencyIsoCode2 = price.getCurrencyIsoCode();
        if (currencyIsoCode != null ? !currencyIsoCode.equals(currencyIsoCode2) : currencyIsoCode2 != null) {
            return false;
        }
        String reason = getReason();
        String reason2 = price.getReason();
        if (reason == null) {
            if (reason2 == null) {
                return true;
            }
        } else if (reason.equals(reason2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        long doubleToLongBits = Double.doubleToLongBits(getAmount());
        int i2 = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 59;
        CurrencyIsoCode currencyIsoCode = getCurrencyIsoCode();
        i2 = (currencyIsoCode == null ? 43 : currencyIsoCode.hashCode()) + (i2 * 59);
        String reason = getReason();
        i2 *= 59;
        if (reason != null) {
            i = reason.hashCode();
        }
        return i2 + i;
    }

    public String toString() {
        return "Price(amount=" + getAmount() + ", currencyIsoCode=" + getCurrencyIsoCode() + ", reason=" + getReason() + ")";
    }

    public double getAmount() {
        return this.amount;
    }

    public CurrencyIsoCode getCurrencyIsoCode() {
        return this.currencyIsoCode;
    }

    public String getReason() {
        return this.reason;
    }

    public Price(@Element(name = "amount") double d, @Element(name = "currency-iso-code") CurrencyIsoCode currencyIsoCode, @Element(name = "reason") String str) {
        this.amount = d;
        this.currencyIsoCode = currencyIsoCode;
        this.reason = str;
    }
}
