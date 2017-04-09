package com.gumtree.android.postad.payment.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.order.OrderApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.postad.payment.ApiPaymentService;
import com.gumtree.android.postad.payment.ApiPaymentService_Factory;
import com.gumtree.android.postad.payment.GatedPostAdPaymentView;
import com.gumtree.android.postad.payment.PaymentService;
import com.gumtree.android.postad.payment.PostAdPaymentActivity;
import com.gumtree.android.postad.payment.PostAdPaymentActivity_MembersInjector;
import com.gumtree.android.postad.payment.PostAdPaymentPresenter;
import com.gumtree.android.postad.payment.SleepService;
import com.gumtree.android.postad.payment.utils.PayPalOrderHelper_Factory;
import com.gumtree.android.postad.payment.utils.PayPalUrlParser_Factory;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.services.converter.DraftFeatureTypeConverter_Factory;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.postad.services.converter.PaymentConverter_Factory;
import com.gumtree.android.tracking.TrackingDataProvider;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerPostAdPaymentComponent implements PostAdPaymentComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerPostAdPaymentComponent.class.desiredAssertionStatus());
    private Provider<ApiPaymentService> apiPaymentServiceProvider;
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private Provider<Network> networkProvider;
    private Provider<PaymentConverter> paymentConverterProvider;
    private MembersInjector<PostAdPaymentActivity> postAdPaymentActivityMembersInjector;
    private Provider<GatedPostAdPaymentView> provideGatedPostAdPaymentViewProvider;
    private Provider<PaymentService> providePaymentServiceProvider;
    private Provider<PostAdPaymentPresenter> providePostAdPaymentPresenterProvider;
    private Provider<SleepService> providesBackgroundSleepServiceProvider;
    private Provider<FeaturesApi> providesFeaturesApiProvider;
    private Provider<OrderApi> providesOrderApiProvider;
    private Provider<TrackingDataProvider> trackingDataProvider;
    private Provider<TrackingPostAdService> trackingPostAdServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private PostAdPaymentModule postAdPaymentModule;

        private Builder() {
        }

        public PostAdPaymentComponent build() {
            if (this.postAdPaymentModule == null) {
                throw new IllegalStateException("postAdPaymentModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerPostAdPaymentComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder postAdPaymentModule(PostAdPaymentModule postAdPaymentModule) {
            if (postAdPaymentModule == null) {
                throw new NullPointerException("postAdPaymentModule");
            }
            this.postAdPaymentModule = postAdPaymentModule;
            return this;
        }

        public Builder applicationComponent(ApplicationComponent applicationComponent) {
            if (applicationComponent == null) {
                throw new NullPointerException("applicationComponent");
            }
            this.applicationComponent = applicationComponent;
            return this;
        }
    }

    private DaggerPostAdPaymentComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.eventBusProvider = new 1(this, builder);
        this.networkProvider = new 2(this, builder);
        this.baseAccountManagerProvider = new 3(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.provideGatedPostAdPaymentViewProvider = ScopedProvider.create(PostAdPaymentModule_ProvideGatedPostAdPaymentViewFactory.create(builder.postAdPaymentModule));
        this.localisedTextProvider = new 4(this, builder);
        this.xmlClientProvider = new 5(this, builder);
        this.providesOrderApiProvider = ScopedProvider.create(PostAdPaymentModule_ProvidesOrderApiFactory.create(builder.postAdPaymentModule, this.xmlClientProvider));
        this.providesFeaturesApiProvider = ScopedProvider.create(PostAdPaymentModule_ProvidesFeaturesApiFactory.create(builder.postAdPaymentModule, this.xmlClientProvider));
        this.paymentConverterProvider = PaymentConverter_Factory.create(DraftFeatureTypeConverter_Factory.create());
        this.apiPaymentServiceProvider = ApiPaymentService_Factory.create(this.providesOrderApiProvider, this.providesFeaturesApiProvider, this.paymentConverterProvider);
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.providePaymentServiceProvider = ScopedProvider.create(PostAdPaymentModule_ProvidePaymentServiceFactory.create(builder.postAdPaymentModule, this.apiPaymentServiceProvider, this.backgroundSchedulerProvider));
        this.trackingDataProvider = new 7(this, builder);
        this.providesBackgroundSleepServiceProvider = ScopedProvider.create(PostAdPaymentModule_ProvidesBackgroundSleepServiceFactory.create(builder.postAdPaymentModule));
        this.trackingPostAdServiceProvider = new 8(this, builder);
        this.providePostAdPaymentPresenterProvider = ScopedProvider.create(PostAdPaymentModule_ProvidePostAdPaymentPresenterFactory.create(builder.postAdPaymentModule, this.provideGatedPostAdPaymentViewProvider, this.localisedTextProvider, this.providePaymentServiceProvider, PayPalOrderHelper_Factory.create(), PayPalUrlParser_Factory.create(), this.trackingDataProvider, this.providesBackgroundSleepServiceProvider, this.trackingPostAdServiceProvider));
        this.postAdPaymentActivityMembersInjector = PostAdPaymentActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdPaymentPresenterProvider, PayPalUrlParser_Factory.create());
    }

    public void inject(PostAdPaymentActivity postAdPaymentActivity) {
        this.postAdPaymentActivityMembersInjector.injectMembers(postAdPaymentActivity);
    }
}
