package com.ebay.classifieds.capi.order;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "payment", strict = false)
public final class PaypalExpressCheckout {
    @Namespace(prefix = "payment", reference = "http://www.ebayclassifiedsgroup.com/schema/payment/v1")
    @Element(name = "cancel-url", required = false)
    private final String cancelURL;
    @Namespace(prefix = "payment", reference = "http://www.ebayclassifiedsgroup.com/schema/payment/v1")
    @Element(name = "confirm-url", required = false)
    private final String confirmURL;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PaypalExpressCheckout)) {
            return false;
        }
        PaypalExpressCheckout paypalExpressCheckout = (PaypalExpressCheckout) obj;
        String confirmURL = getConfirmURL();
        String confirmURL2 = paypalExpressCheckout.getConfirmURL();
        if (confirmURL != null ? !confirmURL.equals(confirmURL2) : confirmURL2 != null) {
            return false;
        }
        confirmURL = getCancelURL();
        confirmURL2 = paypalExpressCheckout.getCancelURL();
        if (confirmURL == null) {
            if (confirmURL2 == null) {
                return true;
            }
        } else if (confirmURL.equals(confirmURL2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String confirmURL = getConfirmURL();
        int hashCode = (confirmURL == null ? 43 : confirmURL.hashCode()) + 59;
        String cancelURL = getCancelURL();
        hashCode *= 59;
        if (cancelURL != null) {
            i = cancelURL.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "PaypalExpressCheckout(confirmURL=" + getConfirmURL() + ", cancelURL=" + getCancelURL() + ")";
    }

    public static PaypalExpressCheckoutBuilder builder() {
        return new PaypalExpressCheckoutBuilder();
    }

    public String getConfirmURL() {
        return this.confirmURL;
    }

    public String getCancelURL() {
        return this.cancelURL;
    }

    public PaypalExpressCheckout(@Element(name = "confirm-url") String str, @Element(name = "cancel-url") String str2) {
        this.confirmURL = str;
        this.cancelURL = str2;
    }
}
