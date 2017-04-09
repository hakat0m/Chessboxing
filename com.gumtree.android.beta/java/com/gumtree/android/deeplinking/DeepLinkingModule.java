package com.gumtree.android.deeplinking;

import android.content.Context;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.seo.SeoApi;
import com.ebay.classifieds.capi.users.UsersApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.deeplinking.activate.ActivateDeepLinkingPresenter;
import com.gumtree.android.deeplinking.activate.ActivateUserService;
import com.gumtree.android.deeplinking.activate.ApiActivateUserService;
import com.gumtree.android.deeplinking.activate.BackgroundActivateUserService;
import com.gumtree.android.deeplinking.activate.DefaultActivateDeepLinkingPresenter;
import com.gumtree.android.deeplinking.activate.GatedActivateDeepLinkingView;
import com.gumtree.android.deeplinking.activate.TrackingActivateService;
import com.gumtree.android.deeplinking.forgotpassword.DefaultForgotPasswordDeepLinkingPresenter;
import com.gumtree.android.deeplinking.forgotpassword.ForgotPasswordDeepLinkingPresenter;
import com.gumtree.android.deeplinking.postad.DefaultPostAdDeepLinkingPresenter;
import com.gumtree.android.deeplinking.postad.PostAdDeepLinkingPresenter;
import com.gumtree.android.deeplinking.postad.TrackingPostAdDeepLinkingService;
import com.gumtree.android.deeplinking.presenter.DeepLinkingPresenter;
import com.gumtree.android.deeplinking.presenter.DefaultDeepLinkingPresenter;
import com.gumtree.android.deeplinking.search.DeepLinkingService;
import com.gumtree.android.deeplinking.search.DefaultSearchDeepLinkingPresenter;
import com.gumtree.android.deeplinking.search.GatedSearchDeepLinkingView;
import com.gumtree.android.deeplinking.search.SearchDeepLinkingPresenter;
import com.gumtree.android.deeplinking.services.BackgroundDeepLinkingService;
import com.gumtree.android.deeplinking.services.SeoDeepLinkingService;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class DeepLinkingModule {
    private final String link;

    public DeepLinkingModule(String str) {
        this.link = str;
    }

    @Provides
    @DeepLinkingScope
    public DeepLinkingPresenter provideDeepLinkPresenter(ActivateDeepLinkingPresenter activateDeepLinkingPresenter, SearchDeepLinkingPresenter searchDeepLinkingPresenter, ForgotPasswordDeepLinkingPresenter forgotPasswordDeepLinkingPresenter, PostAdDeepLinkingPresenter postAdDeepLinkingPresenter) {
        return new DefaultDeepLinkingPresenter(activateDeepLinkingPresenter, searchDeepLinkingPresenter, forgotPasswordDeepLinkingPresenter, postAdDeepLinkingPresenter);
    }

    @Provides
    @DeepLinkingScope
    public SearchDeepLinkingPresenter provideDeepLinkSearchPresenter(@NonNull GatedSearchDeepLinkingView gatedSearchDeepLinkingView, DeepLinkingService deepLinkingService, Context context, @NonNull NetworkStateService networkStateService, @NonNull LocalisedTextProvider localisedTextProvider) {
        return new DefaultSearchDeepLinkingPresenter(gatedSearchDeepLinkingView, this.link, SearchParameters.builder().categoryId(context.getString(2131165400)).categoryName(context.getString(2131165401)).locationId(context.getString(2131165402)).locationName(context.getString(2131165403)).build(), deepLinkingService, networkStateService, localisedTextProvider);
    }

    @Provides
    @DeepLinkingScope
    public ForgotPasswordDeepLinkingPresenter provideForgotPasswordDeepLinkingPresenter(@NonNull CapiConfig capiConfig, @NonNull LocalisedTextProvider localisedTextProvider) {
        return new DefaultForgotPasswordDeepLinkingPresenter(capiConfig.getMyProfileHost(), this.link, localisedTextProvider);
    }

    @Provides
    @DeepLinkingScope
    public ActivateDeepLinkingPresenter provideActivateDeepLinkingPresenter(@NonNull CapiConfig capiConfig, @NonNull GatedActivateDeepLinkingView gatedActivateDeepLinkingView, @NonNull ActivateUserService activateUserService, @NonNull NetworkStateService networkStateService, @NonNull LocalisedTextProvider localisedTextProvider, @NonNull TrackingActivateService trackingActivateService, @NonNull BaseAccountManager baseAccountManager) {
        return new DefaultActivateDeepLinkingPresenter(capiConfig.getMyProfileHost(), gatedActivateDeepLinkingView, this.link, activateUserService, networkStateService, localisedTextProvider, trackingActivateService, baseAccountManager);
    }

    @Provides
    @DeepLinkingScope
    public PostAdDeepLinkingPresenter providePostAdDeepLinkingPresenter(@NonNull TrackingPostAdDeepLinkingService trackingPostAdDeepLinkingService, @NonNull CapiConfig capiConfig, @NonNull LocalisedTextProvider localisedTextProvider) {
        return new DefaultPostAdDeepLinkingPresenter(trackingPostAdDeepLinkingService, capiConfig.getMyProfileHost(), this.link, localisedTextProvider);
    }

    @Provides
    @DeepLinkingScope
    public TrackingPostAdDeepLinkingService providePostAdTrackingDeepLinkingService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @DeepLinkingScope
    public DeepLinkingService provideDeepLinkingService(@Named("xmlClient") ICapiClient iCapiClient, @Named("background") Scheduler scheduler) {
        return new BackgroundDeepLinkingService(new SeoDeepLinkingService((SeoApi) iCapiClient.api(SeoApi.class)), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @DeepLinkingScope
    public ActivateUserService provideActivateService(UsersApi usersApi, @Named("background") Scheduler scheduler) {
        return new BackgroundActivateUserService(new ApiActivateUserService(usersApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @DeepLinkingScope
    TrackingActivateService provideTrackingActivateService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
