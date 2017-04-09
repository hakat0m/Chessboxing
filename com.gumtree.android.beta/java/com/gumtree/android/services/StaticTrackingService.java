package com.gumtree.android.services;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.apptentive.android.sdk.Apptentive;
import com.gumtree.andorid.ad.search.services.refine.TrackingRefinePanelService;
import com.gumtree.android.ad.search.services.suggestions.TrackingSearchKeywordSuggestionsService;
import com.gumtree.android.ad.treebay.TreebayCustomDimensionData;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.api.treebay.ItemSummary;
import com.gumtree.android.api.treebay.TreebayAd;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.forgotpassword.services.TrackingForgotPasswordService;
import com.gumtree.android.auth.login.services.TrackingLoginService;
import com.gumtree.android.auth.registration.services.TrackingRegistrationService;
import com.gumtree.android.auth.resetpassword.services.TrackingResetPasswordService;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.analytics.Track.EventBuilderWrapper;
import com.gumtree.android.common.analytics.Track.PromotionWrapper;
import com.gumtree.android.common.analytics.Track.ScreenViewBuilderWrapper;
import com.gumtree.android.deeplinking.activate.TrackingActivateService;
import com.gumtree.android.deeplinking.postad.TrackingPostAdDeepLinkingService;
import com.gumtree.android.location.services.TrackingLocationService;
import com.gumtree.android.manageads.services.TrackingManageAdsService;
import com.gumtree.android.metadata.model.DraftAdMetadataAttribute;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftAdAttribute;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.PostAdImage;
import com.gumtree.android.postad.promote.PromotionFeature;
import com.gumtree.android.postad.services.TrackingPostAdService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import javax.inject.Inject;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class StaticTrackingService implements TrackingRefinePanelService, TrackingSearchKeywordSuggestionsService, TrackingTreebayService, TrackingForgotPasswordService, TrackingLoginService, TrackingRegistrationService, TrackingResetPasswordService, TrackingActivateService, TrackingPostAdDeepLinkingService, TrackingLocationService, TrackingManageAdsService, TrackingPostAdService {
    private static final String EMPTY = "";
    private static final String EQUALS = "=";
    private static final String NO = "N";
    private static final int NUMBER_OF_CATEGORY_PARENTS = 4;
    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_COMA = ",";
    private static final String YES = "Y";
    private Context appContext;
    private final BaseAccountManager baseAccountManager;
    private HashSet trackedTreebayImpressions = new HashSet();
    private TreebayCustomDimensionData treebayCustomDimensionData = TreebayCustomDimensionData.builder().build();

    @Inject
    public StaticTrackingService(@NonNull Context context, @NonNull BaseAccountManager baseAccountManager) {
        this.appContext = (Context) Validate.notNull(context);
        this.baseAccountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
        Track.prepare((Application) context.getApplicationContext());
    }

    private static String[] processCategoriesTree(DraftCategory draftCategory) {
        String[] strArr = new String[NUMBER_OF_CATEGORY_PARENTS];
        if (draftCategory.getPath() != null) {
            String[] split = draftCategory.getPath().split(SEPARATOR);
            int i = 0;
            while (split.length > i && i < NUMBER_OF_CATEGORY_PARENTS) {
                strArr[i] = split[i];
                i++;
            }
        }
        return strArr;
    }

    private static void eventFeaturePaymentAttempt(String str, @Nullable Iterable<DraftFeature> iterable) {
        Track.sendGAEvent("OrderReview", "FeatureAdAttempt", "adID=" + str + SEPARATOR + featureLabelBuilder(iterable));
    }

    private static String featureLabelBuilder(@Nullable Iterable<DraftFeature> iterable) {
        String str = EMPTY;
        if (iterable == null) {
            return str;
        }
        int i = 1;
        String str2 = str;
        for (DraftFeature identifier : iterable) {
            str = str2 + "ftr" + i + EQUALS + identifier.getIdentifier();
            i++;
            str2 = str + SEPARATOR_COMA;
        }
        return str2;
    }

    private static String bounceLabelBuilder(DraftAd draftAd) {
        if (draftAd != null) {
            String str = (draftAd.getTitle() == null || draftAd.getTitle().isEmpty()) ? NO : YES;
            String str2 = (draftAd.getDescription() == null || draftAd.getDescription().isEmpty()) ? NO : YES;
            String str3 = draftAd.getCategory() != null ? YES : NO;
            String id = draftAd.getCategory() != null ? draftAd.getCategory().getId() : EMPTY;
            String categoryRequiredInfoCompleted = categoryRequiredInfoCompleted(draftAd);
            String categoryOptionalInfoCompleted = categoryOptionalInfoCompleted(draftAd);
            return String.format("ScreenCurrent=%s;Title=%s;Description=%s;Category=%s;CategoryID=%s;CategoryRequiredInfo=%s;CategoryOptionalInfo=%s;", new Object[]{"PostAdSummary", str, str2, str3, id, categoryRequiredInfoCompleted, categoryOptionalInfoCompleted});
        }
        return String.format("ScreenCurrent=%s;", new Object[]{"PostAdSummary"});
    }

    private static String categoryRequiredInfoCompleted(@NonNull DraftAd draftAd) {
        List<DraftAdMetadataAttribute> metadataAttributes = draftAd.getMetadataAttributes();
        Collection attributesValues = draftAd.getAttributesValues();
        if (metadataAttributes != null) {
            int i = 0;
            for (DraftAdMetadataAttribute draftAdMetadataAttribute : metadataAttributes) {
                if (draftAdMetadataAttribute.isRequired()) {
                    i = 1;
                    if (attributesValues != null) {
                        List select = ListUtils.select(attributesValues, StaticTrackingService$$Lambda$1.lambdaFactory$(draftAdMetadataAttribute));
                        if (!(select.isEmpty() || StringUtils.isEmpty(((DraftAdAttribute) select.get(0)).getValue()))) {
                            return YES;
                        }
                    }
                    continue;
                }
                i = i;
            }
            if (i != 0) {
                return NO;
            }
        }
        return EMPTY;
    }

    private static String categoryOptionalInfoCompleted(@NonNull DraftAd draftAd) {
        List<DraftAdMetadataAttribute> metadataAttributes = draftAd.getMetadataAttributes();
        Collection attributesValues = draftAd.getAttributesValues();
        if (metadataAttributes != null) {
            int i = 0;
            for (DraftAdMetadataAttribute draftAdMetadataAttribute : metadataAttributes) {
                if (!draftAdMetadataAttribute.isRequired()) {
                    i = 1;
                    if (attributesValues != null) {
                        List select = ListUtils.select(attributesValues, StaticTrackingService$$Lambda$2.lambdaFactory$(draftAdMetadataAttribute));
                        if (!(select.isEmpty() || StringUtils.isEmpty(((DraftAdAttribute) select.get(0)).getValue()))) {
                            return YES;
                        }
                    }
                    continue;
                }
                i = i;
            }
            if (i != 0) {
                return NO;
            }
        }
        return EMPTY;
    }

    public void eventPostAdScreenBegin(@NonNull DraftAd draftAd) {
        if (draftAd.isNewAd()) {
            Track.sendGAEvent(getPostAdView(draftAd), "PostAdScreenBegin", Track.EMPTY_PARAMETERS);
        } else {
            Track.sendGAEvent(getPostAdView(draftAd), "EditAdScreenBegin", Track.EMPTY_PARAMETERS + draftAd.getId(), new EventBuilderWrapper());
        }
    }

    public void eventPostAdScreenResume(@NonNull DraftAd draftAd) {
        if (draftAd.isNewAd()) {
            Track.sendGAEvent(getPostAdView(draftAd), "PostAdScreenResume", Track.EMPTY_PARAMETERS);
        } else {
            Track.sendGAEvent(getPostAdView(draftAd), "EditAdScreenBegin", Track.EMPTY_PARAMETERS);
        }
    }

    public void eventPostAdSuccess(String str, DraftAd draftAd) {
        String str2;
        boolean hasInsertionFee = draftAd.hasInsertionFee();
        if (draftAd.isNewAd()) {
            str2 = hasInsertionFee ? "PostAdPaidSuccess" : "PostAdFreeSuccess";
            Apptentive.engage(this.appContext, "PostAdSuccess");
            if (str2.equals("PostAdPaidSuccess")) {
                Track.sendAdjustEvent("m3hl7o");
            } else {
                Track.sendAdjustEvent("tkeg2t");
            }
        } else {
            str2 = hasInsertionFee ? "EditAdPaidSuccess" : "EditAdFreeSuccess";
            Apptentive.engage(this.appContext, "EditAdSuccess");
        }
        String postAdView = getPostAdView(draftAd);
        Track.sendGAEvent(postAdView, str2, buildAdvancedParameters(str, postAdView, draftAd));
    }

    public void eventPostAdFail(DraftAd draftAd) {
        boolean hasInsertionFee = draftAd.hasInsertionFee();
        String str = draftAd.isNewAd() ? hasInsertionFee ? "PostAdPaidFail" : "PostAdFreeFail" : hasInsertionFee ? "EditAdPaidFail" : "EditAdFreeFail";
        Track.sendGAEvent(getPostAdView(draftAd), str, buildParameters(draftAd));
    }

    public void eventPostAdAttempt(DraftAd draftAd) {
        boolean hasInsertionFee = draftAd.hasInsertionFee();
        String str = draftAd.isNewAd() ? hasInsertionFee ? "PostAdPaidAttempt" : "PostAdFreeAttempt" : hasInsertionFee ? "EditAdPaidAttempt" : "EditAdFreeAttempt";
        Track.sendGAEvent(getPostAdView(draftAd), str, buildParameters(draftAd));
    }

    public void eventPostAdBounce(DraftAd draftAd) {
        Apptentive.engage(this.appContext, "PostAdBounce");
        Track.sendGAEvent("PostAdSummary", "PostAdBounce", bounceLabelBuilder(draftAd));
    }

    public void eventAddImageBegin(@NonNull DraftAd draftAd) {
        Track.sendGAEvent("PostAdSummary", "AddImageBegin", Track.EMPTY_PARAMETERS + draftAd.getId());
    }

    public void eventFeatureAdAttempt(String str, List<PromotionFeature> list) {
        eventFeaturePaymentAttempt(str, IterableUtils.transformedIterable(list, StaticTrackingService$$Lambda$3.lambdaFactory$()));
    }

    public void eventFeatureAdSuccess(String str) {
        Track.eventFeaturePaymentSuccess(str);
    }

    public void eventFeatureAdCancel(String str) {
        Track.eventFeaturePaymentCancel(str);
    }

    public void eventFeatureAdFailure(String str) {
        Track.eventFeaturePaymentFail(str);
    }

    public void eventPostAdReset() {
        Track.sendGAEvent("PostAdSummary", "PostAdReset", Track.EMPTY_PARAMETERS);
    }

    public void eventAddImage(List<PostAdImage> list) {
        Track.sendGAEvent("PostAdSummary", list.isEmpty() ? "AddImageFail" : "AdImageSuccess", Track.EMPTY_PARAMETERS);
    }

    public void eventPostAdStepPromote(List<PromotionFeature> list) {
        if (!list.isEmpty()) {
            Track.sendGAEvent("PostAdSummary", "PostAdStepPromote", featureLabelBuilder(IterableUtils.transformedIterable(list, StaticTrackingService$$Lambda$4.lambdaFactory$())));
        }
    }

    public void eventPriceValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepPrice", Track.EMPTY_PARAMETERS);
    }

    public void eventTitleValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepTitle", Track.EMPTY_PARAMETERS);
    }

    public void eventDescriptionValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepDescription", Track.EMPTY_PARAMETERS);
    }

    public void eventCategoryValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepCategory", Track.EMPTY_PARAMETERS);
    }

    public void eventAttributesValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepAttributes", Track.EMPTY_PARAMETERS);
    }

    public void eventContactValidated() {
        Track.sendGAEvent("PostAdSummary", "PostAdStepContact", Track.EMPTY_PARAMETERS);
    }

    public void eventLocationCurrentSelected() {
        Track.sendGAEvent("PostAdContact", "LocationCurrentSelected", Track.EMPTY_PARAMETERS);
    }

    public void eventVRMFindAttempt() {
        Track.sendGAEvent("PostAdDetails", "VRMFindAttempt", Track.EMPTY_PARAMETERS);
    }

    public void eventVRMFindSuccess() {
        Track.sendGAEvent("PostAdDetails", "VRMFindSuccess", Track.EMPTY_PARAMETERS);
    }

    public void eventVRMFindFail() {
        Track.sendGAEvent("PostAdDetails", "VRMFindFail", Track.EMPTY_PARAMETERS);
    }

    public void eventLoginCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "LoginCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginAttempt(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        eventBuilderWrapper.setCustomDimension(19, str);
        Track.sendGAEvent("Login", "LoginAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginSuccess(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(19, str);
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "LoginSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginFail(String str, String str2) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        eventBuilderWrapper.setCustomDimension(19, str);
        Track.sendGAEvent("Login", "LoginFail", "catID=0;locID=;adID=," + str2, eventBuilderWrapper);
    }

    public void eventSmartLockLoginCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SplashScreen");
        eventBuilderWrapper.setCustomDimension(1, "SplashScreen");
        Track.sendGAEvent("SplashScreen", "LoginCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSmartLockLoginAttempt(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SplashScreen");
        eventBuilderWrapper.setCustomDimension(1, "SplashScreen");
        eventBuilderWrapper.setCustomDimension(19, str);
        Track.sendGAEvent("SplashScreen", "LoginAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSmartLockLoginSuccess(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SplashScreen");
        eventBuilderWrapper.setCustomDimension(19, str);
        eventBuilderWrapper.setCustomDimension(1, "SplashScreen");
        Track.sendGAEvent("SplashScreen", "LoginSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSmartLockLoginFail(String str, String str2) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SplashScreen");
        eventBuilderWrapper.setCustomDimension(1, "SplashScreen");
        eventBuilderWrapper.setCustomDimension(19, str);
        Track.sendGAEvent("SplashScreen", "LoginFail", "catID=0;locID=;adID=," + str2, eventBuilderWrapper);
    }

    public void eventLoginScreenBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "LoginScreenBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginScreenCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "LoginScreenCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromPostAd() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("PostAdSummary", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromManageAds() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("MyAds", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromSettings() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Settings", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromSavedSearches() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("SavedSearches", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromFavourites() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("MySavedAds", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromMessages() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("MessageCenter", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromActivateLink() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromSearch() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("SavedSearches", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromResetPassword() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ResetPassword", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventLoginBeginFromTokenExpired() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("401 error", "LoginBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void viewRegistration() {
        Track.sendView("UserRegistrationForm");
    }

    public void eventRegistrationBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationForm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationForm");
        Track.sendGAEvent("UserRegistrationForm", "UserRegistrationBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventRegistrationAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationForm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationForm");
        Track.sendGAEvent("UserRegistrationForm", "UserRegistrationAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventRegistrationFail(String str) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationForm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationForm");
        Track.sendGAEvent("UserRegistrationForm", "UserRegistrationFail", "catID=0;locID=;adID=," + str, eventBuilderWrapper);
    }

    public void eventRegistrationPasswordFail(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        if (passwordStrengthService$PasswordStrength.isWeak()) {
            eventRegistrationFail("PasswordWeak");
        } else if (passwordStrengthService$PasswordStrength.isVeryWeak()) {
            eventRegistrationFail("PasswordVeryWeak");
        } else if (passwordStrengthService$PasswordStrength.isTooLong()) {
            eventRegistrationFail("PasswordTooLong");
        } else {
            eventRegistrationFail("PasswordInvalid");
        }
    }

    public void eventRegistrationSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationForm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationForm");
        Track.sendGAEvent("UserRegistrationForm", "UserRegistrationSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void viewRegistrationConfirm() {
        Track.sendView("UserRegistrationConfirm");
    }

    public void eventRegistrationRetry() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationConfirm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationConfirm");
        Track.sendGAEvent("UserRegistrationConfirm", "UserRegistrationRetry", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventRegistrationCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("UserRegistrationForm");
        eventBuilderWrapper.setCustomDimension(11, "UserRegistrationForm");
        Track.sendGAEvent("UserRegistrationForm", "UserRegistrationCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void passwordResetBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "PasswordResetBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void passwordResetAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("ResetPassword");
        eventBuilderWrapper.setCustomDimension(1, "ResetPassword");
        Track.sendGAEvent("ResetPassword", "PasswordResetAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void passwordResetFail() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("ResetPassword");
        eventBuilderWrapper.setCustomDimension(1, "ResetPassword");
        Track.sendGAEvent("ResetPassword", "PasswordResetFail", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void passwordResetSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("ResetPassword");
        eventBuilderWrapper.setCustomDimension(1, "ResetPassword");
        Track.sendGAEvent("ResetPassword", "PasswordResetSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void passwordResetCancel() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCustomDimension(1, "ResetPassword");
        eventBuilderWrapper.setCategory("ResetPassword");
        Track.sendGAEvent("ResetPassword", "PasswordResetCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void viewPasswordResetConfirm() {
        Track.sendView("ResetPasswordConfirm");
    }

    public void createPasswordBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("CreatePassword");
        eventBuilderWrapper.setCustomDimension(1, "CreatePassword");
        Track.sendGAEvent("CreatePassword", "CreatePasswordBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void createPasswordAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("CreatePassword");
        eventBuilderWrapper.setCustomDimension(1, "CreatePassword");
        Track.sendGAEvent("CreatePassword", "CreatePasswordAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void createPasswordFail() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("CreatePassword");
        eventBuilderWrapper.setCustomDimension(1, "CreatePassword");
        Track.sendGAEvent("CreatePassword", "CreatePasswordFail", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void createPasswordSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("CreatePassword");
        eventBuilderWrapper.setCustomDimension(1, "CreatePassword");
        Track.sendGAEvent("CreatePassword", "CreatePasswordSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventActivateAccountBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "ActivateAccountBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventActivateAccountAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "ActivateAccountAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventActivateAccountFail() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "ActivateAccountFail", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventActivateAccountSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "ActivateAccountSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSaveSmartLockBegin() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "SaveSmartLockBegin", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSaveSmartLockSuccess() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "SaveSmartLockSuccess", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSaveSmartLockCanceled() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "SaveSmartLockCancel", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventSaveSmartLockFailure() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "SaveSmartLockFail", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void screenViewSplashScreen() {
        Track.viewSplashScreen();
    }

    public void eventSaveSmartLockAttempt() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("Login", "SaveSmartLockAttempt", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventActivateAccountLoggedInUser() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("Login");
        eventBuilderWrapper.setCustomDimension(1, "Login");
        Track.sendGAEvent("ActivateLink", "ActivateAccountLoggedInUser", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventPostAdBeginFromDeepLink() {
        Track.sendGAEvent("DeepLink", "PostAdBegin", Track.EMPTY_PARAMETERS);
    }

    public void eventTreebayResultsImpressions(@Nullable ItemSummary[] itemSummaryArr, Queue<Integer> queue) {
        List<String> arrayList = new ArrayList();
        if (itemSummaryArr != null) {
            if (itemSummaryArr.length >= 2) {
                arrayList.add(itemSummaryArr[0].getItemId());
                arrayList.add(itemSummaryArr[1].getItemId());
            } else if (itemSummaryArr.length == 1) {
                arrayList.add(itemSummaryArr[0].getItemId());
            }
            EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
            PromotionWrapper promotionWrapper = null;
            for (String str : arrayList) {
                if (!this.trackedTreebayImpressions.contains(str)) {
                    promotionWrapper = new PromotionWrapper().setId(str).setName("partner_ebay").setPosition(Integer.toString(((Integer) queue.poll()).intValue()));
                    eventBuilderWrapper.addPromotion(promotionWrapper);
                    this.trackedTreebayImpressions.add(str);
                }
                promotionWrapper = promotionWrapper;
            }
            if (promotionWrapper != null) {
                Track.setStandardSearchDimensions(this.treebayCustomDimensionData, eventBuilderWrapper);
                Track.sendGAEvent("ResultsSearch", "ResultsImpressions", getTreebayLabel(null), eventBuilderWrapper);
            }
        }
    }

    public void eventTreebayResultsAdClick(String str, int i) {
        EventBuilderWrapper promotionAction = new EventBuilderWrapper().addPromotion(new PromotionWrapper().setId(str).setName("partner_ebay").setPosition(Integer.toString(i))).setPromotionAction("click");
        Track.setStandardSearchDimensions(this.treebayCustomDimensionData, promotionAction);
        Track.sendGAEvent("ResultsSearch", "ResultsAdClick", getTreebayLabel(str), promotionAction);
    }

    public void eventTreebayR2SExternalBegin(TreebayAd treebayAd) {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        Track.setStandardSearchDimensions(this.treebayCustomDimensionData, eventBuilderWrapper);
        Track.sendGAEvent("pVIP", "R2SExternalBegin", getTreebayLabel(treebayAd.getItemId()), eventBuilderWrapper);
    }

    public void screenViewPVip(String str) {
        Track.viewPVIP(str);
    }

    public void clearTreebayTrackedImpressions() {
        this.trackedTreebayImpressions.clear();
    }

    private String getPostAdView(DraftAd draftAd) {
        return draftAd.isNewAd() ? "PostAdSummary" : "EditAdSummary";
    }

    private String buildParameters(DraftAd draftAd) {
        return Track.buildParameters(draftAd.getId(), draftAd.getCategory().getId(), Long.toString(draftAd.getLocation().getId()));
    }

    private String buildAdvancedParameters(String str, String str2, DraftAd draftAd) {
        String[] processCategoriesTree = processCategoriesTree(draftAd.getCategory());
        String id = draftAd.getCategory().getId();
        String localisedName = draftAd.getCategory().getLocalisedName();
        long id2 = draftAd.getLocation().getId();
        String name = draftAd.getLocation().getName();
        String hashedEmail = this.baseAccountManager.getHashedEmail();
        String price = draftAd.getPrice();
        List postAdImages = draftAd.getPostAdImages();
        int size = postAdImages != null ? postAdImages.size() : 0;
        return String.format("cd1=%s;cd2=%s;cd3=%s;cd4=%s;cd5=%s;cd10=%s;cd11=%s;cd12=%d;cd13=%s;cd21=%s;cd23=%s;cd30=%s;cd31=%s;cd32=%s;cd33=%d", new Object[]{str2, processCategoriesTree[0], processCategoriesTree[1], processCategoriesTree[2], processCategoriesTree[3], id, localisedName, Long.valueOf(id2), name, hashedEmail, "true", str, price, "fixed", Integer.valueOf(size)});
    }

    private String getTreebayLabel(String str) {
        if (StringUtils.isEmpty(str)) {
            return String.format("partner%seBay%s", new Object[]{EQUALS, SEPARATOR});
        }
        Object[] objArr = new Object[NUMBER_OF_CATEGORY_PARENTS];
        objArr[0] = EQUALS;
        objArr[1] = SEPARATOR;
        objArr[2] = EQUALS;
        objArr[3] = str;
        return String.format("partner%seBay%spartneradid%s%s", objArr);
    }

    public void setTreebayCustomDimensionData(TreebayCustomDimensionData treebayCustomDimensionData) {
        this.treebayCustomDimensionData = treebayCustomDimensionData;
    }

    public void eventPostAdBeginFromHeader() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("ManageAds");
        eventBuilderWrapper.setLabel("Header");
        Track.sendGAEvent("MyAdsMain", "PostAdBegin", "Header", eventBuilderWrapper);
    }

    public void eventPostAdBeginFromEmptyPage() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("ManageAds");
        eventBuilderWrapper.setLabel("EmptyStatePost");
        Track.sendGAEvent("MyAdsMain", "PostAdBegin", "EmptyStatePost", eventBuilderWrapper);
    }

    public void eventPreviewAdBegin() {
        Track.sendGAEvent("MyAdsMain", "PreviewAd", EMPTY);
    }

    public void eventEditAdBegin() {
        Track.sendGAEvent("MyAdsMain", "EditAdBegin", EMPTY);
    }

    public void eventRemovedCSLink() {
        Track.sendGAEvent("MyAdsMain", "RemovedAdCSLink", EMPTY);
    }

    public void eventNeedsEditingCSLink() {
        Track.sendGAEvent("MyAdsMain", "NeedsEditingCSLink", EMPTY);
    }

    public void eventAdStatusInfoPressed(String str) {
        Track.sendGAEvent("MyAdsMain", "MyAdsStatusInfo", str);
    }

    public void eventShowActiveFragment() {
        Track.sendGAEvent("MyAdsMain", "MA_tab_Active", EMPTY);
    }

    public void eventShowInactiveFragment() {
        Track.sendGAEvent("MyAdsMain", "MA_tab_Inactive", EMPTY);
    }

    public void eventManageAdsBegin() {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(1, "MyAds");
        Track.sendView("MyAdsMain", screenViewBuilderWrapper);
    }

    public void eventDeleteAdBegin() {
        Track.sendGAEvent("MyAdsMain", "DeleteAdBegin", EMPTY);
    }

    public void eventDeleteAdSuccess() {
        Track.sendGAEvent("MyAdsMain", "DeleteAdSuccess", EMPTY);
    }

    public void eventDeleteAdFail(String str) {
        Track.sendGAEvent("MyAdsMain", "DeleteAdFail", str);
    }

    public void eventDeleteAdCancel() {
        Track.sendGAEvent("MyAdsMain", "DeleteAdCancel", EMPTY);
    }

    public void eventKeywordSelected() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SearchKeyword");
        Track.sendGAEvent("SearchKeyword", "KeywordSelected", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void eventKeywordEntered() {
        EventBuilderWrapper eventBuilderWrapper = new EventBuilderWrapper();
        eventBuilderWrapper.setCategory("SearchKeyword");
        Track.sendGAEvent("SearchKeyword", "KeywordEntered", Track.EMPTY_PARAMETERS, eventBuilderWrapper);
    }

    public void screenSearchKeyword() {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(1, "SearchKeyword");
        Track.sendView("SearchKeyword", screenViewBuilderWrapper);
    }

    public void refinePanelOpened() {
        ScreenViewBuilderWrapper screenViewBuilderWrapper = new ScreenViewBuilderWrapper();
        screenViewBuilderWrapper.setCustomDimension(1, "SearchSetup");
        Track.sendView("SearchSetup", screenViewBuilderWrapper);
    }
}
