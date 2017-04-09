package com.gumtree.android.login.forgotpassword.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.forgotpassword.ForgotPasswordApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.forgotpassword.GatedForgotPasswordView_Factory;
import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter;
import com.gumtree.android.auth.forgotpassword.services.ForgotPasswordLocalisedTextProvider;
import com.gumtree.android.auth.forgotpassword.services.ForgotPasswordService;
import com.gumtree.android.auth.forgotpassword.services.TrackingForgotPasswordService;
import com.gumtree.android.auth.services.validators.TextValidatorService;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.login.forgotpassword.ForgotPasswordActivity;
import com.gumtree.android.login.forgotpassword.ForgotPasswordActivity_MembersInjector;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerForgotPasswordComponent implements ForgotPasswordComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerForgotPasswordComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private MembersInjector<ForgotPasswordActivity> forgotPasswordActivityMembersInjector;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<TextValidatorService> provideEmailValidatorServiceProvider;
    private Provider<ForgotPasswordApi> provideForgotPasswordApiProvider;
    private Provider<ForgotPasswordLocalisedTextProvider> provideForgotPasswordLocalisedTextProvider;
    private Provider<ForgotPasswordPresenter> provideForgotPasswordPresenterProvider;
    private Provider<ForgotPasswordService> provideForgotPasswordServiceProvider;
    private Provider<TrackingForgotPasswordService> provideTrackingForgotPasswordServiceProvider;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private ForgotPasswordModule forgotPasswordModule;

        private Builder() {
        }

        public ForgotPasswordComponent build() {
            if (this.forgotPasswordModule == null) {
                this.forgotPasswordModule = new ForgotPasswordModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerForgotPasswordComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder forgotPasswordModule(ForgotPasswordModule forgotPasswordModule) {
            if (forgotPasswordModule == null) {
                throw new NullPointerException("forgotPasswordModule");
            }
            this.forgotPasswordModule = forgotPasswordModule;
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

    private DaggerForgotPasswordComponent(Builder builder) {
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
        this.staticTrackingServiceProvider = new 1(this, builder);
        this.provideTrackingForgotPasswordServiceProvider = ScopedProvider.create(ForgotPasswordModule_ProvideTrackingForgotPasswordServiceFactory.create(builder.forgotPasswordModule, this.staticTrackingServiceProvider));
        this.eventBusProvider = new 2(this, builder);
        this.networkProvider = new 3(this, builder);
        this.baseAccountManagerProvider = new 4(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.xmlClientProvider = new 5(this, builder);
        this.provideForgotPasswordApiProvider = ScopedProvider.create(ForgotPasswordModule_ProvideForgotPasswordApiFactory.create(builder.forgotPasswordModule, this.xmlClientProvider));
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.provideForgotPasswordServiceProvider = ScopedProvider.create(ForgotPasswordModule_ProvideForgotPasswordServiceFactory.create(builder.forgotPasswordModule, this.provideForgotPasswordApiProvider, this.backgroundSchedulerProvider));
        this.networkStateServiceProvider = new 7(this, builder);
        this.contextProvider = new 8(this, builder);
        this.provideForgotPasswordLocalisedTextProvider = ScopedProvider.create(ForgotPasswordModule_ProvideForgotPasswordLocalisedTextProviderFactory.create(builder.forgotPasswordModule, this.contextProvider));
        this.provideEmailValidatorServiceProvider = ScopedProvider.create(ForgotPasswordModule_ProvideEmailValidatorServiceFactory.create(builder.forgotPasswordModule));
        this.provideForgotPasswordPresenterProvider = ScopedProvider.create(ForgotPasswordModule_ProvideForgotPasswordPresenterFactory.create(builder.forgotPasswordModule, GatedForgotPasswordView_Factory.create(), this.provideForgotPasswordServiceProvider, this.networkStateServiceProvider, this.provideForgotPasswordLocalisedTextProvider, this.provideEmailValidatorServiceProvider, this.provideTrackingForgotPasswordServiceProvider));
        this.forgotPasswordActivityMembersInjector = ForgotPasswordActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideForgotPasswordPresenterProvider);
    }

    public TrackingForgotPasswordService trackingForgotPasswordService() {
        return (TrackingForgotPasswordService) this.provideTrackingForgotPasswordServiceProvider.get();
    }

    public void inject(ForgotPasswordActivity forgotPasswordActivity) {
        this.forgotPasswordActivityMembersInjector.injectMembers(forgotPasswordActivity);
    }
}
