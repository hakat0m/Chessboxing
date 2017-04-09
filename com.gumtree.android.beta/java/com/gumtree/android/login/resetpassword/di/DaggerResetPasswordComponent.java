package com.gumtree.android.login.resetpassword.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.resetpassword.ResetPasswordApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.resetpassword.GatedResetPasswordView_Factory;
import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter;
import com.gumtree.android.auth.resetpassword.services.ResetPasswordLocalisedTextProvider;
import com.gumtree.android.auth.resetpassword.services.ResetPasswordService;
import com.gumtree.android.auth.resetpassword.services.TrackingResetPasswordService;
import com.gumtree.android.auth.services.validators.PasswordStrengthService;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.login.resetpassword.ResetPasswordActivity;
import com.gumtree.android.login.resetpassword.ResetPasswordActivity_MembersInjector;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerResetPasswordComponent implements ResetPasswordComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerResetPasswordComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<ResetPasswordApi> provideResetPasswordApiProvider;
    private Provider<ResetPasswordLocalisedTextProvider> provideResetPasswordLocalisedTextProvider;
    private Provider<ResetPasswordPresenter> provideResetPasswordPresenterProvider;
    private Provider<ResetPasswordService> provideResetPasswordServiceProvider;
    private Provider<PasswordStrengthService> provideSimplePasswordStrengthServiceProvider;
    private Provider<TrackingResetPasswordService> provideTrackingResetPasswordServiceProvider;
    private MembersInjector<ResetPasswordActivity> resetPasswordActivityMembersInjector;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private ResetPasswordModule resetPasswordModule;

        private Builder() {
        }

        public ResetPasswordComponent build() {
            if (this.resetPasswordModule == null) {
                throw new IllegalStateException("resetPasswordModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerResetPasswordComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder resetPasswordModule(ResetPasswordModule resetPasswordModule) {
            if (resetPasswordModule == null) {
                throw new NullPointerException("resetPasswordModule");
            }
            this.resetPasswordModule = resetPasswordModule;
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

    private DaggerResetPasswordComponent(Builder builder) {
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
        this.provideTrackingResetPasswordServiceProvider = ScopedProvider.create(ResetPasswordModule_ProvideTrackingResetPasswordServiceFactory.create(builder.resetPasswordModule, this.staticTrackingServiceProvider));
        this.eventBusProvider = new 2(this, builder);
        this.networkProvider = new 3(this, builder);
        this.baseAccountManagerProvider = new 4(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.xmlClientProvider = new 5(this, builder);
        this.provideResetPasswordApiProvider = ScopedProvider.create(ResetPasswordModule_ProvideResetPasswordApiFactory.create(builder.resetPasswordModule, this.xmlClientProvider));
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.provideResetPasswordServiceProvider = ScopedProvider.create(ResetPasswordModule_ProvideResetPasswordServiceFactory.create(builder.resetPasswordModule, this.provideResetPasswordApiProvider, this.backgroundSchedulerProvider));
        this.networkStateServiceProvider = new 7(this, builder);
        this.contextProvider = new 8(this, builder);
        this.provideResetPasswordLocalisedTextProvider = ScopedProvider.create(ResetPasswordModule_ProvideResetPasswordLocalisedTextProviderFactory.create(builder.resetPasswordModule, this.contextProvider));
        this.provideSimplePasswordStrengthServiceProvider = ScopedProvider.create(ResetPasswordModule_ProvideSimplePasswordStrengthServiceFactory.create(builder.resetPasswordModule));
        this.provideResetPasswordPresenterProvider = ScopedProvider.create(ResetPasswordModule_ProvideResetPasswordPresenterFactory.create(builder.resetPasswordModule, GatedResetPasswordView_Factory.create(), this.provideResetPasswordServiceProvider, this.networkStateServiceProvider, this.provideResetPasswordLocalisedTextProvider, this.provideSimplePasswordStrengthServiceProvider, this.provideTrackingResetPasswordServiceProvider));
        this.resetPasswordActivityMembersInjector = ResetPasswordActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideResetPasswordPresenterProvider);
    }

    public TrackingResetPasswordService trackingResetPasswordService() {
        return (TrackingResetPasswordService) this.provideTrackingResetPasswordServiceProvider.get();
    }

    public void inject(ResetPasswordActivity resetPasswordActivity) {
        this.resetPasswordActivityMembersInjector.injectMembers(resetPasswordActivity);
    }
}
