package com.ebay.classifieds.capi.replies;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "reply-to-ad-conversation", strict = false)
@Namespace(prefix = "reply", reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
public class ConversationResponse {
    @Element(name = "ad-id", required = false)
    private long adId;
    @Element(name = "conversation-creation-id", required = false)
    private String conversationCreationId;
    @Element(name = "conversation-id", required = false)
    private long conversationId;
    @Attribute(name = "locale")
    private String locale;
    @Element(name = "reply-message", required = false)
    private String replyMessage;
    @Attribute(name = "version")
    private String version;

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public long getConversationId() {
        return this.conversationId;
    }

    public String getConversationCreationId() {
        return this.conversationCreationId;
    }

    public long getAdId() {
        return this.adId;
    }

    public String getReplyMessage() {
        return this.replyMessage;
    }
}
