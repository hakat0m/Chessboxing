package com.ebay.classifieds.capi.ads;

import java.io.Serializable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "link", strict = false)
public class Link implements Serializable {
    @Attribute(name = "href", required = false)
    private String href;
    @Attribute(name = "rel", required = false)
    private String rel;

    public String getRel() {
        return this.rel;
    }

    public String getHref() {
        return this.href;
    }

    public void setRel(String str) {
        this.rel = str;
    }

    public void setHref(String str) {
        this.href = str;
    }
}
