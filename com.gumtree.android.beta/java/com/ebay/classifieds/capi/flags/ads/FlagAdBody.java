package com.ebay.classifieds.capi.flags.ads;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "flagAd", strict = false)
@Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
public class FlagAdBody {
    private static final String NAMESPACE_REFERENCE = "http://www.ebayclassifiedsgroup.com/schema/flag/v1";
    private static final String PREFIX = "flag";
    @Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
    @Element(name = "comment", required = false)
    private String comment;
    @Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
    @Element(name = "email", required = false)
    private String email;
    @Attribute(name = "locale")
    private String locale;
    @Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
    @Element(name = "reason")
    private String reason;
    @Attribute(name = "version")
    private String version;

    public FlagAdBody(String str, String str2, String str3, String str4, String str5) {
        this.locale = str;
        this.version = str2;
        this.reason = str3;
        this.comment = str4;
        this.email = str5;
    }

    public FlagAdBody(String str, String str2, String str3) {
        this(str, str2);
        this.email = str3;
    }

    public FlagAdBody(String str, String str2) {
        this.locale = "en_GB";
        this.version = "1.15";
        this.reason = str;
        this.comment = str2;
    }
}
