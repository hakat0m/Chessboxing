package com.gumtree.android.dagger.components;

import android.content.Context;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.ebay.classifieds.capi.users.UsersApi;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.treebay.TreebayAdManager;
import com.gumtree.android.api.Retrofit2Client;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.api.ActivateAccountIntentService;
import com.gumtree.android.auth.api.AuthRequest;
import com.gumtree.android.categories.SuggestedCategoryFragment;
import com.gumtree.android.clients.SoftUpdateService;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.connectivity.ConnectivityChangeReceiver;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.fragments.GumtreeAuthBaseStrategy;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.http.GumtreeRestExecutor;
import com.gumtree.android.configuration.GumtreeGlideModule;
import com.gumtree.android.dagger.modules.ApplicationModule;
import com.gumtree.android.dagger.modules.NetworkClientModule;
import com.gumtree.android.dagger.modules.OkHttpModule;
import com.gumtree.android.dagger.modules.SystemModule;
import com.gumtree.android.dagger.scope.ApplicationScope;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.favourites.FavouriteCapiIntentService;
import com.gumtree.android.favourites.FavouriteIntentService;
import com.gumtree.android.favourites.FavouritesFragment;
import com.gumtree.android.favourites.SavedFavouritesSync;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.home.HomeCustomiseFragment;
import com.gumtree.android.location.services.TrackingLocationService;
import com.gumtree.android.locations.LocationChildrenRequest;
import com.gumtree.android.locations.SetLocationFragment;
import com.gumtree.android.locations.postad.PostcodeLookupRequest;
import com.gumtree.android.login.AccountAuthenticator;
import com.gumtree.android.login.AccountAuthenticatorService;
import com.gumtree.android.managead.DeleteAdIntentService;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.managead.ManageAdsFragment;
import com.gumtree.android.message_box.ConversationRequest;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.message_box.ConversationsRequest;
import com.gumtree.android.message_box.DeleteConversationIntentService;
import com.gumtree.android.message_box.FlagConversationIntentService;
import com.gumtree.android.message_box.InboxFragment;
import com.gumtree.android.message_box.PostConversationReplyIntentService;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.message_box.conversation.ConversationFragment;
import com.gumtree.android.message_box.conversation.ConversationIntentService;
import com.gumtree.android.notifications.GcmIntentService;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.notifications.providers.CAPIPushNotificationsProvider;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.MDNSPushNotificationsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.post_ad.FeaturesIntentService;
import com.gumtree.android.post_ad.MetadataIntentService;
import com.gumtree.android.post_ad.PostAdAPIIntentService;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.PostAdInsertionFeeFragment;
import com.gumtree.android.post_ad.feature.PostAdPaymentActivity;
import com.gumtree.android.post_ad.feature.service.ApplyFeatureIntentService;
import com.gumtree.android.post_ad.feature.service.SubmitOrderIntentService;
import com.gumtree.android.post_ad.gallery.GalleryActivity;
import com.gumtree.android.post_ad.payment.PostAdPaypalActivity;
import com.gumtree.android.post_ad.preview.PreviewActivity;
import com.gumtree.android.post_ad.preview.PreviewGalleryActivity;
import com.gumtree.android.post_ad.preview.PreviewImagePagerFragment;
import com.gumtree.android.post_ad.steps.PostAdBaseFragment;
import com.gumtree.android.post_ad.steps.PostAdOneFragment2;
import com.gumtree.android.post_ad.steps.PostAdStepsActivity;
import com.gumtree.android.post_ad.steps.PostAdThreeFragment2;
import com.gumtree.android.post_ad.steps.PostAdTwoFragment2;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.post_ad.summary.PostAdSummaryFragment;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.AdModelConverter;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.savedsearches.SavedSearchSync;
import com.gumtree.android.savedsearches.SavedSearchesActivity;
import com.gumtree.android.savedsearches.SavedSearchesPostIntentService;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.services.StaticTrackingService;
import com.gumtree.android.settings.SettingsFragment;
import com.gumtree.android.srp.BaseDynAttributeFragment;
import com.gumtree.android.srp.NearByAdvertAdapter;
import com.gumtree.android.srp.SRPActivity;
import com.gumtree.android.srp.bing.BingAdSRPView;
import com.gumtree.android.srp.bing.BingSRPIntentService;
import com.gumtree.android.srp.treebay.TreebayAdGalleryItemView;
import com.gumtree.android.srp.treebay.TreebayAdGridItemView;
import com.gumtree.android.tracking.TrackingDataProvider;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.UserService;
import com.gumtree.android.vip.VIPGalleryActivity;
import com.gumtree.android.vip.VIPImagePagerFragment;
import com.gumtree.android.vip.VIPMainFragment;
import com.gumtree.android.vip.VIPSellerOtherItemsFragment;
import com.gumtree.android.vip.api.VipDetailsIntentService;
import com.gumtree.android.vip.bing.BingAdFragment;
import com.gumtree.android.vip.bing.BingAdVIPView;
import com.gumtree.android.vip.bing.BingVIPIntentService;
import com.gumtree.android.vip.reply.ReplyToVIPFragment;
import com.gumtree.android.vip.reply.api.MetadataRequest;
import com.gumtree.android.vip.reply.api.SendReplyRequest;
import com.gumtree.android.vip_treebay.BingAdFragmentTreebay;
import com.gumtree.android.vip_treebay.VIPGalleryActivityTreebay;
import com.gumtree.android.vip_treebay.VIPImagePagerFragmentTreebay;
import com.gumtree.android.vip_treebay.VIPMainFragmentTreebay;
import dagger.Component;
import javax.inject.Named;
import rx.Scheduler;

