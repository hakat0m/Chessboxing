package com.gumtree.android.dagger.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.users.UsersApi;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.GumtreeApplication_MembersInjector;
import com.gumtree.android.ad.treebay.TreebayAdManager;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.ad.treebay.services.item.TreebayAdDetailsService;
import com.gumtree.android.ad.treebay.services.search.TreebaySearchResultService;
import com.gumtree.android.api.EnvironmentSettings;
import com.gumtree.android.api.Retrofit2Client;
import com.gumtree.android.api.bing.BingAdsApi;
import com.gumtree.android.api.bing.BingAdsClient;
import com.gumtree.android.api.callisto.CallistoConfig;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.android.api.okhttp.OkHttpConfig;
import com.gumtree.android.api.treebay.TreebayApi;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.AuthenticatorActivity_MembersInjector;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.api.ActivateAccountIntentService;
import com.gumtree.android.auth.api.ActivateAccountIntentService_MembersInjector;
import com.gumtree.android.auth.api.AuthRequest;
import com.gumtree.android.auth.api.AuthRequest_MembersInjector;
import com.gumtree.android.categories.SuggestedCategoryFragment;
import com.gumtree.android.categories.SuggestedCategoryFragment_MembersInjector;
import com.gumtree.android.category.suggest.service.converter.DefaultCategoryTreeConverter_Factory;
import com.gumtree.android.clients.ClientsApi;
import com.gumtree.android.clients.ClientsService;
import com.gumtree.android.clients.SoftUpdateService;
import com.gumtree.android.clients.SoftUpdateService_MembersInjector;
import com.gumtree.android.clients.SoftUpdateSettingsProvider;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.NavigationActivity_MembersInjector;
import com.gumtree.android.common.connectivity.ConnectivityChangeReceiver;
import com.gumtree.android.common.connectivity.ConnectivityChangeReceiver_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.connectivity.Network_MembersInjector;
import com.gumtree.android.common.fragments.GumtreeAuthBaseStrategy;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.http.CAPIIntentFactory_MembersInjector;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.GumtreeRestExecutor;
import com.gumtree.android.common.http.GumtreeRestExecutor_MembersInjector;
import com.gumtree.android.configuration.GumtreeGlideModule;
import com.gumtree.android.configuration.GumtreeGlideModule_MembersInjector;
import com.gumtree.android.dagger.modules.ApplicationModule;
import com.gumtree.android.dagger.modules.ApplicationModule_GetAppNotificationsProviderFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideAdModelConverterFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideAdsApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideAppContextFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideApplicationDataManagerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideAuthBaseStrategyFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideBadgeCounterManagerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideBaseAccountManagerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideBingAdsApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideBingSRPCacheFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideBingVIPCacheFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideCapiClientManagerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideClientsApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideClientsServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideEventBusFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideFeaturesApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideLocalisedTextProviderFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideNetworkFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideNetworkStateServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvidePostAdRepositoryFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvidePostAdServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvidePriceInfoServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideSoftUpdateSettingsProviderFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideSuggestionProviderFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideSuggestionsApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideSuggestionsServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTrackingLocationServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTrackingPostAdServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTrackingServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTrackingTreebayServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTreebayAdDetailsServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTreebayAdManagerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTreebayApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideTreebaySearchResultServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserAdsApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserProfileApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserProfileServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserProfileStatusServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserProfileUpdateListenerFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUserServiceFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideUsersApiFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvideViewValidatorFactory;
import com.gumtree.android.dagger.modules.ApplicationModule_ProvidesTrackingDataProviderFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideApiAuthenticatorFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideBingAdsClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideCAPIIntentFactoryFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideCallAdapterFactoryFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideCallistoCallAdapterFactoryFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideCallistoClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideConverterFactoryFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideEnvironmentSettingsFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideGsonCapiClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvidePicturesUploadCapiClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideTreebayClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvideXmlCapiClientFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvidesApiConfigFactory;
import com.gumtree.android.dagger.modules.NetworkClientModule_ProvidesCallistoApiConfigFactory;
import com.gumtree.android.dagger.modules.OkHttpModule;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideDebugOkHttp2ClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideDebugOkHttp3ClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideHttpBodyLoggingInterceptorFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideOkHttp3ClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideOkHttpClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideReleaseOkHttpClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideSafeOkHttp3ClientFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvideSslSocketFactoryFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvidesOkHttpConfigFactory;
import com.gumtree.android.dagger.modules.OkHttpModule_ProvidesX509TrustManagerFactory;
import com.gumtree.android.dagger.modules.SystemModule;
import com.gumtree.android.dagger.modules.SystemModule_ProvideBackgroundSchedulerFactory;
import com.gumtree.android.dagger.modules.SystemModule_ProvideObjectMapperFactory;
import com.gumtree.android.dagger.modules.SystemModule_ProvideResourcesFactory;
import com.gumtree.android.dagger.modules.SystemModule_ProvideSharedPreferencesFactory;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.dfp.DFPProcessor_MembersInjector;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.favourites.FavouriteCapiIntentService;
import com.gumtree.android.favourites.FavouriteCapiIntentService_MembersInjector;
import com.gumtree.android.favourites.FavouriteIntentService;
import com.gumtree.android.favourites.FavouriteIntentService_MembersInjector;
import com.gumtree.android.favourites.FavouritesFragment;
import com.gumtree.android.favourites.FavouritesFragment_MembersInjector;
import com.gumtree.android.favourites.SavedFavouritesSync;
import com.gumtree.android.favourites.SavedFavouritesSync_MembersInjector;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.home.HomeActivity_MembersInjector;
import com.gumtree.android.home.HomeCustomiseFragment;
import com.gumtree.android.home.HomeCustomiseFragment_MembersInjector;
import com.gumtree.android.location.services.TrackingLocationService;
import com.gumtree.android.locations.LocationChildrenRequest;
import com.gumtree.android.locations.LocationChildrenRequest_MembersInjector;
import com.gumtree.android.locations.SetLocationFragment;
import com.gumtree.android.locations.SetLocationFragment_MembersInjector;
import com.gumtree.android.locations.postad.PostcodeLookupRequest;
import com.gumtree.android.locations.postad.PostcodeLookupRequest_MembersInjector;
import com.gumtree.android.login.AccountAuthenticator;
import com.gumtree.android.login.AccountAuthenticatorService;
import com.gumtree.android.login.AccountAuthenticator_MembersInjector;
import com.gumtree.android.managead.DeleteAdIntentService;
import com.gumtree.android.managead.DeleteAdIntentService_MembersInjector;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.managead.ManageAdActivity_MembersInjector;
import com.gumtree.android.managead.ManageAdsFragment;
import com.gumtree.android.managead.ManageAdsFragment_MembersInjector;
import com.gumtree.android.message_box.ConversationRequest;
import com.gumtree.android.message_box.ConversationRequest_MembersInjector;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.message_box.ConversationsIntentService_MembersInjector;
import com.gumtree.android.message_box.ConversationsRequest;
import com.gumtree.android.message_box.ConversationsRequest_MembersInjector;
import com.gumtree.android.message_box.DeleteConversationIntentService;
import com.gumtree.android.message_box.DeleteConversationIntentService_MembersInjector;
import com.gumtree.android.message_box.FlagConversationIntentService;
import com.gumtree.android.message_box.FlagConversationIntentService_MembersInjector;
import com.gumtree.android.message_box.InboxFragment;
import com.gumtree.android.message_box.InboxFragment_MembersInjector;
import com.gumtree.android.message_box.PostConversationReplyIntentService;
import com.gumtree.android.message_box.PostConversationReplyIntentService_MembersInjector;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.message_box.conversation.ConversationActivity_MembersInjector;
import com.gumtree.android.message_box.conversation.ConversationFragment;
import com.gumtree.android.message_box.conversation.ConversationFragment_MembersInjector;
import com.gumtree.android.message_box.conversation.ConversationIntentService;
import com.gumtree.android.message_box.conversation.ConversationIntentService_MembersInjector;
import com.gumtree.android.notifications.GcmIntentService;
import com.gumtree.android.notifications.GcmIntentService_MembersInjector;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.notifications.providers.CAPIPushNotificationsProvider;
import com.gumtree.android.notifications.providers.CAPIPushNotificationsProvider_MembersInjector;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.GCMSettingsProvider_MembersInjector;
import com.gumtree.android.notifications.providers.MDNSPushNotificationsProvider;
import com.gumtree.android.notifications.providers.MDNSPushNotificationsProvider_MembersInjector;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.post_ad.FeaturesIntentService;
import com.gumtree.android.post_ad.FeaturesIntentService_MembersInjector;
import com.gumtree.android.post_ad.MetadataIntentService;
import com.gumtree.android.post_ad.MetadataIntentService_MembersInjector;
import com.gumtree.android.post_ad.PostAdAPIIntentService;
import com.gumtree.android.post_ad.PostAdAPIIntentService_MembersInjector;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdBaseActivity_MembersInjector;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.PostAdDBIntentService_MembersInjector;
import com.gumtree.android.post_ad.PostAdInsertionFeeFragment;
import com.gumtree.android.post_ad.PostAdInsertionFeeFragment_MembersInjector;
import com.gumtree.android.post_ad.feature.PostAdPaymentActivity;
import com.gumtree.android.post_ad.feature.PostAdPaymentActivity_MembersInjector;
import com.gumtree.android.post_ad.feature.service.ApplyFeatureIntentService;
import com.gumtree.android.post_ad.feature.service.ApplyFeatureIntentService_MembersInjector;
import com.gumtree.android.post_ad.feature.service.SubmitOrderIntentService;
import com.gumtree.android.post_ad.feature.service.SubmitOrderIntentService_MembersInjector;
import com.gumtree.android.post_ad.gallery.GalleryActivity;
import com.gumtree.android.post_ad.payment.PostAdPaypalActivity;
import com.gumtree.android.post_ad.preview.PreviewActivity;
import com.gumtree.android.post_ad.preview.PreviewGalleryActivity;
import com.gumtree.android.post_ad.preview.PreviewGalleryActivity_MembersInjector;
import com.gumtree.android.post_ad.preview.PreviewImagePagerFragment;
import com.gumtree.android.post_ad.preview.PreviewImagePagerFragment_MembersInjector;
import com.gumtree.android.post_ad.steps.PostAdBaseFragment;
import com.gumtree.android.post_ad.steps.PostAdBaseFragment_MembersInjector;
import com.gumtree.android.post_ad.steps.PostAdOneFragment2;
import com.gumtree.android.post_ad.steps.PostAdOneFragment2_MembersInjector;
import com.gumtree.android.post_ad.steps.PostAdStepsActivity;
import com.gumtree.android.post_ad.steps.PostAdStepsActivity_MembersInjector;
import com.gumtree.android.post_ad.steps.PostAdThreeFragment2;
import com.gumtree.android.post_ad.steps.PostAdThreeFragment2_MembersInjector;
import com.gumtree.android.post_ad.steps.PostAdTwoFragment2;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity_MembersInjector;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity_MembersInjector;
import com.gumtree.android.post_ad.summary.PostAdSummaryFragment;
import com.gumtree.android.post_ad.summary.PostAdSummaryFragment_MembersInjector;
import com.gumtree.android.post_ad.validation.IViewValidator;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.AdModelConverter;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.services.converter.DraftFeatureConverter;
import com.gumtree.android.postad.services.converter.DraftFeatureConverter_Factory;
import com.gumtree.android.postad.services.converter.DraftFeatureTypeConverter_Factory;
import com.gumtree.android.postad.services.converter.DraftOptionConverter;
import com.gumtree.android.postad.services.converter.DraftOptionConverter_Factory;
import com.gumtree.android.postad.services.converter.DraftPeriodConverter_Factory;
import com.gumtree.android.priceinfo.ApiPriceInfoService;
import com.gumtree.android.priceinfo.ApiPriceInfoService_Factory;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.savedsearches.SavedSearchSync;
import com.gumtree.android.savedsearches.SavedSearchSync_MembersInjector;
import com.gumtree.android.savedsearches.SavedSearchesActivity;
import com.gumtree.android.savedsearches.SavedSearchesActivity_MembersInjector;
import com.gumtree.android.savedsearches.SavedSearchesPostIntentService;
import com.gumtree.android.savedsearches.SavedSearchesPostIntentService_MembersInjector;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.settings.SettingsFragment;
import com.gumtree.android.settings.SettingsFragment_MembersInjector;
import com.gumtree.android.srp.BaseDynAttributeFragment;
import com.gumtree.android.srp.BaseDynAttributeFragment_MembersInjector;
import com.gumtree.android.srp.NearByAdvertAdapter;
import com.gumtree.android.srp.NearByAdvertAdapter_MembersInjector;
import com.gumtree.android.srp.SRPActivity;
import com.gumtree.android.srp.SRPActivity_MembersInjector;
import com.gumtree.android.srp.bing.BingAdSRPView;
import com.gumtree.android.srp.bing.BingAdSRPView_MembersInjector;
import com.gumtree.android.srp.bing.BingSRPCache;
import com.gumtree.android.srp.bing.BingSRPIntentService;
import com.gumtree.android.srp.bing.BingSRPIntentService_MembersInjector;
import com.gumtree.android.srp.suggestion.SuggestionProvider;
import com.gumtree.android.srp.suggestion.SuggestionsService;
import com.gumtree.android.srp.treebay.AbstractTreebayAdItemView_MembersInjector;
import com.gumtree.android.srp.treebay.DaggerApplicationComponent_PackageProxy;
import com.gumtree.android.srp.treebay.TreebayAdGalleryItemView;
import com.gumtree.android.srp.treebay.TreebayAdGridItemView;
import com.gumtree.android.tracking.TrackingDataProvider;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.userprofile.services.UserProfileUpdateListener;
import com.gumtree.android.userprofile.services.UserService;
import com.gumtree.android.vip.VIPGalleryActivity;
import com.gumtree.android.vip.VIPGalleryActivity_MembersInjector;
import com.gumtree.android.vip.VIPImagePagerFragment;
import com.gumtree.android.vip.VIPImagePagerFragment_MembersInjector;
import com.gumtree.android.vip.VIPMainFragment;
import com.gumtree.android.vip.VIPMainFragment_MembersInjector;
import com.gumtree.android.vip.VIPSellerOtherItemsFragment;
import com.gumtree.android.vip.VIPSellerOtherItemsFragment_MembersInjector;
import com.gumtree.android.vip.api.VipDetailsIntentService;
import com.gumtree.android.vip.api.VipDetailsIntentService_MembersInjector;
import com.gumtree.android.vip.bing.BingAdFragment;
import com.gumtree.android.vip.bing.BingAdFragment_MembersInjector;
import com.gumtree.android.vip.bing.BingAdVIPView;
import com.gumtree.android.vip.bing.BingAdVIPView_MembersInjector;
import com.gumtree.android.vip.bing.BingVIPCache;
import com.gumtree.android.vip.bing.BingVIPIntentService;
import com.gumtree.android.vip.bing.BingVIPIntentService_MembersInjector;
import com.gumtree.android.vip.reply.ReplyToVIPFragment;
import com.gumtree.android.vip.reply.ReplyToVIPFragment_MembersInjector;
import com.gumtree.android.vip.reply.api.MetadataRequest;
import com.gumtree.android.vip.reply.api.MetadataRequest_MembersInjector;
import com.gumtree.android.vip.reply.api.SendReplyRequest;
import com.gumtree.android.vip.reply.api.SendReplyRequest_MembersInjector;
import com.gumtree.android.vip_treebay.BingAdFragmentTreebay;
import com.gumtree.android.vip_treebay.BingAdFragmentTreebay_MembersInjector;
import com.gumtree.android.vip_treebay.VIPGalleryActivityTreebay;
import com.gumtree.android.vip_treebay.VIPGalleryActivityTreebay_MembersInjector;
import com.gumtree.android.vip_treebay.VIPImagePagerFragmentTreebay;
import com.gumtree.android.vip_treebay.VIPImagePagerFragmentTreebay_MembersInjector;
import com.gumtree.android.vip_treebay.VIPMainFragmentTreebay;
import com.gumtree.android.vip_treebay.VIPMainFragmentTreebay_MembersInjector;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.OkHttpClient;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter.Factory;
import retrofit2.Converter;
import rx.Scheduler;

