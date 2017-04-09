package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "stat", strict = false)
@Namespace(prefix = "stat", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Stat {
    public static final String AD_PAGE_VIEW = "AD_PAGE_VIEW";
    public static final String EMAIL_REPLIES = "EMAIL_REPLIES";
    public static final String LIST_VIEW = "LIST_VIEW";
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "adId", required = false)
    private long id;
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "type", required = false)
    private String type;
    @Namespace(prefix = "types", reference = "http://www.ebayclassifiedsgroup.com/schema/types/v1")
    @Element(name = "value", required = false)
    private String value;

    public long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
