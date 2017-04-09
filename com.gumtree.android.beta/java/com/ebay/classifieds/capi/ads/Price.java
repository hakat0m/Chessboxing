package com.ebay.classifieds.capi.ads;

import java.beans.ConstructorProperties;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "price", strict = false)
@Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
public class Price {
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "amount", required = false)
    private String amount;
    @Element(name = "currency-iso-code", required = false)
    private CurrencyIsoCode currencyIssueCode;

    @ConstructorProperties({"amount", "currencyIssueCode"})
    public Price(String str, CurrencyIsoCode currencyIsoCode) {
        this.amount = str;
        this.currencyIssueCode = currencyIsoCode;
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof Price;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Price)) {
            return false;
        }
        Price price = (Price) obj;
        if (!price.canEqual(this)) {
            return false;
        }
        String amount = getAmount();
        String amount2 = price.getAmount();
        if (amount != null ? !amount.equals(amount2) : amount2 != null) {
            return false;
        }
        CurrencyIsoCode currencyIssueCode = getCurrencyIssueCode();
        CurrencyIsoCode currencyIssueCode2 = price.getCurrencyIssueCode();
        if (currencyIssueCode == null) {
            if (currencyIssueCode2 == null) {
                return true;
            }
        } else if (currencyIssueCode.equals(currencyIssueCode2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String amount = getAmount();
        int hashCode = (amount == null ? 43 : amount.hashCode()) + 59;
        CurrencyIsoCode currencyIssueCode = getCurrencyIssueCode();
        hashCode *= 59;
        if (currencyIssueCode != null) {
            i = currencyIssueCode.hashCode();
        }
        return hashCode + i;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public void setCurrencyIssueCode(CurrencyIsoCode currencyIsoCode) {
        this.currencyIssueCode = currencyIsoCode;
    }

    public String toString() {
        return "Price(amount=" + getAmount() + ", currencyIssueCode=" + getCurrencyIssueCode() + ")";
    }

    public String getAmount() {
        return this.amount;
    }

    public CurrencyIsoCode getCurrencyIssueCode() {
        return this.currencyIssueCode;
    }
}
