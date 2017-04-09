package com.ebay.classifieds.capi.order;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;

public final class PaymentMethod {
    @Attribute(name = "id", required = false)
    private final String id;
    @Attribute(name = "name", required = false)
    private final String name;
    @Namespace(prefix = "payment", reference = "http://www.ebayclassifiedsgroup.com/schema/payment/v1")
    @Element(name = "set-paypal-express-checkout", required = false)
    private final PaypalExpressCheckout paypalExpressCheckout;
    @Attribute(name = "type", required = false)
    private final String type;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PaymentMethod)) {
            return false;
        }
        PaymentMethod paymentMethod = (PaymentMethod) obj;
        String id = getId();
        String id2 = paymentMethod.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getType();
        id2 = paymentMethod.getType();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getName();
        id2 = paymentMethod.getName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        PaypalExpressCheckout paypalExpressCheckout = getPaypalExpressCheckout();
        PaypalExpressCheckout paypalExpressCheckout2 = paymentMethod.getPaypalExpressCheckout();
        if (paypalExpressCheckout == null) {
            if (paypalExpressCheckout2 == null) {
                return true;
            }
        } else if (paypalExpressCheckout.equals(paypalExpressCheckout2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String type = getType();
        hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
        type = getName();
        hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
        PaypalExpressCheckout paypalExpressCheckout = getPaypalExpressCheckout();
        hashCode *= 59;
        if (paypalExpressCheckout != null) {
            i = paypalExpressCheckout.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "PaymentMethod(id=" + getId() + ", type=" + getType() + ", name=" + getName() + ", paypalExpressCheckout=" + getPaypalExpressCheckout() + ")";
    }

    public static PaymentMethodBuilder builder() {
        return new PaymentMethodBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public PaypalExpressCheckout getPaypalExpressCheckout() {
        return this.paypalExpressCheckout;
    }

    public PaymentMethod(@Attribute(name = "id") String str, @Attribute(name = "type") String str2, @Attribute(name = "name") String str3, @Element(name = "set-paypal-express-checkout") PaypalExpressCheckout paypalExpressCheckout) {
        this.id = str;
        this.type = str2;
        this.name = str3;
        this.paypalExpressCheckout = paypalExpressCheckout;
    }
}
