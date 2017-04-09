package com.gumtree.android.postad.customdetails.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.metadata.service.VRMService;
import com.gumtree.android.postad.customdetails.CustomDetailsActivity;
import com.gumtree.android.postad.customdetails.CustomDetailsActivity_MembersInjector;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter;
import com.gumtree.android.postad.customdetails.GatedCustomDetailsView_Factory;
import com.gumtree.android.postad.services.TrackingPostAdService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerCustomDetailsComponent implements CustomDetailsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerCustomDetailsComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private MembersInjector<CustomDetailsActivity> customDetailsActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private Provider<CustomDetailsPresenter> provideCustomDetailsPresenterProvider;
    private Provider<VRMService> provideVRMServiceProvider;
    private Provider<TrackingPostAdService> trackingPostAdServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private CustomDetailsModule customDetailsModule;

        private Builder() {
        }

        public CustomDetailsComponent build() {
            if (this.customDetailsModule == null) {
                throw new IllegalStateException("customDetailsModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerCustomDetailsComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder customDetailsModule(CustomDetailsModule customDetailsModule) {
            if (customDetailsModule == null) {
                throw new NullPointerException("customDetailsModule");
            }
            this.customDetailsModule = customDetailsModule;
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

    private DaggerCustomDetailsComponent(Builder builder) {
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
        this.xmlClientProvider = new 4(this, builder);
        this.backgroundSchedulerProvider = new 5(this, builder);
        this.provideVRMServiceProvider = ScopedProvider.create(CustomDetailsModule_ProvideVRMServiceFactory.create(builder.customDetailsModule, this.xmlClientProvider, this.backgroundSchedulerProvider));
        this.trackingPostAdServiceProvider = new 6(this, builder);
        this.provideCustomDetailsPresenterProvider = ScopedProvider.create(CustomDetailsModule_ProvideCustomDetailsPresenterFactory.create(builder.customDetailsModule, GatedCustomDetailsView_Factory.create(), this.provideVRMServiceProvider, this.trackingPostAdServiceProvider));
        this.customDetailsActivityMembersInjector = CustomDetailsActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideCustomDetailsPresenterProvider);
    }

    public void inject(CustomDetailsActivity customDetailsActivity) {
        this.customDetailsActivityMembersInjector.injectMembers(customDetailsActivity);
    }
}
