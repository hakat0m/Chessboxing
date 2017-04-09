package com.gumtree.android.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.users.UsersApi;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.google.gson.Gson;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.ad.treebay.TreebayAdManager;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.ad.treebay.services.item.ApiTreebayAdDetailsService;
import com.gumtree.android.ad.treebay.services.item.BackgroundTreebayAdDetailsService;
import com.gumtree.android.ad.treebay.services.item.TreebayAdDetailsService;
import com.gumtree.android.ad.treebay.services.search.ApiTreebaySearchResultService;
import com.gumtree.android.ad.treebay.services.search.BackgroundTreebaySearchResultService;
import com.gumtree.android.ad.treebay.services.search.TreebaySearchResultService;
import com.gumtree.android.api.ApiConfig.AppLocalisedTextProvider;
import com.gumtree.android.api.Retrofit2Client;
import com.gumtree.android.api.bing.BingAdsApi;
import com.gumtree.android.api.bing.BingAdsClient;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.android.api.treebay.TreebayApi;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.CustomerAccountManager;
import com.gumtree.android.auth.DefaultApplicationDataManager;
import com.gumtree.android.category.suggest.service.converter.CategoryConverter;
import com.gumtree.android.category.suggest.service.converter.DefaultCategoryTreeConverter;
import com.gumtree.android.clients.ClientsApi;
import com.gumtree.android.clients.ClientsService;
import com.gumtree.android.clients.DefaultClientsService;
import com.gumtree.android.clients.GumtreeSoftUpdateSettingsProvider;
import com.gumtree.android.clients.SoftUpdateSettingsProvider;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.fragments.GumtreeAuthBaseStrategy;
import com.gumtree.android.common.http.BaseCapiClientFactory;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.dagger.scope.ApplicationScope;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.GumtreeEventBus;
import com.gumtree.android.location.services.TrackingLocationService;
import com.gumtree.android.notifications.GumtreeBadgeCounterManager;
import com.gumtree.android.notifications.GumtreeNotificationsProvider;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.post_ad.validation.IViewValidator;
import com.gumtree.android.post_ad.validation.ViewValidator;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.repositories.BackgroundPostAdRepository;
import com.gumtree.android.postad.repositories.PostAdRepository;
import com.gumtree.android.postad.services.ActiveFeatureConverter;
import com.gumtree.android.postad.services.AdModelConverter;
import com.gumtree.android.postad.services.ApiPostAdService;
import com.gumtree.android.postad.services.BackgroundPostAdService;
import com.gumtree.android.postad.services.DraftAdMyAdModelConverter;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.services.converter.DraftFeatureTypeConverter;
import com.gumtree.android.priceinfo.ApiPriceInfoService;
import com.gumtree.android.priceinfo.BackgroundPriceInfoService;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.services.BackgroundNetworkStateService;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.SimpleNetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.srp.bing.BingSRPCache;
import com.gumtree.android.srp.bing.BingSRPCacheServiceBinder;
import com.gumtree.android.srp.suggestion.ApiSuggestionsService;
import com.gumtree.android.srp.suggestion.BackgroundSuggestionsService;
import com.gumtree.android.srp.suggestion.CallistoSuggestionProvider;
import com.gumtree.android.srp.suggestion.CapiSuggestionProvider;
import com.gumtree.android.srp.suggestion.SuggestionProvider;
import com.gumtree.android.srp.suggestion.SuggestionsService;
import com.gumtree.android.tracking.DefaultTrackingDataProvider;
import com.gumtree.android.tracking.TrackingDataProvider;
import com.gumtree.android.userprofile.PreferencesUserProfileStatusService;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.ApiUserProfileService;
import com.gumtree.android.userprofile.services.BackgroundUserProfileService;
import com.gumtree.android.userprofile.services.DefaultUserProfileUpdateListener;
import com.gumtree.android.userprofile.services.DefaultUserService;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.userprofile.services.UserProfileUpdateListener;
import com.gumtree.android.userprofile.services.UserService;
import com.gumtree.android.vip.bing.BingVIPCache;
import com.gumtree.android.vip.bing.BingVIPCacheServiceBinder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ApplicationModule {
    private final Context appContext;

    public ApplicationModule(Context context) {
        this.appContext = context;
    }

    @Provides
    @ApplicationScope
    public Context provideAppContext() {
        return this.appContext;
    }

    @Provides
    @ApplicationScope
    public BaseAccountManager provideBaseAccountManager() {
        return new CustomerAccountManager(this.appContext);
    }

    @Provides
    @ApplicationScope
    SuggestionProvider provideSuggestionProvider(SuggestionsService suggestionsService, CapiClientManager capiClientManager) {
        return DevelopmentFlags.getInstance().isCallistoEnabled() ? new CallistoSuggestionProvider(suggestionsService) : new CapiSuggestionProvider(capiClientManager);
    }

    @Provides
    @ApplicationScope
    public FeaturesApi provideFeaturesApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (FeaturesApi) iCapiClient.api(FeaturesApi.class);
    }

    @Provides
    @ApplicationScope
    public UserProfileApi provideUserProfileApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (UserProfileApi) iCapiClient.api(UserProfileApi.class);
    }

    @Provides
    @ApplicationScope
    public UserAdsApi provideUserAdsApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (UserAdsApi) iCapiClient.api(UserAdsApi.class);
    }

    @Provides
    @ApplicationScope
    public NetworkStateService provideNetworkStateService(@Named("background") Scheduler scheduler) {
        return new BackgroundNetworkStateService(new SimpleNetworkStateService(this.appContext), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    public PriceInfoService providePriceInfoService(ApiPriceInfoService apiPriceInfoService, @Named("background") Scheduler scheduler) {
        return new BackgroundPriceInfoService(apiPriceInfoService, AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        return new GumtreeEventBus(true);
    }

    @Provides
    @ApplicationScope
    public Network provideNetwork() {
        return new Network();
    }

    @Provides
    @ApplicationScope
    public IViewValidator provideViewValidator() {
        return new ViewValidator();
    }

    @Provides
    @ApplicationScope
    public IBadgeCounterManager provideBadgeCounterManager(EventBus eventBus) {
        return new GumtreeBadgeCounterManager(eventBus);
    }

    @Provides
    @ApplicationScope
    public PushNotificationsProvider getAppNotificationsProvider(BaseAccountManager baseAccountManager, EventBus eventBus) {
        return new GumtreeNotificationsProvider(baseAccountManager, eventBus, this.appContext);
    }

    @Provides
    @ApplicationScope
    AuthBaseStrategy provideAuthBaseStrategy(BaseAccountManager baseAccountManager, ApplicationDataManager applicationDataManager) {
        return new GumtreeAuthBaseStrategy(baseAccountManager, applicationDataManager);
    }

    @Provides
    @ApplicationScope
    public LocalisedTextProvider provideLocalisedTextProvider() {
        return new AppLocalisedTextProvider(this.appContext);
    }

    @Provides
    @ApplicationScope
    public Repository<DraftAd> providePostAdRepository(Context context, @Named("background") Scheduler scheduler) {
        return new BackgroundPostAdRepository(new PostAdRepository(context.getFilesDir(), new Gson()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    public UserProfileStatusService provideUserProfileStatusService() {
        return new PreferencesUserProfileStatusService(this.appContext);
    }

    @Provides
    @ApplicationScope
    public UserProfileUpdateListener provideUserProfileUpdateListener(EventBus eventBus) {
        return new DefaultUserProfileUpdateListener(eventBus);
    }

    @Provides
    @ApplicationScope
    public AdModelConverter provideAdModelConverter(BaseAccountManager baseAccountManager, DefaultCategoryTreeConverter defaultCategoryTreeConverter) {
        return new DraftAdMyAdModelConverter(new ActiveFeatureConverter(new DraftFeatureTypeConverter()), new CategoryConverter(defaultCategoryTreeConverter), baseAccountManager);
    }

    @Provides
    @ApplicationScope
    public UserService provideUserService(UserProfileService userProfileService, BaseAccountManager baseAccountManager, UserProfileUpdateListener userProfileUpdateListener, UserProfileStatusService userProfileStatusService, @Named("background") Scheduler scheduler) {
        return new DefaultUserService(new BackgroundUserProfileService(userProfileService, AndroidSchedulers.mainThread(), scheduler), baseAccountManager, userProfileStatusService, userProfileUpdateListener);
    }

    @Provides
    @ApplicationScope
    public UserProfileService provideUserProfileService(@NonNull UserProfileApi userProfileApi, @NonNull BaseAccountManager baseAccountManager) {
        return new ApiUserProfileService(userProfileApi, baseAccountManager);
    }

    @Provides
    @ApplicationScope
    public PostAdService providePostAdService(UserAdsApi userAdsApi, BaseAccountManager baseAccountManager, @Named("background") Scheduler scheduler, AdModelConverter adModelConverter) {
        return new BackgroundPostAdService(new ApiPostAdService(userAdsApi, baseAccountManager, adModelConverter), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    public BingSRPCache provideBingSRPCache() {
        return new BingSRPCache(new BingSRPCacheServiceBinder(this.appContext));
    }

    @Provides
    @ApplicationScope
    public BingVIPCache provideBingVIPCache() {
        return new BingVIPCache(new BingVIPCacheServiceBinder(this.appContext));
    }

    @Provides
    @ApplicationScope
    public BingAdsApi provideBingAdsApi(BingAdsClient bingAdsClient) {
        return (BingAdsApi) bingAdsClient.api(BingAdsApi.class);
    }

    @Provides
    @ApplicationScope
    public TreebayApi provideTreebayApi(@Named("treebayClient") Retrofit2Client retrofit2Client) {
        return (TreebayApi) retrofit2Client.api(TreebayApi.class);
    }

    @Provides
    @ApplicationScope
    public SuggestionsApi provideSuggestionsApi(@Named("callistoClient") Retrofit2Client retrofit2Client) {
        return (SuggestionsApi) retrofit2Client.api(SuggestionsApi.class);
    }

    @Provides
    @ApplicationScope
    public SuggestionsService provideSuggestionsService(SuggestionsApi suggestionsApi, @Named("background") Scheduler scheduler) {
        return new BackgroundSuggestionsService(new ApiSuggestionsService(suggestionsApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    public TrackingDataProvider providesTrackingDataProvider() {
        return new DefaultTrackingDataProvider();
    }

    @Provides
    @ApplicationScope
    public StaticTrackingService provideTrackingService(Context context, BaseAccountManager baseAccountManager) {
        return new StaticTrackingService(context, baseAccountManager);
    }

    @Provides
    @ApplicationScope
    public TrackingPostAdService provideTrackingPostAdService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @ApplicationScope
    public TrackingLocationService provideTrackingLocationService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @ApplicationScope
    public TrackingTreebayService provideTrackingTreebayService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @ApplicationScope
    public ClientsApi provideClientsApi(@Named("gsonClient") ICapiClient iCapiClient) {
        return (ClientsApi) iCapiClient.api(ClientsApi.class);
    }

    @Provides
    @ApplicationScope
    public SoftUpdateSettingsProvider provideSoftUpdateSettingsProvider(Context context) {
        return new GumtreeSoftUpdateSettingsProvider(context);
    }

    @Provides
    @ApplicationScope
    public ClientsService provideClientsService(ClientsApi clientsApi) {
        return new DefaultClientsService(clientsApi);
    }

    @Provides
    @ApplicationScope
    UsersApi provideUsersApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (UsersApi) iCapiClient.api(UsersApi.class);
    }

    @Provides
    @ApplicationScope
    public AdsApi provideAdsApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (AdsApi) iCapiClient.api(AdsApi.class);
    }

    @Provides
    @ApplicationScope
    public TreebayAdManager provideTreebayAdManager(TreebaySearchResultService treebaySearchResultService, TrackingTreebayService trackingTreebayService) {
        return new TreebayAdManager(treebaySearchResultService, trackingTreebayService);
    }

    @Provides
    @ApplicationScope
    public TreebaySearchResultService provideTreebaySearchResultService(TreebayApi treebayApi, @Named("background") Scheduler scheduler) {
        return new BackgroundTreebaySearchResultService(new ApiTreebaySearchResultService(treebayApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    public TreebayAdDetailsService provideTreebayAdDetailsService(TreebayApi treebayApi, @Named("background") Scheduler scheduler) {
        return new BackgroundTreebayAdDetailsService(new ApiTreebayAdDetailsService(treebayApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @ApplicationScope
    ApplicationDataManager provideApplicationDataManager(Repository<DraftAd> repository, IBadgeCounterManager iBadgeCounterManager, BaseAccountManager baseAccountManager, EventBus eventBus) {
        return new DefaultApplicationDataManager(this.appContext, repository, iBadgeCounterManager, baseAccountManager, eventBus);
    }

    @Provides
    @ApplicationScope
    CapiClientManager provideCapiClientManager(ApplicationDataManager applicationDataManager) {
        return new CapiClientManager(applicationDataManager, new BaseCapiClientFactory());
    }
}
