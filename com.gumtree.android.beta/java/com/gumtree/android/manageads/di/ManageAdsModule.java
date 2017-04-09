package com.gumtree.android.manageads.di;

import android.content.Context;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.common.paging.PagingManager;
import com.gumtree.android.manageads.DefaultManageAdsLocalisedColorProvider;
import com.gumtree.android.manageads.DefaultManageAdsLocalisedStatusTextProvider;
import com.gumtree.android.manageads.DefaultManageAdsLocalisedTextProvider;
import com.gumtree.android.manageads.GatedManageAdsView;
import com.gumtree.android.manageads.active.GatedActiveAdsView;
import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter;
import com.gumtree.android.manageads.active.presenter.DefaultActiveAdsPresenter;
import com.gumtree.android.manageads.active.services.converter.DefaultActiveAdsConverter;
import com.gumtree.android.manageads.inactive.GatedInactiveAdsView;
import com.gumtree.android.manageads.inactive.presenter.DefaultInactiveAdsPresenter;
import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter;
import com.gumtree.android.manageads.inactive.services.converter.DefaultInactiveAdsConverter;
import com.gumtree.android.manageads.presenter.DefaultManageAdsPresenter;
import com.gumtree.android.manageads.presenter.ManageAdsPresenter;
import com.gumtree.android.manageads.services.ApiManageAdsService;
import com.gumtree.android.manageads.services.BackgroundManageAdsService;
import com.gumtree.android.manageads.services.ManageAdsService;
import com.gumtree.android.manageads.services.TrackingManageAdsService;
import com.gumtree.android.manageads.services.converter.ManageAdsConverter;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedColorProvider;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedStatusTextProvider;
import com.gumtree.android.manageads.services.providers.ManageAdsLocalisedTextProvider;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ManageAdsModule {
    @Provides
    @Named("inactive")
    @ManageAdsScope
    public ManageAdsConverter provideInactiveAdsConverter(ManageAdsLocalisedTextProvider manageAdsLocalisedTextProvider, ManageAdsLocalisedStatusTextProvider manageAdsLocalisedStatusTextProvider, ManageAdsLocalisedColorProvider manageAdsLocalisedColorProvider) {
        return new DefaultInactiveAdsConverter(manageAdsLocalisedTextProvider, manageAdsLocalisedStatusTextProvider, manageAdsLocalisedColorProvider);
    }

    @Provides
    @Named("active")
    @ManageAdsScope
    public ManageAdsConverter provideActiveAdsConverter(ManageAdsLocalisedTextProvider manageAdsLocalisedTextProvider, ManageAdsLocalisedStatusTextProvider manageAdsLocalisedStatusTextProvider, ManageAdsLocalisedColorProvider manageAdsLocalisedColorProvider) {
        return new DefaultActiveAdsConverter(manageAdsLocalisedTextProvider, manageAdsLocalisedStatusTextProvider, manageAdsLocalisedColorProvider);
    }

    @Provides
    @Named("inactive")
    @ManageAdsScope
    public ManageAdsService provideInactiveAdsService(UserAdsApi userAdsApi, @Named("background") Scheduler scheduler, @Named("inactive") ManageAdsConverter manageAdsConverter) {
        return new BackgroundManageAdsService(new ApiManageAdsService(userAdsApi, manageAdsConverter), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @Named("active")
    @ManageAdsScope
    public ManageAdsService provideActiveAdsService(UserAdsApi userAdsApi, @Named("background") Scheduler scheduler, @Named("active") ManageAdsConverter manageAdsConverter) {
        return new BackgroundManageAdsService(new ApiManageAdsService(userAdsApi, manageAdsConverter), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ManageAdsScope
    public ManageAdsPresenter provideManageAdsPresenter(GatedManageAdsView gatedManageAdsView, BaseAccountManager baseAccountManager, TrackingManageAdsService trackingManageAdsService) {
        return new DefaultManageAdsPresenter(gatedManageAdsView, baseAccountManager, trackingManageAdsService);
    }

    @Provides
    @ManageAdsScope
    public ManageAdsLocalisedTextProvider provideManageAdsLocalisedTextProvider(Context context) {
        return new DefaultManageAdsLocalisedTextProvider(context);
    }

    @Provides
    @ManageAdsScope
    public ManageAdsLocalisedStatusTextProvider provideManageAdsLocalisedStatusTextProvider(Context context) {
        return new DefaultManageAdsLocalisedStatusTextProvider(context);
    }

    @Provides
    @ManageAdsScope
    public ManageAdsLocalisedColorProvider provideManageAdsLocalisedColorProvider() {
        return new DefaultManageAdsLocalisedColorProvider();
    }

    @Provides
    @ManageAdsScope
    public ActiveAdsPresenter provideManageAdsActivePresenter(GatedActiveAdsView gatedActiveAdsView, NetworkStateService networkStateService, @Named("active") ManageAdsService manageAdsService, BaseAccountManager baseAccountManager, ManageAdsLocalisedTextProvider manageAdsLocalisedTextProvider, PagingConfig pagingConfig, TrackingManageAdsService trackingManageAdsService) {
        return new DefaultActiveAdsPresenter(gatedActiveAdsView, networkStateService, manageAdsService, baseAccountManager, manageAdsLocalisedTextProvider, new PagingManager(pagingConfig), trackingManageAdsService);
    }

    @Provides
    @ManageAdsScope
    public InactiveAdsPresenter provideManageAdsInactivePresenter(GatedInactiveAdsView gatedInactiveAdsView, NetworkStateService networkStateService, @Named("inactive") ManageAdsService manageAdsService, BaseAccountManager baseAccountManager, ManageAdsLocalisedTextProvider manageAdsLocalisedTextProvider, PagingConfig pagingConfig, TrackingManageAdsService trackingManageAdsService) {
        return new DefaultInactiveAdsPresenter(gatedInactiveAdsView, networkStateService, manageAdsService, baseAccountManager, manageAdsLocalisedTextProvider, new PagingManager(pagingConfig), trackingManageAdsService);
    }

    @Provides
    @ManageAdsScope
    public PagingConfig providePagingConfig() {
        return new PagingConfig();
    }

    @Provides
    @ManageAdsScope
    TrackingManageAdsService provideTrackingManageAdsService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
