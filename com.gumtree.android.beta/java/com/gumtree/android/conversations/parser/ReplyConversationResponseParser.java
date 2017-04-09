package com.gumtree.android.conversations.parser;

import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.common.utils.XmlStringUtils;
import com.gumtree.android.conversations.ReplyConversationResponse;
import java.io.InputStream;

public class ReplyConversationResponseParser implements Parser<ReplyConversationResponse> {
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String NULL_SERVER_RESPONSE = "Response from server is null";
    public static final String UNKNOWN_ERROR = "Unknown error";

    public ReplyConversationResponse parse(InputStream inputStream) {
        ReplyConversationResponse replyConversationResponse = new ReplyConversationResponse();
        String xmlStringUtils = XmlStringUtils.toString(inputStream);
        if (inputStream == null || xmlStringUtils == null) {
            replyConversationResponse.setErrorMessage(NULL_SERVER_RESPONSE);
            replyConversationResponse.setSuccess(false);
        } else if (isInternalServerError(xmlStringUtils)) {
            replyConversationResponse.setErrorMessage(INTERNAL_SERVER_ERROR);
            replyConversationResponse.setSuccess(false);
        } else if (xmlStringUtils.contains("<reply:reply-to-ad-conversation")) {
            replyConversationResponse.setSuccess(true);
        } else {
            replyConversationResponse.setErrorMessage(UNKNOWN_ERROR);
            replyConversationResponse.setSuccess(false);
        }
        return replyConversationResponse;
    }

    private boolean isInternalServerError(String str) {
        return str.contains("http-status-code=\"500\"");
    }
}
