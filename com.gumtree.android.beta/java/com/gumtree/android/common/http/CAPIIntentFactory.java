package com.gumtree.android.common.http;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.reply.ReplyMetadataField;
import com.gumtree.android.reply.parser.ReplyToAdvertXmlBuilder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;

public abstract class CAPIIntentFactory {
    private static final SimpleDateFormat API_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final String APPLICATION_XML = "application/xml";
    private static final String INCLUDE_TOP_ADS = "includeTopAds";
    private static final String JSON = ".json";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String SRP_REQUIRED_FIELDS = "title,description,phone,email,modification-date-time,start-date-time,category.localized_name,category.id,locations,link,pictures,id,price.amount,price-frequency,features-active,neighborhood,search-distance,ad-status";
    private static final String TRUE = "true";
    private static final String USERS_S_ADS_S_JSON = "users/%s/ads/%s.json";
    @Inject
    BaseAccountManager manager;

    public abstract String getBaseUrl();

    public abstract String getFixedPathIfAny();

    public abstract String getForgotPasswordUrl();

    public abstract Locale getLocale();

    public abstract String getRegistrationUrl();

    public abstract String getUpdateEmailUrl();

    public CAPIIntentFactory() {
        GumtreeApplication.component().inject(this);
    }

    public Intent getHttpIntentForCategories(long j, ResultReceiver resultReceiver) {
        HttpIntentBuilder timestamp = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath("categories/1.json").setTimestamp(j);
        addResultReceiverToIntent(resultReceiver, timestamp);
        return timestamp.build().getIntent();
    }

    public Intent getHttpIntentForAds(long j, String str, int i, Bundle bundle, ResultReceiver resultReceiver, String str2) {
        HttpIntentBuilder path = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath("ads.json");
        addParamsForSRP(bundle, path);
        path.addRequestParameter("_in", SRP_REQUIRED_FIELDS);
        path.addRequestParameter(PAGE, str).addRequestParameter(SIZE, BuildConfig.FLAVOR + i).addRequestParameter(INCLUDE_TOP_ADS, TRUE).addRequestParameter("nearby", TRUE).addRequestParameter("modBefore", getFormattedAdsModifiedBeforeDate(j)).setTrackingAutocompleteHeader(str2).setTimestamp(j);
        addResultReceiverToIntent(resultReceiver, path);
        return path.build().getIntent();
    }

    private String getFormattedAdsModifiedBeforeDate(long j) {
        return API_DATE_FORMATTER.format(new Date(j));
    }

    private void addParamsForSRP(Bundle bundle, HttpIntentBuilder httpIntentBuilder) {
        for (String str : AppUtils.getListOfKeyNames(bundle)) {
            httpIntentBuilder.addRequestParameter(str, bundle.getString(str));
        }
    }

    public Intent getHttpIntentForAd(String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder timestamp = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath("ads/" + str + JSON).addRequestParameter("_metadata", TRUE).setTimestamp(System.currentTimeMillis());
        addResultReceiverToIntent(resultReceiver, timestamp);
        return timestamp.build().getIntent();
    }

    public Intent getHttpIntentForAdStatus(String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder addRequestParameter = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath(String.format(USERS_S_ADS_S_JSON, new Object[]{this.manager.getUsername(), str})).addRequestParameter("_id", "ad-status");
        addResultReceiverToIntent(resultReceiver, addRequestParameter);
        HttpIntent build = addRequestParameter.build();
        build.setAuthorization(this.manager.getAuthentication());
        return build.getIntent();
    }

    public Intent getHttpIntentForSearchMetadata(String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder timestamp = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath("ads/search-metadata/" + str + JSON).setTimestamp(System.currentTimeMillis());
        addResultReceiverToIntent(resultReceiver, timestamp);
        return timestamp.build().getIntent();
    }

    public Intent getHttpIntentForMetadata(String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder timestamp = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath("ads/metadata/" + str + JSON).setTimestamp(System.currentTimeMillis());
        addResultReceiverToIntent(resultReceiver, timestamp);
        return timestamp.build().getIntent();
    }

    public Intent postHttpIntentForReplyToAd(long j, Collection<ReplyMetadataField> collection, String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder adType = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setRequestType(2).setPath("replies/reply-to-ad").setContentType(APPLICATION_XML).setSkipResponseParsing(true).setAdType(str);
        ReplyToAdvertXmlBuilder replyToAdvertXmlBuilder = new ReplyToAdvertXmlBuilder();
        replyToAdvertXmlBuilder.setAdId(String.valueOf(j));
        replyToAdvertXmlBuilder.setReplyFields(collection);
        adType.setPayload(replyToAdvertXmlBuilder.build());
        addResultReceiverToIntent(resultReceiver, adType);
        return adType.build().getIntent();
    }

    public Intent postHttpIntentForLogin(String str, String str2, String str3) {
        HttpIntentBuilder contentType = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setRequestType(2).setPath("users/login.json").setPayload("username=" + str + "&password=" + str2).setContentType("application/x-www-form-urlencoded");
        if (str3 != null) {
            contentType.addRequestParameter("rel", str3);
        }
        return contentType.build().getIntent();
    }

    public Intent getHttpIntentForManagedAds(String str, long j, String str2, ResultReceiver resultReceiver) {
        HttpIntentBuilder timestamp = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath(String.format("users/%s/ads.json", new Object[]{this.manager.getUsername()})).addRequestParameter(INCLUDE_TOP_ADS, TRUE).addRequestParameter(PAGE, str).addRequestParameter(SIZE, "20").setAuthorizationHeader(this.manager.getAuthentication()).setTimestamp(j);
        addCacheControl(str2, timestamp);
        addResultReceiverToIntent(resultReceiver, timestamp);
        return timestamp.build().getIntent();
    }

    private void addCacheControl(String str, HttpIntentBuilder httpIntentBuilder) {
        if (!TextUtils.isEmpty(str)) {
            httpIntentBuilder.setCacheControlProperty(str);
        }
    }

    public Intent getHttpIntentForUserAd(String str, String str2, ResultReceiver resultReceiver) {
        HttpIntentBuilder path = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setPath(String.format("users/%s/ads/%s.json?_in=title,description,email,phone,poster-contact-email,price-frequency,neighborhood,category,locations,price,pictures,attributes", new Object[]{this.manager.getUsername(), str}));
        addResultReceiverToIntent(resultReceiver, path);
        addCacheControl(str2, path);
        HttpIntent build = path.build();
        build.setAuthorization(this.manager.getAuthentication());
        return build.getIntent();
    }

    public HttpIntent getHttpIntentForAdDelete(String str, ResultReceiver resultReceiver) {
        HttpIntentBuilder skipResponseParsing = new HttpIntentBuilder().createNewHttpIntent().setBaseUrl(getBaseUrl(), getFixedPathIfAny()).setRequestType(3).setPath(String.format(USERS_S_ADS_S_JSON, new Object[]{this.manager.getUsername(), str})).setContentType(APPLICATION_XML).setSkipResponseParsing(true);
        addResultReceiverToIntent(resultReceiver, skipResponseParsing);
        HttpIntent build = skipResponseParsing.build();
        build.setAuthorization(this.manager.getAuthentication());
        return build;
    }

    void addResultReceiverToIntent(ResultReceiver resultReceiver, HttpIntentBuilder httpIntentBuilder) {
        if (resultReceiver != null) {
            httpIntentBuilder.setResultReceiver(resultReceiver);
        }
    }
}
