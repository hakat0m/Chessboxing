package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.features.OrderConfirmation;

public class OnOrderCreatedEvent {
    private Result<OrderConfirmation> result;

    public OnOrderCreatedEvent(Result<OrderConfirmation> result) {
        this.result = result;
    }

    public Result<OrderConfirmation> getOrder() {
        return this.result;
    }
}
