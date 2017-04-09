package com.ebay.classifieds.capi.replies;

import java.io.Serializable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@NamespaceList({@Namespace(prefix = "reply", reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1"), @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1"), @Namespace(prefix = "category", reference = "http://www.ebayclassifiedsgroup.com/schema/category/v1"), @Namespace(prefix = "location", reference = "http://www.ebayclassifiedsgroup.com/schema/location/v1"), @Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1"), @Namespace(prefix = "attr", reference = "http://www.ebayclassifiedsgroup.com/schema/attribute/v1"), @Namespace(prefix = "pic", reference = "http://www.ebayclassifiedsgroup.com/schema/picture/v1"), @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1"), @Namespace(prefix = "rate", reference = "http://www.ebayclassifiedsgroup.com/schema/rate/v1")})
@Root(name = "reply-to-ad-conversation", strict = false)
@Namespace(prefix = "reply", reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
public class CreateConversation implements Serializable {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "ad-id", required = false)
    private long adId;
    @Attribute(name = "locale")
    private String locale = "en-UK";
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-direction", required = false)
    private ReplyDirection replyDirection;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-email", required = false)
    private String replyEmail;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-message", required = false)
    private String replyMessage;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-phone", required = false)
    private String replyPhone;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-stored-cv-id", required = false)
    private String replyStoredCv;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/reply/v1")
    @Element(name = "reply-username", required = false)
    private String replyUsername;
    @Attribute(name = "version")
    private String version = "1.15";

    public void setReplyUsername(String str) {
        this.replyUsername = str;
    }

    public void setReplyPhone(String str) {
        this.replyPhone = str;
    }

    public void setAdId(long j) {
        this.adId = j;
    }

    public void setReplyMessage(String str) {
        this.replyMessage = str;
    }

    public void setReplyStoredCv(String str) {
        this.replyStoredCv = str;
    }

    public void setReplyEmail(String str) {
        this.replyEmail = str;
    }

    public void setType(ReplyTypes replyTypes) {
        this.replyDirection = new ReplyDirection();
        this.replyDirection.setType(replyTypes);
    }
}
