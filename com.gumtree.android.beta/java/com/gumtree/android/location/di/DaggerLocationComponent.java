package com.gumtree.android.location.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.location.LocationActivity;
import com.gumtree.android.location.LocationActivity_MembersInjector;
import com.gumtree.android.location.presenters.GatedLocationView_Factory;
import com.gumtree.android.location.presenters.LocationPresenter;
import com.gumtree.android.location.services.LocationModelConverter;
import com.gumtree.android.location.services.LocationService;
import com.gumtree.android.location.services.TrackingLocationService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerLocationComponent implements LocationComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerLocationComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private MembersInjector<LocationActivity> locationActivityMembersInjector;
    private Provider<Network> networkProvider;
    private Provider<LocationPresenter> providePostAdContactDetailsPresenterProvider;
    private Provider<LocationModelConverter> providesLocationModelConverterProvider;
    private Provider<LocationService> providesLocationServiceProvider;
    private Provider<TrackingLocationService> trackingLocationServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private LocationModule locationModule;

        private Builder() {
        }

        public LocationComponent build() {
            if (this.locationModule == null) {
                throw new IllegalStateException("locationModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerLocationComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder locationModule(LocationModule locationModule) {
            if (locationModule == null) {
                throw new NullPointerException("locationModule");
            }
            this.locationModule = locationModule;
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

    private DaggerLocationComponent(Builder builder) {
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
        this.providesLocationModelConverterProvider = LocationModule_ProvidesLocationModelConverterFactory.create(builder.locationModule);
        this.contextProvider = new 5(this, builder);
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.providesLocationServiceProvider = LocationModule_ProvidesLocationServiceFactory.create(builder.locationModule, this.xmlClientProvider, this.providesLocationModelConverterProvider, this.contextProvider, this.backgroundSchedulerProvider);
        this.localisedTextProvider = new 7(this, builder);
        this.trackingLocationServiceProvider = new 8(this, builder);
        this.providePostAdContactDetailsPresenterProvider = ScopedProvider.create(LocationModule_ProvidePostAdContactDetailsPresenterFactory.create(builder.locationModule, GatedLocationView_Factory.create(), this.providesLocationServiceProvider, this.localisedTextProvider, this.trackingLocationServiceProvider));
        this.locationActivityMembersInjector = LocationActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdContactDetailsPresenterProvider);
    }

    public void inject(LocationActivity locationActivity) {
        this.locationActivityMembersInjector.injectMembers(locationActivity);
    }
}
