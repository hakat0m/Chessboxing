package com.ebay.classifieds.capi.phone;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(strict = false)
@Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
public class Phone {
    @Namespace(prefix = "ad", reference = "http://www.ebayclassifiedsgroup.com/schema/ad/v1")
    @Element(name = "local")
    private String local;

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String str) {
        this.local = str;
    }
}
