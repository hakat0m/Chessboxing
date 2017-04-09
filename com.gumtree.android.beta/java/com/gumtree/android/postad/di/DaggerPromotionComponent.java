package com.gumtree.android.postad.di;

import android.content.Context;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.postad.promote.DefaultPromotionPresenter;
import com.gumtree.android.postad.promote.DefaultPromotionPresenter_Factory;
import com.gumtree.android.postad.promote.GatedPromotionView_Factory;
import com.gumtree.android.postad.promote.PromotionActivity;
import com.gumtree.android.postad.promote.PromotionActivity_MembersInjector;
import com.gumtree.android.postad.promote.PromotionPresenter;
import com.gumtree.android.postad.promote.StringProvider;
import com.gumtree.android.priceinfo.PriceInfoService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;

public final class DaggerPromotionComponent implements PromotionComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerPromotionComponent.class.desiredAssertionStatus());
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<DefaultPromotionPresenter> defaultPromotionPresenterProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private Provider<Network> networkProvider;
    private Provider<PriceInfoService> priceInfoServiceProvider;
    private MembersInjector<PromotionActivity> promotionActivityMembersInjector;
    private Provider<PromotionPresenter> providesPromotionPresenterProvider;
    private Provider<StringProvider> providesStringProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private PromotionModule promotionModule;

        private Builder() {
        }

        public PromotionComponent build() {
            if (this.promotionModule == null) {
                this.promotionModule = new PromotionModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerPromotionComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder promotionModule(PromotionModule promotionModule) {
            if (promotionModule == null) {
                throw new NullPointerException("promotionModule");
            }
            this.promotionModule = promotionModule;
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

    private DaggerPromotionComponent(Builder builder) {
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
        this.priceInfoServiceProvider = new 4(this, builder);
        this.localisedTextProvider = new 5(this, builder);
        this.contextProvider = new 6(this, builder);
        this.providesStringProvider = ScopedProvider.create(PromotionModule_ProvidesStringProviderFactory.create(builder.promotionModule, this.localisedTextProvider, this.contextProvider));
        this.defaultPromotionPresenterProvider = DefaultPromotionPresenter_Factory.create(this.priceInfoServiceProvider, GatedPromotionView_Factory.create(), this.providesStringProvider);
        this.providesPromotionPresenterProvider = ScopedProvider.create(PromotionModule_ProvidesPromotionPresenterFactory.create(builder.promotionModule, this.defaultPromotionPresenterProvider));
        this.promotionActivityMembersInjector = PromotionActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providesPromotionPresenterProvider);
    }

    public void inject(PromotionActivity promotionActivity) {
        this.promotionActivityMembersInjector.injectMembers(promotionActivity);
    }
}
