package com.gumtree.android.vip.reply.api;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.ebay.classifieds.capi.replies.AdResponse;
import com.ebay.classifieds.capi.replies.ConversationResponse;
import com.ebay.classifieds.capi.replies.CreateConversation;
import com.ebay.classifieds.capi.replies.RepliesApi;
import com.ebay.classifieds.capi.replies.ReplyTypes;
import com.gumtree.Log;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.reply.ReplyMetadataField;
import com.gumtree.android.reply.parser.ReplyToAdvertXmlBuilder;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import retrofit.mime.TypedByteArray;

public class SendReplyRequest implements Request<AdResponse> {
    private static final String ONE = "1";
    @Inject
    BaseAccountManager accountManager;
    private final long adId;
    private final String adType;
    private final String categoryId;
    @Inject
    Context context;
    private final List<ReplyMetadataField> fields;
    @Named("xmlClient")
    @Inject
    ICapiClient iCapiClient;
    private final boolean isListSearch;
    private final boolean isNearBySearch;

    public SendReplyRequest(long j, String str, String str2, boolean z, boolean z2, List<ReplyMetadataField> list) {
        ComponentsManager.get().getAppComponent().inject(this);
        this.adId = j;
        this.fields = list;
        this.adType = str;
        this.categoryId = str2;
        this.isNearBySearch = z;
        this.isListSearch = z2;
    }

    public Result<AdResponse> execute() {
        Result<AdResponse> result = new Result(ResultError.unknown());
        try {
            Result<AdResponse> result2;
            Object adResponseFromConversation;
            if (this.accountManager.isUserLoggedIn()) {
                adResponseFromConversation = getAdResponseFromConversation();
                sendAnalyticsForLoggedInUser();
                result2 = new Result(adResponseFromConversation);
            } else {
                adResponseFromConversation = getAdResponse();
                PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString("pref_hashed_email", adResponseFromConversation.getHashedUserEmail()).apply();
                Track.eventReplyVipEmailSuccess(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
                result2 = new Result(adResponseFromConversation);
            }
            if (!result2.hasError()) {
                return result2;
            }
            Track.eventReplyVipEmailFail(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
            return result2;
        } catch (UnsupportedEncodingException e) {
            Track.eventReplyVipEmailFail(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
            return result;
        } catch (NullPointerException e2) {
            Track.eventReplyVipEmailFail(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
            return result;
        } catch (Throwable e3) {
            if (e3.getCause() instanceof ApiException) {
                Log.e(getClass().getSimpleName(), "Runtime exception from rx.toBlocking", e3);
                return new Result((ApiException) e3.getCause());
            }
            throw e3;
        }
    }

    private void sendAnalyticsForLoggedInUser() {
        String field = getField("reply-stored-cv-id");
        if (hasCv(field) && ONE.equals(isCVChecked(field))) {
            Track.eventReplyWithCV(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
        } else {
            Track.eventReplyWithCV(this.categoryId, this.adId, this.isNearBySearch, this.isListSearch);
        }
    }

    @NonNull
    private AdResponse getAdResponseFromConversation() {
        ConversationResponse conversationResponse = getConversationResponse();
        AdResponse adResponse = new AdResponse();
        adResponse.setAdId(conversationResponse.getAdId());
        adResponse.setReplyMessage(conversationResponse.getReplyMessage());
        adResponse.setLocale(conversationResponse.getLocale());
        adResponse.setVersion("1.15");
        adResponse.setHashedUserEmail(this.accountManager.getHashedEmail());
        return adResponse;
    }

    private ConversationResponse getConversationResponse() {
        CreateConversation createConversation = new CreateConversation();
        createConversation.setType(ReplyTypes.TO_OWNER);
        createConversation.setAdId(this.adId);
        createConversation.setReplyMessage(getField("reply-message"));
        createConversation.setReplyUsername(getField("username"));
        createConversation.setReplyEmail(getField("reply-from-email"));
        createConversation.setReplyPhone(getField("phone"));
        String field = getField("reply-stored-cv-id");
        if (hasCv(field)) {
            createConversation.setReplyStoredCv(isCVChecked(field));
        }
        return (ConversationResponse) ((RepliesApi) this.iCapiClient.api(RepliesApi.class)).replyToConversation(this.adType, createConversation).toBlocking().first();
    }

    private boolean hasCv(String str) {
        return !TextUtils.isEmpty(str);
    }

    private String isCVChecked(String str) {
        String str2 = BuildConfig.FLAVOR;
        if ("true".equals(str.toLowerCase())) {
            return ONE;
        }
        return str2;
    }

    private String getField(String str) {
        for (ReplyMetadataField replyMetadataField : this.fields) {
            if (replyMetadataField.getId().equals(str)) {
                return replyMetadataField.getValue();
            }
        }
        return BuildConfig.FLAVOR;
    }

    private AdResponse getAdResponse() throws UnsupportedEncodingException {
        ReplyToAdvertXmlBuilder replyToAdvertXmlBuilder = new ReplyToAdvertXmlBuilder();
        replyToAdvertXmlBuilder.setAdId(String.valueOf(this.adId));
        replyToAdvertXmlBuilder.setReplyFields(this.fields);
        return (AdResponse) ((RepliesApi) this.iCapiClient.api(RepliesApi.class)).replyToAd(this.adType, new TypedByteArray("application/xml", replyToAdvertXmlBuilder.build().getBytes(Constants.ENCODING))).toBlocking().first();
    }
}
