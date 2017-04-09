package com.ebay.classifieds.capi.ads;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "ad-status", strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Status {
    @Element(name = "value", required = false)
    private String value;

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