public final class DaggerApplicationComponent implements ApplicationComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerApplicationComponent.class.desiredAssertionStatus());
    private MembersInjector<AccountAuthenticator> accountAuthenticatorMembersInjector;
    private MembersInjector<ActivateAccountIntentService> activateAccountIntentServiceMembersInjector;
    private Provider<ApiPriceInfoService> apiPriceInfoServiceProvider;
    private MembersInjector<ApplyFeatureIntentService> applyFeatureIntentServiceMembersInjector;
    private MembersInjector<AuthRequest> authRequestMembersInjector;
    private MembersInjector<AuthenticatorActivity> authenticatorActivityMembersInjector;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private MembersInjector<BaseDynAttributeFragment> baseDynAttributeFragmentMembersInjector;
    private MembersInjector<BingAdFragment> bingAdFragmentMembersInjector;
    private MembersInjector<BingAdFragmentTreebay> bingAdFragmentTreebayMembersInjector;
    private MembersInjector<BingAdSRPView> bingAdSRPViewMembersInjector;
    private MembersInjector<BingAdVIPView> bingAdVIPViewMembersInjector;
    private MembersInjector<BingSRPIntentService> bingSRPIntentServiceMembersInjector;
    private MembersInjector<BingVIPIntentService> bingVIPIntentServiceMembersInjector;
    private MembersInjector<CAPIIntentFactory> cAPIIntentFactoryMembersInjector;
    private MembersInjector<CAPIPushNotificationsProvider> cAPIPushNotificationsProviderMembersInjector;
    private final DaggerApplicationComponent_PackageProxy com_gumtree_android_srp_treebay_Proxy;
    private MembersInjector<ConnectivityChangeReceiver> connectivityChangeReceiverMembersInjector;
    private MembersInjector<ConversationActivity> conversationActivityMembersInjector;
    private MembersInjector<ConversationFragment> conversationFragmentMembersInjector;
    private MembersInjector<ConversationIntentService> conversationIntentServiceMembersInjector;
    private MembersInjector<ConversationRequest> conversationRequestMembersInjector;
    private MembersInjector<ConversationsIntentService> conversationsIntentServiceMembersInjector;
    private MembersInjector<ConversationsRequest> conversationsRequestMembersInjector;
    private MembersInjector<DFPProcessor> dFPProcessorMembersInjector;
    private MembersInjector<DeleteAdIntentService> deleteAdIntentServiceMembersInjector;
    private MembersInjector<DeleteConversationIntentService> deleteConversationIntentServiceMembersInjector;
    private Provider<DraftFeatureConverter> draftFeatureConverterProvider;
    private Provider<DraftOptionConverter> draftOptionConverterProvider;
    private MembersInjector<FavouriteCapiIntentService> favouriteCapiIntentServiceMembersInjector;
    private MembersInjector<FavouriteIntentService> favouriteIntentServiceMembersInjector;
    private MembersInjector<FavouritesFragment> favouritesFragmentMembersInjector;
    private MembersInjector<FeaturesIntentService> featuresIntentServiceMembersInjector;
    private MembersInjector<FlagConversationIntentService> flagConversationIntentServiceMembersInjector;
    private MembersInjector<GCMSettingsProvider> gCMSettingsProviderMembersInjector;
    private MembersInjector<GalleryActivity> galleryActivityMembersInjector;
    private MembersInjector<GcmIntentService> gcmIntentServiceMembersInjector;
    private Provider<PushNotificationsProvider> getAppNotificationsProvider;
    private MembersInjector<GumtreeApplication> gumtreeApplicationMembersInjector;
    private MembersInjector<GumtreeGlideModule> gumtreeGlideModuleMembersInjector;
    private MembersInjector<GumtreeRestExecutor> gumtreeRestExecutorMembersInjector;
    private MembersInjector<HomeActivity> homeActivityMembersInjector;
    private MembersInjector<HomeCustomiseFragment> homeCustomiseFragmentMembersInjector;
    private MembersInjector<InboxFragment> inboxFragmentMembersInjector;
    private MembersInjector<LocationChildrenRequest> locationChildrenRequestMembersInjector;
    private MembersInjector<MDNSPushNotificationsProvider> mDNSPushNotificationsProviderMembersInjector;
    private MembersInjector<ManageAdActivity> manageAdActivityMembersInjector;
    private MembersInjector<ManageAdsFragment> manageAdsFragmentMembersInjector;
    private MembersInjector<MetadataIntentService> metadataIntentServiceMembersInjector;
    private MembersInjector<MetadataRequest> metadataRequestMembersInjector;
    private MembersInjector<NavigationActivity> navigationActivityMembersInjector;
    private MembersInjector<NearByAdvertAdapter> nearByAdvertAdapterMembersInjector;
    private MembersInjector<Network> networkMembersInjector;
    private MembersInjector<PostAdAPIIntentService> postAdAPIIntentServiceMembersInjector;
    private MembersInjector<PostAdBaseActivity> postAdBaseActivityMembersInjector;
    private MembersInjector<PostAdBaseFragment> postAdBaseFragmentMembersInjector;
    private MembersInjector<PostAdDBIntentService> postAdDBIntentServiceMembersInjector;
    private MembersInjector<PostAdInsertionFeeFragment> postAdInsertionFeeFragmentMembersInjector;
    private MembersInjector<PostAdNavigationActivity> postAdNavigationActivityMembersInjector;
    private MembersInjector<PostAdOneFragment2> postAdOneFragment2MembersInjector;
    private MembersInjector<PostAdPaymentActivity> postAdPaymentActivityMembersInjector;
    private MembersInjector<PostAdPaypalActivity> postAdPaypalActivityMembersInjector;
    private MembersInjector<PostAdStepsActivity> postAdStepsActivityMembersInjector;
    private MembersInjector<PostAdSummaryActivity> postAdSummaryActivityMembersInjector;
    private MembersInjector<PostAdSummaryFragment> postAdSummaryFragmentMembersInjector;
    private MembersInjector<PostAdThreeFragment2> postAdThreeFragment2MembersInjector;
    private MembersInjector<PostAdTwoFragment2> postAdTwoFragment2MembersInjector;
    private MembersInjector<PostConversationReplyIntentService> postConversationReplyIntentServiceMembersInjector;
    private MembersInjector<PostcodeLookupRequest> postcodeLookupRequestMembersInjector;
    private MembersInjector<PreviewActivity> previewActivityMembersInjector;
    private MembersInjector<PreviewGalleryActivity> previewGalleryActivityMembersInjector;
    private MembersInjector<PreviewImagePagerFragment> previewImagePagerFragmentMembersInjector;
    private Provider<AdModelConverter> provideAdModelConverterProvider;
    private Provider<AdsApi> provideAdsApiProvider;
    private Provider<Authenticator> provideApiAuthenticatorProvider;
    private Provider<Context> provideAppContextProvider;
    private Provider<ApplicationDataManager> provideApplicationDataManagerProvider;
    private Provider<AuthBaseStrategy> provideAuthBaseStrategyProvider;
    private Provider<Scheduler> provideBackgroundSchedulerProvider;
    private Provider<IBadgeCounterManager> provideBadgeCounterManagerProvider;
    private Provider<BaseAccountManager> provideBaseAccountManagerProvider;
    private Provider<BingAdsApi> provideBingAdsApiProvider;
    private Provider<BingAdsClient> provideBingAdsClientProvider;
    private Provider<BingSRPCache> provideBingSRPCacheProvider;
    private Provider<BingVIPCache> provideBingVIPCacheProvider;
    private Provider<CAPIIntentFactory> provideCAPIIntentFactoryProvider;
    private Provider<Factory> provideCallAdapterFactoryProvider;
    private Provider<Factory> provideCallistoCallAdapterFactoryProvider;
    private Provider<Retrofit2Client> provideCallistoClientProvider;
    private Provider<CapiClientManager> provideCapiClientManagerProvider;
    private Provider<ClientsApi> provideClientsApiProvider;
    private Provider<ClientsService> provideClientsServiceProvider;
    private Provider<Converter.Factory> provideConverterFactoryProvider;
    private Provider<OkHttpClient> provideDebugOkHttp2ClientProvider;
    private Provider<okhttp3.OkHttpClient> provideDebugOkHttp3ClientProvider;
    private Provider<EnvironmentSettings> provideEnvironmentSettingsProvider;
    private Provider<EventBus> provideEventBusProvider;
    private Provider<FeaturesApi> provideFeaturesApiProvider;
    private Provider<ICapiClient> provideGsonCapiClientProvider;
    private Provider<HttpLoggingInterceptor> provideHttpBodyLoggingInterceptorProvider;
    private Provider<LocalisedTextProvider> provideLocalisedTextProvider;
    private Provider<Network> provideNetworkProvider;
    private Provider<NetworkStateService> provideNetworkStateServiceProvider;
    private Provider<ObjectMapper> provideObjectMapperProvider;
    private Provider<okhttp3.OkHttpClient> provideOkHttp3ClientProvider;
    private Provider<OkHttpClient> provideOkHttpClientProvider;
    private Provider<ICapiClient> providePicturesUploadCapiClientProvider;
    private Provider<Repository<DraftAd>> providePostAdRepositoryProvider;
    private Provider<PostAdService> providePostAdServiceProvider;
    private Provider<PriceInfoService> providePriceInfoServiceProvider;
    private Provider<OkHttpClient> provideReleaseOkHttpClientProvider;
    private Provider<Resources> provideResourcesProvider;
    private Provider<okhttp3.OkHttpClient> provideSafeOkHttp3ClientProvider;
    private Provider<SharedPreferences> provideSharedPreferencesProvider;
    private Provider<SoftUpdateSettingsProvider> provideSoftUpdateSettingsProvider;
    private Provider<SSLSocketFactory> provideSslSocketFactoryProvider;
    private Provider<SuggestionProvider> provideSuggestionProvider;
    private Provider<SuggestionsApi> provideSuggestionsApiProvider;
    private Provider<SuggestionsService> provideSuggestionsServiceProvider;
    private Provider<TrackingLocationService> provideTrackingLocationServiceProvider;
    private Provider<TrackingPostAdService> provideTrackingPostAdServiceProvider;
    private Provider<StaticTrackingService> provideTrackingServiceProvider;
    private Provider<TrackingTreebayService> provideTrackingTreebayServiceProvider;
    private Provider<TreebayAdDetailsService> provideTreebayAdDetailsServiceProvider;
    private Provider<TreebayAdManager> provideTreebayAdManagerProvider;
    private Provider<TreebayApi> provideTreebayApiProvider;
    private Provider<Retrofit2Client> provideTreebayClientProvider;
    private Provider<TreebaySearchResultService> provideTreebaySearchResultServiceProvider;
    private Provider<UserAdsApi> provideUserAdsApiProvider;
    private Provider<UserProfileApi> provideUserProfileApiProvider;
    private Provider<UserProfileService> provideUserProfileServiceProvider;
    private Provider<UserProfileStatusService> provideUserProfileStatusServiceProvider;
    private Provider<UserProfileUpdateListener> provideUserProfileUpdateListenerProvider;
    private Provider<UserService> provideUserServiceProvider;
    private Provider<UsersApi> provideUsersApiProvider;
    private Provider<IViewValidator> provideViewValidatorProvider;
    private Provider<ICapiClient> provideXmlCapiClientProvider;
    private Provider<CapiConfig> providesApiConfigProvider;
    private Provider<CallistoConfig> providesCallistoApiConfigProvider;
    private Provider<OkHttpConfig> providesOkHttpConfigProvider;
    private Provider<TrackingDataProvider> providesTrackingDataProvider;
    private Provider<X509TrustManager> providesX509TrustManagerProvider;
    private MembersInjector<ReplyToVIPFragment> replyToVIPFragmentMembersInjector;
    private MembersInjector<SRPActivity> sRPActivityMembersInjector;
    private MembersInjector<SavedFavouritesSync> savedFavouritesSyncMembersInjector;
    private MembersInjector<SavedSearchSync> savedSearchSyncMembersInjector;
    private MembersInjector<SavedSearchesActivity> savedSearchesActivityMembersInjector;
    private MembersInjector<SavedSearchesPostIntentService> savedSearchesPostIntentServiceMembersInjector;
    private MembersInjector<SendReplyRequest> sendReplyRequestMembersInjector;
    private MembersInjector<SetLocationFragment> setLocationFragmentMembersInjector;
    private MembersInjector<SettingsFragment> settingsFragmentMembersInjector;
    private MembersInjector<SoftUpdateService> softUpdateServiceMembersInjector;
    private MembersInjector<SubmitOrderIntentService> submitOrderIntentServiceMembersInjector;
    private MembersInjector<SuggestedCategoryFragment> suggestedCategoryFragmentMembersInjector;
    private MembersInjector<TreebayAdGalleryItemView> treebayAdGalleryItemViewMembersInjector;
    private MembersInjector<TreebayAdGridItemView> treebayAdGridItemViewMembersInjector;
    private MembersInjector<VIPGalleryActivity> vIPGalleryActivityMembersInjector;
    private MembersInjector<VIPGalleryActivityTreebay> vIPGalleryActivityTreebayMembersInjector;
    private MembersInjector<VIPImagePagerFragment> vIPImagePagerFragmentMembersInjector;
    private MembersInjector<VIPImagePagerFragmentTreebay> vIPImagePagerFragmentTreebayMembersInjector;
    private MembersInjector<VIPMainFragment> vIPMainFragmentMembersInjector;
    private MembersInjector<VIPMainFragmentTreebay> vIPMainFragmentTreebayMembersInjector;
    private MembersInjector<VIPSellerOtherItemsFragment> vIPSellerOtherItemsFragmentMembersInjector;
    private MembersInjector<VipDetailsIntentService> vipDetailsIntentServiceMembersInjector;

    public final class Builder {
        private ApplicationModule applicationModule;
        private NetworkClientModule networkClientModule;
        private OkHttpModule okHttpModule;
        private SystemModule systemModule;

        private Builder() {
        }

        public ApplicationComponent build() {
            if (this.applicationModule == null) {
                throw new IllegalStateException("applicationModule must be set");
            }
            if (this.systemModule == null) {
                this.systemModule = new SystemModule();
            }
            if (this.okHttpModule == null) {
                this.okHttpModule = new OkHttpModule();
            }
            if (this.networkClientModule == null) {
                this.networkClientModule = new NetworkClientModule();
            }
            return new DaggerApplicationComponent();
        }

        public Builder applicationModule(ApplicationModule applicationModule) {
            if (applicationModule == null) {
                throw new NullPointerException("applicationModule");
            }
            this.applicationModule = applicationModule;
            return this;
        }

        public Builder systemModule(SystemModule systemModule) {
            if (systemModule == null) {
                throw new NullPointerException("systemModule");
            }
            this.systemModule = systemModule;
            return this;
        }

        public Builder okHttpModule(OkHttpModule okHttpModule) {
            if (okHttpModule == null) {
                throw new NullPointerException("okHttpModule");
            }
            this.okHttpModule = okHttpModule;
            return this;
        }

        public Builder networkClientModule(NetworkClientModule networkClientModule) {
            if (networkClientModule == null) {
                throw new NullPointerException("networkClientModule");
            }
            this.networkClientModule = networkClientModule;
            return this;
        }
    }

    private DaggerApplicationComponent(Builder builder) {
        this.com_gumtree_android_srp_treebay_Proxy = new DaggerApplicationComponent_PackageProxy();
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            initialize1(builder);
            initialize2(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.provideAppContextProvider = ScopedProvider.create(ApplicationModule_ProvideAppContextFactory.create(builder.applicationModule));
        this.provideEventBusProvider = ScopedProvider.create(ApplicationModule_ProvideEventBusFactory.create(builder.applicationModule));
        this.provideBadgeCounterManagerProvider = ScopedProvider.create(ApplicationModule_ProvideBadgeCounterManagerFactory.create(builder.applicationModule, this.provideEventBusProvider));
        this.provideBaseAccountManagerProvider = ScopedProvider.create(ApplicationModule_ProvideBaseAccountManagerFactory.create(builder.applicationModule));
        this.provideBackgroundSchedulerProvider = ScopedProvider.create(SystemModule_ProvideBackgroundSchedulerFactory.create(builder.systemModule));
        this.providePostAdRepositoryProvider = ScopedProvider.create(ApplicationModule_ProvidePostAdRepositoryFactory.create(builder.applicationModule, this.provideAppContextProvider, this.provideBackgroundSchedulerProvider));
        this.provideApplicationDataManagerProvider = ScopedProvider.create(ApplicationModule_ProvideApplicationDataManagerFactory.create(builder.applicationModule, this.providePostAdRepositoryProvider, this.provideBadgeCounterManagerProvider, this.provideBaseAccountManagerProvider, this.provideEventBusProvider));
        this.provideApiAuthenticatorProvider = ScopedProvider.create(NetworkClientModule_ProvideApiAuthenticatorFactory.create(builder.networkClientModule, this.provideApplicationDataManagerProvider));
        this.providesOkHttpConfigProvider = ScopedProvider.create(OkHttpModule_ProvidesOkHttpConfigFactory.create(builder.okHttpModule));
        this.provideReleaseOkHttpClientProvider = ScopedProvider.create(OkHttpModule_ProvideReleaseOkHttpClientFactory.create(builder.okHttpModule, this.providesOkHttpConfigProvider));
        this.providesX509TrustManagerProvider = ScopedProvider.create(OkHttpModule_ProvidesX509TrustManagerFactory.create(builder.okHttpModule));
        this.provideSslSocketFactoryProvider = ScopedProvider.create(OkHttpModule_ProvideSslSocketFactoryFactory.create(builder.okHttpModule, this.providesX509TrustManagerProvider));
        this.provideDebugOkHttp2ClientProvider = ScopedProvider.create(OkHttpModule_ProvideDebugOkHttp2ClientFactory.create(builder.okHttpModule, this.provideSslSocketFactoryProvider, this.providesOkHttpConfigProvider));
        this.provideOkHttpClientProvider = ScopedProvider.create(OkHttpModule_ProvideOkHttpClientFactory.create(builder.okHttpModule, this.provideReleaseOkHttpClientProvider, this.provideDebugOkHttp2ClientProvider));
        this.provideResourcesProvider = ScopedProvider.create(SystemModule_ProvideResourcesFactory.create(builder.systemModule, this.provideAppContextProvider));
        this.provideSharedPreferencesProvider = ScopedProvider.create(SystemModule_ProvideSharedPreferencesFactory.create(builder.systemModule, this.provideAppContextProvider));
        this.provideEnvironmentSettingsProvider = ScopedProvider.create(NetworkClientModule_ProvideEnvironmentSettingsFactory.create(builder.networkClientModule, this.provideResourcesProvider, this.provideSharedPreferencesProvider));
        this.providesApiConfigProvider = ScopedProvider.create(NetworkClientModule_ProvidesApiConfigFactory.create(builder.networkClientModule, this.provideAppContextProvider, this.provideOkHttpClientProvider, this.provideBaseAccountManagerProvider, this.provideEnvironmentSettingsProvider));
        this.provideXmlCapiClientProvider = ScopedProvider.create(NetworkClientModule_ProvideXmlCapiClientFactory.create(builder.networkClientModule, this.provideApiAuthenticatorProvider, this.providesApiConfigProvider));
        this.provideGsonCapiClientProvider = ScopedProvider.create(NetworkClientModule_ProvideGsonCapiClientFactory.create(builder.networkClientModule, this.provideApiAuthenticatorProvider, this.providesApiConfigProvider));
        this.providePicturesUploadCapiClientProvider = ScopedProvider.create(NetworkClientModule_ProvidePicturesUploadCapiClientFactory.create(builder.networkClientModule, this.provideApiAuthenticatorProvider, this.providesApiConfigProvider));
        this.provideLocalisedTextProvider = ScopedProvider.create(ApplicationModule_ProvideLocalisedTextProviderFactory.create(builder.applicationModule));
        this.provideNetworkProvider = ScopedProvider.create(ApplicationModule_ProvideNetworkFactory.create(builder.applicationModule));
        this.provideAuthBaseStrategyProvider = ScopedProvider.create(ApplicationModule_ProvideAuthBaseStrategyFactory.create(builder.applicationModule, this.provideBaseAccountManagerProvider, this.provideApplicationDataManagerProvider));
        this.provideUserProfileApiProvider = ScopedProvider.create(ApplicationModule_ProvideUserProfileApiFactory.create(builder.applicationModule, this.provideXmlCapiClientProvider));
        this.provideUserProfileServiceProvider = ScopedProvider.create(ApplicationModule_ProvideUserProfileServiceFactory.create(builder.applicationModule, this.provideUserProfileApiProvider, this.provideBaseAccountManagerProvider));
        this.provideUserProfileUpdateListenerProvider = ScopedProvider.create(ApplicationModule_ProvideUserProfileUpdateListenerFactory.create(builder.applicationModule, this.provideEventBusProvider));
        this.provideUserProfileStatusServiceProvider = ScopedProvider.create(ApplicationModule_ProvideUserProfileStatusServiceFactory.create(builder.applicationModule));
        this.provideUserServiceProvider = ScopedProvider.create(ApplicationModule_ProvideUserServiceFactory.create(builder.applicationModule, this.provideUserProfileServiceProvider, this.provideBaseAccountManagerProvider, this.provideUserProfileUpdateListenerProvider, this.provideUserProfileStatusServiceProvider, this.provideBackgroundSchedulerProvider));
        this.provideUserAdsApiProvider = ScopedProvider.create(ApplicationModule_ProvideUserAdsApiFactory.create(builder.applicationModule, this.provideXmlCapiClientProvider));
        this.provideAdModelConverterProvider = ScopedProvider.create(ApplicationModule_ProvideAdModelConverterFactory.create(builder.applicationModule, this.provideBaseAccountManagerProvider, DefaultCategoryTreeConverter_Factory.create()));
        this.providePostAdServiceProvider = ScopedProvider.create(ApplicationModule_ProvidePostAdServiceFactory.create(builder.applicationModule, this.provideUserAdsApiProvider, this.provideBaseAccountManagerProvider, this.provideBackgroundSchedulerProvider, this.provideAdModelConverterProvider));
        this.provideNetworkStateServiceProvider = ScopedProvider.create(ApplicationModule_ProvideNetworkStateServiceFactory.create(builder.applicationModule, this.provideBackgroundSchedulerProvider));
        this.provideFeaturesApiProvider = ScopedProvider.create(ApplicationModule_ProvideFeaturesApiFactory.create(builder.applicationModule, this.provideXmlCapiClientProvider));
        this.draftOptionConverterProvider = DraftOptionConverter_Factory.create(DraftPeriodConverter_Factory.create());
        this.draftFeatureConverterProvider = DraftFeatureConverter_Factory.create(DraftFeatureTypeConverter_Factory.create(), this.draftOptionConverterProvider);
        this.apiPriceInfoServiceProvider = ApiPriceInfoService_Factory.create(this.provideFeaturesApiProvider, this.draftFeatureConverterProvider);
        this.providePriceInfoServiceProvider = ScopedProvider.create(ApplicationModule_ProvidePriceInfoServiceFactory.create(builder.applicationModule, this.apiPriceInfoServiceProvider, this.provideBackgroundSchedulerProvider));
        this.providesTrackingDataProvider = ScopedProvider.create(ApplicationModule_ProvidesTrackingDataProviderFactory.create(builder.applicationModule));
        this.provideTrackingServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTrackingServiceFactory.create(builder.applicationModule, this.provideAppContextProvider, this.provideBaseAccountManagerProvider));
        this.provideTrackingPostAdServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTrackingPostAdServiceFactory.create(builder.applicationModule, this.provideTrackingServiceProvider));
        this.getAppNotificationsProvider = ScopedProvider.create(ApplicationModule_GetAppNotificationsProviderFactory.create(builder.applicationModule, this.provideBaseAccountManagerProvider, this.provideEventBusProvider));
        this.provideTrackingLocationServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTrackingLocationServiceFactory.create(builder.applicationModule, this.provideTrackingServiceProvider));
        this.provideUsersApiProvider = ScopedProvider.create(ApplicationModule_ProvideUsersApiFactory.create(builder.applicationModule, this.provideXmlCapiClientProvider));
        this.provideCAPIIntentFactoryProvider = ScopedProvider.create(NetworkClientModule_ProvideCAPIIntentFactoryFactory.create(builder.networkClientModule, this.provideEnvironmentSettingsProvider));
        this.provideAdsApiProvider = ScopedProvider.create(ApplicationModule_ProvideAdsApiFactory.create(builder.applicationModule, this.provideXmlCapiClientProvider));
        this.provideObjectMapperProvider = ScopedProvider.create(SystemModule_ProvideObjectMapperFactory.create(builder.systemModule));
        this.provideConverterFactoryProvider = ScopedProvider.create(NetworkClientModule_ProvideConverterFactoryFactory.create(builder.networkClientModule, this.provideObjectMapperProvider));
        this.provideCallAdapterFactoryProvider = ScopedProvider.create(NetworkClientModule_ProvideCallAdapterFactoryFactory.create(builder.networkClientModule));
        this.provideSafeOkHttp3ClientProvider = ScopedProvider.create(OkHttpModule_ProvideSafeOkHttp3ClientFactory.create(builder.okHttpModule, this.providesOkHttpConfigProvider));
        this.provideHttpBodyLoggingInterceptorProvider = ScopedProvider.create(OkHttpModule_ProvideHttpBodyLoggingInterceptorFactory.create(builder.okHttpModule));
        this.provideDebugOkHttp3ClientProvider = ScopedProvider.create(OkHttpModule_ProvideDebugOkHttp3ClientFactory.create(builder.okHttpModule, this.providesOkHttpConfigProvider, this.provideHttpBodyLoggingInterceptorProvider, this.provideSslSocketFactoryProvider, this.providesX509TrustManagerProvider));
        this.provideOkHttp3ClientProvider = ScopedProvider.create(OkHttpModule_ProvideOkHttp3ClientFactory.create(builder.okHttpModule, this.provideSafeOkHttp3ClientProvider, this.provideDebugOkHttp3ClientProvider));
        this.provideTreebayClientProvider = ScopedProvider.create(NetworkClientModule_ProvideTreebayClientFactory.create(builder.networkClientModule, this.provideConverterFactoryProvider, this.provideCallAdapterFactoryProvider, this.provideOkHttp3ClientProvider));
        this.provideTreebayApiProvider = ScopedProvider.create(ApplicationModule_ProvideTreebayApiFactory.create(builder.applicationModule, this.provideTreebayClientProvider));
        this.provideTreebaySearchResultServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTreebaySearchResultServiceFactory.create(builder.applicationModule, this.provideTreebayApiProvider, this.provideBackgroundSchedulerProvider));
        this.provideTrackingTreebayServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTrackingTreebayServiceFactory.create(builder.applicationModule, this.provideTrackingServiceProvider));
        this.provideTreebayAdManagerProvider = ScopedProvider.create(ApplicationModule_ProvideTreebayAdManagerFactory.create(builder.applicationModule, this.provideTreebaySearchResultServiceProvider, this.provideTrackingTreebayServiceProvider));
        this.provideCallistoCallAdapterFactoryProvider = ScopedProvider.create(NetworkClientModule_ProvideCallistoCallAdapterFactoryFactory.create(builder.networkClientModule, this.provideCallAdapterFactoryProvider));
        this.providesCallistoApiConfigProvider = ScopedProvider.create(NetworkClientModule_ProvidesCallistoApiConfigFactory.create(builder.networkClientModule, this.provideEnvironmentSettingsProvider));
        this.provideCallistoClientProvider = ScopedProvider.create(NetworkClientModule_ProvideCallistoClientFactory.create(builder.networkClientModule, this.provideConverterFactoryProvider, this.provideCallistoCallAdapterFactoryProvider, this.provideOkHttp3ClientProvider, this.providesCallistoApiConfigProvider));
        this.provideSuggestionsApiProvider = ScopedProvider.create(ApplicationModule_ProvideSuggestionsApiFactory.create(builder.applicationModule, this.provideCallistoClientProvider));
        this.connectivityChangeReceiverMembersInjector = ConnectivityChangeReceiver_MembersInjector.create(MembersInjectors.noOp(), this.provideNetworkProvider);
        this.vIPImagePagerFragmentMembersInjector = VIPImagePagerFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.vIPImagePagerFragmentTreebayMembersInjector = VIPImagePagerFragmentTreebay_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.provideViewValidatorProvider = ScopedProvider.create(ApplicationModule_ProvideViewValidatorFactory.create(builder.applicationModule));
        this.postAdSummaryFragmentMembersInjector = PostAdSummaryFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideViewValidatorProvider);
        this.settingsFragmentMembersInjector = SettingsFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBadgeCounterManagerProvider, this.getAppNotificationsProvider, this.provideBaseAccountManagerProvider, this.provideUserServiceProvider, this.provideUserProfileStatusServiceProvider, this.providePostAdRepositoryProvider, this.provideApplicationDataManagerProvider, this.provideEnvironmentSettingsProvider);
        this.postAdBaseFragmentMembersInjector = PostAdBaseFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.postAdOneFragment2MembersInjector = PostAdOneFragment2_MembersInjector.create(this.postAdBaseFragmentMembersInjector, this.provideBaseAccountManagerProvider);
        this.postAdTwoFragment2MembersInjector = MembersInjectors.delegatingTo(this.postAdBaseFragmentMembersInjector);
        this.postAdThreeFragment2MembersInjector = PostAdThreeFragment2_MembersInjector.create(this.postAdBaseFragmentMembersInjector, this.provideUserServiceProvider, this.provideUserProfileStatusServiceProvider);
        this.favouritesFragmentMembersInjector = FavouritesFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider);
        this.postAdInsertionFeeFragmentMembersInjector = PostAdInsertionFeeFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.manageAdsFragmentMembersInjector = ManageAdsFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider);
        this.previewImagePagerFragmentMembersInjector = PreviewImagePagerFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.inboxFragmentMembersInjector = InboxFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider, this.provideEventBusProvider);
        this.conversationFragmentMembersInjector = ConversationFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider, this.provideEventBusProvider, this.provideUserServiceProvider);
        this.vIPSellerOtherItemsFragmentMembersInjector = VIPSellerOtherItemsFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideUserProfileServiceProvider);
        this.provideCapiClientManagerProvider = ScopedProvider.create(ApplicationModule_ProvideCapiClientManagerFactory.create(builder.applicationModule, this.provideApplicationDataManagerProvider));
        this.suggestedCategoryFragmentMembersInjector = SuggestedCategoryFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideCapiClientManagerProvider);
    }

    private void initialize1(Builder builder) {
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideNetworkProvider, this.provideBaseAccountManagerProvider);
        this.navigationActivityMembersInjector = NavigationActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider, this.provideBadgeCounterManagerProvider);
        this.conversationActivityMembersInjector = ConversationActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider);
        this.postAdNavigationActivityMembersInjector = PostAdNavigationActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideEventBusProvider, this.provideAuthBaseStrategyProvider, this.provideBaseAccountManagerProvider);
        this.postAdSummaryActivityMembersInjector = PostAdSummaryActivity_MembersInjector.create(this.postAdNavigationActivityMembersInjector, this.provideBaseAccountManagerProvider);
        this.vIPGalleryActivityMembersInjector = VIPGalleryActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider);
        this.vIPGalleryActivityTreebayMembersInjector = VIPGalleryActivityTreebay_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider);
        this.postAdBaseActivityMembersInjector = PostAdBaseActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider, this.provideAuthBaseStrategyProvider, this.provideBaseAccountManagerProvider);
        this.postAdStepsActivityMembersInjector = PostAdStepsActivity_MembersInjector.create(this.postAdBaseActivityMembersInjector, this.provideBaseAccountManagerProvider);
        this.postAdPaymentActivityMembersInjector = PostAdPaymentActivity_MembersInjector.create(this.postAdBaseActivityMembersInjector, this.provideAuthBaseStrategyProvider);
        this.postAdPaypalActivityMembersInjector = MembersInjectors.delegatingTo(this.postAdBaseActivityMembersInjector);
        this.previewActivityMembersInjector = MembersInjectors.delegatingTo(this.postAdBaseActivityMembersInjector);
        this.previewGalleryActivityMembersInjector = PreviewGalleryActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideEventBusProvider);
        this.galleryActivityMembersInjector = MembersInjectors.delegatingTo(this.postAdBaseActivityMembersInjector);
        this.homeActivityMembersInjector = HomeActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideEventBusProvider, this.provideBadgeCounterManagerProvider, this.provideBaseAccountManagerProvider);
        this.authenticatorActivityMembersInjector = AuthenticatorActivity_MembersInjector.create(this.baseActivityMembersInjector, this.getAppNotificationsProvider, this.provideEventBusProvider, this.provideUserServiceProvider, this.provideBaseAccountManagerProvider);
        this.manageAdActivityMembersInjector = ManageAdActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideBaseAccountManagerProvider);
        this.savedSearchesActivityMembersInjector = SavedSearchesActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideBaseAccountManagerProvider);
        this.sRPActivityMembersInjector = SRPActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideBaseAccountManagerProvider, this.provideTreebayAdManagerProvider, this.provideTrackingTreebayServiceProvider);
        this.postAdDBIntentServiceMembersInjector = PostAdDBIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.metadataIntentServiceMembersInjector = MetadataIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.featuresIntentServiceMembersInjector = FeaturesIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.postAdAPIIntentServiceMembersInjector = PostAdAPIIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.submitOrderIntentServiceMembersInjector = SubmitOrderIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.deleteAdIntentServiceMembersInjector = DeleteAdIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideEnvironmentSettingsProvider);
        this.deleteConversationIntentServiceMembersInjector = DeleteConversationIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.conversationIntentServiceMembersInjector = ConversationIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.conversationsIntentServiceMembersInjector = ConversationsIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider);
        this.postConversationReplyIntentServiceMembersInjector = PostConversationReplyIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.flagConversationIntentServiceMembersInjector = FlagConversationIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.vipDetailsIntentServiceMembersInjector = VipDetailsIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideCapiClientManagerProvider);
        this.savedSearchesPostIntentServiceMembersInjector = SavedSearchesPostIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.activateAccountIntentServiceMembersInjector = ActivateAccountIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideCapiClientManagerProvider);
        this.networkMembersInjector = Network_MembersInjector.create(this.provideEventBusProvider, this.provideAppContextProvider);
        this.gCMSettingsProviderMembersInjector = GCMSettingsProvider_MembersInjector.create(this.provideEventBusProvider);
        this.mDNSPushNotificationsProviderMembersInjector = MDNSPushNotificationsProvider_MembersInjector.create(this.provideBaseAccountManagerProvider);
        this.cAPIPushNotificationsProviderMembersInjector = CAPIPushNotificationsProvider_MembersInjector.create(this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.conversationRequestMembersInjector = ConversationRequest_MembersInjector.create(this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.conversationsRequestMembersInjector = ConversationsRequest_MembersInjector.create(this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.gcmIntentServiceMembersInjector = GcmIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider);
        this.gumtreeApplicationMembersInjector = GumtreeApplication_MembersInjector.create(MembersInjectors.noOp(), this.provideCAPIIntentFactoryProvider, this.getAppNotificationsProvider, this.provideXmlCapiClientProvider, this.provideAppContextProvider, this.provideUserServiceProvider, this.provideBaseAccountManagerProvider);
        this.authRequestMembersInjector = AuthRequest_MembersInjector.create(this.provideXmlCapiClientProvider);
        this.locationChildrenRequestMembersInjector = LocationChildrenRequest_MembersInjector.create(this.provideXmlCapiClientProvider);
        this.postcodeLookupRequestMembersInjector = PostcodeLookupRequest_MembersInjector.create(this.provideXmlCapiClientProvider);
        this.cAPIIntentFactoryMembersInjector = CAPIIntentFactory_MembersInjector.create(this.provideBaseAccountManagerProvider);
        this.provideBingVIPCacheProvider = ScopedProvider.create(ApplicationModule_ProvideBingVIPCacheFactory.create(builder.applicationModule));
        this.vIPMainFragmentMembersInjector = VIPMainFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBingVIPCacheProvider);
        this.com_gumtree_android_srp_treebay_Proxy.abstractTreebayAdItemViewMembersInjector = AbstractTreebayAdItemView_MembersInjector.create(MembersInjectors.noOp(), this.provideTrackingTreebayServiceProvider);
        this.treebayAdGalleryItemViewMembersInjector = MembersInjectors.delegatingTo(this.com_gumtree_android_srp_treebay_Proxy.abstractTreebayAdItemViewMembersInjector);
        this.treebayAdGridItemViewMembersInjector = MembersInjectors.delegatingTo(this.com_gumtree_android_srp_treebay_Proxy.abstractTreebayAdItemViewMembersInjector);
        this.provideTreebayAdDetailsServiceProvider = ScopedProvider.create(ApplicationModule_ProvideTreebayAdDetailsServiceFactory.create(builder.applicationModule, this.provideTreebayApiProvider, this.provideBackgroundSchedulerProvider));
        this.vIPMainFragmentTreebayMembersInjector = VIPMainFragmentTreebay_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideTreebayAdDetailsServiceProvider, this.provideTrackingTreebayServiceProvider);
        this.savedFavouritesSyncMembersInjector = SavedFavouritesSync_MembersInjector.create(this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.favouriteCapiIntentServiceMembersInjector = FavouriteCapiIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.savedSearchSyncMembersInjector = SavedSearchSync_MembersInjector.create(this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.metadataRequestMembersInjector = MetadataRequest_MembersInjector.create(this.provideXmlCapiClientProvider, this.provideAppContextProvider, this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.replyToVIPFragmentMembersInjector = ReplyToVIPFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider);
        this.sendReplyRequestMembersInjector = SendReplyRequest_MembersInjector.create(this.provideXmlCapiClientProvider, this.provideBaseAccountManagerProvider, this.provideAppContextProvider);
        this.dFPProcessorMembersInjector = DFPProcessor_MembersInjector.create(this.provideAppContextProvider);
        this.provideBingSRPCacheProvider = ScopedProvider.create(ApplicationModule_ProvideBingSRPCacheFactory.create(builder.applicationModule));
        this.bingAdSRPViewMembersInjector = BingAdSRPView_MembersInjector.create(MembersInjectors.noOp(), this.provideBingSRPCacheProvider, this.provideEventBusProvider);
        this.bingAdVIPViewMembersInjector = BingAdVIPView_MembersInjector.create(MembersInjectors.noOp(), this.provideBingVIPCacheProvider);
        this.provideBingAdsClientProvider = ScopedProvider.create(NetworkClientModule_ProvideBingAdsClientFactory.create(builder.networkClientModule, this.providesApiConfigProvider, this.provideOkHttpClientProvider));
        this.provideBingAdsApiProvider = ScopedProvider.create(ApplicationModule_ProvideBingAdsApiFactory.create(builder.applicationModule, this.provideBingAdsClientProvider));
        this.bingSRPIntentServiceMembersInjector = BingSRPIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideBingSRPCacheProvider, this.provideBingAdsApiProvider, this.provideEventBusProvider, this.providesApiConfigProvider);
        this.bingVIPIntentServiceMembersInjector = BingVIPIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideBingVIPCacheProvider, this.provideBingAdsApiProvider, this.provideEventBusProvider);
        this.bingAdFragmentMembersInjector = BingAdFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideBingVIPCacheProvider, this.provideEventBusProvider);
        this.bingAdFragmentTreebayMembersInjector = BingAdFragmentTreebay_MembersInjector.create(MembersInjectors.noOp(), this.provideBingVIPCacheProvider, this.provideEventBusProvider);
        this.provideClientsApiProvider = ScopedProvider.create(ApplicationModule_ProvideClientsApiFactory.create(builder.applicationModule, this.provideGsonCapiClientProvider));
        this.provideClientsServiceProvider = ScopedProvider.create(ApplicationModule_ProvideClientsServiceFactory.create(builder.applicationModule, this.provideClientsApiProvider));
        this.provideSoftUpdateSettingsProvider = ScopedProvider.create(ApplicationModule_ProvideSoftUpdateSettingsProviderFactory.create(builder.applicationModule, this.provideAppContextProvider));
        this.softUpdateServiceMembersInjector = SoftUpdateService_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideClientsServiceProvider, this.provideSoftUpdateSettingsProvider);
        this.setLocationFragmentMembersInjector = SetLocationFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider);
        this.homeCustomiseFragmentMembersInjector = HomeCustomiseFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideEventBusProvider, this.provideBaseAccountManagerProvider);
        this.favouriteIntentServiceMembersInjector = FavouriteIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider);
        this.applyFeatureIntentServiceMembersInjector = ApplyFeatureIntentService_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider, this.provideCapiClientManagerProvider);
        this.gumtreeRestExecutorMembersInjector = GumtreeRestExecutor_MembersInjector.create(this.provideApplicationDataManagerProvider);
        this.provideSuggestionsServiceProvider = ScopedProvider.create(ApplicationModule_ProvideSuggestionsServiceFactory.create(builder.applicationModule, this.provideSuggestionsApiProvider, this.provideBackgroundSchedulerProvider));
        this.provideSuggestionProvider = ScopedProvider.create(ApplicationModule_ProvideSuggestionProviderFactory.create(builder.applicationModule, this.provideSuggestionsServiceProvider, this.provideCapiClientManagerProvider));
        this.baseDynAttributeFragmentMembersInjector = BaseDynAttributeFragment_MembersInjector.create(MembersInjectors.noOp(), this.provideSuggestionProvider);
        this.accountAuthenticatorMembersInjector = AccountAuthenticator_MembersInjector.create(MembersInjectors.noOp(), this.provideBaseAccountManagerProvider);
        this.nearByAdvertAdapterMembersInjector = NearByAdvertAdapter_MembersInjector.create(MembersInjectors.noOp(), this.provideTreebayAdManagerProvider);
    }

    private void initialize2(Builder builder) {
        this.gumtreeGlideModuleMembersInjector = GumtreeGlideModule_MembersInjector.create(MembersInjectors.noOp(), this.provideOkHttp3ClientProvider);
    }

    public Context context() {
        return (Context) this.provideAppContextProvider.get();
    }

    public IBadgeCounterManager badgeCounterManager() {
        return (IBadgeCounterManager) this.provideBadgeCounterManagerProvider.get();
    }

    public BaseAccountManager baseAccountManager() {
        return (BaseAccountManager) this.provideBaseAccountManagerProvider.get();
    }

    public ICapiClient xmlClient() {
        return (ICapiClient) this.provideXmlCapiClientProvider.get();
    }

    public ICapiClient gsonClient() {
        return (ICapiClient) this.provideGsonCapiClientProvider.get();
    }

    public ICapiClient picturesUploadClient() {
        return (ICapiClient) this.providePicturesUploadCapiClientProvider.get();
    }

    public LocalisedTextProvider localisedTextProvider() {
        return (LocalisedTextProvider) this.provideLocalisedTextProvider.get();
    }

    public Network network() {
        return (Network) this.provideNetworkProvider.get();
    }

    public EventBus eventBus() {
        return (EventBus) this.provideEventBusProvider.get();
    }

    public Repository<DraftAd> postAdRepository() {
        return (Repository) this.providePostAdRepositoryProvider.get();
    }

    public AuthBaseStrategy authBaseStrategy() {
        return (AuthBaseStrategy) this.provideAuthBaseStrategyProvider.get();
    }

    public UserService userService() {
        return (UserService) this.provideUserServiceProvider.get();
    }

    public PostAdService postAdService() {
        return (PostAdService) this.providePostAdServiceProvider.get();
    }

    public UserProfileStatusService userProfileStatusService() {
        return (UserProfileStatusService) this.provideUserProfileStatusServiceProvider.get();
    }

    public AdModelConverter adModelConverter() {
        return (AdModelConverter) this.provideAdModelConverterProvider.get();
    }

    public NetworkStateService networkStateService() {
        return (NetworkStateService) this.provideNetworkStateServiceProvider.get();
    }

    public PriceInfoService priceInfoService() {
        return (PriceInfoService) this.providePriceInfoServiceProvider.get();
    }

    public TrackingDataProvider trackingDataProvider() {
        return (TrackingDataProvider) this.providesTrackingDataProvider.get();
    }

    public TrackingPostAdService trackingPostAdService() {
        return (TrackingPostAdService) this.provideTrackingPostAdServiceProvider.get();
    }

    public StaticTrackingService staticTrackingService() {
        return (StaticTrackingService) this.provideTrackingServiceProvider.get();
    }

    public UserProfileApi userProfileApi() {
        return (UserProfileApi) this.provideUserProfileApiProvider.get();
    }

    public UserAdsApi userAdsApi() {
        return (UserAdsApi) this.provideUserAdsApiProvider.get();
    }

    public PushNotificationsProvider pushNotificationsProvider() {
        return (PushNotificationsProvider) this.getAppNotificationsProvider.get();
    }

    public TrackingLocationService trackingLocationService() {
        return (TrackingLocationService) this.provideTrackingLocationServiceProvider.get();
    }

    public UsersApi usersApi() {
        return (UsersApi) this.provideUsersApiProvider.get();
    }

    public CapiConfig capiConfig() {
        return (CapiConfig) this.providesApiConfigProvider.get();
    }

    public CAPIIntentFactory capiIntentFactory() {
        return (CAPIIntentFactory) this.provideCAPIIntentFactoryProvider.get();
    }

    public AdsApi adsApi() {
        return (AdsApi) this.provideAdsApiProvider.get();
    }

    public TreebayAdManager treebayAdManager() {
        return (TreebayAdManager) this.provideTreebayAdManagerProvider.get();
    }

    public SuggestionsApi suggestionsApi() {
        return (SuggestionsApi) this.provideSuggestionsApiProvider.get();
    }

    public Scheduler backgroundScheduler() {
        return (Scheduler) this.provideBackgroundSchedulerProvider.get();
    }

    public Retrofit2Client callistoClient() {
        return (Retrofit2Client) this.provideCallistoClientProvider.get();
    }

    public Retrofit2Client treebayClient() {
        return (Retrofit2Client) this.provideTreebayClientProvider.get();
    }

    public void inject(ConnectivityChangeReceiver connectivityChangeReceiver) {
        this.connectivityChangeReceiverMembersInjector.injectMembers(connectivityChangeReceiver);
    }

    public void inject(VIPImagePagerFragment vIPImagePagerFragment) {
        this.vIPImagePagerFragmentMembersInjector.injectMembers(vIPImagePagerFragment);
    }

    public void inject(VIPImagePagerFragmentTreebay vIPImagePagerFragmentTreebay) {
        this.vIPImagePagerFragmentTreebayMembersInjector.injectMembers(vIPImagePagerFragmentTreebay);
    }

    public void inject(PostAdSummaryFragment postAdSummaryFragment) {
        this.postAdSummaryFragmentMembersInjector.injectMembers(postAdSummaryFragment);
    }

    public void inject(SettingsFragment settingsFragment) {
        this.settingsFragmentMembersInjector.injectMembers(settingsFragment);
    }

    public void inject(PostAdOneFragment2 postAdOneFragment2) {
        this.postAdOneFragment2MembersInjector.injectMembers(postAdOneFragment2);
    }

    public void inject(PostAdTwoFragment2 postAdTwoFragment2) {
        this.postAdTwoFragment2MembersInjector.injectMembers(postAdTwoFragment2);
    }

    public void inject(PostAdThreeFragment2 postAdThreeFragment2) {
        this.postAdThreeFragment2MembersInjector.injectMembers(postAdThreeFragment2);
    }

    public void inject(FavouritesFragment favouritesFragment) {
        this.favouritesFragmentMembersInjector.injectMembers(favouritesFragment);
    }

    public void inject(PostAdInsertionFeeFragment postAdInsertionFeeFragment) {
        this.postAdInsertionFeeFragmentMembersInjector.injectMembers(postAdInsertionFeeFragment);
    }

    public void inject(PostAdBaseFragment postAdBaseFragment) {
        this.postAdBaseFragmentMembersInjector.injectMembers(postAdBaseFragment);
    }

    public void inject(ManageAdsFragment manageAdsFragment) {
        this.manageAdsFragmentMembersInjector.injectMembers(manageAdsFragment);
    }

    public void inject(PreviewImagePagerFragment previewImagePagerFragment) {
        this.previewImagePagerFragmentMembersInjector.injectMembers(previewImagePagerFragment);
    }

    public void inject(InboxFragment inboxFragment) {
        this.inboxFragmentMembersInjector.injectMembers(inboxFragment);
    }

    public void inject(ConversationFragment conversationFragment) {
        this.conversationFragmentMembersInjector.injectMembers(conversationFragment);
    }

    public void inject(VIPSellerOtherItemsFragment vIPSellerOtherItemsFragment) {
        this.vIPSellerOtherItemsFragmentMembersInjector.injectMembers(vIPSellerOtherItemsFragment);
    }

    public void inject(SuggestedCategoryFragment suggestedCategoryFragment) {
        this.suggestedCategoryFragmentMembersInjector.injectMembers(suggestedCategoryFragment);
    }

    public void inject(NavigationActivity navigationActivity) {
        this.navigationActivityMembersInjector.injectMembers(navigationActivity);
    }

    public void inject(ConversationActivity conversationActivity) {
        this.conversationActivityMembersInjector.injectMembers(conversationActivity);
    }

    public void inject(PostAdSummaryActivity postAdSummaryActivity) {
        this.postAdSummaryActivityMembersInjector.injectMembers(postAdSummaryActivity);
    }

    public void inject(PostAdNavigationActivity postAdNavigationActivity) {
        this.postAdNavigationActivityMembersInjector.injectMembers(postAdNavigationActivity);
    }

    public void inject(VIPGalleryActivity vIPGalleryActivity) {
        this.vIPGalleryActivityMembersInjector.injectMembers(vIPGalleryActivity);
    }

    public void inject(VIPGalleryActivityTreebay vIPGalleryActivityTreebay) {
        this.vIPGalleryActivityTreebayMembersInjector.injectMembers(vIPGalleryActivityTreebay);
    }

    public void inject(PostAdStepsActivity postAdStepsActivity) {
        this.postAdStepsActivityMembersInjector.injectMembers(postAdStepsActivity);
    }

    public void inject(PostAdPaymentActivity postAdPaymentActivity) {
        this.postAdPaymentActivityMembersInjector.injectMembers(postAdPaymentActivity);
    }

    public void inject(PostAdPaypalActivity postAdPaypalActivity) {
        this.postAdPaypalActivityMembersInjector.injectMembers(postAdPaypalActivity);
    }

    public void inject(PreviewActivity previewActivity) {
        this.previewActivityMembersInjector.injectMembers(previewActivity);
    }

    public void inject(PreviewGalleryActivity previewGalleryActivity) {
        this.previewGalleryActivityMembersInjector.injectMembers(previewGalleryActivity);
    }

    public void inject(GalleryActivity galleryActivity) {
        this.galleryActivityMembersInjector.injectMembers(galleryActivity);
    }

    public void inject(HomeActivity homeActivity) {
        this.homeActivityMembersInjector.injectMembers(homeActivity);
    }

    public void inject(PostAdBaseActivity postAdBaseActivity) {
        this.postAdBaseActivityMembersInjector.injectMembers(postAdBaseActivity);
    }

    public void inject(AuthenticatorActivity authenticatorActivity) {
        this.authenticatorActivityMembersInjector.injectMembers(authenticatorActivity);
    }

    public void inject(BaseActivity baseActivity) {
        this.baseActivityMembersInjector.injectMembers(baseActivity);
    }

    public void inject(ManageAdActivity manageAdActivity) {
        this.manageAdActivityMembersInjector.injectMembers(manageAdActivity);
    }

    public void inject(SavedSearchesActivity savedSearchesActivity) {
        this.savedSearchesActivityMembersInjector.injectMembers(savedSearchesActivity);
    }

    public void inject(SRPActivity sRPActivity) {
        this.sRPActivityMembersInjector.injectMembers(sRPActivity);
    }

    public void inject(PostAdDBIntentService postAdDBIntentService) {
        this.postAdDBIntentServiceMembersInjector.injectMembers(postAdDBIntentService);
    }

    public void inject(MetadataIntentService metadataIntentService) {
        this.metadataIntentServiceMembersInjector.injectMembers(metadataIntentService);
    }

    public void inject(FeaturesIntentService featuresIntentService) {
        this.featuresIntentServiceMembersInjector.injectMembers(featuresIntentService);
    }

    public void inject(PostAdAPIIntentService postAdAPIIntentService) {
        this.postAdAPIIntentServiceMembersInjector.injectMembers(postAdAPIIntentService);
    }

    public void inject(SubmitOrderIntentService submitOrderIntentService) {
        this.submitOrderIntentServiceMembersInjector.injectMembers(submitOrderIntentService);
    }

    public void inject(DeleteAdIntentService deleteAdIntentService) {
        this.deleteAdIntentServiceMembersInjector.injectMembers(deleteAdIntentService);
    }

    public void inject(DeleteConversationIntentService deleteConversationIntentService) {
        this.deleteConversationIntentServiceMembersInjector.injectMembers(deleteConversationIntentService);
    }

    public void inject(ConversationIntentService conversationIntentService) {
        this.conversationIntentServiceMembersInjector.injectMembers(conversationIntentService);
    }

    public void inject(ConversationsIntentService conversationsIntentService) {
        this.conversationsIntentServiceMembersInjector.injectMembers(conversationsIntentService);
    }

    public void inject(PostConversationReplyIntentService postConversationReplyIntentService) {
        this.postConversationReplyIntentServiceMembersInjector.injectMembers(postConversationReplyIntentService);
    }

    public void inject(FlagConversationIntentService flagConversationIntentService) {
        this.flagConversationIntentServiceMembersInjector.injectMembers(flagConversationIntentService);
    }

    public void inject(VipDetailsIntentService vipDetailsIntentService) {
        this.vipDetailsIntentServiceMembersInjector.injectMembers(vipDetailsIntentService);
    }

    public void inject(SavedSearchesPostIntentService savedSearchesPostIntentService) {
        this.savedSearchesPostIntentServiceMembersInjector.injectMembers(savedSearchesPostIntentService);
    }

    public void inject(ActivateAccountIntentService activateAccountIntentService) {
        this.activateAccountIntentServiceMembersInjector.injectMembers(activateAccountIntentService);
    }

    public void inject(Network network) {
        this.networkMembersInjector.injectMembers(network);
    }

    public void inject(GCMSettingsProvider gCMSettingsProvider) {
        this.gCMSettingsProviderMembersInjector.injectMembers(gCMSettingsProvider);
    }

    public void inject(MDNSPushNotificationsProvider mDNSPushNotificationsProvider) {
        this.mDNSPushNotificationsProviderMembersInjector.injectMembers(mDNSPushNotificationsProvider);
    }

    public void inject(CAPIPushNotificationsProvider cAPIPushNotificationsProvider) {
        this.cAPIPushNotificationsProviderMembersInjector.injectMembers(cAPIPushNotificationsProvider);
    }

    public void inject(ConversationRequest conversationRequest) {
        this.conversationRequestMembersInjector.injectMembers(conversationRequest);
    }

    public void inject(ConversationsRequest conversationsRequest) {
        this.conversationsRequestMembersInjector.injectMembers(conversationsRequest);
    }

    public void inject(GcmIntentService gcmIntentService) {
        this.gcmIntentServiceMembersInjector.injectMembers(gcmIntentService);
    }

    public void inject(GumtreeApplication gumtreeApplication) {
        this.gumtreeApplicationMembersInjector.injectMembers(gumtreeApplication);
    }

    public void inject(AuthRequest authRequest) {
        this.authRequestMembersInjector.injectMembers(authRequest);
    }

    public void inject(LocationChildrenRequest locationChildrenRequest) {
        this.locationChildrenRequestMembersInjector.injectMembers(locationChildrenRequest);
    }

    public void inject(PostcodeLookupRequest postcodeLookupRequest) {
        this.postcodeLookupRequestMembersInjector.injectMembers(postcodeLookupRequest);
    }

    public void inject(CAPIIntentFactory cAPIIntentFactory) {
        this.cAPIIntentFactoryMembersInjector.injectMembers(cAPIIntentFactory);
    }

    public void inject(VIPMainFragment vIPMainFragment) {
        this.vIPMainFragmentMembersInjector.injectMembers(vIPMainFragment);
    }

    public void inject(TreebayAdGalleryItemView treebayAdGalleryItemView) {
        this.treebayAdGalleryItemViewMembersInjector.injectMembers(treebayAdGalleryItemView);
    }

    public void inject(TreebayAdGridItemView treebayAdGridItemView) {
        this.treebayAdGridItemViewMembersInjector.injectMembers(treebayAdGridItemView);
    }

    public void inject(VIPMainFragmentTreebay vIPMainFragmentTreebay) {
        this.vIPMainFragmentTreebayMembersInjector.injectMembers(vIPMainFragmentTreebay);
    }

    public void inject(SavedFavouritesSync savedFavouritesSync) {
        this.savedFavouritesSyncMembersInjector.injectMembers(savedFavouritesSync);
    }

    public void inject(FavouriteCapiIntentService favouriteCapiIntentService) {
        this.favouriteCapiIntentServiceMembersInjector.injectMembers(favouriteCapiIntentService);
    }

    public void inject(SavedSearchSync savedSearchSync) {
        this.savedSearchSyncMembersInjector.injectMembers(savedSearchSync);
    }

    public void inject(MetadataRequest metadataRequest) {
        this.metadataRequestMembersInjector.injectMembers(metadataRequest);
    }

    public void inject(ReplyToVIPFragment replyToVIPFragment) {
        this.replyToVIPFragmentMembersInjector.injectMembers(replyToVIPFragment);
    }

    public void inject(SendReplyRequest sendReplyRequest) {
        this.sendReplyRequestMembersInjector.injectMembers(sendReplyRequest);
    }

    public void inject(DFPProcessor dFPProcessor) {
        this.dFPProcessorMembersInjector.injectMembers(dFPProcessor);
    }

    public void inject(BingAdSRPView bingAdSRPView) {
        this.bingAdSRPViewMembersInjector.injectMembers(bingAdSRPView);
    }

    public void inject(BingAdVIPView bingAdVIPView) {
        this.bingAdVIPViewMembersInjector.injectMembers(bingAdVIPView);
    }

    public void inject(BingSRPIntentService bingSRPIntentService) {
        this.bingSRPIntentServiceMembersInjector.injectMembers(bingSRPIntentService);
    }

    public void inject(BingVIPIntentService bingVIPIntentService) {
        this.bingVIPIntentServiceMembersInjector.injectMembers(bingVIPIntentService);
    }

    public void inject(BingAdFragment bingAdFragment) {
        this.bingAdFragmentMembersInjector.injectMembers(bingAdFragment);
    }

    public void inject(BingAdFragmentTreebay bingAdFragmentTreebay) {
        this.bingAdFragmentTreebayMembersInjector.injectMembers(bingAdFragmentTreebay);
    }

    public void inject(SoftUpdateService softUpdateService) {
        this.softUpdateServiceMembersInjector.injectMembers(softUpdateService);
    }

    public void inject(SetLocationFragment setLocationFragment) {
        this.setLocationFragmentMembersInjector.injectMembers(setLocationFragment);
    }

    public void inject(HomeCustomiseFragment homeCustomiseFragment) {
        this.homeCustomiseFragmentMembersInjector.injectMembers(homeCustomiseFragment);
    }

    public void inject(GumtreeAuthBaseStrategy gumtreeAuthBaseStrategy) {
        MembersInjectors.noOp().injectMembers(gumtreeAuthBaseStrategy);
    }

    public void inject(FavouriteIntentService favouriteIntentService) {
        this.favouriteIntentServiceMembersInjector.injectMembers(favouriteIntentService);
    }

    public void inject(AccountAuthenticatorService accountAuthenticatorService) {
        MembersInjectors.noOp().injectMembers(accountAuthenticatorService);
    }

    public void inject(ApplyFeatureIntentService applyFeatureIntentService) {
        this.applyFeatureIntentServiceMembersInjector.injectMembers(applyFeatureIntentService);
    }

    public void inject(GumtreeRestExecutor gumtreeRestExecutor) {
        this.gumtreeRestExecutorMembersInjector.injectMembers(gumtreeRestExecutor);
    }

    public void inject(BaseDynAttributeFragment baseDynAttributeFragment) {
        this.baseDynAttributeFragmentMembersInjector.injectMembers(baseDynAttributeFragment);
    }

    public void inject(AccountAuthenticator accountAuthenticator) {
        this.accountAuthenticatorMembersInjector.injectMembers(accountAuthenticator);
    }

    public void inject(NearByAdvertAdapter nearByAdvertAdapter) {
        this.nearByAdvertAdapterMembersInjector.injectMembers(nearByAdvertAdapter);
    }

    public void inject(GumtreeGlideModule gumtreeGlideModule) {
        this.gumtreeGlideModuleMembersInjector.injectMembers(gumtreeGlideModule);
    }
}
