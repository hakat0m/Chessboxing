package com.ebay.classifieds.capi.order;

import android.support.annotation.NonNull;
import java.beans.ConstructorProperties;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "payment", strict = false)
@Namespace(prefix = "payment", reference = "http://www.ebayclassifiedsgroup.com/schema/payment/v1")
public final class OrderPayment {
    @Attribute(name = "id", required = false)
    private final String paymentId;
    @NonNull
    @Element(name = "provider", required = false)
    private final String paymentProvider;
    @Element(name = "payment-transaction-code", required = false)
    private final String paymentTransactionCode;
    @Element(name = "redirect-url", required = false)
    private final String redirectUrl;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderPayment)) {
            return false;
        }
        OrderPayment orderPayment = (OrderPayment) obj;
        String paymentId = getPaymentId();
        String paymentId2 = orderPayment.getPaymentId();
        if (paymentId != null ? !paymentId.equals(paymentId2) : paymentId2 != null) {
            return false;
        }
        paymentId = getPaymentProvider();
        paymentId2 = orderPayment.getPaymentProvider();
        if (paymentId != null ? !paymentId.equals(paymentId2) : paymentId2 != null) {
            return false;
        }
        paymentId = getPaymentTransactionCode();
        paymentId2 = orderPayment.getPaymentTransactionCode();
        if (paymentId != null ? !paymentId.equals(paymentId2) : paymentId2 != null) {
            return false;
        }
        paymentId = getRedirectUrl();
        paymentId2 = orderPayment.getRedirectUrl();
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
        hashCode = (paymentProvider == null ? 43 : paymentProvider.hashCode()) + (hashCode * 59);
        paymentProvider = getPaymentTransactionCode();
        hashCode = (paymentProvider == null ? 43 : paymentProvider.hashCode()) + (hashCode * 59);
        paymentProvider = getRedirectUrl();
        hashCode *= 59;
        if (paymentProvider != null) {
            i = paymentProvider.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OrderPayment(paymentId=" + getPaymentId() + ", paymentProvider=" + getPaymentProvider() + ", paymentTransactionCode=" + getPaymentTransactionCode() + ", redirectUrl=" + getRedirectUrl() + ")";
    }

    public static OrderPaymentBuilder builder() {
        return new OrderPaymentBuilder();
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    @NonNull
    public String getPaymentProvider() {
        return this.paymentProvider;
    }

    public String getPaymentTransactionCode() {
        return this.paymentTransactionCode;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @ConstructorProperties({"id", "provider", "payment-transaction-code", "redirect-url"})
    public OrderPayment(@Attribute(name = "id") String str, @Element(name = "provider") String str2, @Element(name = "payment-transaction-code") String str3, @Element(name = "redirect-url") String str4) {
        this.paymentId = str;
        this.paymentProvider = str2;
        this.paymentTransactionCode = str3;
        this.redirectUrl = str4;
    }
}
