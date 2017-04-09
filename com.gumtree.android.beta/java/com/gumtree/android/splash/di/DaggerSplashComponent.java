package com.gumtree.android.splash.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.google.services.GoogleService;
import com.gumtree.android.auth.login.services.LoginService;
import com.gumtree.android.auth.login.services.TrackingLoginService;
import com.gumtree.android.auth.services.AuthService;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.splash.GatedSplashView_Factory;
import com.gumtree.android.splash.SplashActivity;
import com.gumtree.android.splash.SplashActivity_MembersInjector;
import com.gumtree.android.splash.presenter.SplashPresenter;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerSplashComponent implements SplashComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSplashComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<AuthService> provideAuthServiceProvider;
    private Provider<GoogleService> provideGoogleServiceProvider;
    private Provider<LoginApi> provideLoginApiProvider;
    private Provider<LoginService> provideLoginServiceProvider;
    private Provider<SplashPresenter> provideSplashPresenterProvider;
    private Provider<TrackingLoginService> provideTrackingAuthServiceProvider;
    private Provider<UserProfileService> provideUserProfileServiceProvider;
    private Provider<PushNotificationsProvider> pushNotificationsProvider;
    private MembersInjector<SplashActivity> splashActivityMembersInjector;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<UserProfileApi> userProfileApiProvider;
    private Provider<UserService> userServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private SplashModule splashModule;

        private Builder() {
        }

        public SplashComponent build() {
            if (this.splashModule == null) {
                this.splashModule = new SplashModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerSplashComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder splashModule(SplashModule splashModule) {
            if (splashModule == null) {
                throw new NullPointerException("splashModule");
            }
            this.splashModule = splashModule;
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

    private DaggerSplashComponent(Builder builder) {
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
        this.provideLoginApiProvider = ScopedProvider.create(SplashModule_ProvideLoginApiFactory.create(builder.splashModule, this.xmlClientProvider));
        this.userProfileApiProvider = new 5(this, builder);
        this.provideUserProfileServiceProvider = ScopedProvider.create(SplashModule_ProvideUserProfileServiceFactory.create(builder.splashModule, this.userProfileApiProvider, this.baseAccountManagerProvider));
        this.pushNotificationsProvider = new 6(this, builder);
        this.provideAuthServiceProvider = ScopedProvider.create(SplashModule_ProvideAuthServiceFactory.create(builder.splashModule, this.pushNotificationsProvider));
        this.userServiceProvider = new 7(this, builder);
        this.backgroundSchedulerProvider = new 8(this, builder);
        this.provideLoginServiceProvider = ScopedProvider.create(SplashModule_ProvideLoginServiceFactory.create(builder.splashModule, this.provideLoginApiProvider, this.provideUserProfileServiceProvider, this.baseAccountManagerProvider, this.provideAuthServiceProvider, this.userServiceProvider, this.backgroundSchedulerProvider));
        this.networkStateServiceProvider = new 9(this, builder);
        this.staticTrackingServiceProvider = new 10(this, builder);
        this.provideTrackingAuthServiceProvider = ScopedProvider.create(SplashModule_ProvideTrackingAuthServiceFactory.create(builder.splashModule, this.staticTrackingServiceProvider));
        this.provideGoogleServiceProvider = ScopedProvider.create(SplashModule_ProvideGoogleServiceFactory.create(builder.splashModule, this.backgroundSchedulerProvider));
        this.provideSplashPresenterProvider = ScopedProvider.create(SplashModule_ProvideSplashPresenterFactory.create(builder.splashModule, GatedSplashView_Factory.create(), this.provideLoginServiceProvider, this.networkStateServiceProvider, this.provideTrackingAuthServiceProvider, this.provideGoogleServiceProvider, this.baseAccountManagerProvider));
        this.splashActivityMembersInjector = SplashActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideSplashPresenterProvider);
    }

    public void inject(SplashActivity splashActivity) {
        this.splashActivityMembersInjector.injectMembers(splashActivity);
    }
}
