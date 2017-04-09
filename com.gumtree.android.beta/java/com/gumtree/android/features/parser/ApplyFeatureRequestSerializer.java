package com.gumtree.android.features.parser;

import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.features.OrderConfirmation;

public class ApplyFeatureRequestSerializer {
    private final OrderConfirmation order;

    public ApplyFeatureRequestSerializer(OrderConfirmation orderConfirmation) {
        this.order = orderConfirmation;
    }

    public String serialize() {
        return "<feat:feature-confirmation xmlns:feat=\"http://www.ebayclassifiedsgroup.com/schema/feature/v1\"><feat:payment-authorization payment-id=\"" + getPaymentId(this.order) + "\" payment-provider=\"" + this.order.getProvider() + "\"/>" + "</feat:feature-confirmation>";
    }

    private String getPaymentId(OrderConfirmation orderConfirmation) {
        if ("paypal".equalsIgnoreCase(orderConfirmation.getProvider())) {
            return orderConfirmation.getTransactionId();
        }
        return BuildConfig.FLAVOR + orderConfirmation.getOrderId();
    }
}
