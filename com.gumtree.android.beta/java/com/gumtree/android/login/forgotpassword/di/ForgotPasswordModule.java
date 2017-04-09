package com.gumtree.android.login.forgotpassword.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.users.forgotpassword.ForgotPasswordApi;
import com.gumtree.android.auth.forgotpassword.GatedForgotPasswordView;
import com.gumtree.android.auth.forgotpassword.presenter.DefaultForgotPasswordPresenter;
import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter;
import com.gumtree.android.auth.forgotpassword.services.ApiForgotPasswordService;
import com.gumtree.android.auth.forgotpassword.services.BackgroundForgotPasswordService;
import com.gumtree.android.auth.forgotpassword.services.ForgotPasswordLocalisedTextProvider;
import com.gumtree.android.auth.forgotpassword.services.ForgotPasswordService;
import com.gumtree.android.auth.forgotpassword.services.TrackingForgotPasswordService;
import com.gumtree.android.auth.services.validators.EmailValidatorService;
import com.gumtree.android.auth.services.validators.TextValidatorService;
import com.gumtree.android.login.forgotpassword.DefaultForgotPasswordLocalisedTextProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ForgotPasswordModule {
    @Provides
    @ForgotPasswordScope
    ForgotPasswordApi provideForgotPasswordApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (ForgotPasswordApi) iCapiClient.api(ForgotPasswordApi.class);
    }

    @Provides
    @ForgotPasswordScope
    public ForgotPasswordService provideForgotPasswordService(ForgotPasswordApi forgotPasswordApi, @Named("background") Scheduler scheduler) {
        return new BackgroundForgotPasswordService(new ApiForgotPasswordService(forgotPasswordApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ForgotPasswordScope
    public ForgotPasswordPresenter provideForgotPasswordPresenter(GatedForgotPasswordView gatedForgotPasswordView, ForgotPasswordService forgotPasswordService, NetworkStateService networkStateService, ForgotPasswordLocalisedTextProvider forgotPasswordLocalisedTextProvider, @Named("email") TextValidatorService textValidatorService, TrackingForgotPasswordService trackingForgotPasswordService) {
        return new DefaultForgotPasswordPresenter(gatedForgotPasswordView, forgotPasswordService, networkStateService, forgotPasswordLocalisedTextProvider, textValidatorService, trackingForgotPasswordService);
    }

    @Provides
    @ForgotPasswordScope
    public ForgotPasswordLocalisedTextProvider provideForgotPasswordLocalisedTextProvider(Context context) {
        return new DefaultForgotPasswordLocalisedTextProvider(context);
    }

    @Provides
    @Named("email")
    @ForgotPasswordScope
    public TextValidatorService provideEmailValidatorService() {
        return new EmailValidatorService();
    }

    @Provides
    @ForgotPasswordScope
    TrackingForgotPasswordService provideTrackingForgotPasswordService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
