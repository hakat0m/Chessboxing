package com.ebay.classifieds.capi.order;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface OrderApi {
    public static final String ORDER_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/order/v1";
    public static final String ORDER_PREFIX = "order";
    public static final String PAYMENT_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/payment/v1";
    public static final String PAYMENT_PREFIX = "payment";

    @POST("/orders")
    Observable<OrderResponse> placePaidAdOrder(@Body OrderPayload orderPayload);
}
