package com.ebay.classifieds.capi.ads;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "link", strict = false)
@Namespace(prefix = "pic", reference = "http://www.ebayclassifiedsgroup.com/schema/picture/v1")
public class PictureLink implements Serializable {
    @Attribute(name = "href", required = false)
    private String href;
    @Attribute(name = "rel", required = false)
    private String rel;

    @ConstructorProperties({"rel", "href"})
    public PictureLink(String str, String str2) {
        this.rel = str;
        this.href = str2;
    }

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
