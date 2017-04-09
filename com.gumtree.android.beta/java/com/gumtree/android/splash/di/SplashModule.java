package com.gumtree.android.splash.di;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.google.services.BackgroundGoogleService;
import com.gumtree.android.auth.google.services.GoogleService;
import com.gumtree.android.auth.login.services.ApiLoginService;
import com.gumtree.android.auth.login.services.BackgroundLoginService;
import com.gumtree.android.auth.login.services.LoginService;
import com.gumtree.android.auth.login.services.TrackingLoginService;
import com.gumtree.android.auth.services.AuthService;
import com.gumtree.android.login.AndroidAuthService;
import com.gumtree.android.login.google.DefaultGoogleService;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.splash.GatedSplashView;
import com.gumtree.android.splash.presenter.DefaultSplashPresenter;
import com.gumtree.android.splash.presenter.SplashPresenter;
import com.gumtree.android.userprofile.services.ApiUserProfileService;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class SplashModule {
    @Provides
    @SplashScope
    public SplashPresenter provideSplashPresenter(GatedSplashView gatedSplashView, LoginService loginService, NetworkStateService networkStateService, TrackingLoginService trackingLoginService, GoogleService googleService, BaseAccountManager baseAccountManager) {
        return new DefaultSplashPresenter(loginService, networkStateService, trackingLoginService, googleService, gatedSplashView, baseAccountManager);
    }

    @Provides
    @SplashScope
    public LoginService provideLoginService(LoginApi loginApi, UserProfileService userProfileService, BaseAccountManager baseAccountManager, AuthService authService, UserService userService, @Named("background") Scheduler scheduler) {
        return new BackgroundLoginService(new ApiLoginService(loginApi, userProfileService, baseAccountManager, authService, userService), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @SplashScope
    public AuthService provideAuthService(@NonNull PushNotificationsProvider pushNotificationsProvider) {
        return new AndroidAuthService(null, pushNotificationsProvider, new GCMSettingsProvider());
    }

    @Provides
    @SplashScope
    LoginApi provideLoginApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (LoginApi) iCapiClient.api(LoginApi.class);
    }

    @Provides
    @SplashScope
    public UserProfileService provideUserProfileService(@NonNull UserProfileApi userProfileApi, @NonNull BaseAccountManager baseAccountManager) {
        return new ApiUserProfileService(userProfileApi, baseAccountManager);
    }

    @Provides
    @SplashScope
    GoogleService provideGoogleService(@Named("background") Scheduler scheduler) {
        return new BackgroundGoogleService(new DefaultGoogleService(), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @SplashScope
    TrackingLoginService provideTrackingAuthService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
