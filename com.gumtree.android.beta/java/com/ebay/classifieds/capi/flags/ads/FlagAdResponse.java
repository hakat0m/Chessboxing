package com.ebay.classifieds.capi.flags.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "flagAd", strict = false)
@Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
public class FlagAdResponse {
    @Element(name = "comment", required = false)
    private String comment;
    @Element(name = "email", required = false)
    private String email;
    @Element(name = "reason", required = false)
    private String reason;

    public String getComment() {
        return this.comment;
    }

    public String getReason() {
        return this.reason;
    }

    public String getEmail() {
        return this.email;
    }
}
