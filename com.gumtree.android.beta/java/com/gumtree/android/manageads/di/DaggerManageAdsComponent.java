package com.gumtree.android.manageads.di;

import android.content.Context;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.NavigationActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.manageads.GatedManageAdsView_Factory;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.manageads.ManageAdsActivity_MembersInjector;
import com.gumtree.android.manageads.active.ActiveAdsFragment;
import com.gumtree.android.manageads.active.ActiveAdsFragment_MembersInjector;
import com.gumtree.android.manageads.active.GatedActiveAdsView_Factory;
import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter;
import com.gumtree.android.manageads.inactive.GatedInactiveAdsView_Factory;
import com.gumtree.android.manageads.inactive.InactiveAdsFragment;
import com.gumtree.android.manageads.inactive.InactiveAdsFragment_MembersInjector;
import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter;
import com.gumtree.android.manageads.presenter.ManageAdsPresenter;
import com.gumtree.android.manageads.services.ManageAdsService;
import com.gumtree.android.manageads.services.TrackingManageAdsService;
import com.gumtree.android.manageads.services.converter.ManageAdsConverter;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedColorProvider;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedStatusTextProvider;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedTextProvider;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerManageAdsComponent implements ManageAdsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerManageAdsComponent.class.desiredAssertionStatus());
    private MembersInjector<ActiveAdsFragment> activeAdsFragmentMembersInjector;
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<IBadgeCounterManager> badgeCounterManagerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private MembersInjector<InactiveAdsFragment> inactiveAdsFragmentMembersInjector;
    private MembersInjector<ManageAdsActivity> manageAdsActivityMembersInjector;
    private MembersInjector<NavigationActivity> navigationActivityMembersInjector;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<ManageAdsConverter> provideActiveAdsConverterProvider;
    private Provider<ManageAdsService> provideActiveAdsServiceProvider;
    private Provider<ManageAdsConverter> provideInactiveAdsConverterProvider;
    private Provider<ManageAdsService> provideInactiveAdsServiceProvider;
    private Provider<ActiveAdsPresenter> provideManageAdsActivePresenterProvider;
    private Provider<InactiveAdsPresenter> provideManageAdsInactivePresenterProvider;
    private Provider<ManageAdsLocalisedColorProvider> provideManageAdsLocalisedColorProvider;
    private Provider<ManageAdsLocalisedStatusTextProvider> provideManageAdsLocalisedStatusTextProvider;
    private Provider<ManageAdsLocalisedTextProvider> provideManageAdsLocalisedTextProvider;
    private Provider<ManageAdsPresenter> provideManageAdsPresenterProvider;
    private Provider<PagingConfig> providePagingConfigProvider;
    private Provider<TrackingManageAdsService> provideTrackingManageAdsServiceProvider;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<UserAdsApi> userAdsApiProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private ManageAdsModule manageAdsModule;

        private Builder() {
        }

        public ManageAdsComponent build() {
            if (this.manageAdsModule == null) {
                this.manageAdsModule = new ManageAdsModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerManageAdsComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder manageAdsModule(ManageAdsModule manageAdsModule) {
            if (manageAdsModule == null) {
                throw new NullPointerException("manageAdsModule");
            }
            this.manageAdsModule = manageAdsModule;
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

    private DaggerManageAdsComponent(Builder builder) {
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
        this.badgeCounterManagerProvider = new 4(this, builder);
        this.navigationActivityMembersInjector = NavigationActivity_MembersInjector.create(this.baseActivityMembersInjector, this.eventBusProvider, this.badgeCounterManagerProvider);
        this.staticTrackingServiceProvider = new 5(this, builder);
        this.provideTrackingManageAdsServiceProvider = ScopedProvider.create(ManageAdsModule_ProvideTrackingManageAdsServiceFactory.create(builder.manageAdsModule, this.staticTrackingServiceProvider));
        this.provideManageAdsPresenterProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsPresenterFactory.create(builder.manageAdsModule, GatedManageAdsView_Factory.create(), this.baseAccountManagerProvider, this.provideTrackingManageAdsServiceProvider));
        this.manageAdsActivityMembersInjector = ManageAdsActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideManageAdsPresenterProvider);
        this.networkStateServiceProvider = new 6(this, builder);
        this.userAdsApiProvider = new 7(this, builder);
        this.backgroundSchedulerProvider = new 8(this, builder);
        this.contextProvider = new 9(this, builder);
        this.provideManageAdsLocalisedTextProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsLocalisedTextProviderFactory.create(builder.manageAdsModule, this.contextProvider));
        this.provideManageAdsLocalisedStatusTextProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsLocalisedStatusTextProviderFactory.create(builder.manageAdsModule, this.contextProvider));
        this.provideManageAdsLocalisedColorProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsLocalisedColorProviderFactory.create(builder.manageAdsModule));
        this.provideActiveAdsConverterProvider = ScopedProvider.create(ManageAdsModule_ProvideActiveAdsConverterFactory.create(builder.manageAdsModule, this.provideManageAdsLocalisedTextProvider, this.provideManageAdsLocalisedStatusTextProvider, this.provideManageAdsLocalisedColorProvider));
        this.provideActiveAdsServiceProvider = ScopedProvider.create(ManageAdsModule_ProvideActiveAdsServiceFactory.create(builder.manageAdsModule, this.userAdsApiProvider, this.backgroundSchedulerProvider, this.provideActiveAdsConverterProvider));
        this.providePagingConfigProvider = ScopedProvider.create(ManageAdsModule_ProvidePagingConfigFactory.create(builder.manageAdsModule));
        this.provideManageAdsActivePresenterProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsActivePresenterFactory.create(builder.manageAdsModule, GatedActiveAdsView_Factory.create(), this.networkStateServiceProvider, this.provideActiveAdsServiceProvider, this.baseAccountManagerProvider, this.provideManageAdsLocalisedTextProvider, this.providePagingConfigProvider, this.provideTrackingManageAdsServiceProvider));
        this.activeAdsFragmentMembersInjector = ActiveAdsFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideManageAdsActivePresenterProvider, this.providePagingConfigProvider);
        this.provideInactiveAdsConverterProvider = ScopedProvider.create(ManageAdsModule_ProvideInactiveAdsConverterFactory.create(builder.manageAdsModule, this.provideManageAdsLocalisedTextProvider, this.provideManageAdsLocalisedStatusTextProvider, this.provideManageAdsLocalisedColorProvider));
        this.provideInactiveAdsServiceProvider = ScopedProvider.create(ManageAdsModule_ProvideInactiveAdsServiceFactory.create(builder.manageAdsModule, this.userAdsApiProvider, this.backgroundSchedulerProvider, this.provideInactiveAdsConverterProvider));
        this.provideManageAdsInactivePresenterProvider = ScopedProvider.create(ManageAdsModule_ProvideManageAdsInactivePresenterFactory.create(builder.manageAdsModule, GatedInactiveAdsView_Factory.create(), this.networkStateServiceProvider, this.provideInactiveAdsServiceProvider, this.baseAccountManagerProvider, this.provideManageAdsLocalisedTextProvider, this.providePagingConfigProvider, this.provideTrackingManageAdsServiceProvider));
        this.inactiveAdsFragmentMembersInjector = InactiveAdsFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideManageAdsInactivePresenterProvider, this.providePagingConfigProvider);
    }

    public void inject(ManageAdsActivity manageAdsActivity) {
        this.manageAdsActivityMembersInjector.injectMembers(manageAdsActivity);
    }

    public void inject(ActiveAdsFragment activeAdsFragment) {
        this.activeAdsFragmentMembersInjector.injectMembers(activeAdsFragment);
    }

    public void inject(InactiveAdsFragment inactiveAdsFragment) {
        this.inactiveAdsFragmentMembersInjector.injectMembers(inactiveAdsFragment);
    }
}