@ApplicationScope
@Component(modules = {ApplicationModule.class, SystemModule.class, OkHttpModule.class, NetworkClientModule.class})
public interface ApplicationComponent extends BaseComponent {
    AdModelConverter adModelConverter();

    AdsApi adsApi();

    AuthBaseStrategy authBaseStrategy();

    @Named("background")
    Scheduler backgroundScheduler();

    IBadgeCounterManager badgeCounterManager();

    BaseAccountManager baseAccountManager();

    @Named("callistoClient")
    Retrofit2Client callistoClient();

    CapiConfig capiConfig();

    CAPIIntentFactory capiIntentFactory();

    Context context();

    EventBus eventBus();

    @Named("gsonClient")
    ICapiClient gsonClient();

    void inject(GumtreeApplication gumtreeApplication);

    void inject(AuthenticatorActivity authenticatorActivity);

    void inject(ActivateAccountIntentService activateAccountIntentService);

    void inject(AuthRequest authRequest);

    void inject(SuggestedCategoryFragment suggestedCategoryFragment);

    void inject(SoftUpdateService softUpdateService);

    void inject(BaseActivity baseActivity);

    void inject(NavigationActivity navigationActivity);

    void inject(ConnectivityChangeReceiver connectivityChangeReceiver);

    void inject(Network network);

    void inject(GumtreeAuthBaseStrategy gumtreeAuthBaseStrategy);

    void inject(CAPIIntentFactory cAPIIntentFactory);

    void inject(GumtreeRestExecutor gumtreeRestExecutor);

    void inject(GumtreeGlideModule gumtreeGlideModule);

    void inject(DFPProcessor dFPProcessor);

    void inject(FavouriteCapiIntentService favouriteCapiIntentService);

    void inject(FavouriteIntentService favouriteIntentService);

    void inject(FavouritesFragment favouritesFragment);

    void inject(SavedFavouritesSync savedFavouritesSync);

    void inject(HomeActivity homeActivity);

    void inject(HomeCustomiseFragment homeCustomiseFragment);

    void inject(LocationChildrenRequest locationChildrenRequest);

    void inject(SetLocationFragment setLocationFragment);

    void inject(PostcodeLookupRequest postcodeLookupRequest);

    void inject(AccountAuthenticator accountAuthenticator);

    void inject(AccountAuthenticatorService accountAuthenticatorService);

    void inject(DeleteAdIntentService deleteAdIntentService);

    void inject(ManageAdActivity manageAdActivity);

    void inject(ManageAdsFragment manageAdsFragment);

    void inject(ConversationRequest conversationRequest);

    void inject(ConversationsIntentService conversationsIntentService);

    void inject(ConversationsRequest conversationsRequest);

    void inject(DeleteConversationIntentService deleteConversationIntentService);

    void inject(FlagConversationIntentService flagConversationIntentService);

    void inject(InboxFragment inboxFragment);

    void inject(PostConversationReplyIntentService postConversationReplyIntentService);

    void inject(ConversationActivity conversationActivity);

    void inject(ConversationFragment conversationFragment);

    void inject(ConversationIntentService conversationIntentService);

    void inject(GcmIntentService gcmIntentService);

