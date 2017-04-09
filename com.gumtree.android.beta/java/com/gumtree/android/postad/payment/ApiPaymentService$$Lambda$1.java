package com.gumtree.android.postad.payment;

import com.ebay.classifieds.capi.order.OrderResponse;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiPaymentService$$Lambda$1 implements Func1 {
    private final ApiPaymentService arg$1;

    private ApiPaymentService$$Lambda$1(ApiPaymentService apiPaymentService) {
        this.arg$1 = apiPaymentService;
    }

    public static Func1 lambdaFactory$(ApiPaymentService apiPaymentService) {
        return new ApiPaymentService$$Lambda$1(apiPaymentService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$placePaidAdOrder$0((OrderResponse) obj);
    }
}
