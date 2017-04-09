package com.gumtree.android.common.analytics;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.LogLevel;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePickerActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.treebay.TreebayCustomDimensionData;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.AppLocation.LocationType;
import com.gumtree.android.common.location.GeocoderLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.common.location.GumtreePostcodeLocation;
import com.gumtree.android.common.location.RadiusDAO.RadiusDecimal;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.features.Feature;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.Categories;
import com.gumtree.android.model.Locations;
import com.gumtree.android.post_ad.model.PostAdAttributeItem;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.vip.VIPContactFragment;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

public class Track {
    private static final String DIRECTION_B2S = "B2S";
    private static final String DIRECTION_S2B = "S2B";
    public static final String EMPTY_PARAMETERS = "catID=0;locID=;adID=";
    private static final String LOGIN = "Login";
    private static final String NO_ID = "0";
    private static final String NO_NAME = "None";
    public static final String TELECAPTURE_TRACKING_PARAMETERS = "utm_source=GumtreeApp&utm_medium=Android&utm_term=Telecapture&utm_content=Telecapture";
    public static final long TRACKING_VALUE = 1;
    private static final String VIP = "VIP";
    public static Tracker tracker;

    private Track() {
    }

    public static void prepare(Application application) {
        if (tracker == null) {
            setupGA(application);
            setupAdjust(application);
        }
    }

    private static void setupAdjust(Application application) {
        AdjustConfig adjustConfig = new AdjustConfig(application, "53s38mq2thzl", AdjustConfig.ENVIRONMENT_PRODUCTION);
        adjustConfig.setLogLevel(LogLevel.ASSERT);
        adjustConfig.setSendInBackground(true);
        Adjust.onCreate(adjustConfig);
        application.registerActivityLifecycleCallbacks(new AdjustLifeCycleCallbacks());
    }

    private static void setupGA(Context context) {
        GoogleAnalytics instance = GoogleAnalytics.getInstance(context);
        instance.getLogger().setLogLevel(3);
        instance.setLocalDispatchPeriod(120);
        tracker = instance.newTracker("UA-22612908-15");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(false);
        tracker.setSessionTimeout(-1);
        tracker.setAnonymizeIp(true);
    }

    public static void viewHome(AppLocation appLocation) {
        sendView("Home", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID, getString(2131165839), BuildConfig.FLAVOR, appLocation);
    }

    public static void viewResultBrowse(int i, AppLocation appLocation, boolean z, @NonNull Search search) {
        String query = search.getQuery();
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(48, convertSRPViewType(z));
        screenViewBuilderWrapper.setCustomDimension(47, convertSRPSortType(search));
        setAppLocationDimensions(appLocation, screenViewBuilderWrapper);
        setStandardSearchDimensions(search, i + 1, screenViewBuilderWrapper);
        if (query == null || query.isEmpty()) {
            screenViewBuilderWrapper.setCustomDimension(1, "ResultsBrowse");
            sendView("ResultsBrowse", screenViewBuilderWrapper);
            return;
        }
        screenViewBuilderWrapper.setCustomDimension(1, "ResultsSearch");
        sendView("ResultsSearch", screenViewBuilderWrapper);
    }

    public static void setStandardSearchDimensions(@Nullable TreebayCustomDimensionData treebayCustomDimensionData, EventBuilderWrapper eventBuilderWrapper) {
        if (treebayCustomDimensionData != null) {
            setLocationDimensions(treebayCustomDimensionData.getLocationId(), treebayCustomDimensionData.getLocationName(), eventBuilderWrapper);
            setCategoryDimensions(treebayCustomDimensionData.getCategoryId(), treebayCustomDimensionData.getCategoryName(), eventBuilderWrapper);
            eventBuilderWrapper.setCustomDimension(41, Integer.toString(treebayCustomDimensionData.getPage()));
            eventBuilderWrapper.setCustomDimension(40, treebayCustomDimensionData.getSearchQuery());
        }
    }

    public static void setStandardSearchDimensions(@NonNull Search search, int i, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        setSearchLocationDimensions(search.getLocationId(), search.getLocationName(), screenViewBuilderWrapper);
        setSearchDistanceDimensions(search.getRadius(), screenViewBuilderWrapper);
        setPriceDimensions(search, screenViewBuilderWrapper);
        setCategoryDimensions(search.getCategoryId(), search.getCategoryName(), screenViewBuilderWrapper);
        screenViewBuilderWrapper.setCustomDimension(41, Integer.toString(i));
        screenViewBuilderWrapper.setCustomDimension(40, search.getQuery());
    }

    public static void viewResultBrowse(int i, AppLocation appLocation, boolean z) {
        int i2 = i + 1;
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(48, convertSRPViewType(z));
        sendView("ResultsBrowse", NO_ID, NO_NAME, "?p=" + i2, appLocation, screenViewBuilderWrapper);
    }

    public static void eventAppOpened(boolean z) {
        sendAdjustEvent("wn7wve");
    }

    private static String convertSRPViewType(boolean z) {
        if (z) {
            return "LST";
        }
        return "GAL";
    }

    private static String convertSRPSortType(Search search) {
        String stringValue = search.getStringValue(Search.SORT_TYPE);
        if (stringValue == null) {
            return "AGE_LOW_HIGH";
        }
        Object obj = -1;
        switch (stringValue.hashCode()) {
            case -1464398807:
                if (stringValue.equals(Search.SEARCH_DATE_DESCENDING)) {
                    obj = 3;
                    break;
                }
                break;
            case -483859678:
                if (stringValue.equals(Search.SEARCH_PRICE_ASCENDING)) {
                    obj = 1;
                    break;
                }
                break;
            case 1216810958:
                if (stringValue.equals(Search.SEARCH_PRICE_DESCENDING)) {
                    obj = 2;
                    break;
                }
                break;
            case 1290180334:
                if (stringValue.equals(Search.SEARCH_DISTANCE_ASCENDING)) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return "DISTANCE_LOW_HIGH";
            case HighlightView.GROW_NONE /*1*/:
                return "PRICE_LOW_HIGH";
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return "PRICE_HIGH_LOW";
            default:
                return "AGE_LOW_HIGH";
        }
    }

