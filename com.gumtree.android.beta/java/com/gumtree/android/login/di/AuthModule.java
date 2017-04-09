package com.gumtree.android.login.di;

import android.accounts.AccountAuthenticatorResponse;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.users.UsersApi;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthTextProvider;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.GatedAuthView;
import com.gumtree.android.auth.facebook.GatedFacebookLoginView;
import com.gumtree.android.auth.facebook.presenter.DefaultFacebookLoginPresenter;
import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter;
import com.gumtree.android.auth.google.GatedGoogleLoginView;
import com.gumtree.android.auth.google.presenter.DefaultGoogleLoginPresenter;
import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter;
import com.gumtree.android.auth.google.services.BackgroundGoogleService;
import com.gumtree.android.auth.google.services.GoogleService;
import com.gumtree.android.auth.login.GatedLoginView;
import com.gumtree.android.auth.login.presenter.DefaultLoginPresenter;
import com.gumtree.android.auth.login.presenter.LoginPresenter;
import com.gumtree.android.auth.login.services.ApiLoginService;
import com.gumtree.android.auth.login.services.BackgroundLoginService;
import com.gumtree.android.auth.login.services.LoginService;
import com.gumtree.android.auth.login.services.TrackingLoginService;
import com.gumtree.android.auth.presenter.AuthNav;
import com.gumtree.android.auth.presenter.AuthPresenter;
import com.gumtree.android.auth.presenter.DefaultAuthPresenter;
import com.gumtree.android.auth.presenter.NavView;
import com.gumtree.android.auth.registration.RegistrationTextProvider;
import com.gumtree.android.auth.registration.presenter.DefaultRegistrationPresenter;
import com.gumtree.android.auth.registration.presenter.GatedRegistrationView;
import com.gumtree.android.auth.registration.presenter.RegistrationPresenter;
import com.gumtree.android.auth.registration.services.ApiRegistrationService;
import com.gumtree.android.auth.registration.services.BackgroundRegistrationService;
import com.gumtree.android.auth.registration.services.RegistrationService;
import com.gumtree.android.auth.registration.services.TrackingRegistrationService;
import com.gumtree.android.auth.services.AuthService;
import com.gumtree.android.auth.services.DefaultLoginCacheService;
import com.gumtree.android.auth.services.LoginCacheService;
import com.gumtree.android.auth.services.validators.DefaultPasswordStrengthService;
import com.gumtree.android.auth.services.validators.EmailValidatorService;
import com.gumtree.android.auth.services.validators.NameValidatorService;
import com.gumtree.android.auth.services.validators.PasswordStrengthService;
import com.gumtree.android.auth.services.validators.SimplePasswordStrengthService;
import com.gumtree.android.auth.services.validators.TextValidatorService;
import com.gumtree.android.auth.trust.TrustHandler;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.login.AndroidAuthService;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.login.DefaultAuthTextProvider;
import com.gumtree.android.login.facebook.FacebookLoginFragment;
import com.gumtree.android.login.google.DefaultGoogleService;
import com.gumtree.android.login.google.GoogleLoginFragment;
import com.gumtree.android.login.login.LoginFragment;
import com.gumtree.android.login.registration.DefaultRegistrationTextProvider;
import com.gumtree.android.login.registration.RegistrationFragment;
import com.gumtree.android.login.trust.DefaultTrustHandler;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.userprofile.services.ApiUserProfileService;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import org.apache.commons.lang3.Validate;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AuthModule {
    private final AuthIdentifier authIdentifier;
    private final AccountAuthenticatorResponse authResponse;
    private final DefaultGoogleService googleService;
    private NavView nav;

    private AuthModule(@NonNull AuthIdentifier authIdentifier, AccountAuthenticatorResponse accountAuthenticatorResponse, @NonNull NavView navView, @NonNull DefaultGoogleService defaultGoogleService) {
        this.authResponse = accountAuthenticatorResponse;
        this.authIdentifier = (AuthIdentifier) Validate.notNull(authIdentifier);
        this.nav = (NavView) Validate.notNull(navView);
        this.googleService = (DefaultGoogleService) Validate.notNull(defaultGoogleService);
    }

    public static void inject(@NonNull AuthActivity authActivity) {
        AuthComponent authComponent = (AuthComponent) ComponentsManager.get().getBaseComponent(AuthComponent.KEY);
        if (authComponent == null) {
            authComponent = createAuthComponent(authActivity);
        }
        authComponent.inject(authActivity);
    }

    public static void inject(@NonNull FacebookLoginFragment facebookLoginFragment) {
        AuthComponent authComponent = (AuthComponent) ComponentsManager.get().getBaseComponent(AuthComponent.KEY);
        if (authComponent == null) {
            authComponent = createAuthComponent(facebookLoginFragment.getActivity());
        }
        authComponent.inject(facebookLoginFragment);
    }

    public static void inject(@NonNull GoogleLoginFragment googleLoginFragment) {
        AuthComponent authComponent = (AuthComponent) ComponentsManager.get().getBaseComponent(AuthComponent.KEY);
        if (authComponent == null) {
            authComponent = createAuthComponent(googleLoginFragment.getActivity());
        }
        authComponent.inject(googleLoginFragment);
    }

    public static void inject(@NonNull LoginFragment loginFragment) {
        AuthComponent authComponent = (AuthComponent) ComponentsManager.get().getBaseComponent(AuthComponent.KEY);
        if (authComponent == null) {
            authComponent = createAuthComponent(loginFragment.getActivity());
        }
        authComponent.inject(loginFragment);
    }

    public static void inject(RegistrationFragment registrationFragment) {
        AuthComponent authComponent = (AuthComponent) ComponentsManager.get().getBaseComponent(AuthComponent.KEY);
        if (authComponent == null) {
            authComponent = createAuthComponent(registrationFragment.getActivity());
        }
        authComponent.inject(registrationFragment);
    }

    private static AuthComponent createAuthComponent(@NonNull Activity activity) {
        AccountAuthenticatorResponse accountAuthenticatorResponse = (AccountAuthenticatorResponse) activity.getIntent().getParcelableExtra("accountAuthenticatorResponse");
        AuthIdentifier authIdentifier = (AuthIdentifier) activity.getIntent().getSerializableExtra(AuthIdentifier.class.getSimpleName());
        NavView navView = (NavView) activity.getIntent().getSerializableExtra(NavView.class.getSimpleName());
        if (authIdentifier == null) {
            authIdentifier = AuthIdentifier.SETTINGS;
        }
        if (navView == null) {
            navView = NavView.SPLASH;
        }
        Object build = DaggerAuthComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).authModule(new AuthModule(authIdentifier, accountAuthenticatorResponse, navView, new DefaultGoogleService())).build();
        ComponentsManager.get().putBaseComponent(AuthComponent.KEY, build);
        return build;
    }

    @Provides
    @AuthScope
    public AuthService provideAuthService(@NonNull PushNotificationsProvider pushNotificationsProvider) {
        return new AndroidAuthService(this.authResponse, pushNotificationsProvider, new GCMSettingsProvider());
    }

    @Provides
    @AuthScope
    public AuthPresenter provideAuthPresenter(@NonNull GatedAuthView gatedAuthView, @NonNull AuthService authService, @NonNull TrackingLoginService trackingLoginService, @NonNull TrackingRegistrationService trackingRegistrationService, @NonNull AuthTextProvider authTextProvider, @NonNull GoogleService googleService) {
        return new DefaultAuthPresenter(gatedAuthView, authService, this.authIdentifier, trackingLoginService, trackingRegistrationService, authTextProvider, this.nav, googleService);
    }

    @Provides
    @AuthScope
    public UserProfileService provideUserProfileService(@NonNull UserProfileApi userProfileApi, @NonNull BaseAccountManager baseAccountManager) {
        return new ApiUserProfileService(userProfileApi, baseAccountManager);
    }

    @Provides
    @AuthScope
    public AuthIdentifier provideAuthIdentifier() {
        return this.authIdentifier;
    }

    @Provides
    @AuthScope
    public AuthNav provideAuthNav(AuthPresenter authPresenter) {
        return authPresenter;
    }

    @Provides
    @AuthScope
    public FacebookLoginPresenter provideFacebookLoginPresenter(GatedFacebookLoginView gatedFacebookLoginView, LoginService loginService, NetworkStateService networkStateService, TrackingLoginService trackingLoginService) {
        return new DefaultFacebookLoginPresenter(gatedFacebookLoginView, loginService, networkStateService, this.authIdentifier, trackingLoginService);
    }

    @Provides
    @AuthScope
    LoginApi provideLoginApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (LoginApi) iCapiClient.api(LoginApi.class);
    }

    @Provides
    @AuthScope
    public LoginService provideLoginService(LoginApi loginApi, UserProfileService userProfileService, BaseAccountManager baseAccountManager, AuthService authService, UserService userService) {
        return new BackgroundLoginService(new ApiLoginService(loginApi, userProfileService, baseAccountManager, authService, userService), AndroidSchedulers.mainThread(), Schedulers.newThread());
    }

    @Provides
    @AuthScope
    public GoogleLoginPresenter provideGoogleLoginPresenter(GatedGoogleLoginView gatedGoogleLoginView, LoginService loginService, NetworkStateService networkStateService, AuthIdentifier authIdentifier, AuthTextProvider authTextProvider, AuthNav authNav, TrackingLoginService trackingLoginService, GoogleService googleService, LoginCacheService loginCacheService) {
        return new DefaultGoogleLoginPresenter(gatedGoogleLoginView, loginService, networkStateService, authIdentifier, authTextProvider, authNav, trackingLoginService, googleService, loginCacheService);
    }

    @Provides
    @AuthScope
    public AuthTextProvider provideAuthTextProvider(Context context, LocalisedTextProvider localisedTextProvider) {
        return new DefaultAuthTextProvider(context, localisedTextProvider);
    }

    @Provides
    @Named("default")
    @AuthScope
    public PasswordStrengthService provideDefaultPasswordStrengthService() {
        return new DefaultPasswordStrengthService();
    }

    @Provides
    @Named("simple")
    @AuthScope
    public PasswordStrengthService provideSimplePasswordStrengthService() {
        return new SimplePasswordStrengthService();
    }

    @Provides
    @Named("email")
    @AuthScope
    public TextValidatorService provideEmailValidatorService() {
        return new EmailValidatorService();
    }

    @Provides
    @Named("name")
    @AuthScope
    public TextValidatorService provideNameValidatorService() {
        return new NameValidatorService();
    }

    @Provides
    @AuthScope
    public LoginPresenter provideLoginPresenter(GatedLoginView gatedLoginView, LoginService loginService, NetworkStateService networkStateService, @Named("simple") PasswordStrengthService passwordStrengthService, @Named("email") TextValidatorService textValidatorService, LoginCacheService loginCacheService, TrackingLoginService trackingLoginService, GoogleService googleService) {
        return new DefaultLoginPresenter(gatedLoginView, loginService, networkStateService, passwordStrengthService, textValidatorService, loginCacheService, trackingLoginService, this.authIdentifier, googleService);
    }

    @Provides
    @AuthScope
    public RegistrationPresenter provideRegistrationPresenter(GatedRegistrationView gatedRegistrationView, RegistrationService registrationService, NetworkStateService networkStateService, @Named("name") TextValidatorService textValidatorService, @Named("default") PasswordStrengthService passwordStrengthService, @Named("email") TextValidatorService textValidatorService2, RegistrationTextProvider registrationTextProvider, LoginCacheService loginCacheService, TrackingRegistrationService trackingRegistrationService) {
        return new DefaultRegistrationPresenter(gatedRegistrationView, registrationService, networkStateService, textValidatorService, passwordStrengthService, textValidatorService2, registrationTextProvider, loginCacheService, trackingRegistrationService);
    }

    @Provides
    @AuthScope
    public RegistrationService provideRegistrationService(UsersApi usersApi, @Named("background") Scheduler scheduler, TrustHandler trustHandler, RegistrationTextProvider registrationTextProvider) {
        return new BackgroundRegistrationService(new ApiRegistrationService(trustHandler, usersApi, registrationTextProvider), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @AuthScope
    public TrustHandler provideTrustHandler(Context context) {
        return new DefaultTrustHandler(context);
    }

    @Provides
    @AuthScope
    public RegistrationTextProvider provideRegistrationTextProvider(Context context, LocalisedTextProvider localisedTextProvider) {
        return new DefaultRegistrationTextProvider(context, localisedTextProvider);
    }

    @Provides
    @AuthScope
    public LoginCacheService provideLoginCacheService() {
        return new DefaultLoginCacheService();
    }

    @Provides
    @AuthScope
    TrackingLoginService provideTrackingAuthService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @AuthScope
    TrackingRegistrationService provideTrackingRegistrationService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @AuthScope
    GoogleService provideBackgroundGoogleService(@Named("background") Scheduler scheduler) {
        return new BackgroundGoogleService(this.googleService, scheduler, Schedulers.newThread());
    }
}
