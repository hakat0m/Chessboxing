package com.ebay.classifieds.capi.features;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "feature-confirmation", strict = false)
@Namespace(prefix = "feat", reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
public final class ApplyFeaturePayload {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "features-booked", required = false)
    private final FeaturesBooked featuresBooked;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/feature/v1")
    @Element(name = "payment-authorization")
    private final PaymentAuthorization paymentAuthorization;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ApplyFeaturePayload)) {
            return false;
        }
        ApplyFeaturePayload applyFeaturePayload = (ApplyFeaturePayload) obj;
        FeaturesBooked featuresBooked = getFeaturesBooked();
        FeaturesBooked featuresBooked2 = applyFeaturePayload.getFeaturesBooked();
        if (featuresBooked != null ? !featuresBooked.equals(featuresBooked2) : featuresBooked2 != null) {
            return false;
        }
        PaymentAuthorization paymentAuthorization = getPaymentAuthorization();
        PaymentAuthorization paymentAuthorization2 = applyFeaturePayload.getPaymentAuthorization();
        if (paymentAuthorization == null) {
            if (paymentAuthorization2 == null) {
                return true;
            }
        } else if (paymentAuthorization.equals(paymentAuthorization2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        FeaturesBooked featuresBooked = getFeaturesBooked();
        int hashCode = (featuresBooked == null ? 43 : featuresBooked.hashCode()) + 59;
        PaymentAuthorization paymentAuthorization = getPaymentAuthorization();
        hashCode *= 59;
        if (paymentAuthorization != null) {
            i = paymentAuthorization.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "ApplyFeaturePayload(featuresBooked=" + getFeaturesBooked() + ", paymentAuthorization=" + getPaymentAuthorization() + ")";
    }

    public static ApplyFeaturePayloadBuilder builder() {
        return new ApplyFeaturePayloadBuilder();
    }

    public FeaturesBooked getFeaturesBooked() {
        return this.featuresBooked;
    }

    public PaymentAuthorization getPaymentAuthorization() {
        return this.paymentAuthorization;
    }

    public ApplyFeaturePayload(@Element(name = "features-booked") FeaturesBooked featuresBooked, @Element(name = "payment-authorization") PaymentAuthorization paymentAuthorization) {
        this.featuresBooked = featuresBooked;
        this.paymentAuthorization = paymentAuthorization;
    }
}
