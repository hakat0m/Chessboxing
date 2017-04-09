package com.gumtree.android.login.resetpassword.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.resetpassword.ResetPasswordApi;
import com.gumtree.android.auth.resetpassword.GatedResetPasswordView;
import com.gumtree.android.auth.resetpassword.presenter.DefaultResetPasswordPresenter;
import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter;
import com.gumtree.android.auth.resetpassword.services.ApiResetPasswordService;
import com.gumtree.android.auth.resetpassword.services.BackgroundResetPasswordService;
import com.gumtree.android.auth.resetpassword.services.ResetPasswordLocalisedTextProvider;
import com.gumtree.android.auth.resetpassword.services.ResetPasswordService;
import com.gumtree.android.auth.resetpassword.services.TrackingResetPasswordService;
import com.gumtree.android.auth.services.validators.DefaultPasswordStrengthService;
import com.gumtree.android.auth.services.validators.PasswordStrengthService;
import com.gumtree.android.login.resetpassword.DefaultResetPasswordLocalisedTextProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ResetPasswordModule {
    private String email;
    private String token;

    public ResetPasswordModule(String str, String str2) {
        this.email = str;
        this.token = str2;
    }

    @Provides
    @ResetPasswordScope
    ResetPasswordApi provideResetPasswordApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (ResetPasswordApi) iCapiClient.api(ResetPasswordApi.class);
    }

    @Provides
    @ResetPasswordScope
    public ResetPasswordService provideResetPasswordService(ResetPasswordApi resetPasswordApi, @Named("background") Scheduler scheduler) {
        return new BackgroundResetPasswordService(new ApiResetPasswordService(resetPasswordApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ResetPasswordScope
    public ResetPasswordPresenter provideResetPasswordPresenter(GatedResetPasswordView gatedResetPasswordView, ResetPasswordService resetPasswordService, NetworkStateService networkStateService, ResetPasswordLocalisedTextProvider resetPasswordLocalisedTextProvider, @Named("default") PasswordStrengthService passwordStrengthService, TrackingResetPasswordService trackingResetPasswordService) {
        return new DefaultResetPasswordPresenter(gatedResetPasswordView, resetPasswordService, networkStateService, resetPasswordLocalisedTextProvider, passwordStrengthService, trackingResetPasswordService, this.email, this.token);
    }

    @Provides
    @ResetPasswordScope
    public ResetPasswordLocalisedTextProvider provideResetPasswordLocalisedTextProvider(Context context) {
        return new DefaultResetPasswordLocalisedTextProvider(context);
    }

    @Provides
    @Named("default")
    @ResetPasswordScope
    public PasswordStrengthService provideSimplePasswordStrengthService() {
        return new DefaultPasswordStrengthService();
    }

    @Provides
    @ResetPasswordScope
    TrackingResetPasswordService provideTrackingResetPasswordService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
