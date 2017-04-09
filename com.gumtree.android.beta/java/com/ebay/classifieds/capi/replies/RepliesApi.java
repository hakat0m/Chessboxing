package com.ebay.classifieds.capi.replies;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedInput;
import rx.Observable;

public interface RepliesApi {
    public static final String NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/reply/v1";

    @POST("/replies/reply-to-ad")
    Observable<AdResponse> replyToAd(@Header("X-ECG-AD-TYPE") String str, @Body TypedInput typedInput);

    @POST("/replies/reply-to-ad-conversation")
    Observable<ConversationResponse> replyToConversation(@Header("X-ECG-AD-TYPE") String str, @Body CreateConversation createConversation);
}
