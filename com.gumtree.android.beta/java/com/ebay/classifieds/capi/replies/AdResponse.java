package com.ebay.classifieds.capi.replies;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "reply-to-ad", strict = false)
@Namespace(prefix = "reply", reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
public class AdResponse {
    @Element(name = "ad-id", required = false)
    private long adId;
    @Element(name = "hashed-user-email-hex", required = false)
    private String hashedUserEmail;
    @Attribute(name = "locale")
    private String locale;
    @Element(name = "opt-in-marketing", required = false)
    private boolean marketing;
    @Element(name = "reply-message", required = false)
    private String replyMessage;
    @Element(name = "username", required = false)
    private String username;
    @Attribute(name = "version")
    private String version;

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public void setAdId(long j) {
        this.adId = j;
    }

    public void setReplyMessage(String str) {
        this.replyMessage = str;
    }

    public void setMarketing(boolean z) {
        this.marketing = z;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public void setHashedUserEmail(String str) {
        this.hashedUserEmail = str;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getVersion() {
        return this.version;
    }

    public long getAdId() {
        return this.adId;
    }

    public String getReplyMessage() {
        return this.replyMessage;
    }

    public boolean isMarketing() {
        return this.marketing;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHashedUserEmail() {
        return this.hashedUserEmail;
    }
}
