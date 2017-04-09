package com.ebay.classifieds.capi.features;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "payment-authorization", strict = false)
public final class PaymentAuthorization {
    @Attribute(name = "payment-id", required = false)
    private final String paymentId;
    @Attribute(name = "payment-provider", required = false)
    private final String paymentProvider;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PaymentAuthorization)) {
            return false;
        }
        PaymentAuthorization paymentAuthorization = (PaymentAuthorization) obj;
        String paymentId = getPaymentId();
        String paymentId2 = paymentAuthorization.getPaymentId();
        if (paymentId != null ? !paymentId.equals(paymentId2) : paymentId2 != null) {
            return false;
        }
        paymentId = getPaymentProvider();
        paymentId2 = paymentAuthorization.getPaymentProvider();
        if (paymentId == null) {
            if (paymentId2 == null) {
                return true;
            }
        } else if (paymentId.equals(paymentId2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String paymentId = getPaymentId();
        int hashCode = (paymentId == null ? 43 : paymentId.hashCode()) + 59;
        String paymentProvider = getPaymentProvider();
        hashCode *= 59;
        if (paymentProvider != null) {
            i = paymentProvider.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "PaymentAuthorization(paymentId=" + getPaymentId() + ", paymentProvider=" + getPaymentProvider() + ")";
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public String getPaymentProvider() {
        return this.paymentProvider;
    }

    public PaymentAuthorization(@Attribute(name = "payment-id") String str, @Attribute(name = "payment-provider") String str2) {
        this.paymentId = str;
        this.paymentProvider = str2;
    }
}
