package com.gumtree.android.ads.parser;

import com.gumtree.android.ads.PostAdResult;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.common.utils.XmlStringUtils;
import com.gumtree.android.conversations.parser.ReplyConversationResponseParser;
import java.io.InputStream;

public class PostAdResponseParser implements Parser<PostAdResult> {
    private static final String AD_AD = "<ad:ad";
    private static final String ID = "id=\"";
    private static final String START_MESSAGE = "<message>";

    public PostAdResult parse(InputStream inputStream) {
        PostAdResult postAdResult = new PostAdResult();
        String xmlStringUtils = XmlStringUtils.toString(inputStream);
        if (inputStream == null || xmlStringUtils == null) {
            postAdResult.setError(ReplyConversationResponseParser.NULL_SERVER_RESPONSE);
        } else if (isInternalServerError(xmlStringUtils)) {
            postAdResult.setError(ReplyConversationResponseParser.INTERNAL_SERVER_ERROR);
        } else if (xmlStringUtils.contains("api-base-error")) {
            postAdResult.setError(getError(xmlStringUtils));
        } else if (xmlStringUtils.contains(AD_AD)) {
            postAdResult.setAdId(getAdId(xmlStringUtils));
        } else {
            postAdResult.setError(ReplyConversationResponseParser.UNKNOWN_ERROR);
        }
        return postAdResult;
    }

    private String getAdId(String str) {
        int indexOf = str.indexOf(AD_AD);
        String substring = str.substring(indexOf, str.indexOf(">", indexOf));
        int indexOf2 = substring.indexOf(ID) + ID.length();
        return substring.substring(indexOf2, substring.indexOf("\"", indexOf2));
    }

    private boolean isInternalServerError(String str) {
        return str.contains("http-status-code=\"500\"");
    }

    private String getError(String str) {
        String firstMessage = getFirstMessage(str, 0);
        if ("An unexpected error has occurred".equals(firstMessage)) {
            String fieldMessage = getFieldMessage(str);
            if (fieldMessage != null && fieldMessage.length() > 0) {
                return fieldMessage;
            }
        }
        return firstMessage;
    }

    private String getFieldMessage(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return getFirstMessage(str, str.indexOf("<api-field-errors>"));
    }

    private String getFirstMessage(String str, int i) {
        int indexOf = str.indexOf(START_MESSAGE, i);
        int indexOf2 = str.indexOf("</message>", indexOf);
        if (indexOf == -1 || indexOf2 == -1) {
            return null;
        }
        return str.substring(START_MESSAGE.length() + indexOf, indexOf2);
    }
}
