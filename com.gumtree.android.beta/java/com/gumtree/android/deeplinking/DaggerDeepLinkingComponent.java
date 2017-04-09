package com.gumtree.android.deeplinking;

import android.content.Context;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.users.UsersApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.deeplinking.activate.ActivateDeepLinkingPresenter;
import com.gumtree.android.deeplinking.activate.ActivateUserService;
import com.gumtree.android.deeplinking.activate.GatedActivateDeepLinkingView_Factory;
import com.gumtree.android.deeplinking.activate.TrackingActivateService;
import com.gumtree.android.deeplinking.forgotpassword.ForgotPasswordDeepLinkingPresenter;
import com.gumtree.android.deeplinking.postad.PostAdDeepLinkingPresenter;
import com.gumtree.android.deeplinking.postad.TrackingPostAdDeepLinkingService;
import com.gumtree.android.deeplinking.presenter.DeepLinkingPresenter;
import com.gumtree.android.deeplinking.search.DeepLinkingService;
import com.gumtree.android.deeplinking.search.GatedSearchDeepLinkingView_Factory;
import com.gumtree.android.deeplinking.search.SearchDeepLinkingPresenter;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerDeepLinkingComponent implements DeepLinkingComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerDeepLinkingComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<CapiConfig> capiConfigProvider;
    private Provider<Context> contextProvider;
    private MembersInjector<DeepLinkingActivity> deepLinkingActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<ActivateDeepLinkingPresenter> provideActivateDeepLinkingPresenterProvider;
    private Provider<ActivateUserService> provideActivateServiceProvider;
    private Provider<DeepLinkingPresenter> provideDeepLinkPresenterProvider;
    private Provider<SearchDeepLinkingPresenter> provideDeepLinkSearchPresenterProvider;
    private Provider<DeepLinkingService> provideDeepLinkingServiceProvider;
    private Provider<ForgotPasswordDeepLinkingPresenter> provideForgotPasswordDeepLinkingPresenterProvider;
    private Provider<PostAdDeepLinkingPresenter> providePostAdDeepLinkingPresenterProvider;
    private Provider<TrackingPostAdDeepLinkingService> providePostAdTrackingDeepLinkingServiceProvider;
    private Provider<TrackingActivateService> provideTrackingActivateServiceProvider;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<UsersApi> usersApiProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private DeepLinkingModule deepLinkingModule;

        private Builder() {
        }

        public DeepLinkingComponent build() {
            if (this.deepLinkingModule == null) {
                throw new IllegalStateException("deepLinkingModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerDeepLinkingComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder deepLinkingModule(DeepLinkingModule deepLinkingModule) {
            if (deepLinkingModule == null) {
                throw new NullPointerException("deepLinkingModule");
            }
            this.deepLinkingModule = deepLinkingModule;
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

    private DaggerDeepLinkingComponent(Builder builder) {
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
        this.capiConfigProvider = new 4(this, builder);
        this.usersApiProvider = new 5(this, builder);
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.provideActivateServiceProvider = ScopedProvider.create(DeepLinkingModule_ProvideActivateServiceFactory.create(builder.deepLinkingModule, this.usersApiProvider, this.backgroundSchedulerProvider));
        this.networkStateServiceProvider = new 7(this, builder);
        this.localisedTextProvider = new 8(this, builder);
        this.staticTrackingServiceProvider = new 9(this, builder);
        this.provideTrackingActivateServiceProvider = ScopedProvider.create(DeepLinkingModule_ProvideTrackingActivateServiceFactory.create(builder.deepLinkingModule, this.staticTrackingServiceProvider));
        this.provideActivateDeepLinkingPresenterProvider = ScopedProvider.create(DeepLinkingModule_ProvideActivateDeepLinkingPresenterFactory.create(builder.deepLinkingModule, this.capiConfigProvider, GatedActivateDeepLinkingView_Factory.create(), this.provideActivateServiceProvider, this.networkStateServiceProvider, this.localisedTextProvider, this.provideTrackingActivateServiceProvider, this.baseAccountManagerProvider));
        this.xmlClientProvider = new 10(this, builder);
        this.provideDeepLinkingServiceProvider = ScopedProvider.create(DeepLinkingModule_ProvideDeepLinkingServiceFactory.create(builder.deepLinkingModule, this.xmlClientProvider, this.backgroundSchedulerProvider));
        this.contextProvider = new 11(this, builder);
        this.provideDeepLinkSearchPresenterProvider = ScopedProvider.create(DeepLinkingModule_ProvideDeepLinkSearchPresenterFactory.create(builder.deepLinkingModule, GatedSearchDeepLinkingView_Factory.create(), this.provideDeepLinkingServiceProvider, this.contextProvider, this.networkStateServiceProvider, this.localisedTextProvider));
        this.provideForgotPasswordDeepLinkingPresenterProvider = ScopedProvider.create(DeepLinkingModule_ProvideForgotPasswordDeepLinkingPresenterFactory.create(builder.deepLinkingModule, this.capiConfigProvider, this.localisedTextProvider));
        this.providePostAdTrackingDeepLinkingServiceProvider = ScopedProvider.create(DeepLinkingModule_ProvidePostAdTrackingDeepLinkingServiceFactory.create(builder.deepLinkingModule, this.staticTrackingServiceProvider));
        this.providePostAdDeepLinkingPresenterProvider = ScopedProvider.create(DeepLinkingModule_ProvidePostAdDeepLinkingPresenterFactory.create(builder.deepLinkingModule, this.providePostAdTrackingDeepLinkingServiceProvider, this.capiConfigProvider, this.localisedTextProvider));
        this.provideDeepLinkPresenterProvider = ScopedProvider.create(DeepLinkingModule_ProvideDeepLinkPresenterFactory.create(builder.deepLinkingModule, this.provideActivateDeepLinkingPresenterProvider, this.provideDeepLinkSearchPresenterProvider, this.provideForgotPasswordDeepLinkingPresenterProvider, this.providePostAdDeepLinkingPresenterProvider));
        this.deepLinkingActivityMembersInjector = DeepLinkingActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideDeepLinkPresenterProvider);
    }

    public void inject(DeepLinkingActivity deepLinkingActivity) {
        this.deepLinkingActivityMembersInjector.injectMembers(deepLinkingActivity);
    }
}