    void inject(CAPIPushNotificationsProvider cAPIPushNotificationsProvider);

    void inject(GCMSettingsProvider gCMSettingsProvider);

    void inject(MDNSPushNotificationsProvider mDNSPushNotificationsProvider);

    void inject(FeaturesIntentService featuresIntentService);

    void inject(MetadataIntentService metadataIntentService);

    void inject(PostAdAPIIntentService postAdAPIIntentService);

    void inject(PostAdBaseActivity postAdBaseActivity);

    void inject(PostAdDBIntentService postAdDBIntentService);

    void inject(PostAdInsertionFeeFragment postAdInsertionFeeFragment);

    void inject(PostAdPaymentActivity postAdPaymentActivity);

    void inject(ApplyFeatureIntentService applyFeatureIntentService);

    void inject(SubmitOrderIntentService submitOrderIntentService);

    void inject(GalleryActivity galleryActivity);

    void inject(PostAdPaypalActivity postAdPaypalActivity);

    void inject(PreviewActivity previewActivity);

    void inject(PreviewGalleryActivity previewGalleryActivity);

    void inject(PreviewImagePagerFragment previewImagePagerFragment);

    void inject(PostAdBaseFragment postAdBaseFragment);

    void inject(PostAdOneFragment2 postAdOneFragment2);

    void inject(PostAdStepsActivity postAdStepsActivity);

    void inject(PostAdThreeFragment2 postAdThreeFragment2);

    void inject(PostAdTwoFragment2 postAdTwoFragment2);

    void inject(PostAdNavigationActivity postAdNavigationActivity);

    void inject(PostAdSummaryActivity postAdSummaryActivity);

    void inject(PostAdSummaryFragment postAdSummaryFragment);

    void inject(SavedSearchSync savedSearchSync);

    void inject(SavedSearchesActivity savedSearchesActivity);

    void inject(SavedSearchesPostIntentService savedSearchesPostIntentService);

    void inject(SettingsFragment settingsFragment);

    void inject(BaseDynAttributeFragment baseDynAttributeFragment);

    void inject(NearByAdvertAdapter nearByAdvertAdapter);

    void inject(SRPActivity sRPActivity);

    void inject(BingAdSRPView bingAdSRPView);

    void inject(BingSRPIntentService bingSRPIntentService);

    void inject(TreebayAdGalleryItemView treebayAdGalleryItemView);

    void inject(TreebayAdGridItemView treebayAdGridItemView);

    void inject(VIPGalleryActivity vIPGalleryActivity);

    void inject(VIPImagePagerFragment vIPImagePagerFragment);

    void inject(VIPMainFragment vIPMainFragment);

    void inject(VIPSellerOtherItemsFragment vIPSellerOtherItemsFragment);

    void inject(VipDetailsIntentService vipDetailsIntentService);

    void inject(BingAdFragment bingAdFragment);

    void inject(BingAdVIPView bingAdVIPView);

    void inject(BingVIPIntentService bingVIPIntentService);

    void inject(ReplyToVIPFragment replyToVIPFragment);

    void inject(MetadataRequest metadataRequest);

    void inject(SendReplyRequest sendReplyRequest);

    void inject(BingAdFragmentTreebay bingAdFragmentTreebay);

    void inject(VIPGalleryActivityTreebay vIPGalleryActivityTreebay);

    void inject(VIPImagePagerFragmentTreebay vIPImagePagerFragmentTreebay);

    void inject(VIPMainFragmentTreebay vIPMainFragmentTreebay);

    LocalisedTextProvider localisedTextProvider();

    Network network();

    NetworkStateService networkStateService();

    @Named("picturesUploadClient")
    ICapiClient picturesUploadClient();

    Repository<DraftAd> postAdRepository();

    PostAdService postAdService();

    PriceInfoService priceInfoService();

    PushNotificationsProvider pushNotificationsProvider();

    StaticTrackingService staticTrackingService();

    SuggestionsApi suggestionsApi();

    TrackingDataProvider trackingDataProvider();

    TrackingLocationService trackingLocationService();

    TrackingPostAdService trackingPostAdService();

    TreebayAdManager treebayAdManager();

    @Named("treebayClient")
    Retrofit2Client treebayClient();

    UserAdsApi userAdsApi();

    UserProfileApi userProfileApi();

    UserProfileStatusService userProfileStatusService();

    UserService userService();

    UsersApi usersApi();

    @Named("xmlClient")
    ICapiClient xmlClient();
}
