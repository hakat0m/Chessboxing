package com.ebay.classifieds.capi.replies;

import java.io.Serializable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "reply-direction", strict = false)
public class ReplyDirection implements Serializable {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value", required = false)
    private ReplyTypes types;

    public void setType(ReplyTypes replyTypes) {
        this.types = replyTypes;
    }
}
