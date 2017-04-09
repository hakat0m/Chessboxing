package com.gumtree.android.postad.payment.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.order.OrderApi;
import com.gumtree.android.postad.payment.ApiPaymentService;
import com.gumtree.android.postad.payment.BackgroundPaymentService;
import com.gumtree.android.postad.payment.DefaultPostAdPaymentPresenter;
import com.gumtree.android.postad.payment.GatedPostAdPaymentView;
import com.gumtree.android.postad.payment.PaymentService;
import com.gumtree.android.postad.payment.PostAdPaymentPresenter;
import com.gumtree.android.postad.payment.SleepService;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.payment.utils.PayPalOrderHelper;
import com.gumtree.android.postad.payment.utils.PayPalUrlParser;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.tracking.TrackingDataProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class PostAdPaymentModule {
    public static final int REDIRECT_TO_MANAGE_ADS_TIMEOUT = 1000;
    private OrderData orderData;

    public PostAdPaymentModule(OrderData orderData) {
        this.orderData = orderData;
    }

    @Provides
    @PostAdPaymentScope
    public GatedPostAdPaymentView provideGatedPostAdPaymentView() {
        return new GatedPostAdPaymentView();
    }

    @Provides
    @PostAdPaymentScope
    public PostAdPaymentPresenter providePostAdPaymentPresenter(GatedPostAdPaymentView gatedPostAdPaymentView, LocalisedTextProvider localisedTextProvider, PaymentService paymentService, PayPalOrderHelper payPalOrderHelper, PayPalUrlParser payPalUrlParser, TrackingDataProvider trackingDataProvider, SleepService sleepService, TrackingPostAdService trackingPostAdService) {
        return new DefaultPostAdPaymentPresenter(gatedPostAdPaymentView, this.orderData, localisedTextProvider, paymentService, payPalOrderHelper, payPalUrlParser, trackingDataProvider, sleepService, trackingPostAdService);
    }

    @Provides
    @PostAdPaymentScope
    public OrderApi providesOrderApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (OrderApi) iCapiClient.api(OrderApi.class);
    }

    @Provides
    @PostAdPaymentScope
    public FeaturesApi providesFeaturesApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (FeaturesApi) iCapiClient.api(FeaturesApi.class);
    }

    @Provides
    @PostAdPaymentScope
    public PaymentService providePaymentService(ApiPaymentService apiPaymentService, @Named("background") Scheduler scheduler) {
        return new BackgroundPaymentService(apiPaymentService, AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @PostAdPaymentScope
    public SleepService providesBackgroundSleepService() {
        return new SleepService(1000, AndroidSchedulers.mainThread());
    }
}