    public static void viewPVIP(String str) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(30, str);
        screenViewBuilderWrapper.setCustomDimension(1, "pVIP");
        tracker.setScreenName("pVIP");
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void viewVIP(String str, String str2, String str3, boolean z, boolean z2, String str4) {
        sendAdjustEvent("bajcxm");
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
            setCategoryDimensions(str, str2, screenViewBuilderWrapper);
            screenViewBuilderWrapper.setCustomDimension(30, String.valueOf(str3));
            screenViewBuilderWrapper.setCustomDimension(1, VIP);
            screenViewBuilderWrapper.setCustomDimension(49, convertNearby(z));
            screenViewBuilderWrapper.setCustomDimension(48, convertSRPViewType(z2));
            if (str4 != null && str4.length() > 0) {
                screenViewBuilderWrapper.setCustomDimension(17, str4);
            }
            tracker.setScreenName("/VIP/" + str + "_" + str2 + "/0_None/");
            tracker.send(screenViewBuilderWrapper.build());
        }
    }

    public static void viewLocation() {
        tracker.setScreenName("/Location/");
        tracker.send(new ScreenViewBuilderWrapper().build());
    }

    public static void viewCategory(String str, String str2, AppLocation appLocation) {
        sendView("Category", str, str2, BuildConfig.FLAVOR, appLocation);
    }

    public static void viewFavourites() {
        tracker.setScreenName("/MySavedAds/0_None/0_None");
        tracker.send(new ScreenViewBuilderWrapper().build());
    }

    public static void viewSplashScreen() {
        tracker.setScreenName("SplashScreen");
        tracker.send(new ScreenViewBuilderWrapper().build());
    }

    public static void viewHelp(AppLocation appLocation) {
        sendView("Help", NO_ID, NO_NAME, BuildConfig.FLAVOR, appLocation);
    }

    public static void viewReplyVIP(String str, String str2) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        setCategoryDimensions(str, str2, screenViewBuilderWrapper);
        tracker.setScreenName("/R2SEmail/" + str + "_" + str2 + "/0_None");
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void viewSavedSearches() {
        sendView("SavedSearches", NO_ID, NO_NAME, NO_ID, NO_NAME);
    }

    public static void viewPostAd(PostAdData postAdData, String str, String str2) {
        if (postAdData != null) {
            PostAdAttributeItem attribute = postAdData.getAttribute(VIPContactFragment.CATEGORY_ID);
            PostAdAttributeItem attribute2 = postAdData.getAttribute("location");
            if (NO_ID.equals(postAdData.getAdId())) {
                sendView(str, attribute != null ? attribute.getValue() : BuildConfig.FLAVOR, attribute != null ? attribute.getLabel() : BuildConfig.FLAVOR, attribute2 != null ? attribute2.getValue() : BuildConfig.FLAVOR, attribute2 != null ? attribute2.getLabel() : BuildConfig.FLAVOR);
            } else {
                sendView(str2, attribute.getValue(), attribute.getLabel(), attribute2.getValue(), attribute2.getLabel());
            }
        }
    }

    public static void viewPostAdSummary(PostAdData postAdData) {
        viewPostAd(postAdData, "PostAdSummary", "EditAdSummary");
    }

    public static void viewPostAdStep1(PostAdData postAdData) {
        viewPostAd(postAdData, "PostAdStep1", "EditAdStep1");
    }

    public static void viewPostAdStep2(PostAdData postAdData) {
        viewPostAd(postAdData, "PostAdStep1", "EditAdStep1");
    }

    public static void viewPostAdStep3(PostAdData postAdData) {
        viewPostAd(postAdData, "PostAdStep3", "EditAdStep3");
    }

    public static void viewPostAdPreview(String str) {
        if (NO_ID.equals(str)) {
            sendView("PostAdPreview", NO_ID, NO_NAME, NO_ID, NO_NAME);
        } else {
            sendView("EditAdPreview", NO_ID, NO_NAME, NO_ID, NO_NAME);
        }
    }

    public static void viewPostAdGallery(String str) {
        if (NO_ID.equals(str)) {
            sendView("PostAdGallery", NO_ID, NO_NAME, NO_ID, NO_NAME);
        } else {
            sendView("EditAdGallery", NO_ID, NO_NAME, NO_ID, NO_NAME);
        }
    }

    public static void viewBumpUpByPhone() {
        sendView("BumpUpByPhone", NO_ID, NO_NAME, NO_ID, NO_NAME);
    }

    public static void viewMyAds() {
        sendView("MyAds", NO_ID, NO_NAME, NO_ID, NO_NAME);
    }

    public static void viewConversations(String str) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(50, str);
        tracker.setScreenName("/MessageCenter/0_None/0_None");
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void viewConversation(String str, String str2) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(30, str);
        screenViewBuilderWrapper.setCustomDimension(50, str2);
        tracker.setScreenName("/AdConversation/0_None/0_None");
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void eventEmail(AppLocation appLocation) {
        sendGAEvent("Help", "CSEmailBegin", "catID=0;locID=" + appLocation.getTrackingData(), new EventBuilderWrapper());
    }

    public static void eventReplyVipEmailSuccess(String str, long j, boolean z, boolean z2) {
        eventReplyVip("R2SEmailSuccess", str, j, z, z2);
        sendAdjustEvent("h37ur1");
    }

    public static void eventReplyVipEmailFail(String str, long j, boolean z, boolean z2) {
        eventReplyVip("R2SEmailFail", str, j, z, z2);
    }

    public static void eventReplyWithCV(String str, long j, boolean z, boolean z2) {
        eventReplyVip("replyWithCV", str, j, z, z2);
    }

    public static void eventReplyWithoutCV(String str, long j, boolean z, boolean z2) {
        eventReplyVip("replyWithoutCV", str, j, z, z2);
    }

    private static void eventReplyVip(String str, String str2, long j, boolean z, boolean z2) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(49, convertNearby(z));
        eventBuilderWrapper.setCustomDimension(48, convertSRPViewType(z2));
        sendGAEvent("R2SEmail", str, "catID=" + str2 + ";locID=;adID=" + j, eventBuilderWrapper);
    }

    public static void eventViewFeaturedAd(String str, long j) {
        sendGAEvent("ResultsBrowse", "FeaturedAdClick", "catID=" + str + ";locID=;adID=" + j, new EventBuilderWrapper());
    }

    public static void eventEditAdBegin(String str) {
        sendGAEvent("MyAds", "EditAdBegin", EMPTY_PARAMETERS + str, new EventBuilderWrapper());
    }

    public static void eventPostAdBeginFromMenu() {
        sendGAEvent("Button", "PostAdBegin", EMPTY_PARAMETERS, new EventBuilderWrapper());
    }

    public static void eventPostAdBeginFromHome() {
        sendGAEvent("Home", "PostAdBegin", EMPTY_PARAMETERS);
    }

    public static void eventPostAdPreview(PostAdData postAdData) {
        if (postAdData != null) {
            if (postAdData.isPost()) {
                sendGAEvent("PostAdSummary", "PostAdPreview", buildParameters(postAdData), new EventBuilderWrapper());
                return;
            }
            sendGAEvent("EditAdSummary", "EditAdPreview", buildParameters(postAdData), new EventBuilderWrapper());
        }
    }

    public static void eventPostAdAttempt(PostAdData postAdData) {
        if (postAdData != null) {
            if (!postAdData.isPaidAd()) {
                if (postAdData.getSelectedFeatures() != null && postAdData.getSelectedFeatures().size() > 0) {
                    eventFeaturePaymentAttempt(postAdData.getAdId(), postAdData.getSelectedFeatures());
                }
                eventPostAdFreeAttempt(postAdData);
            } else if (postAdData.isPaidAd()) {
                eventPostAdPaidAttempt(postAdData);
            }
        }
    }

    private static void eventPostAdFreeAttempt(PostAdData postAdData) {
        if (postAdData.isPost()) {
            sendGAEvent("PostAdSummary", "PostAdFreeAttempt", buildParameters(postAdData), new EventBuilderWrapper());
            return;
        }
        sendGAEvent("EditAdSummary", "EditAdAttempt", buildParameters(postAdData));
    }

    private static void eventPostAdPaidAttempt(PostAdData postAdData) {
        if (postAdData != null) {
            String buildParameters = buildParameters(postAdData);
            if (postAdData.getSelectedFeatures() != null && postAdData.getSelectedFeatures().size() > 0) {
                eventFeaturePaymentAttempt(postAdData.getAdId(), postAdData.getSelectedFeatures());
            }
            sendGAEvent("OrderReview", "PostAdPaidAttempt", buildParameters);
        }
    }

    public static void eventPostAdFreeSuccess(PostAdData postAdData) {
        if (postAdData != null) {
            if (postAdData.isPost()) {
                sendGAEvent("PostAdSummary", "PostAdFreeSuccess", buildParameters(postAdData));
                sendAdjustEvent("tkeg2t");
                return;
            }
            sendGAEvent("EditAdSummary", "EditAdSuccess", buildParameters(postAdData));
        }
    }

    public static void eventPostAdPaidSuccess(PostAdData postAdData) {
        if (postAdData != null) {
            sendGAEvent("PayPalPayments", "PostAdPaidSuccess", buildParameters(postAdData));
            sendAdjustEvent("m3hl7o");
        }
    }

    public static void eventPostAdFail(PostAdData postAdData) {
        if (postAdData != null) {
            if (postAdData.isPost()) {
                String buildParameters = buildParameters(postAdData);
                if (postAdData.isPaidAd()) {
                    sendGAEvent("PostAdSummary", "PostAdPaidFail", buildParameters);
                    return;
                } else {
                    sendGAEvent("PostAdSummary", "PostAdFreeFail", buildParameters);
                    return;
                }
            }
            sendGAEvent("EditAdSummary", "EditAdFail", buildParameters(postAdData));
        }
    }

    public static void eventPostAdPaidFail(PostAdData postAdData) {
        if (postAdData != null) {
            sendGAEvent("PayPalPayments", "PostAdFreeFail", buildParameters(postAdData));
        }
    }

    public static void eventPostAdCancel(PostAdData postAdData) {
        if (postAdData != null) {
            if (postAdData.isPost()) {
                sendGAEvent("PostAdSummary", "PostAdCancel", buildParameters(postAdData));
                if (postAdData.getSelectedFeatures() != null && postAdData.getSelectedFeatures().size() > 0) {
                    eventFeaturePaymentCancel(postAdData.getAdId());
                    return;
                }
                return;
            }
            sendGAEvent("EditAdSummary", "EditAdCancel", buildParameters(postAdData));
            if (postAdData.getSelectedFeatures() != null && postAdData.getSelectedFeatures().size() > 0) {
                eventFeaturePaymentCancel(postAdData.getAdId());
            }
        }
    }

    public static void eventAddImageBegin(String str) {
        if (NO_ID.equals(str)) {
            sendGAEvent("PostAdGallery", "AddImageBegin", EMPTY_PARAMETERS);
        } else {
            sendGAEvent("EditAdGallery", "AddImageBegin", EMPTY_PARAMETERS + str);
        }
    }

    public static void eventAddImageCancel(String str) {
        if (NO_ID.equals(str)) {
            sendGAEvent("PostAdGallery", "AddImageCancel", EMPTY_PARAMETERS);
        } else {
            sendGAEvent("EditAdGallery", "AddImageCancel", EMPTY_PARAMETERS + str);
        }
    }

    public static void eventAddImageAttempt(String str) {
        if (NO_ID.equals(str)) {
            sendGAEvent("PostAdGallery", "AddImageAttempt", EMPTY_PARAMETERS);
        } else {
            sendGAEvent("EditAdGallery", "AddImageAttempt", EMPTY_PARAMETERS + str);
        }
    }

    public static void eventExpandPartnershipVIP(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, VIP);
        eventBuilderWrapper.setCustomDimension(80, PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        sendGAEvent(VIP, "TabClick", str, eventBuilderWrapper);
    }

    public static void eventLoginBeginFromSettings() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("Settings", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromManageAds() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("MyAds", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromPostAd() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("PostAdSummary", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromSavedSearches() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("SavedSearches", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromFavourites() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("MySavedAds", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromMessages() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("MessageCenter", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginBeginFromActivateLink() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("ActivateLink", "LoginBegin", eventBuilderWrapper);
    }

    public static void eventLoginCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent(LOGIN, "LoginCancel", eventBuilderWrapper);
    }

    public static void eventLoginAttempt(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        eventBuilderWrapper.setCustomDimension(19, str);
        sendGAEvent(LOGIN, "LoginAttempt");
    }

    public static void eventLoginSuccess(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(19, str);
        sendGAEvent(LOGIN, "LoginSuccess", eventBuilderWrapper);
    }

    public static void eventLoginFail(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        eventBuilderWrapper.setCustomDimension(19, str);
        sendGAEvent(LOGIN, "LoginFail", eventBuilderWrapper);
    }

    public static void eventActivateAccountBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("ActivateLink", "ActivateAccountBegin", eventBuilderWrapper);
    }

    public static void eventActivateAccountAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("ActivateLink", "ActivateAccountAttempt", eventBuilderWrapper);
    }

    public static void eventActivateAccountFail() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("ActivateLink", "ActivateAccountFail", eventBuilderWrapper);
    }

    public static void eventActivateAccountSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, LOGIN);
        sendGAEvent("ActivateLink", "ActivateAccountSuccess", eventBuilderWrapper);
    }

    public static void messageSendAttempt(String str, boolean z) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(30, str);
        eventBuilderWrapper.setCustomDimension(37, getDirection(z));
        sendGAEvent("AdConversation", "MessageSendAttempt", eventBuilderWrapper);
    }

    public static void messageSendSuccess(String str, boolean z) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(30, str);
        eventBuilderWrapper.setCustomDimension(37, getDirection(z));
        sendGAEvent("AdConversation", "MessageSendSuccess", eventBuilderWrapper);
    }

    public static void messageSendFail(String str, boolean z) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(30, str);
        eventBuilderWrapper.setCustomDimension(37, getDirection(z));
        sendGAEvent("AdConversation", "MessageSendFailure", eventBuilderWrapper);
    }

    public static void eventMessageCenterEmailEditBegin() {
        sendGAEvent("MessageCenterContact", "MessageCenterEmailEditBegin", EMPTY_PARAMETERS);
    }

    public static void eventPostAdEmailEditBegin() {
        sendGAEvent("PostAdContact", "PostAdEmailEditBegin", EMPTY_PARAMETERS);
    }

    public static void eventSettingsUserDetailsEditBegin() {
        sendGAEvent("Settings", "SettingsUserDetailsEditBegin", EMPTY_PARAMETERS);
    }

    @NonNull
    private static String getDirection(boolean z) {
        return z ? DIRECTION_S2B : DIRECTION_B2S;
    }

    public static void eventLogoutBegin() {
        sendGAEvent("Settings", "LogoutAttempt");
    }

    public static void eventLogoutSuccess() {
        sendGAEvent("Settings", "LogoutSuccess");
    }

    public static void eventMotorsCheckBegin() {
        sendGAEvent(VIP, "MotorsCheckBegin");
    }

    public static void eventMotorsCheckCancel() {
        sendGAEvent(VIP, "MotorsCheckCancel");
    }

    public static void eventMotorsCheckAttempt() {
        sendGAEvent(VIP, "MotorsCheckAttempt");
    }

    public static void eventSaveSearchBegin() {
        sendGAEvent("ResultsBrowse", "SaveSearchBegin");
    }

    public static void eventSaveSearchCancel() {
        sendGAEvent("ResultsBrowse", "SaveSearchCancel");
    }

    public static void eventSaveSearchAttempt() {
        sendGAEvent("ResultsBrowse", "SaveSearchAttempt");
    }

    public static void eventSaveSearchSuccess() {
        sendGAEvent("ResultsBrowse", "SaveSearchSuccess");
    }

    public static void eventSavedSearchDeleteSuccess() {
        sendGAEvent("SavedSearches", "SavedSearchDeleteSuccess");
    }

    public static void eventSavedSearchOpen() {
        sendGAEvent("SavedSearches", "SavedSearchOpen");
    }

    public static void eventSavedSearchNotificationOpen() {
        sendGAEvent("SavedSearches", "SavedSearchNotificationOpen");
    }

    public static void eventMessageNotificationOpenToConversations() {
        sendGAEvent("MessageCenter", "MessageNotificationOpen");
    }

    public static void eventMessageNotificationOpenToConversation() {
        sendGAEvent("AdConversation", "MessageNotificationOpen");
    }

    public static void eventSavedSearchToggleOn() {
        sendGAEvent("SavedSearches", "SavedSearchToggle", "enable=Yes;");
    }

    public static void eventSavedSearchToggleOff() {
        sendGAEvent("SavedSearches", "SavedSearchToggle", "enable=No;");
    }

    public static void eventAlertNotificationToggleOn() {
        sendGAEvent("Settings", "AlertNotificationToggle", "enable=Yes;");
    }

    public static void eventAlertNotificationToggleOff() {
        sendGAEvent("Settings", "AlertNotificationToggle", "enable=No;");
    }

    public static void eventMessageNotificationToggleOn() {
        sendGAEvent("Settings", "MessageNotificationToggle", "enable=Yes;");
    }

    public static void eventMessageNotificationToggleOff() {
        sendGAEvent("Settings", "MessageNotificationToggle", "enable=No;");
    }

    public static void eventNotificationUnsubscription() {
        sendNoInteraction("Settings", "NotificationSubscription", "subscription=No;");
    }

    public static void eventNotificationSubscription() {
        sendNoInteraction("Settings", "NotificationSubscription", "subscription=Yes;");
    }

    public static void eventNotificationUnsubscriptionOK() {
        sendNoInteraction("Settings", "NotificationSubscriptionOK", "subscription=No;");
    }

    public static void eventNotificationSubscriptionOK() {
        sendNoInteraction("Settings", "NotificationSubscriptionOK", "subscription=Yes;");
    }

    public static void eventFavouriteRefresh() {
        sendGAEvent("MySavedAds", "WatchlistRefresh");
    }

    public static void eventFavouriteRemove(long j) {
        sendGAEvent("MySavedAds", "WatchlistRemove", "catID=;locID=;adID=" + j);
    }

    public static void eventFavouriteRemoveSRP(String str, long j) {
        sendGAEvent("ResultsBrowse", "WatchlistRemove", "catID=" + str + ";locID=;adID=" + j);
    }

    public static void eventFavouriteRemoveVIP(String str, long j) {
        sendGAEvent(VIP, "WatchlistRemove", "catID=" + str + ";locID=;adID=" + j);
    }

    public static void eventFavouriteAddSRP(String str, long j) {
        sendGAEvent("ResultsBrowse", "WatchlistAdd", "catID=" + str + ";locID=;adID=" + j);
    }

    public static void eventFavouriteAddVIP(String str, long j) {
        sendGAEvent(VIP, "WatchlistAdd", "catID=" + str + ";locID=;adID=" + j);
    }

    public static void eventShareAdVIP(String str, long j) {
        sendGAEvent(VIP, "ShareAdBegin", "catID=" + str + ";locID=;adID=" + j);
    }

    private static void eventVIP(String str, String str2, long j, boolean z, boolean z2) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(49, convertNearby(z));
        eventBuilderWrapper.setCustomDimension(48, convertSRPViewType(z2));
        sendGAEvent(VIP, str, "catID=" + str2 + ";locID=;adID=" + j, eventBuilderWrapper);
    }

    public static void eventBumpUpAttempt(long j) {
        sendGAEvent("BumpUpByPhone", "BumpUpAttempt", EMPTY_PARAMETERS + j);
    }

    public static void eventBumpUpCancel(long j) {
        sendGAEvent("BumpUpByPhone", "BumpUpCancel", EMPTY_PARAMETERS + j);
    }

    public static void eventDeleteAdBegin(long j) {
        sendGAEvent("MyAds", "DeleteAdBegin", EMPTY_PARAMETERS + j);
    }

    public static void eventDeleteAdCancel(long j) {
        sendGAEvent("MyAds", "DeleteAdCancel", EMPTY_PARAMETERS + j);
    }

    public static void eventDeleteAdAttempt(String str, String str2) {
        sendGAEvent("MyAds", "DeleteAdAttempt", "catID=" + str + ";locID=;adID=" + str2);
    }

    public static void eventDeleteAdSuccess(String str, String str2) {
        sendGAEvent("MyAds", "DeleteAdSuccess", "catID=" + str + ";locID=;adID=" + str2);
    }

    public static void eventDeleteAdFail(String str, String str2) {
        sendGAEvent("MyAds", "DeleteAdFail", "catID=" + str + ";locID=;adID=" + str2);
    }

    public static void eventDeleteConversationBegin(@NonNull String str) {
        sendGAEvent("AdConversation", "DeleteConversationBegin", conversationIdEventBuilder(str));
    }

    public static void eventDeleteConversationCancel(@NonNull String str) {
        sendGAEvent("AdConversation", "DeleteConversationCancel", conversationIdEventBuilder(str));
    }

    public static void eventDeleteConversationAttempt(@NonNull String str) {
        sendGAEvent("AdConversation", "DeleteConversationAttempt", conversationIdEventBuilder(str));
    }

    public static void eventDeleteConversationSuccess(@NonNull String str) {
        sendGAEvent("AdConversation", "DeleteConversationSuccess", conversationIdEventBuilder(str));
    }

    public static void eventDeleteConversationFail(@NonNull String str) {
        sendGAEvent("AdConversation", "DeleteConversationFail", conversationIdEventBuilder(str));
    }

    @NonNull
    private static EventBuilderWrapper conversationIdEventBuilder(@NonNull String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.set("ConversationID", str);
        return eventBuilderWrapper;
    }

    public static void eventTelecaptureBegin() {
        sendGAEvent("MyAds", "TelecaptureBegin", EMPTY_PARAMETERS);
    }

    public static void eventPayNow() {
        sendGAEvent("MyAds", "PayNow", EMPTY_PARAMETERS);
    }

    public static void eventTelecaptureAttempt() {
        sendGAEvent("MyAds", "TelecaptureAttempt", EMPTY_PARAMETERS);
    }

    public static void eventTelecaptureCancel() {
        sendGAEvent("MyAds", "TelecaptureCancel", EMPTY_PARAMETERS);
    }

    public static void eventRefreshMyAds() {
        sendGAEvent("MyAds", "DeleteAdFail", EMPTY_PARAMETERS);
    }

    public static void eventMyAdsActionBarPostAd() {
        sendGAEvent("MyAds", "PostAdActionBar", EMPTY_PARAMETERS);
    }

    public static void eventMyAdsEmptyPostAd() {
        sendGAEvent("MyAds", "PostAdEmptyView", EMPTY_PARAMETERS);
    }

    public static void eventAttemptNFCVip(long j) {
        sendSocial("NFC", "ShareAdAttempt", "adID=" + j);
    }

    public static void eventSuccessNFCVip(long j) {
        sendSocial("NFC", "ShareAdSuccess", "adID=" + j);
    }

    public static void eventFeaturePaymentBeginManageAds(String str) {
        sendGAEvent("MyAds", "FeatureAdBegin", "adID=" + str);
    }

    public static void eventFeaturePaymentBeginPostAd(String str) {
        sendGAEvent("PostAdSummary", "FeatureAdBegin", "adID=" + str);
    }

    public static void eventFeaturePaymentBeginEditAd(String str) {
        sendGAEvent("EditAdSummary", "FeatureAdBegin", "adID=" + str);
    }

    public static void eventHomeFireQuickSearch(String str) {
        sendGAEvent("Home", "InvokeQuickSearch", "loc=" + str);
    }

    public static void eventChangeViewSrp(int i) {
        sendGAEvent("ResultsBrowse", "SwitchView", "view=" + (i == 0 ? "LIST" : "GALLERY"));
    }

    public static void eventHomeAttemptUpdate() {
        sendGAEvent("Home", "AttemptUpdate");
    }

    public static void eventHomeCancelUpdate() {
        sendGAEvent("Home", "CancelUpdate");
    }

    public static void eventSRPFireQuickSearch() {
        sendGAEvent("ResultsBrowse", "InvokeQuickSearch");
    }

    public static void eventSRPFireRefineSearch() {
        sendGAEvent("ResultsBrowse", "InvokeRefineSearch");
    }

    public static void eventSRPFireRefineBeginButton() {
        sendGAEvent("ResultsBrowse", "RefineBegin", "button");
    }

    public static void eventSRPFireRefineBeginSwipe() {
        sendGAEvent("ResultsBrowse", "RefineBegin", "swipe");
    }

    public static void eventFeaturePaymentAttempt(String str, ArrayList<Feature> arrayList) {
        String str2 = BuildConfig.FLAVOR;
        Iterator it = arrayList.iterator();
        int i = 1;
        String str3 = str2;
        while (it.hasNext()) {
            str2 = str3 + "ftr" + i + "=" + ((Feature) it.next()).getName();
            i++;
            str3 = str2 + ",";
        }
        sendGAEvent("OrderReview", "FeatureAdAttempt", "adID=" + str + ";" + str3);
    }

    public static void eventFeaturePaymentCancel(String str) {
        sendGAEvent("OrderReview", "FeatureAdCancel", "adID=" + str);
    }

    public static void eventFeaturePaymentSuccess(String str) {
        sendGAEvent("PayPalPayments", "FeatureAdSuccess", "adID=" + str);
        sendAdjustEvent("ul4us8");
    }

    public static void eventActionCall(String str, long j, boolean z, boolean z2) {
        eventVIP("R2SPhoneBegin", str, j, z, z2);
        sendAdjustEvent("oo16qw");
    }

    public static void eventActionSms(String str, long j, boolean z, boolean z2) {
        eventVIP("R2SSMSBegin", str, j, z, z2);
        sendAdjustEvent("pbknax");
    }

    public static void eventActionChat(String str, long j, boolean z, boolean z2) {
        eventVIP("R2SChatBegin", str, j, z, z2);
        sendAdjustEvent("ir1bia");
    }

    public static void eventEmailBegin(String str, long j, boolean z, boolean z2) {
        eventVIP("R2SEmailBegin", str, j, z, z2);
    }

    public static void eventActionReplyWeb(String str, long j, boolean z, boolean z2) {
        eventVIP("R2SReplyWebBegin", str, j, z, z2);
        sendAdjustEvent("jk22e9");
    }

    public static void eventFeaturePaymentFail(String str) {
        sendGAEvent("PayPalPayments", "FeatureAdFail", "adID=" + str);
    }

    public static void eventFeaturePaymentFail(String str, String str2) {
        sendGAEvent("OrderReview", "FeatureAdFail", "adID=" + str + ";err=" + str2);
    }

    public static void eventPostAdCategorySuggested(String str, String str2) {
        sendGAEvent("PostAdStep1", "PostAdCatSug", "categoryName=" + str + ";orderId=" + str2);
    }

    public static void eventPostAdCategoryTree(String str) {
        sendGAEvent("PostAdStep1", "PostAdCatTree", "categoryName=" + str);
    }

    public static void eventPostcodeSelected() {
        sendGAEvent("PostAdStep1", "PostcodeSelected");
    }

    public static void eventManualLocationSelected(String str) {
        sendGAEvent("PostAdStep1", "ManualLocationSelected", "loc=" + str);
    }

    public static void eventSetLocationPostcode(AppLocation appLocation) {
        sendGAEvent("Location", "PostcodeSelected", "loc=" + appLocation.getTrackingData());
    }

    public static void eventSetLocationManually(AppLocation appLocation) {
        sendGAEvent("Location", "ManualLocationSelected", "loc=" + appLocation.getTrackingData() + ";type=" + appLocation.getType().toString());
    }

    public static void eventReportAdBegin(String str) {
        sendGAEvent(VIP, "ReportAdBegin", "adID=" + str);
    }

    public static void eventReportAdCancel(String str) {
        sendGAEvent(VIP, "ReportAdCancel", "adID=" + str);
    }

    public static void eventReportAdAttempt(String str) {
        sendGAEvent(VIP, "ReportAdAttempt", "adID=" + str);
    }

    public static void eventReportAdFail(String str) {
        sendGAEvent(VIP, "ReportAdFail", "adID=" + str);
    }

    public static void eventReportAdSuccess(String str) {
        sendGAEvent(VIP, "ReportAdSuccess", "adID=" + str);
    }

    public static void eventViewAdStats() {
        sendGAEvent("MyAds", "ViewAdStats");
    }

    public static void dbPerformanceEvent(String str, long j) {
        performanceEvent(str, "db", j);
    }

    public static void httpPerformanceEvent(String str, long j) {
        performanceEvent(str, "http", j);
    }

    private static String getLabel(long j) {
        if (j > 60000) {
            return ">1m";
        }
        if (j > 10000) {
            return ">10s";
        }
        if (j > 3000) {
            return ">3s";
        }
        return "<3s";
    }

    private static String getApiEndPointForPerformanceLogging(String str) {
        if (str == null) {
            return null;
        }
        try {
            String[] split = str.split(".json/?");
            if (split.length <= 0) {
                return null;
            }
            String replace = split[0].replace("https://android-api-de.gumtree.com/api", BuildConfig.FLAVOR);
            if (replace.equals(Ads.TABLE_REF)) {
                return "ads";
            }
            if (replace.startsWith("/ads/search-metadata/")) {
                return "ads/search-metadata/?";
            }
            if (replace.startsWith("/ads/metadata/")) {
                return "ads/search-metadata/?";
            }
            if (replace.startsWith("/users/")) {
                if (replace.endsWith("ads")) {
                    return "users/?/ads";
                }
                return "users/?/ads/";
            } else if (replace.startsWith("/ads/")) {
                return "ads/?";
            } else {
                if (replace.startsWith(Categories.TABLE_REF)) {
                    return "categories";
                }
                if (replace.startsWith(Locations.TABLE_REF)) {
                    return "locations";
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertNearby(boolean z) {
        if (z) {
            return "Nearby Ads";
        }
        return "Organic";
    }

    private static String buildParameters(PostAdData postAdData) {
        return buildParameters(postAdData.getAdId(), postAdData.getCategoryId(), postAdData.getLocationId());
    }

    public static String buildParameters(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder("catID=");
        if (TextUtils.isEmpty(str2)) {
            stringBuilder.append(NO_ID);
        } else {
            stringBuilder.append(str2);
        }
        stringBuilder.append(";locID=");
        if (!TextUtils.isEmpty(str3)) {
            stringBuilder.append(str3);
        }
        stringBuilder.append(";adID=");
        if (!TextUtils.isEmpty(str)) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    private static void sendView(String str, String str2, String str3, String str4, String str5) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        setCategoryDimensions(str2, str3, screenViewBuilderWrapper);
        setLocationDimensions(str4, str5, screenViewBuilderWrapper);
        screenViewBuilderWrapper.setCustomDimension(1, str);
        tracker.setScreenName(DFPProcessor.SEPARATOR + str + DFPProcessor.SEPARATOR + str2 + "_" + str3 + DFPProcessor.SEPARATOR + str4 + "_" + str5);
        tracker.send(screenViewBuilderWrapper.build());
    }

    private static void sendView(String str, String str2, String str3, String str4, AppLocation appLocation) {
        sendView(str, str2, str3, str4, appLocation, new ScreenViewBuilderWrapper());
    }

    private static void sendView(String str, String str2, String str3, String str4, AppLocation appLocation, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        setCategoryDimensions(str2, str3, screenViewBuilderWrapper);
        setAppLocationDimensions(appLocation, screenViewBuilderWrapper);
        screenViewBuilderWrapper.setCustomDimension(1, str);
        tracker.setScreenName(DFPProcessor.SEPARATOR + str + DFPProcessor.SEPARATOR + str2 + "_" + str3 + DFPProcessor.SEPARATOR + appLocation.getTrackingData() + "_" + str4);
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void sendView(String str, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        tracker.setScreenName(str);
        tracker.send(screenViewBuilderWrapper.build());
    }

    public static void sendView(String str) {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        tracker.setScreenName(str);
        tracker.send(screenViewBuilderWrapper.build());
    }

    private static String getString(int i) {
        return GumtreeApplication.getContext().getString(i);
    }

    private static void setCategoryDimensions(String str, String str2, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        screenViewBuilderWrapper.setCustomDimension(10, str);
        screenViewBuilderWrapper.setCustomDimension(11, str2);
    }

    private static void setSearchLocationDimensions(String str, String str2, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        screenViewBuilderWrapper.setCustomDimension(12, str);
        screenViewBuilderWrapper.setCustomDimension(13, str2);
        screenViewBuilderWrapper.setCustomDimension(98, str2);
    }

    private static void setLocationDimensions(String str, String str2, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        screenViewBuilderWrapper.setCustomDimension(12, str);
        screenViewBuilderWrapper.setCustomDimension(13, str2);
    }

    private static void setSearchDistanceDimensions(@Nullable String str, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        screenViewBuilderWrapper.setCustomDimension(44, RadiusDecimal.getDecimalFromLiteral(str));
    }

    private static void setPriceDimensions(Search search, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        Object stringValue = search.getStringValue(StatefulActivity.NAME_MIN_PRICE);
        Object stringValue2 = search.getStringValue(StatefulActivity.NAME_MAX_PRICE);
        if (StringUtils.isNotBlank(stringValue)) {
            screenViewBuilderWrapper.setCustomDimension(100, stringValue);
        }
        if (StringUtils.isNotBlank(stringValue2)) {
            screenViewBuilderWrapper.setCustomDimension(EbkImagePickerActivity.REQUEST_CODE_IMAGE_GALLERY, stringValue2);
        }
    }

    private static void setCategoryDimensions(@Nullable String str, @Nullable String str2, EventBuilderWrapper eventBuilderWrapper) {
        eventBuilderWrapper.setCustomDimension(10, str);
        eventBuilderWrapper.setCustomDimension(11, str2);
    }

    private static void setLocationDimensions(@Nullable String str, @Nullable String str2, EventBuilderWrapper eventBuilderWrapper) {
        eventBuilderWrapper.setCustomDimension(12, str);
        eventBuilderWrapper.setCustomDimension(13, str2);
    }

    private static void setAppLocationDimensions(AppLocation appLocation, ScreenViewBuilderWrapper screenViewBuilderWrapper) {
        if (appLocation.getType().equals(LocationType.GumtreeLocation)) {
            screenViewBuilderWrapper.setCustomDimension(12, ((GumtreeLocation) appLocation).getLocationId());
            if (appLocation instanceof GumtreePostcodeLocation) {
                screenViewBuilderWrapper.setCustomDimension(45, appLocation.getName());
                return;
            } else {
                screenViewBuilderWrapper.setCustomDimension(13, appLocation.getName());
                return;
            }
        }
        screenViewBuilderWrapper.setCustomDimension(45, ((GeocoderLocation) appLocation).getPostCode());
        screenViewBuilderWrapper.setCustomDimension(46, convertLatLong((GeocoderLocation) appLocation));
    }

    private static String convertLatLong(GeocoderLocation geocoderLocation) {
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        return decimalFormat.format(geocoderLocation.getLat()) + ";" + decimalFormat.format(geocoderLocation.getLng());
    }

    public static void dbDeleteEvent(String str, long j) {
        if (GumtreeApplication.isBeta()) {
            try {
                tracker.send(new EventBuilderWrapper().setCategory("PerformanceMonitoring").setAction("db:delete-" + str).setLabel(getLabel(j)).setValue(j).build());
            } catch (Exception e) {
            }
        }
    }

    private static void performanceEvent(String str, String str2, long j) {
        if (GumtreeApplication.isBeta()) {
            String apiEndPointForPerformanceLogging = getApiEndPointForPerformanceLogging(str);
            if (apiEndPointForPerformanceLogging != null) {
                try {
                    tracker.send(new EventBuilderWrapper().setCategory("PerformanceMonitoring").setAction(str2 + ":" + apiEndPointForPerformanceLogging).setLabel(getLabel(j)).setValue(j).build());
                } catch (Exception e) {
                }
            }
        }
    }

    @Deprecated
    private static void sendGAEvent(String str, String str2) {
        sendGAEvent(str, str2, new EventBuilderWrapper());
    }

    public static void sendGAEvent(String str, String str2, String str3) {
        sendGAEvent(str, str2, str3, new EventBuilderWrapper());
    }

    @Deprecated
    public static void sendGAEvent(String str, String str2, EventBuilderWrapper eventBuilderWrapper) {
        try {
            tracker.send(eventBuilderWrapper.setCategory(str).setAction(str2).setValue(TRACKING_VALUE).setLabel(EMPTY_PARAMETERS).build());
        } catch (Exception e) {
        }
    }

    public static void sendGAEvent(String str, String str2, String str3, EventBuilderWrapper eventBuilderWrapper) {
        try {
            tracker.send(eventBuilderWrapper.setCategory(str).setAction(str2).setLabel(str3).setValue(TRACKING_VALUE).build());
        } catch (Exception e) {
        }
    }

    private static void sendSocial(String str, String str2, String str3) {
        try {
            tracker.send(new SocialBuilderWrapper().setNetwork(str).setAction(str2).setTarget(str3).build());
        } catch (Exception e) {
        }
    }

    public static void viewNotificationReceived() {
        sendNoInteraction("NotificationReceived", "AlertNotificationReceived", BuildConfig.FLAVOR);
    }

    public static void viewAlertNotificationReceived() {
        sendNoInteraction("MessageNotificationReceived", "AlertNotificationReceived", BuildConfig.FLAVOR);
    }

    public static void viewMessageNotificationReceived() {
        sendNoInteraction("MessageNotificationReceived", "MessageNotificationReceived", BuildConfig.FLAVOR);
    }

    private static void sendNoInteraction(String str, String str2, String str3) {
        try {
            tracker.send(new EventBuilderWrapper().setCategory(str).setAction(str2).setLabel(str3).setValue(TRACKING_VALUE).set("&ni", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID).build());
        } catch (Exception e) {
        }
    }

    public static void sendAdjustEvent(String str) {
        Adjust.trackEvent(new AdjustEvent(str));
    }
}
