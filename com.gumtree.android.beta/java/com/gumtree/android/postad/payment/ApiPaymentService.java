package com.gumtree.android.postad.payment;

import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.order.OrderApi;
import com.ebay.classifieds.capi.order.OrderResponse;
import com.gumtree.android.postad.PaymentData;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import javax.inject.Inject;
import rx.Observable;

public class ApiPaymentService implements PaymentService {
    private FeaturesApi featuresApi;
    private OrderApi orderApi;
    private PaymentConverter paymentConverter;

    @Inject
    public ApiPaymentService(OrderApi orderApi, FeaturesApi featuresApi, PaymentConverter paymentConverter) {
        this.orderApi = orderApi;
        this.featuresApi = featuresApi;
        this.paymentConverter = paymentConverter;
    }

    public Observable<PaymentData> placePaidAdOrder(OrderData orderData) {
        return this.orderApi.placePaidAdOrder(this.paymentConverter.orderPaymentModelToOrderApi(orderData)).map(ApiPaymentService$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ PaymentData lambda$placePaidAdOrder$0(OrderResponse orderResponse) {
        return this.paymentConverter.orderApiResponseToOrderModel(orderResponse);
    }

    public Observable<Void> applyFees(OrderData orderData, PaymentData paymentData) {
        return this.featuresApi.applyFees(orderData.getAdId(), this.paymentConverter.applyFeePaymentModelToFeaturesApi(orderData, paymentData));
    }
}
