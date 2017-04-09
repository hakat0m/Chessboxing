package com.gumtree.android.sellersotheritems.di;

import android.content.Context;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.sellersotheritems.SellersOtherItemsActivity;
import com.gumtree.android.sellersotheritems.SellersOtherItemsActivity_MembersInjector;
import com.gumtree.android.sellersotheritems.presenters.GatedSellersOtherItemsView_Factory;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter;
import com.gumtree.android.sellersotheritems.services.SellerAdsService;
import com.gumtree.android.sellersotheritems.services.SellersOtherItemsTextProvider;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerSellersOtherItemsComponent implements SellersOtherItemsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSellersOtherItemsComponent.class.desiredAssertionStatus());
    private Provider<AdsApi> adsApiProvider;
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private Provider<Network> networkProvider;
    private Provider<PagingConfig> providePagingConfigProvider;
    private Provider<SellerAdsService> provideSellerAdsServiceProvider;
    private Provider<SellersOtherItemsPresenter> provideSellersOtherItemsPresenterProvider;
    private Provider<SellersOtherItemsTextProvider> provideSellersOtherItemsTextProvider;
    private MembersInjector<SellersOtherItemsActivity> sellersOtherItemsActivityMembersInjector;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private SellersOtherItemsModule sellersOtherItemsModule;

        private Builder() {
        }

        public SellersOtherItemsComponent build() {
            if (this.sellersOtherItemsModule == null) {
                throw new IllegalStateException("sellersOtherItemsModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerSellersOtherItemsComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder sellersOtherItemsModule(SellersOtherItemsModule sellersOtherItemsModule) {
            if (sellersOtherItemsModule == null) {
                throw new NullPointerException("sellersOtherItemsModule");
            }
            this.sellersOtherItemsModule = sellersOtherItemsModule;
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

    private DaggerSellersOtherItemsComponent(Builder builder) {
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
        this.adsApiProvider = new 4(this, builder);
        this.backgroundSchedulerProvider = new 5(this, builder);
        this.provideSellerAdsServiceProvider = ScopedProvider.create(SellersOtherItemsModule_ProvideSellerAdsServiceFactory.create(builder.sellersOtherItemsModule, this.adsApiProvider, this.backgroundSchedulerProvider));
        this.contextProvider = new 6(this, builder);
        this.localisedTextProvider = new 7(this, builder);
        this.provideSellersOtherItemsTextProvider = ScopedProvider.create(SellersOtherItemsModule_ProvideSellersOtherItemsTextProviderFactory.create(builder.sellersOtherItemsModule, this.contextProvider, this.localisedTextProvider));
        this.providePagingConfigProvider = ScopedProvider.create(SellersOtherItemsModule_ProvidePagingConfigFactory.create(builder.sellersOtherItemsModule));
        this.provideSellersOtherItemsPresenterProvider = ScopedProvider.create(SellersOtherItemsModule_ProvideSellersOtherItemsPresenterFactory.create(builder.sellersOtherItemsModule, GatedSellersOtherItemsView_Factory.create(), this.provideSellerAdsServiceProvider, this.provideSellersOtherItemsTextProvider, this.providePagingConfigProvider));
        this.sellersOtherItemsActivityMembersInjector = SellersOtherItemsActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideSellersOtherItemsPresenterProvider, this.providePagingConfigProvider);
    }

    public void inject(SellersOtherItemsActivity sellersOtherItemsActivity) {
        this.sellersOtherItemsActivityMembersInjector.injectMembers(sellersOtherItemsActivity);
    }
}
